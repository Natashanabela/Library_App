<?php
    include 'koneksi.php';

    $id_kategori         = $_POST['id_kategori'];
    $nama_kategori       = $_POST['nama_kategori'];

    $action     = $_POST['action'];

    switch($action){
        case "add":
            $query = "INSERT INTO kategori (id_kategori, nama_kategori) VALUES ('".$id_kategori."','".$nama_kategori."')";
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
            $query = "UPDATE kategori SET nama_kategori = '".$nama_kategori."' WHERE id_kategori = '".$id_kategori."'";
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
            $query = "DELETE FROM kategori WHERE (id_kategori = '".$id_kategori."')";
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