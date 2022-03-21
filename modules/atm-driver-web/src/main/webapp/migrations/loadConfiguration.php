<?php
    /**************************************************************************/
	/* Usage : php loadConfiguration.php 0850
	/*
	/* Carga la Configuracion contenida en el archivo Configuration_NCR_EMV.txt 
	/* en las tablas de la Base de Datos (Screens, States, etc) 
	/*
    /**************************************************************************/
	require_once('../database/db_credentials.php');
    require_once('SimpleMapper.php');
    require_once('Screen.php');
    require_once('State.php');
    require_once('FIT.php');
    require_once('states_table.php');

	$config_id = '';

    function decimal2hex(string $decimal)
    {
		$remain = strlen($decimal) % 3;
		if ($remain != 0)
			echo "JFRD loadConfiguration.php " . __LINE__ . " ERROR ERROR  ERROR  ERROR\n";
		$outHexStr = '';
		for ($i = 0; $i < strlen($decimal); $i += 3)
		{
			$wrkStr = substr($decimal, $i, 3);
			$hexStr = dechex( intval($wrkStr) );
			if (strlen($hexStr) < 2) $hexStr = '0' . $hexStr;
			$outHexStr .= $hexStr;
		}
		return $outHexStr;
		
    }    

    function processFIT(string $line)
    {
		global $config_id;

	    $fit = new FIT();

        $fit->fit_config_id = $config_id;
        $fit->fit_number = substr($line, 5, 3);
		// substr($line, 8, 3); ?????????
        $fit->fit_bin_prefix       = decimal2hex( substr($line,  11, 15) );
        $fit->fit_desc = 'FIT-' . $fit->fit_bin_prefix;
        $fit->fit_indirectstateidx = decimal2hex( substr($line,  26,  3) );
        $fit->fit_algoidx          = decimal2hex( substr($line,  29,  3) );
        $fit->fit_langcodeidx      = decimal2hex( substr($line, 113,  3) );
        $fit->fit_maxpinlen        = decimal2hex( substr($line,  32,  3) );
        $fit->fit_localpinchecklen = decimal2hex( substr($line,  35,  3) );
        $fit->fit_pinpad           = decimal2hex( substr($line,  38,  3) );
        $fit->fit_pinretrycount    = decimal2hex( substr($line,  50,  3) );
        $fit->fit_pinoffsetidx     = decimal2hex( substr($line,  53,  3) );
        // $fit->fit_pinblkformat     =
        $fit->fit_panlocidx        = decimal2hex( substr($line,  41,  3) );
        $fit->fit_panlen           = decimal2hex( substr($line,  44,  3) );
        $fit->fit_panpad           = decimal2hex( substr($line,  47,  3) );
        $fit->fit_decimaltab       = decimal2hex( substr($line,  56, 24) );
        $fit->fit_encpinkey        = decimal2hex( substr($line,  80, 24) );
        $fit->fit_idxrefpoints     = decimal2hex( substr($line, 104, 9) );
	  
	  
		$fit->output();
		$fit->save();
	}

    function processScreen(string $line)
    {
		global $config_id;

	    $screen = new Screen();

        $screen->scr_config_id = $config_id;
        $screen->scr_number = substr($line, 5, 3);
        $screen->scr_desc = 'Sin Descripcion';

		$data = substr($line, 8);
		$data = str_replace('', '␌', $data);
		$data = str_replace('', '␏', $data);
		$data = str_replace('', '␎', $data);

        $screen->scr_data = $data;

		// $screen->output();
		$screen->save();
	}

    function processState(string $line)
    {
		global $config_id;
		global $states_table;

	    $state = new State();

        $state->std_config_id = $config_id;
        $state->std_number = substr($line, 5, 3);
        $state->std_type = $line[8];
        $state->std_desc = substr( $states_table[$state->std_type], 4);
        $state->std_1 = substr($line,  9, 3);
        $state->std_2 = substr($line, 12, 3);
        $state->std_3 = substr($line, 15, 3);
        $state->std_4 = substr($line, 18, 3);
        $state->std_5 = substr($line, 21, 3);
        $state->std_6 = substr($line, 24, 3);
        $state->std_7 = substr($line, 27, 3);
        $state->std_8 = substr($line, 30, 3);
		
		// $state->output();
		$state->save();
	}


    function processImageFile( string $parm_config_id )
    {
		global $config_id;

		$config_id = $parm_config_id;
		$fileName = 'Configuration_' . $config_id . '.txt';
		$returnString = '';
        $handle = fopen($fileName, "r");
        if ($handle) 
		{
            while (($line = fgets($handle)) !== false) 
			{
				if ($line[0] != '*')
				{
                    $line = substr($line, 0, strlen($line) - 2);
					$confType = $line[0];
					$TableNum = substr($line, 2, 2);
					
                    // echo $line;
					// echo " confType $confType TableNum $TableNum \n";

					switch ($confType)
					{
						case '3' : /* Screen, States, Fit, etc */
						    if ($TableNum == '11') processScreen($line);
						    if ($TableNum == '12') processState($line);
						    if ($TableNum == '15') processFIT($line);
						    break;
						case '8' : /* Configuracion EMV */
						    break;
						default : /* No debe ocurrir */
				            echo '******** ERROR * ERROR ******** ' . $line;
						    break;
					}
				}
            }
            fclose($handle);
        }
	}

    /*******************************/
	/* Programa Principal (Inicio) */
    /*******************************/
	SimpleMapper\SimpleMapper::$pdo = new PDO(DB_DSN, DB_USER, DB_PASS);

    if ($argc > 1)
	{
        for ($i = 1; $i < $argc; $i++)
		{
	        $str = processImageFile($argv[$i]);
		}
		goto end;
	}
	else
    {
		echo "Usage php loadConfiguration.php [Configuration_Number_1] .. [Configuration_Number_n] \n";
		echo "      php loadConfiguration.php 0850 \n";
	}		

    end:
        exit;	
    /*******************************/
    /*   Programa Principal (Fin)  */
    /*******************************/
?>	