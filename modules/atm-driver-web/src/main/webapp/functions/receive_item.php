<?php require_once('../database/db_credentials.php'); ?>
<?php
    // cehck if the form was submitted  
    if(isset($_GET['receiveItemSubmitButton'])){
      // check if the user filled out all the fields
      if(isset($_GET['item_id']) && isset($_GET['item_category']) && isset($_GET['item_quantity']) && isset($_GET['quantity_received'])) {
        // sanitize the input data and assign variables
        $item_id = filter_var($_GET['item_id'], FILTER_SANITIZE_STRING);
        $item_category = filter_var($_GET['item_category'], FILTER_SANITIZE_STRING);
        $item_quantity = filter_var($_GET['item_quantity'], FILTER_SANITIZE_STRING);
        $quantity_received = filter_var($_GET['quantity_received'], FILTER_SANITIZE_STRING);
        // make connection to db 
        $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASS, DB_NAME);
        // check for db connection errors                      // 
        if (mysqli_connect_errno()) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
            exit();
        }

        // define the SQL query to check if the category already exist
        $sql_check = "SELECT * FROM distribute_receive WHERE category='$item_category'";

        if ($result = mysqli_query($con, $sql_check) ) {
          // if the query executes successfully, get the row count
          $rowcount=mysqli_num_rows($result);
          // check if a category exist by counting the rows returned from the db
          if($rowcount) {
            // if the category exist in the database update the distributed quantity
            $sql_update_receive_quantity = "UPDATE distribute_receive SET receive_total=receive_total+'$quantity_received' WHERE category='$item_category'";
            mysqli_query($con, $sql_update_receive_quantity);
          }
          else {
            // if the category does not exist in the database then insert the row
            $sql_insert_row = "INSERT INTO distribute_receive (category, distribute_total, receive_total) VALUES ('$item_category', '0', '$quantity_received')";
            mysqli_query($con, $sql_insert_row);
          }
          
        }
        // adjust (subtract the received amount) the quantity from the inventory_items table
        $sql_update_quantity = "UPDATE inventory_items SET quantity=quantity+'$quantity_received' WHERE id=$item_id";

        // if the record was successfully updated
        if (mysqli_query($con, $sql_update_quantity) ) { 
            $new_url = '../manage_inventory.php?receive_success=true';
            header('Location: '.$new_url);

       } else {
          //  if the record failed to be added
           $new_url = '../manage_inventory.php?receive_success=false'; 
           header('Location: '.$new_url);

        }
        // close the db connection
        mysqli_close($con); 
        }

      }
?>
