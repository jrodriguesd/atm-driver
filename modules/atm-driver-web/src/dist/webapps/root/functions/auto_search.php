<?php require_once('../database/db_credentials.php'); ?>
<?php
// check if the search term is present in the url
if(isset($_GET['term'])) {
// sanitize the input data and assign variables
$term = filter_var($_GET['term'], FILTER_SANITIZE_STRING);
// make connection to db
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASS, DB_NAME);
// check for db connection errors 
if (mysqli_connect_errno()) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
    exit();
} 
// define SQL Query
$sql = "SELECT item_name FROM inventory_items WHERE item_name LIKE '$term%'";
$result = mysqli_query($con, $sql);
// loop over result and assign data to array
while($row = mysqli_fetch_assoc($result)) { 
    $returned_array[] = $row["item_name"];
}
// return the array in JSON format
if(isset($returned_array)) {
    echo(json_encode($returned_array));
}
// close the connection
mysqli_close($con); 
}

?>