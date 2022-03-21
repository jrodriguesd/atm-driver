/*
 * Screens Table
 */
DROP TABLE IF EXISTS screens;
CREATE TABLE screens 
(
    scr_id        INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    scr_config_id VARCHAR(4)       NOT NULL,
    scr_number    VARCHAR(3)       NOT NULL,
    scr_desc      VARCHAR(250),
    scr_data      VARCHAR(250),
    PRIMARY KEY (scr_id),
    UNIQUE KEY (scr_config_id, scr_number)  
);
