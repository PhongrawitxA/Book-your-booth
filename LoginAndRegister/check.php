<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['event_id']) && isset($_POST['booth_id']) && isset($_POST['booth_status'])) {
    if ($db->dbConnect()) {
        if ($db->check($_POST['event_id'], $_POST['booth_id'], $_POST['booth_status'] )) {
            echo "Select Success";
        } else echo "Can't select booth that has gray color";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
