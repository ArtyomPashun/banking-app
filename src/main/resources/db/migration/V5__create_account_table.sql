CREATE TABLE account
(
    id SERIAL PRIMARY KEY,
    account_number BIGINT unique,
    current_limit NUMERIC(10,2),
    monthly_limit NUMERIC(10,2),
    limit_setting_date DATE
)