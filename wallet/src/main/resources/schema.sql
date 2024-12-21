CREATE TABLE IF NOT EXISTS wallet (
    wallet_number BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    document VARCHAR(14) NOT NULL,
    balance NUMERIC(18, 8) DEFAULT 0.00,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS transaction_history (
    transaction_history_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    transaction_type ENUM('DEPOSIT', 'WITHDRAW', 'TRANSFER') NOT NULL,
    transaction_value NUMERIC(18, 8) NOT NULL,
    transaction_currency VARCHAR(10) NOT NULL,
    document_origin VARCHAR(14) NOT NULL,
    document_destination VARCHAR(14) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by BIGINT DEFAULT NULL
);