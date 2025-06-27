INSERT INTO tb_plans (id, name, price, billing_cycle)
VALUES (
           '11111111-1111-1111-1111-111111111111',
           'Plano Mensal',
           29.99,
           'MENSAL'
       )
    ON CONFLICT (id) DO NOTHING;
