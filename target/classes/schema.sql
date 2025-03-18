CREATE TABLE USER_CREDIT (
    ID int not null AUTO_INCREMENT,
    payment_history_score FLOAT NOT NULL,
    internal_credit_score FLOAT NOT NULL,
    PRIMARY KEY (id));