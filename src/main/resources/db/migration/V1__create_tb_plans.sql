CREATE TYPE billing_cycle_enum AS ENUM ('MENSAL', 'ANUAL');

CREATE TABLE tb_plans (

    id UUID PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    price DECIMAL(6,2) NOT NULL,
    billing_cycle billing_cycle_enum NOT NULL
);

