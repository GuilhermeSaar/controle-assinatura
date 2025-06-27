

CREATE TABLE tb_plans (

    id UUID PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    price DECIMAL(6,2) NOT NULL,
    billing_cycle VARCHAR(20) NOT NULL
);

