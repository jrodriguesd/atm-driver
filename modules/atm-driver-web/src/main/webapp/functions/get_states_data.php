<?php 
	require_once('../database/db_credentials.php');
    require_once('../migrations/SimpleMapper.php');
    require_once('../migrations/State.php');
    require_once('../migrations/states_table.php');
	
	$config_id = '0850';


    $screens = 
    [
        '000' => [
		           'std_config_id' => '0850',
				   'std_number'    => '000',
                   'std_type'      => 'A',
                   'std_desc'      => 'Time Out',
                   'std_1'         => '010',
                   'std_2'         => '005',
                   'std_3'         => '013',
                   'std_4'         => '002',
                   'std_5'         => '002',
                   'std_6'         => '008',
                   'std_7'         => '001',
                   'std_8'         => '035',
                 ],
        '005' => [
		           'std_config_id' => '0850',
				   'std_number'    => '005',
                   'std_type'      => 'K',
                   'std_desc'      => 'FIT Switch State',
                   'std_1'         => '010',
                   'std_2'         => '015',
                   'std_3'         => '015',
                   'std_4'         => '015',
                   'std_5'         => '020',
                   'std_6'         => '013',
                   'std_7'         => '255',
                   'std_8'         => '255',
                 ],
        '010' => [
		           'std_config_id' => '0850',
				   'std_number'    => '010',
                   'std_type'      => '+',
                   'std_desc'      => 'Begin ICC Initialisation State',
                   'std_1'         => '025',
                   'std_2'         => '055',
                   'std_3'         => '001',
                   'std_4'         => '001',
                   'std_5'         => '000',
                   'std_6'         => '000',
                   'std_7'         => '000',
                   'std_8'         => '000',
                 ],
    ];

    /*******************************/
	/* Programa Principal (Inicio) */
    /*******************************/
	SimpleMapper\SimpleMapper::$pdo = new PDO(DB_DSN, DB_USER, DB_PASS);
    $query = State::where("std_config_id = :config_id", 
	                        array('config_id' => $config_id)
						   );

    $states = $query->fetchAll();
	// var_dump($states);

    // encode the array and print the data
    echo(json_encode($states));
    end:
        exit;	
    /*******************************/
    /*   Programa Principal (Fin)  */
    /*******************************/
?>