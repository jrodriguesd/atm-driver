mysql -u weg -plorena_01 wms_db < screens.sql
mysql -u weg -plorena_01 wms_db < states.sql
mysql -u weg -plorena_01 wms_db < fits.sql
php loadConfiguration.php 0850 > log.dat
php loadConfiguration.php 0870 > log.dat
