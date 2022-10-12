SELECT DISTINCT a.cart_id
FROM (
    SELECT *
    FROM cart_products
    WHERE name = 'Milk'
) a
    INNER JOIN (
        SELECT *
        FROM cart_products
        WHERE name = 'Yogurt'
    ) b
    ON a.cart_id = b.cart_id
