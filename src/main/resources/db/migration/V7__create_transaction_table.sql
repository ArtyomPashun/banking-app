CREATE TABLE transaction
(
    id SERIAL PRIMARY KEY,
    account_from BIGINT REFERENCES account(account_number),
    account_to BIGINT,
    currency VARCHAR(30),
    sum NUMERIC(10,2),
    expense_category_id INT REFERENCES expense_category(id),
    date DATE,
    is_limit_exceed BOOLEAN
)