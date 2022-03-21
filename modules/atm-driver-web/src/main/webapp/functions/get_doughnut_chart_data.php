<?php require_once('../database/db_credentials.php'); ?>

<?php 
// make connection to db
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASS, DB_NAME);                      
// check for db connection errors
if (mysqli_connect_errno()) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
    exit();
} 
// define an array to store the list of categories
$categories = array('clothing', 'household', 'stationery', 'electronics', 'sports');

// loop over array of categories
$count_array = array();
foreach ($categories as $category) {
    // define the SQL query to get all the inventory items for each category
    $sql = "SELECT * FROM inventory_items WHERE category = '$category'";
    if($result = mysqli_query($con, $sql)) {
        $rowcount=mysqli_num_rows($result);
        $count_array[$category] = $rowcount;
    }
}

// encode the array and print the data
echo(json_encode($count_array));

// close the db connection
mysqli_close($con); 
?>