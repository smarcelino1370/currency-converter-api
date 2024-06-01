CREATE TABLE conversion_transaction (
    id UUID PRIMARY KEY,
    user_id VARCHAR(64) NOT NULL,
    origin VARCHAR(3) NOT NULL,
    destination VARCHAR(3) NOT NULL,
    rate NUMERIC(16,6) NOT NULL,
    transaction_date DATE NOT NULL,
    amount NUMERIC(16,6) NOT NULL
);
