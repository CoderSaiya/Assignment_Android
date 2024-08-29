<?php
$host = "localhost";
$user = "root";
$password = "NhatCuong04@";
$database = "MoneyManage";

// Tạo kết nối
$conn = new mysqli($host, $user, $password, $database);

// Kiểm tra kết nối
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
