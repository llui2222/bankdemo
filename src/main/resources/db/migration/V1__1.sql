CREATE TABLE IF NOT EXISTS accounts (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    account int NOT NULL,
    debit int NOT NULL DEFAULT 0
);

CREATE INDEX idx_account ON accounts (account);

INSERT INTO accounts (account,debit) VALUES (55555555,10);
INSERT INTO accounts (account,debit) VALUES (33333333,15);