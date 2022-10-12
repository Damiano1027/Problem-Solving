WITH RECURSIVE temp1 AS (
	SELECT 0 AS `hour`
    UNION ALL
    SELECT `hour` + 1
    FROM temp1
    WHERE `hour` < 23
)
SELECT temp1.`hour`, IFNULL(temp3.`count`, 0) AS `count`
FROM temp1
    LEFT JOIN (
        SELECT hour, COUNT(*) AS `count`
        FROM ( 
            SELECT HOUR(datetime) AS hour
            FROM animal_outs
        ) temp2
        GROUP BY hour 
    ) temp3
    ON temp1.`hour` = temp3.`hour`
