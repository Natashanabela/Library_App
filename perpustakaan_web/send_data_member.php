<?php
    include 'koneksi.php';

    $id_member         = $_POST['id_member'];
    $nama_member       = $_POST['nama_member'];
    $jenis_kelamin      = $_POST['jenis_kelamin'];
    $nomor_telepon      = $_POST['nomor_telepon'];
    $action     = $_POST['action'];

    switch($action){
        case "add":
            $query = "INSERT INTO member (id_member, nama_member, jenis_kelamin, nomor_telepon) VALUES ('".$id_member."','".$nama_member."','".$jenis_kelamin."','".$nomor_telepon."')";
            $result = mysqli_query($conn, $query);
            if($result == 1){
                $response["error_text"]="Success";
            }
            else{
                $response["error_text"]="Fail";
            }
            echo json_encode($response);
            mysqli_close($conn);
            break;

        case "edit":
            $query = "UPDATE member SET nama_member = '".$nama_member."', jenis_kelamin = '".$jenis_kelamin."', nomor_telepon = '".$nomor_telepon."' WHERE id_member = '".$id_member."'";
            $result = mysqli_query($conn, $query);
            if($result == 1){
                $response["error_text"]="Success";
            }
            else{
                $response["error_text"]="Fail";
            }
            echo json_encode($response);
            mysqli_close($conn);
            break;

        case "delete":
            $query = "DELETE FROM member WHERE (id_member = '".$id_member."')";
            $result = mysqli_query($conn, $query);
            if($result == 1){
                $response["error_text"]="Success";
            }
            else{
                $response["error_text"]="Fail";
            }
            echo json_encode($response);
            mysqli_close($conn);
            break;
    }
?>