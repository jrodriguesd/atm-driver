<?php
    // https://github.com/kenny1har/simple-php-activerecord
    require_once('SimpleMapper.php');

    class State extends SimpleMapper\SimpleMapper 
	{
        public static $table = 'states';
        public static $pk = 'std_id';
        public $std_config_id;
        public $std_number;
        public $std_type;
        public $std_desc;
        public $std_1;
        public $std_2;
        public $std_3;
        public $std_4;
        public $std_5;
        public $std_6;
        public $std_7;
        public $std_8;
	
        public function output()
		{
            echo "State: std_config_id " . $this->std_config_id . " std_number " . $this->std_number . " std_type " . $this->std_type . " std_desc>" . $this->std_desc . "<std_1 " . $this->std_1 . " std_2 " . $this->std_2 . "<br />\n<br />\n";
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
    State::initialize();

?>
