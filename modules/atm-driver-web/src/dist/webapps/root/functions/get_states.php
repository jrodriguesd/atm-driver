<?php
    $screens = yaml_parse_file('states.yaml');

    // encode the array and print the data
    echo(json_encode($screens));
	
	// echo yaml_emit($screens);
?>
