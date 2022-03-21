<?php require_once('../database/db_credentials.php'); ?>
<?php
    // check if the form was submitted
    if(isset($_GET['addItemSubmitButton'])){
      // check if the user filled out all the fields
      if(isset($_GET['item_upc']) && isset($_GET['item_name']) && isset($_GET['item_desc']) && isset($_GET['item_category']) && isset($_GET['item_quantity'])) {
        // sanitize the input data and assign variables
        $item_upc = filter_var($_GET['item_upc'], FILTER_SANITIZE_STRING);
        $item_name = filter_var($_GET['item_name'], FILTER_SANITIZE_STRING);
        $item_desc = filter_var($_GET['item_desc'], FILTER_SANITIZE_STRING);
        $item_category = filter_var($_GET['item_category'], FILTER_SANITIZE_STRING);
        $item_quantity = filter_var($_GET['item_quantity'], FILTER_SANITIZE_STRING);
        // make connection to db
        $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASS, DB_NAME);
        // check for db connection errors 
        if (mysqli_connect_errno()) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
            exit();
        } 
        // define SQL query
        $sql = "INSERT INTO inventory_items (upc, item_name, item_desc, category, quantity) VALUES ('$item_upc', '$item_name', '$item_desc', '$item_category', '$item_quantity')";
        $new_url = '../manage_inventory.php?add_success=true';
        // if the record was successfully added
        if (mysqli_query($con, $sql)) { header('Location: '.$new_url);

       } else {
          // if the record failed to be added
           $new_url = '../manage_inventory.php?add_success=false'; 
           header('Location: '.$new_url);
        }
        // close the db connection
        mysqli_close($con); 
        }

      }
?>
