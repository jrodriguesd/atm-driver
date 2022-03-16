<?php
    /**************************************************************************/
	/* Usage : php fitutil.php fit_data
	/*
	/* Convierte un registro de fit de hex a decimal data
	/*
    /**************************************************************************/

    function displayfit(string $data)
	{
		echo "            Institution ID - PFIID " . substr($data, 0, 10). "\n";
		echo " Indirect next state index - PSTDX " . substr($data, 10, 2). "\n";
		echo "  Algorithm/ Bank ID index - PAGDX " . substr($data, 12, 2). "\n";
		echo "Maximum PIN digits entered - PMXPN " . substr($data, 14, 2). "\n";
		echo "Maximum PIN digits checked - PCKLN " . substr($data, 16, 2). "\n";
		echo "                   PIN pad - PINPD " . substr($data, 18, 2). "\n";
	}


    function processfit(string $data)
	{
		$remain = strlen($data) % 2;
		if ($remain != 0)
			echo "JFRD fitutil.php " . __LINE__ . " ERROR ERROR  ERROR  ERROR\n";

        $outDecStr = '000';
		for ($i = 0; $i < strlen($data); $i += 2)
		{
			$wrkStr = substr($data, $i, 2);
			$decStr = "". hexdec( $wrkStr );
			if (strlen($decStr) < 3) $decStr = '0' . $decStr;
			$outDecStr .= $decStr;
		}
		echo $outDecStr;
		
	}

    /*******************************/
	/* Programa Principal (Inicio) */
    /*******************************/

    if ($argc > 1)
	{
	    $str = displayfit( substr($argv[1], 3) );
	    // $str = processfit( substr($argv[1], 3) );
	}
	else
    {
		echo "Usage php fitutil.php [fit_data] \n";
	}		

    end:
        exit;	
    /*******************************/
    /*   Programa Principal (Fin)  */
    /*******************************/
?>	