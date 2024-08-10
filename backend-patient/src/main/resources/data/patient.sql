CREATE TABLE IF NOT EXISTS patient
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    birth_date DATE        NOT NULL,
    gender     VARCHAR(10) NOT NULL,
    address    VARCHAR(255),
    phone      VARCHAR(20)
);

INSERT INTO patient (last_name, first_name, birth_date, gender, address, phone)
VALUES ('TestNone', 'test', '1966-12-31', 'F', '1 Brookside St', '100-222-3333'),
       ('TestBorderline', 'test', '1945-06-24', 'M', '2 High St', '200-333-4444'),
       ('TestInDanger', 'test', '2004-06-18', 'M', '3 Club Road', '300-444-5555'),
       ('TestEarlyOnset', 'test', '2002-06-28', 'F', '4 Valley Dr', '400-555-6666');