/*
 * FIT's Table
 */
DROP TABLE IF EXISTS fits;
CREATE TABLE fits 
(
    fit_id               INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    fit_config_id        VARCHAR(4)       NOT NULL,
    fit_number           VARCHAR(3)       NOT NULL,
    fit_bin_prefix       VARCHAR(10)      NOT NULL,
    fit_desc             VARCHAR(250)     NOT NULL,
    fit_indirectstateidx VARCHAR(2)       NOT NULL,
    fit_algoidx          VARCHAR(2)       NOT NULL,
    fit_langcodeidx      VARCHAR(2)       NOT NULL,
    fit_maxpinlen        VARCHAR(2)       NOT NULL,
    fit_localpinchecklen VARCHAR(2)       NOT NULL,
    fit_pinpad           VARCHAR(2)       NOT NULL,
    fit_pinretrycount    VARCHAR(2)       NOT NULL,
    fit_pinoffsetidx     VARCHAR(2)       NOT NULL,
    fit_pinblkformat     VARCHAR(2),
    fit_panlocidx        VARCHAR(2)       NOT NULL,
    fit_panlen           VARCHAR(2)       NOT NULL,
    fit_panpad           VARCHAR(2)       NOT NULL,
    fit_decimaltab       VARCHAR(16)      NOT NULL,
    fit_encpinkey        VARCHAR(16)      NOT NULL,
    fit_idxrefpoints     VARCHAR(6)       NOT NULL,
    PRIMARY KEY (fit_id),
    UNIQUE KEY (fit_config_id, fit_number)  
);
