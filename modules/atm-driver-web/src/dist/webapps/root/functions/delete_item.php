<?php require_once('../database/db_credentials.php'); ?>
<?php 
    // check if the delete id is present in the url
    if(isset($_GET['delete_id'])){
         // sanitize the input data and assign variables
        $id = filter_var($_GET['delete_id'], FILTER_SANITIZE_STRING) ;
        // make connection to db
        $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASS, DB_NAME);
        // check for db connection errors 
        if (mysqli_connect_errno()) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
            exit();
        }
        // define SQL query
        $sql = "DELETE FROM inventory_items WHERE id = $id";
        $new_url = '../manage_inventory.php?delete_success=true';
        // if the record was successfully deleted
        if (mysqli_query($con, $sql)) { header('Location: '.$new_url);

      } else {
          // if the record failed to be deleted
           $new_url = '../manage_inventory.php?delete_success=false'; 
           header('Location: '.$new_url);
          
        }
        // close the connection
        mysqli_close($con); 
        }
?>
