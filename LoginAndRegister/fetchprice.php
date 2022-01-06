<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','LoginAndRegister');

 
$con = mysqli_connect(HOST,USER,PASS,DB);
if (isset($_GET['booth_id']) && isset($_GET['event_id'])) {
    $event_id = $_GET['event_id'];
    $booth_id = $_GET['booth_id'];
} else {
    $event_id = '';
    $booth_id = '';
}
$sql = "select * from Booth where event_id = '$event_id' and booth_id = '$booth_id'";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push(
    $result,array('price'=>$row[2])
    
);
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>