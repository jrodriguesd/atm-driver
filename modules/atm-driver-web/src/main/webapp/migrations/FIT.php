<?php
    // https://github.com/kenny1har/simple-php-activerecord
    require_once('SimpleMapper.php');

    class FIT extends SimpleMapper\SimpleMapper 
	{
        public static $table = 'fits';
        public static $pk = 'fit_id';
        public $fit_config_id;
        public $fit_number;
        public $fit_bin_prefix;
        public $fit_desc;
        public $fit_indirectstateidx;
        public $fit_algoidx;
        public $fit_langcodeidx;
        public $fit_maxpinlen;
        public $fit_localpinchecklen;
        public $fit_pinpad;
        public $fit_pinretrycount;
        public $fit_pinoffsetidx;
        public $fit_pinblkformat;
        public $fit_panlocidx;
        public $fit_panlen;
        public $fit_panpad;
        public $fit_decimaltab;
        public $fit_encpinkey;
        public $fit_idxrefpoints;
	
        public function output()
		{
            echo "State: fit_config_id " . $this->fit_config_id . " fit_number " . $this->fit_number . " fit_bin_prefix " . $this->fit_bin_prefix . " fit_desc>" . $this->fit_desc . "<br />\n<br />\n";
        }

        // public static function getByTicker(string $ticker) 
	    // {
        //     $query = Company::where("sc_ticker = :q_ticker", 
	    // 	                        array('q_ticker' => $ticker)
	    // 						   );
	    // 
        //     if ( $val = $query->fetch() ) 
	    // 		return $val;
	    // 	else
	    // 	    return NULL;
	    // }

    }
    FIT::initialize();

?>
