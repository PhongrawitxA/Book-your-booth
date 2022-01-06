<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','LoginAndRegister');

 
$con = mysqli_connect(HOST,USER,PASS,DB);
if (isset($_GET['username'])) {
    $username = $_GET['username'];
} else {
    $username = '';
}
$sql = "select * from Users where username = '$username'";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push(
    $result,array('email'=>$row[3])
);
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>