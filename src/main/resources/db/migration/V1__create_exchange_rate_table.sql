CREATE TABLE exchange_rate
(
    id              SERIAL PRIMARY KEY,
    currency_code VARCHAR(30),
    rate         NUMERIC(10,5),
    date         DATE
)