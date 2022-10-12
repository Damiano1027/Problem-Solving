SELECT category, price, product_name
FROM food_product
WHERE price IN (
    SELECT MAX(price)
    FROM food_product
    GROUP BY category
    HAVING 
        category = '과자'
        OR category = '국'
        OR category = '김치'
        OR category = '식용유'
) AND ( category = '과자'
        OR category = '국'
        OR category = '김치'
        OR category = '식용유'
      )
ORDER BY price DESC
