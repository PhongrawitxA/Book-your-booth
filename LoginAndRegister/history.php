<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','LoginAndRegister');

 
$con = mysqli_connect(HOST,USER,PASS,DB);
$Name = $_POST['username'];

$sql = "select * from Booking natural join Event natural join Zone where username = '$Name' order by booking_time";
 
$res = mysqli_query($con,$sql);

 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,array('event_name'=>$row[6],'zone_name'=>$row[8],'booth_id'=>$row[3],'booking_time'=>$row[4]));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>