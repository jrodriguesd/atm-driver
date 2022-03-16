<?php require_once('../database/db_credentials.php'); ?>

<?php
// make connection to db
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASS, DB_NAME);                      
// check for db connection errors
if (mysqli_connect_errno()) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
    exit();
}
// define an array to store the data
$data = array();
    // define the SQL query to select category, distribute_total, receive_total from the distribute_receive table
    $sql = "SELECT category, distribute_total, receive_total FROM distribute_receive";
    if($result = mysqli_query($con, $sql)) {
        foreach($result as $row) {
            $data[] = $row;
        }
    }
// encode the array and print the data
print(json_encode($data));

// close the db connection
mysqli_close($con); 
?>