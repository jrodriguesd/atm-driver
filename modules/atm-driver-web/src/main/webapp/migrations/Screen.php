<?php
    // https://github.com/kenny1har/simple-php-activerecord
    require_once('SimpleMapper.php');

    class Screen extends SimpleMapper\SimpleMapper 
	{
        public static $table = 'screens';
        public static $pk = 'scr_id';
        public $scr_config_id;
        public $scr_number;
        public $scr_desc;
        public $scr_data;
	
        public function output()
		{
            echo "Screen: scr_config_id " . $this->scr_config_id . " scr_number " . $this->scr_number . " scr_desc>" . $this->scr_desc . " scr_data " . $this->scr_data .  "<br />\n<br />\n";
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
    Screen::initialize();

?>
