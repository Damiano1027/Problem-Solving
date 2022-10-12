SELECT ai.animal_id, ai.animal_type, ai.name
FROM animal_ins ai
INNER JOIN animal_outs ao
ON ai.animal_id = ao.animal_id
WHERE 
    ai.sex_upon_intake LIKE 'Intact %' 
    AND ai.sex_upon_intake != ao.sex_upon_outcome
