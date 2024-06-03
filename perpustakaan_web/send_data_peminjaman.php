<?php
    include 'koneksi.php';

    $id_peminjaman          = $_POST['id_peminjaman'];
    $id_member              = $_POST['id_member'];
    $id_buku                = $_POST['id_buku'];
    $tanggal_peminjaman     = $_POST['tanggal_peminjaman'];
    $tanggal_pengembalian  = $_POST['tanggal_pengembalian'];
    $action                 = $_POST['action'];

    switch($action){
        case "add":
            // Cek apakah member ada di tabel member
            $cekmemberQuery = "SELECT COUNT(*) as memberCount FROM member WHERE id_member = '".$id_member."'";
            $memberResult = mysqli_query($conn, $cekmemberQuery);

            if ($memberResult) {
                $memberCount = mysqli_fetch_assoc($memberResult)['memberCount'];

                if ($memberCount > 0) {
                    // Ketika member sudah ada, cek apakah buku ada di tabel buku
                    $cekbukuQuery = "SELECT COUNT(*) as bukuCount FROM buku WHERE id_buku = '".$id_buku."'";
                    $bukuResult = mysqli_query($conn, $cekbukuQuery);

                    if ($bukuResult) {
                        $bukuCount = mysqli_fetch_assoc($bukuResult)['bukuCount'];

                        if ($bukuCount > 0) {
                            // Ketika buku sudah ada, cek apakah statusnya "Ready"
                            $cekstatusQuery = "SELECT status_buku FROM buku WHERE id_buku = '".$id_buku."'";
                            $statusResult = mysqli_query($conn, $cekstatusQuery);

                            if ($statusResult) {
                                $status = mysqli_fetch_assoc($statusResult)['status_buku'];

                                if ($status == 'Ready') {
                                    // Lanjut ke proses peminjaman
                                    $query = "INSERT INTO peminjaman (id_member, id_buku, tanggal_peminjaman, tanggal_pengembalian) VALUES ('".$id_member."', '".$id_buku."', '".$tanggal_peminjaman."', '".$tanggal_pengembalian."')";
                                    $result = mysqli_query($conn, $query);

                                    if ($result == 1) {
                                        $response["error_text"] = "Sukses Meminjam Buku";
                                    } else {
                                        $response["error_text"] = "Gagal Meminjam Buku";
                                    }
                                } else {
                                    $response["error_text"] = "Buku Tidak Ready";
                                }
                            } else {
                                $response["error_text"] = "Error saat cek status buku";
                            }
                        } else {
                            $response["error_text"] = "Tidak ada buku dengan id_buku " . $id_buku;
                        }
                    } else {
                        $response["error_text"] = "Error saat cek ketersediaan buku";
                    }
                } else {
                    $response["error_text"] = "Tidak ada member dengan id_member " . $id_member;
                }
            } else {
                $response["error_text"] = "Error saat cek member";
            }

            echo json_encode($response);
            mysqli_close($conn);
            break;

        case "edit":

            // Cek apakah member ada di tabel member
            $cekmemberQuery = "SELECT COUNT(*) as memberCount FROM member WHERE id_member = '".$id_member."'";
            $memberResult = mysqli_query($conn, $cekmemberQuery);

            if ($memberResult) {
                $memberCount = mysqli_fetch_assoc($memberResult)['memberCount'];

                if ($memberCount > 0) {
                    // Ketika member sudah ada, cek apakah buku ada di tabel buku
                    $cekbukuQuery = "SELECT COUNT(*) as bukuCount FROM buku WHERE id_buku = '".$id_buku."'";
                    $bukuResult = mysqli_query($conn, $cekbukuQuery);

                    if ($bukuResult) {
                        $bukuCount = mysqli_fetch_assoc($bukuResult)['bukuCount'];

                        if ($bukuCount > 0) {
                            // Ketika buku sudah ada, cek apakah statusnya "Ready"
                            $cekstatusQuery = "SELECT status_buku FROM buku WHERE id_buku = '".$id_buku."'";
                            $statusResult = mysqli_query($conn, $cekstatusQuery);

                            if ($statusResult) {
                                $status = mysqli_fetch_assoc($statusResult)['status_buku'];

                                if ($status == 'Ready') {
                                    // Lanjut ke proses peminjaman
                                    $query = "UPDATE peminjaman SET id_member = '".$id_member."', id_buku = '".$id_buku."', tanggal_peminjaman = '".$tanggal_peminjaman."', tanggal_pengembalian = '".$tanggal_pengembalian."' WHERE id_member = '".$id_member."'";
                                    $result = mysqli_query($conn, $query);

                                    if ($result == 1) {
                                        $response["error_text"] = "Sukses Meminjam Buku";
                                    } else {
                                        $response["error_text"] = "Gagal Meminjam Buku";
                                    }
                                } else {
                                    $response["error_text"] = "Buku Tidak Ready";
                                }
                            } else {
                                $response["error_text"] = "Error saat cek status buku";
                            }
                        } else {
                            $response["error_text"] = "Tidak ada buku dengan id_buku " . $id_buku;
                        }
                    } else {
                        $response["error_text"] = "Error saat cek ketersediaan buku";
                    }
                } else {
                    $response["error_text"] = "Tidak ada member dengan id_member " . $id_member;
                }
            } else {
                $response["error_text"] = "Error saat cek member";
            }

            echo json_encode($response);
            mysqli_close($conn);
            break;
            


        case "delete":
            $query = "DELETE FROM peminjaman WHERE (id_peminjaman = '" . $id_peminjaman . "')";
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