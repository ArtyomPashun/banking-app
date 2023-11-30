CREATE TABLE transaction
(
    id SERIAL PRIMARY KEY,
    account_from BIGINT REFERENCES account(id),
    account_to BIGINT,
    currency VARCHAR(30),
    sum NUMERIC(10,2),
    expense_category_id INT REFERENCES expense_category(id),
    date timestamp with time zone,
    is_limit_exceed BOOLEAN
)