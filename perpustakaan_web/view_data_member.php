<?php
    include 'koneksi.php';

    $result = mysqli_query($conn, "SELECT * FROM member");
    if(mysqli_num_rows($result) > 0){
        $items  = array();
        while($row = mysqli_fetch_object($result)){
            array_push($items, $row);
        }

        $response['data'] = $items;
    }
    else{
        $response['error_text'] = "No member found";
    }

    echo json_encode($response);
?>