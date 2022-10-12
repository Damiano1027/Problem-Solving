SELECT result.product_id, result.product_name, SUM(result.total_sales) AS total_sales
FROM (
 SELECT fp.product_id, fp.product_name, fp.price * fo.amount AS total_sales
 FROM food_product fp
    INNER JOIN food_order fo
        ON fp.product_id = fo.product_id
 WHERE fo.produce_date LIKE '2022-05-%'
) result
GROUP BY product_id
ORDER BY total_sales DESC
