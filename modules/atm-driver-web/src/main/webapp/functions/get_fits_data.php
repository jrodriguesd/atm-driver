<?php 
	require_once('../database/db_credentials.php');
    require_once('../migrations/SimpleMapper.php');
    require_once('../migrations/FIT.php');
	
	$config_id = '0850';

    $fits = 
    [
        '000' => [
		           'fit_config_id'        => '0850',
				   'fit_number'           => '001',
                   'fit_bin_prefix'       => '542449FFFF',
                   'fit_desc'             => 'FIT Description MC',
                   'fit_indirectstateidx' => '01',
                   'fit_algorithmidx'     => '00',
                   'fit_langcodeidx'      => '00',
                   'fit_maxpinlength'     => '00',
                   'fit_localpinchecklen' => '00',
                   'fit_pinpadchar'       => 'F',
                   'fit_pinretrycount'    => '00',
                   'fit_pinoffsetidx'     => '00',
                   'fit_pinblkformat'     => '00',
                   'fit_panlocidx'        => '00',
                   'fit_panlength'        => '00',
                   'fit_panpadchar'       => '00',
                   'fit_decimaltab'       => '0000000000000000',
                   'fit_encpinkey'        => '0000000000000000',
                   'fit_idxrefpoints'     => '000000',
                 ],
  ];

    /*******************************/
	/* Programa Principal (Inicio) */
    /*******************************/
	SimpleMapper\SimpleMapper::$pdo = new PDO(DB_DSN, DB_USER, DB_PASS);
    $query = FIT::where("fit_config_id = :config_id", 
	                        array('config_id' => $config_id)
						   );
	
    $fitsIn = $query->fetchAll();
	// var_dump($fits);
    $fitsOut = [];
	foreach ($fitsIn as $kr => $vr)
	{
		$vrOut = [];
        foreach ($vr as $k => $v)
        {
		    switch ($k)
		    {
		    	case 'fit_indirectstateidx' :
		    	case 'fit_maxpinlen' :
		    	case 'fit_localpinchecklen' :
		    	case 'fit_pinpad' :
		    	case 'fit_panlen' :
		    	case 'fit_panpad' :
				    $vrOut[$k.'-0'] = $v[0];
				    $vrOut[$k.'-1'] = $v[1];
		    	    break;
		    	case 'fit_idxrefpoints' :
				    $vrOut[$k.'-1'] = $v[0];
				    $vrOut[$k.'-2'] = $v[1];
				    $vrOut[$k.'-3'] = $v[2];
				    $vrOut[$k.'-4'] = $v[3];
				    $vrOut[$k.'-5'] = $v[4];
				    $vrOut[$k.'-6'] = $v[5];
		    	    break;
				default:
			        $vrOut[$k] = $v;
				    break;
		    }
		}
		$fitsOut[$kr] = $vrOut;
	}

    // encode the array and print the data
    echo(json_encode($fitsOut));
    end:
        exit;	
    /*******************************/
    /*   Programa Principal (Fin)  */
    /*******************************/
?>