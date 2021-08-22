<?php
require 'db.php';
session_start();

header("Access-Control-Allow-Origin:*");
header("Access-Control-Allow-Methods: GET,POST,PUT,OPTIONS");
header("Access-Control-Allow-Headers:*");

// try {
    $type=$_POST['type'];
    $address=$_POST['address'];

    $sql = "INSERT INTO requestdb.hrnnhq_requests VALUE (DEFAULT, " . $type . ", '" . $address . "')";
    if ($conn->query($sql) === TRUE) {
        echo "New record created successfully";
      } else {
        echo "Error: " . $sql . "<br>" . $conn->error;
      }

    $id = "";

    echo "test";
    
    $sql = "SELECT requestId FROM requestdb.hrnnhq_requests WHERE parameter = '" . $address ."'";
    if($result = $conn->query($sql) === TRUE)
    {
      echo "success";
    } else {
      echo "fail";
    }
    while ($row = mysql_fetch_assoc($result)) {
      $id = $row['requestId'];
    }

    echo "test2";

    $sql = "SELECT response FROM requestdb.hrnnhq_requests WHERE requestId = '" . $id ."'";
    $response = "";
    while (true) {
      sleep(1.5);
      $result = $conn->query($sql);
      if(!(!$result)) {
        while ($row = mysql_fetch_assoc($result)) {
          $response = $row['response'];
        }

        break;
      }
    }

    echo "test3";
    
    $conn->close();

    return $response;

//     if($result->num_rows > 0) {
//         while ($row = $result->fetch_assoc()){
//             echo $row['requestId'] . ' ' . $row['requestType'] . ' ' . $row[parameter] . '<br>';
//         }
//     } else {
//         echo "There are no data!";
//     }

    echo $id . ' has been added to the collection.';
//     $conn->close();
// }
// catch(Exception $e) {
//     echo $e->getMessage();
// }
?>