<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['username']) && isset($_POST['event_id']) && isset($_POST['booth_id'])) {
    if ($db->dbConnect()) {
        if ($db->delete($_POST['username'],$_POST['event_id'],$_POST['booth_id'])) {
            echo "Delete Success";
        } else echo "Delete Fail";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>