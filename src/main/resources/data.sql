INSERT INTO person
    VALUES ('test',1.1), ('test2',1.2), ('test3',1.3)
    ON DUPLICATE KEY UPDATE name = VALUES(name);