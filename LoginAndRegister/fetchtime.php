<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','LoginAndRegister');

 
$con = mysqli_connect(HOST,USER,PASS,DB);
if (isset($_GET['username']) && isset($_GET['event_id']) && isset($_GET['booth_id'])) {
    $username = $_GET['username'];
    $event_id = $_GET['event_id'];
    $booth_id = $_GET['booth_id'];
} else {
    $username = '';
    $event_id = '';
    $booth_id = '';
}
$sql = "select * from Booking where username = '$username' and event_id = '$event_id' and booth_id = '$booth_id'";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,array('booking_time'=>$row[4]));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>