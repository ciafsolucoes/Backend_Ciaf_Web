#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SQL_FILE="${SQL_FILE:-$SCRIPT_DIR/create_dbs.sql}"
MYSQL_HOST="${MYSQL_HOST:-localhost}"
MYSQL_PORT="${MYSQL_PORT:-3306}"
MYSQL_USER="${MYSQL_USER:-root}"
MYSQL_BASE_DB="${MYSQL_BASE_DB:-ciaf_id}"
TEMPLATE_DB="${TEMPLATE_DB:-emitente_1}"
TEMPLATE_EMITENTE_ID="${TEMPLATE_EMITENTE_ID:-1}"

if ! command -v mysql >/dev/null 2>&1; then
    echo "Erro: cliente mysql nao encontrado no PATH." >&2
    exit 1
fi

if [[ ! -f "$SQL_FILE" ]]; then
    echo "Erro: arquivo SQL nao encontrado: $SQL_FILE" >&2
    exit 1
fi

usage() {
    cat <<EOF
Uso:
  $0
  $0 emitente_N
  $0 --range INICIO FIM

Exemplos:
  $0 emitente_3
  $0 --range 3 13
EOF
}

validate_emitente_name() {
    local db_name="$1"

    if [[ ! "$db_name" =~ ^emitente_([0-9]+)$ ]]; then
        echo "Erro: nome de banco invalido: $db_name. Use o formato emitente_N." >&2
        exit 1
    fi
}

render_sql_for_db() {
    local db_name="$1"
    local emitente_id="$2"
    local output_file="$3"

    sed         -e "s/${TEMPLATE_DB}/${db_name}/g"         -e "s/@emitente_id := ${TEMPLATE_EMITENTE_ID}/@emitente_id := ${emitente_id}/g"         "$SQL_FILE" > "$output_file"
}

db_exists() {
    local db_name="$1"
    mysql "${mysql_args[@]}"         -e "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '$db_name';"
}

run_single_db() {
    local db_name="$1"
    local emitente_id="$2"
    local temp_sql
    local existing_db

    existing_db="$(db_exists "$db_name")"
    if [[ -n "$existing_db" ]]; then
        echo "Banco $db_name ja existe. Pulando."
        skipped_count=$((skipped_count + 1))
        return 0
    fi

    temp_sql="$(mktemp)"
    temp_files+=("$temp_sql")

    render_sql_for_db "$db_name" "$emitente_id" "$temp_sql"

    echo "Banco $db_name nao existe. Executando SQL gerado..."
    mysql         --host="$MYSQL_HOST"         --port="$MYSQL_PORT"         --user="$MYSQL_USER"         "$MYSQL_BASE_DB" < "$temp_sql"

    echo "Banco $db_name criado com sucesso."
    created_count=$((created_count + 1))
}

target_dbs=()

case "${1:-}" in
    "")
        target_dbs+=("$TEMPLATE_DB")
        ;;
    --range)
        if [[ $# -ne 3 ]]; then
            usage >&2
            exit 1
        fi

        range_start="$2"
        range_end="$3"

        if [[ ! "$range_start" =~ ^[0-9]+$ || ! "$range_end" =~ ^[0-9]+$ ]]; then
            echo "Erro: INICIO e FIM devem ser inteiros nao negativos." >&2
            exit 1
        fi

        if (( range_start > range_end )); then
            echo "Erro: INICIO nao pode ser maior que FIM." >&2
            exit 1
        fi

        for ((i = range_start; i <= range_end; i++)); do
            target_dbs+=("emitente_${i}")
        done
        ;;
    -*)
        usage >&2
        exit 1
        ;;
    *)
        if [[ $# -ne 1 ]]; then
            usage >&2
            exit 1
        fi
        target_dbs+=("$1")
        ;;
esac

mysql_args=(
    --host="$MYSQL_HOST"
    --port="$MYSQL_PORT"
    --user="$MYSQL_USER"
    --batch
    --skip-column-names
)

created_count=0
skipped_count=0
temp_files=()

cleanup() {
    local file
    for file in "${temp_files[@]}"; do
        [[ -f "$file" ]] && rm -f "$file"
    done
}

trap cleanup EXIT

for target_db in "${target_dbs[@]}"; do
    validate_emitente_name "$target_db"
    emitente_id="${target_db##*_}"
    run_single_db "$target_db" "$emitente_id"
done

echo "Resumo: criados=$created_count, pulados=$skipped_count, total=$((created_count + skipped_count))."
