#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SQL_FILE="${SQL_FILE:-$SCRIPT_DIR/create_dbs.sql}"
MYSQL_HOST="${MYSQL_HOST:-localhost}"
MYSQL_PORT="${MYSQL_PORT:-3306}"
MYSQL_USER="${MYSQL_USER:-root}"
MYSQL_BASE_DB="${MYSQL_BASE_DB:-ciaf_id}"

if ! command -v mysql >/dev/null 2>&1; then
    echo "Erro: cliente mysql nao encontrado no PATH." >&2
    exit 1
fi

if [[ ! -f "$SQL_FILE" ]]; then
    echo "Erro: arquivo SQL nao encontrado: $SQL_FILE" >&2
    exit 1
fi

template_db="$(awk '/CREATE DATABASE `/ { gsub(/`/, "", $3); print $3; exit }' "$SQL_FILE")"
template_emitente_id="$(awk '/SET @emitente_id :=/ { gsub(/[^0-9]/, "", $4); print $4; exit }' "$SQL_FILE")"

if [[ -z "$template_db" || -z "$template_emitente_id" ]]; then
    echo "Erro: nao foi possivel identificar o banco modelo e o emitente_id no arquivo $SQL_FILE." >&2
    exit 1
fi

db_prefix="${template_db%_*}"

if [[ "$db_prefix" == "$template_db" || -z "$db_prefix" ]]; then
    echo "Erro: o nome do banco modelo precisa seguir o padrao prefixo_numero. Ex.: emitente_1." >&2
    exit 1
fi

mysql_args=(
    --host="$MYSQL_HOST"
    --port="$MYSQL_PORT"
    --user="$MYSQL_USER"
    --batch
    --skip-column-names
)

last_db="$(
    mysql "${mysql_args[@]}" \
        -e "SELECT SCHEMA_NAME
              FROM INFORMATION_SCHEMA.SCHEMATA
             WHERE SCHEMA_NAME REGEXP '^${db_prefix}_[0-9]+$'
             ORDER BY CAST(SUBSTRING_INDEX(SCHEMA_NAME, '_', -1) AS UNSIGNED) DESC
             LIMIT 1;"
)"

if [[ -z "$last_db" ]]; then
    next_number=1
else
    last_number="${last_db##*_}"
    next_number=$((last_number + 1))
fi

next_db="${db_prefix}_${next_number}"
tmp_sql="$(mktemp /tmp/create_dbs_${next_db}_XXXX.sql)"
trap 'rm -f "$tmp_sql"' EXIT

perl -0pe '
    my ($old_db, $new_db, $new_id) = @ARGV;
    s/\Q$old_db\E/$new_db/g;
    s/SET \@emitente_id := \d+;/SET \@emitente_id := $new_id;/g;
' "$template_db" "$next_db" "$next_number" "$SQL_FILE" > "$tmp_sql"

echo "Ultimo banco encontrado: ${last_db:-nenhum}"
echo "Novo banco: $next_db"
echo "Novo emitente_id: $next_number"

mysql \
    --host="$MYSQL_HOST" \
    --port="$MYSQL_PORT" \
    --user="$MYSQL_USER" \
    "$MYSQL_BASE_DB" < "$tmp_sql"

echo "Script executado com sucesso para o banco $next_db."
