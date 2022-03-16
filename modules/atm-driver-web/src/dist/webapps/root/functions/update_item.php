<?php require_once('./database/db_credentials.php'); ?>
<?php
    // check if the form was submitted
    if(isset($_GET['updateItemSubmitButton'])){
      // check if the user filled out sll the fields
      if(isset($_GET['item_id']) && isset($_GET['item_upc']) && isset($_GET['item_name']) && isset($_GET['item_desc']) && isset($_GET['item_category']) && isset($_GET['item_quantity'])) {
        // sanitize the input data and assign variables
        $item_id = filter_var($_GET['item_id'], FILTER_SANITIZE_STRING);
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

        // define the SQL query to update the item in the inventory_items table
        $sql = "UPDATE inventory_items SET upc='$item_upc', item_name='$item_name', item_desc='$item_desc', category='$item_category', quantity='$item_quantity' WHERE id=$item_id";
        
        // if the record was successfully updated
        if (mysqli_query($con, $sql)) { 
            $new_url = 'manage_inventory.php?update_success=true';
            header('Location: '.$new_url);

       } else {
          // if the record failed to be updated
           $new_url = 'manage_inventory.php?update_success=false'; 
           header('Location: '.$new_url);

        }
        // close the db connection
        mysqli_close($con); 
        }

      }
?>
