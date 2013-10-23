<?php

$connect = mysqli_connect('localhost', 'root', 'cs2107', 'cs4274');

if (mysqli_connect_errno($connect)) {
    echo "Failes to connect to MySQL: " . mysqli_connect_error();
}

$latitude = $_POST['latitude'];
$longitude = $_POST['longitude'];
$altitude = $_POST['altitude'];
$name = $_POST['name'];
$info = $_POST['info'];
$cat = $_POST['category'];

$query = "insert into places (latitude, longitude, altitude, name, category, info) 
	values ($latitude, $longitude, $altitude, '$name', '$cat', '$info')";
$result = mysqli_query($connect, $query);

if (!$result && $x != '') {
    die ("Error: " . mysqli_error());
}

mysqli_close($connect);
?>  


<form method="post">
<label for="latitude">Latitude:</label>
<input type="text" id="latitude" name="latitude" required/>
<br/>
<label for="longitude">Longitude:</label>
<input type="text" id="longitude" name="longitude" required/>
<br/>
<label for="altitude">Altitude:</label>
<input type="text" id="altitude" name="altitude" />
<br/>
<label for="name">name:</label>
<input type="text" id="name" name="name" required/>
<br/>
<label for="category">Category:</label>
<textarea id="category" name="category"></textarea>
<br/>
<label for="info">info:</label>
<textarea id="info" name="info"></textarea>
<br/>

<input type="submit" value="Submit"/>
</form>