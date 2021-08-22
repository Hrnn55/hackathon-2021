<?php
$host = 'requestdb.cm9huvgic56n.us-east-1.rds.amazonaws.com';
$user = 'admin';
$pass = 'yy123456';
$db = 'requestdb';
$conn = new mysqli($host, $user, $pass, $db) or die($conn->connect_error);
