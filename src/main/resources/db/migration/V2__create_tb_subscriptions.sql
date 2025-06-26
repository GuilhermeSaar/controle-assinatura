
CREATE TYPE subscription_status_enum AS ENUM ('PENDENTE', 'ATIVA', 'SUSPENSA', 'CANCELADA');

CREATE TABLE tb_subscriptions (

    id UUID PRIMARY KEY,
    plan_id UUID NOT NULL,
    customer_email VARCHAR(50) NOT NULL,
    status subscription_status_enum NOT NULL,
    next_billing_date DATE NOT NULL,

    FOREIGN KEY (plan_id) REFERENCES tb_plans(id)
);