/*
 * States Table
 */
DROP TABLE IF EXISTS states;
CREATE TABLE states 
(
    std_id        INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    std_config_id VARCHAR(4)       NOT NULL,
    std_number    VARCHAR(3)       NOT NULL,
    std_type      CHAR             NOT NULL,
    std_desc      VARCHAR(250),
    std_1         VARCHAR(3)       NOT NULL,
    std_2         VARCHAR(3)       NOT NULL,
    std_3         VARCHAR(3)       NOT NULL,
    std_4         VARCHAR(3)       NOT NULL,
    std_5         VARCHAR(3)       NOT NULL,
    std_6         VARCHAR(3)       NOT NULL,
    std_7         VARCHAR(3)       NOT NULL,
    std_8         VARCHAR(3)       NOT NULL,
    PRIMARY KEY (std_id),
    UNIQUE KEY (std_config_id, std_number)  
);
