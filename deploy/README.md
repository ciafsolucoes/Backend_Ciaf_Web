# Deploy do Ciaf Web na VPS

Estrutura esperada na VPS (as duas pastas lado a lado):

    ~/ciaf/id_api_mutitenant   (backend + compose)
    ~/ciaf/front_one           (frontend)

1. DNS: aponte `WEB_DOMAIN` e `API_DOMAIN` (registro A) para o IP da VPS.
2. A VPS usa o nginx do host (80/443): copie deploy/nginx/ciafweb.conf para /etc/nginx/conf.d/, troque SEU_SITE e SUA_API, `nginx -t && systemctl reload nginx` e depois `certbot --nginx -d SEU_SITE -d SUA_API --redirect`. O front sai em 127.0.0.1:13000 e o backend em 127.0.0.1:18081.
3. Na pasta id_api_mutitenant: `cp deploy/.env.example .env` e edite (API_DOMAIN, JWT_SIGNING_KEY, MAIL_PASSWORD).
4. `docker compose -f docker-compose.prod.yml up -d --build`
5. Acompanhar: `docker compose -f docker-compose.prod.yml logs -f backend`
   (o backend leva ~30s; o Caddy emite o HTTPS sozinho).
6. Atualizar: `git pull` nas duas pastas e repetir o passo 4.
7. Backup: `docker exec ciaf-web-mysql-db mysqldump -uroot -pgr007879 --all-databases > backup.sql`

## Deploy automático pelo GitHub (igual ao ciaf-inter)

O workflow `.github/workflows/deploy-vps.yml` roda a cada push no branch `docker`,
a cada tag `v*` ou manualmente (Actions → Deploy VPS → Run workflow).

Preparo único na VPS:

    mkdir -p /opt/ciaf-web && cd /opt/ciaf-web
    git clone https://github.com/ciafsolucoes/id_api_mutitenant.git
    git clone https://github.com/ciafsolucoes/front_one.git
    cd id_api_mutitenant && cp deploy/.env.example .env && nano .env
    docker compose -f docker-compose.prod.yml up -d --build   # primeira subida

(repositório privado: use deploy key ou token no `git clone`, para o `git fetch` do deploy funcionar sem senha.)

No GitHub (repo id_api_mutitenant → Settings → Secrets and variables → Actions):
`VPS_HOST`, `VPS_USER`, `VPS_SSH_KEY` (mesmos valores do ciaf-inter se for a mesma VPS).
Variáveis opcionais: `VPS_PASTA`, `FRONT_PASTA`, `FRONT_BRANCH`.

Publicar: `git push origin main:docker` ou `git tag -a v0.1.0 -m "..." && git push origin v0.1.0`.

## Migrar o MySQL que já roda na VPS (sem perder dados)

Na VPS, dentro de `id_api_mutitenant`, com o `.env` preenchido:

    bash deploy/migrar-mysql.sh

Ele pede a senha root do MySQL antigo, faz backup em `~/backup-mysql-antigo-*.sql`, sobe o Ciaf Web,
restaura os dados e compara a quantidade de tabelas. Não altera o MySQL antigo.
