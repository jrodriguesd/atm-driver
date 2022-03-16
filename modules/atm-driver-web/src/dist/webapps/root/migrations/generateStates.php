<?php 
    /**************************************************************************/
	/* Usage : php generateStates.php
	/*
	/* Genera la tabla inicial de estados. Esta tabla generada necesita se 
	/* corregida y completada
	/*
    /**************************************************************************/
    require_once('states_table.php');

    function generateState(string $key, string $val)
	{
        echo "        '$key' => [" . PHP_EOL;
        echo "                   'sta_name' => [" . PHP_EOL;
		echo "                                     'title'  => '$val'," . PHP_EOL;
		echo "                                 ]," . PHP_EOL;
        echo "                   'sta_desc' => [" . PHP_EOL;
		echo "                                     'title'  => '$val'," . PHP_EOL;
		echo "                                 ]," . PHP_EOL;
		echo "                   'sta_1'    => [" . PHP_EOL;
		echo "                                     'title'  => 'Screen Number'," . PHP_EOL;
		echo "                                 ]," . PHP_EOL;
		echo "                   'sta_2'    => [" . PHP_EOL;
		echo "                                     'title'  => 'Good Read Next State'," . PHP_EOL;
		echo "                                 ]," . PHP_EOL;
		echo "                   'sta_3'    => [" . PHP_EOL;
		echo "                                     'title'  => 'Error Screen Number'," . PHP_EOL;
		echo "                                 ]," . PHP_EOL;
		echo "                   'sta_4'    => [" . PHP_EOL;
		echo "                                     'title'  => 'Read Condition 1'," . PHP_EOL;
		echo "                                 ]," . PHP_EOL;
		echo "                   'sta_5'    => [" . PHP_EOL;
		echo "                                     'title'  => 'Read Condition 2'," . PHP_EOL;
		echo "                                 ]," . PHP_EOL;
		echo "                   'sta_6'    => [" . PHP_EOL;
		echo "                                     'title'  => 'Read Condition 3'," . PHP_EOL;
		echo "                                 ]," . PHP_EOL;
		echo "                   'sta_7'    => [" . PHP_EOL;
		echo "                                     'title'  => 'Card Return Falg'," . PHP_EOL;
		echo "                                 ]," . PHP_EOL;
		echo "                   'sta_8'    => [" . PHP_EOL;
		echo "                                     'title'  => 'No FIT Match Next State'," . PHP_EOL;
		echo "                                 ]," . PHP_EOL;
        echo "               ]," .PHP_EOL;
	}

    /*******************************/
	/* Programa Principal (Inicio) */
    /*******************************/

    echo '<?php' .PHP_EOL;
    echo '    $screens =' .PHP_EOL; 
    echo '    [' .PHP_EOL;


	foreach ($states_table as $k => $v)
	    generateState($k, $v);
    echo '    ];' . PHP_EOL;
	echo PHP_EOL;
	echo '    // encode the array and print the data' . PHP_EOL;
	echo '    echo(json_encode($screens));' . PHP_EOL;
	echo '?>' . PHP_EOL;

    end:
        exit;	

    /*******************************/
    /*   Programa Principal (Fin)  */
    /*******************************/
?>	