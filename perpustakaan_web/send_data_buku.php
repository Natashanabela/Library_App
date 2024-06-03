<?php
include 'koneksi.php';

$id_buku = $_POST['id_buku'];
$judul = $_POST['judul'];
$pengarang = $_POST['pengarang'];
$penerbit = $_POST['penerbit'];
$id_kategori = $_POST['id_kategori'];
$tahun_terbit = $_POST['tahun_terbit'];
$jumlah_halaman = $_POST['jumlah_halaman'];
$status_buku = $_POST['status_buku'];
$action = $_POST['action'];
$resId = $_POST['resId'];
$image = $_POST['image'];

switch ($action) {
    case "add":
        // Cek apakah kategori ada di tabel kategori
        $cekkategoriQuery = "SELECT COUNT(*) as kategoriCount FROM kategori WHERE id_kategori = '$id_kategori'";
        $kategoriResult = mysqli_query($conn, $cekkategoriQuery);

        if ($kategoriResult) {
            $kategoriCount = mysqli_fetch_assoc($kategoriResult)['kategoriCount'];

            if ($kategoriCount > 0) {
                // Jika kategori ada, maka lanjut add data
                date_default_timezone_set('Asia/Jakarta');
                $path = 'img/' . date('d-m-Y-His') . '-' . rand(100, 10000) . '.jpg';

                $query = "INSERT INTO buku (image, id_buku, judul, pengarang, penerbit, id_kategori, tahun_terbit, jumlah_halaman, status_buku) VALUES ('" . $path . "','" . $id_buku . "','" . $judul . "','" . $pengarang . "','" . $penerbit . "','" . $id_kategori . "','" . $tahun_terbit . "','" . $jumlah_halaman . "','" . $status_buku . "')";
                $result = mysqli_query($conn, $query);

                if ($result == 1) {
                    file_put_contents($path, base64_decode($image));
                    $response['error_text'] = "Data berhasil dimasukkan";
                } else {
                    $response['error_text'] = "Data gagal dimasukkan";
                }
            } else {
                $response["error_text"] = "Tidak ada kategori dengan id_kategori " . $id_kategori;
            }
        } else {
            $response["error_text"] = "Error cek kategori";
        }

        echo json_encode($response);
        mysqli_close($conn);
        break;

    case "edit":
        // Cek apakah kategori ada di tabel kategori
        $cekkategoriQuery = "SELECT COUNT(*) as kategoriCount FROM kategori WHERE id_kategori = '$id_kategori'";
        $kategoriResult = mysqli_query($conn, $cekkategoriQuery);

        if ($kategoriResult) {
            $kategoriCount = mysqli_fetch_assoc($kategoriResult)['kategoriCount'];

            if ($kategoriCount > 0) {
                // Jika kategori ada, maka lanjut edit data
                if ($resId == 1) {
                    date_default_timezone_set('Asia/Jakarta');
                    $path = 'img/' . date('d-m-Y-His') . '-' . rand(100, 10000) . '.jpg';
                    $data = mysqli_query($conn, "SELECT * FROM buku WHERE id_buku='" . $id_buku . "'");
                    $d = mysqli_fetch_array($data);
                    unlink($d['image']);
                    $query = "UPDATE buku SET image = '" . $path . "', judul = '" . $judul . "', pengarang = '" . $pengarang . "', penerbit = '" . $penerbit . "', id_kategori = '" . $id_kategori . "', tahun_terbit = '" . $tahun_terbit . "', jumlah_halaman = '" . $jumlah_halaman . "', status_buku = '" . $status_buku . "' WHERE id_buku = '" . $id_buku . "'";
                    $result = mysqli_query($conn, $query);

                    if ($result == 1) {
                        file_put_contents($path, base64_decode($image));
                        $response["error_text"] = "Success";
                    } else {
                        $response["error_text"] = "Fail";
                    }
                } else {
                    $query = "UPDATE buku SET judul = '" . $judul . "', pengarang = '" . $pengarang . "', penerbit = '" . $penerbit . "', id_kategori = '" . $id_kategori . "', tahun_terbit = '" . $tahun_terbit . "', jumlah_halaman = '" . $jumlah_halaman . "', status_buku = '" . $status_buku . "' WHERE id_buku = '" . $id_buku . "'";
                    $result = mysqli_query($conn, $query);

                    if ($result == 1) {
                        $response["error_text"] = "Success";
                    } else {
                        $response["error_text"] = "Fail";
                    }
                }
            } else {
                $response["error_text"] = "Tidak ada kategori dengan id_kategori " . $id_kategori;
            }
        } else {
            $response["error_text"] = "Error cek kategori";
        }

        echo json_encode($response);
        mysqli_close($conn);
        break;

    case "delete":
        $data = mysqli_query($conn, "SELECT * FROM buku WHERE id_buku='" . $id_buku . "'");
        $d = mysqli_fetch_array($data);
        unlink($d['image']);
        $query = "DELETE FROM buku WHERE (id_buku = '" . $id_buku . "')";
        $result = mysqli_query($conn, $query);
        if ($result == 1) {
            $response["error_text"] = "Success";
        } else {
            $response["error_text"] = "Fail";
        }
        echo json_encode($response);
        mysqli_close($conn);
        break;
}
?>