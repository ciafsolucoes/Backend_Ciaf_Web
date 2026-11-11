#!/usr/bin/env bash
# Copia TODOS os bancos do MySQL antigo (container mysql_docker-db-1) para o MySQL do Ciaf Web.
# NAO apaga nem altera nada no MySQL antigo: ele continua como backup.
# Rode na VPS, dentro da pasta id_api_mutitenant:   bash deploy/migrar-mysql.sh
set -euo pipefail

ANTIGO="${ANTIGO:-mysql_docker-db-1}"
NOVO="ciaf-web-mysql-db"
SENHA_NOVO="gr007879"
COMPOSE="docker compose -f docker-compose.prod.yml"
BACKUP="$HOME/backup-mysql-antigo-$(date +%Y%m%d-%H%M%S).sql"

[ -f docker-compose.prod.yml ] || { echo "Rode dentro da pasta id_api_mutitenant."; exit 1; }
[ -f .env ] || { echo "Falta o arquivo .env (copie de deploy/.env.example e preencha)."; exit 1; }
docker inspect "$ANTIGO" >/dev/null 2>&1 || { echo "Container $ANTIGO nao encontrado. Veja o nome com: docker ps"; exit 1; }

read -r -s -p "Senha do usuario root do MySQL ANTIGO: " SENHA_ANTIGO; echo
antigo() { docker exec -e MYSQL_PWD="$SENHA_ANTIGO" "$ANTIGO" "$@"; }
antigo mysql -uroot -e "select 1" >/dev/null || { echo "Senha incorreta."; exit 1; }

echo "==> Bancos encontrados no MySQL antigo:"
BANCOS=$(antigo mysql -uroot -N -e "select schema_name from information_schema.schemata where schema_name not in ('mysql','sys','information_schema','performance_schema')")
echo "$BANCOS" | sed 's/^/    - /'
[ -n "$BANCOS" ] || { echo "Nenhum banco de usuario encontrado."; exit 1; }

echo "==> 1/5 Fazendo backup em $BACKUP"
antigo mysqldump -uroot --single-transaction --routines --triggers --events --databases $BANCOS > "$BACKUP"
[ -s "$BACKUP" ] || { echo "Backup vazio, abortando."; exit 1; }
cp "$BACKUP" "$BACKUP.copia"
echo "    Backup ok ($(du -h "$BACKUP" | cut -f1)). Guarde tambem uma copia fora da VPS."

echo "==> 2/5 Subindo o Ciaf Web (pode demorar alguns minutos no primeiro build)"
$COMPOSE up -d --build
for i in $(seq 1 60); do
  [ "$(docker inspect -f '{{.State.Health.Status}}' "$NOVO" 2>/dev/null)" = "healthy" ] && break
  sleep 5
done

echo "==> 3/5 Parando so o backend novo para restaurar com seguranca"
$COMPOSE stop backend

echo "==> 4/5 Restaurando os dados no MySQL novo"
docker exec -i -e MYSQL_PWD="$SENHA_NOVO" "$NOVO" mysql -uroot < "$BACKUP"

echo "==> 5/5 Conferindo (tabelas por banco: antigo x novo)"
CONSULTA="select table_schema, count(*) from information_schema.tables where table_schema not in ('mysql','sys','information_schema','performance_schema') group by table_schema order by 1"
antigo mysql -uroot -N -e "$CONSULTA" > /tmp/antigo.txt
docker exec -e MYSQL_PWD="$SENHA_NOVO" "$NOVO" mysql -uroot -N -e "$CONSULTA" > /tmp/novo.txt
if diff -q /tmp/antigo.txt /tmp/novo.txt >/dev/null; then
  echo "    OK: mesmos bancos e mesma quantidade de tabelas."
else
  echo "    ATENCAO: diferencas (antigo < > novo):"; diff /tmp/antigo.txt /tmp/novo.txt || true
fi

$COMPOSE start backend
echo
echo "Pronto. O MySQL antigo continua intacto. Teste o login no site;"
echo "so depois de alguns dias funcionando desligue o mysql_docker antigo."
