# Virtual Store Frontend API Documentation

This document summarizes the backend endpoints needed for a virtual store frontend based on this codebase.

## 1) Environment

- Base URL (local): `http://localhost:8081`
- Content type: `application/json`
- Auth type: JWT Bearer token

## 2) Authentication

### `POST /auth/signin` (public)
Authenticate user and get token.

Request:
```json
{
  "username": "user_or_email",
  "password": "your_password"
}
```

Response `200`:
```json
{
  "username": "john",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "roles": "ROLE_USER",
  "emitente_id": 1
}
```

### `GET /auth/name` (public in security config)
Returns username from token.

### `POST /auth/forgot-password` (public)
Request:
```json
{
  "email": "user@email.com"
}
```

### `POST /auth/reset-password` (public)
Use query param `token` and plain string body for new password.
Example: `POST /auth/reset-password?token=...`

### `POST /auth/test-token` (public)
Checks token validity/expiration logic.

## 3) Authorization Header

Most endpoints require token (except public endpoints configured in `SecurityConfig`).

Use:
`Authorization: Bearer <token>`

Important:
- In the current backend implementation, token parsing also appears in code paths that remove `"Bearer"` without trimming.
- If you get unauthorized with the normal header, test `Authorization: Bearer<token>` (without space) and align with backend team.

## 4) Core Store Endpoints

## 4.1 Products (Catalog)

### `GET /products/emitente/{emitenteId}`
List products for a store (`emitente`).

### `GET /products/{id}`
Get product by id.

### `GET /products/name/{name}/{emitenteId}`
Search products by name.

### `GET /products/cean/{cean}/{emitente_id}`
Find product by barcode.

### `GET /products/stock/{id}`
Get stock quantity of a product.

### `POST /products`
Create product.

### `PUT /products/{id}`
Update product.

### `DELETE /products/{id}`
Delete product.

Products entity has many fiscal fields, but common storefront fields are:
- `id`
- `emitente_id` (required)
- `description`
- `salevalue`
- `stock`
- `cean`
- `cprod`
- `brand`

## 4.2 Customers (Persons)

### `POST /persons`
Create customer.

### `GET /persons/{id}`
Get customer by id.

### `GET /persons/name/{corporate}`
Find customer by name.

### `GET /persons/cpfcnpf/{cpf_cnpj}`
Find customer by CPF/CNPJ.

### `PUT /persons/{id}`
Update customer.

Customer object includes:
- `emitente_id` (required)
- `tipo`
- `name`
- `cpf_cnpj`
- `email`
- `phonenumber`
- `cellnumber`
- `address`
- `number`
- `district`
- `city`
- `state`
- `cep`

## 4.3 Sales (Orders)

### `POST /vendas`
Create sale/order.

### `GET /vendas/{id}`
Get sale by id.

### `GET /vendas/last/{emitente_id}`
Get last sale id for store.

### `GET /vendas/last-number/{emitente_id}`
Get last sale number.

### `GET /vendas/day/{emitente_id}`
Daily sales.

### `POST /vendas/venda-completa/{emitente_id}`
Main checkout endpoint (sale + receivables + cashbox updates).

Request body (`VendaCompletaDto`):
```json
{
  "venda": {
    "emitente_id": 1,
    "nr_venda": 1203,
    "id_client": 45,
    "client": "Maria",
    "client_email": "maria@email.com",
    "vendedor": "Loja Online",
    "data": "2026-02-19 10:30",
    "valor": 199.9,
    "venda_salva": true,
    "pdf": false,
    "itens": [
      {
        "product_id": 10,
        "prod_XProd": "Produto A",
        "prod_QCom": 2,
        "prod_VUnCom": 99.95,
        "totalPrice": 199.9
      }
    ]
  },
  "billsToReceive": [
    {
      "emitente_id": 1,
      "nr_venda": 1203,
      "devedor": "Maria",
      "type": "PIX",
      "description": "Venda online",
      "value": "199.90",
      "final_value": "199.90",
      "final_value_real": 199.9,
      "efetive": true
    }
  ],
  "caixaDiario": {
    "emitente_id": 1,
    "opened": true,
    "nr_venda": 1203,
    "valor": 199.9,
    "documento": "VENDA",
    "emitente": "Loja Virtual"
  }
}
```

Response `200`: string message (`"Venda Salva com Sucesso"` or error message text).

## 4.4 Sale Items

### `POST /vendasitems`
Create item.

### `PUT /vendasitems/{id}`
Update item.

### `DELETE /vendasitems/{id}`
Delete item.

## 4.5 Payments

### `POST /pagamentos`
Create single payment.

### `POST /pagamentos/pgs`
Create multiple payments.

### `GET /pagamentos/number/{id}`
Get payments by `venda_id`.

### `DELETE /pagamentos/number/{venda_id}`
Delete payments by sale id.

## 4.6 NFC-e (Consumer Invoice)

If the store flow issues NFC-e:

- `GET /nfce/emitente/{emitente_id}`
- `GET /nfce/last/{emitente_id}`
- `GET /nfce/number/of-emitente/{emitente_id}` (next/last invoice number helper)
- `POST /nfce` (create invoice)
- `PUT /nfce/{id}` (update invoice)
- `DELETE /nfce/{id}` (delete invoice)

## 5) Images/Files

Endpoints under `/files/**` are public in security config.

- `POST /files/{name}` with multipart `file`: upload logo/image linked to username.
- `GET /files/downloadFile/{fileName}`: download file.
- `GET /files/download/{fileName}`: download from csv dir.

## 6) Public vs Protected Summary

Public (no token) includes:
- `/auth/signin`
- `/auth/forgot-password`
- `/auth/reset-password`
- `/auth/name`
- `/auth/test-token`
- `/files/**`
- `/photos/**`
- `GET /emitente` and `POST /emitente` (by security config list)

Everything else defaults to authenticated.

## 7) Recommended Frontend Flow

1. Login at `/auth/signin`, store `token` and `emitente_id`.
2. Load catalog via `/products/emitente/{emitente_id}`.
3. Optional customer register/search via `/persons`.
4. Checkout using `/vendas/venda-completa/{emitente_id}`.
5. Persist payment details via `/pagamentos` or `/pagamentos/pgs` when needed.
6. Issue NFC-e via `/nfce` endpoints if fiscal flow is enabled.

## 8) Integration Notes

- Most entities inherit `emitente_id` and this field is required.
- Several responses are plain strings instead of structured JSON (handle both).
- Error bodies are not standardized; frontend should parse HTTP code first.
- Unauthorized returns HTTP `401` with message `"Unauthorized"`.
