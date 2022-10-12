SELECT mp.member_name, rr.review_text, DATE_FORMAT(rr.review_date, '%Y-%m-%d')
FROM member_profile mp
INNER JOIN rest_review rr
ON mp.member_id = rr.member_id
WHERE mp.member_id IN (
    SELECT member_id
    FROM (
        SELECT member_id, COUNT(*) AS rest_count
        FROM rest_review
        GROUP BY member_id
    ) a
    WHERE rest_count = (
        SELECT MAX(rest_count)
        FROM (
            SELECT member_id, COUNT(*) AS rest_count
            FROM rest_review
            GROUP BY member_id
        ) b
    )
)
ORDER BY rr.review_date ASC
