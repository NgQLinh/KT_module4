-- 0. XÓA BẢNG THEO THỨ TỰ
DROP TABLE IF EXISTS san_pham;
DROP TABLE IF EXISTS loai_san_pham;

-- 1. TẠO DATABASE
CREATE DATABASE IF NOT EXISTS quan_ly_dau_gia;
USE quan_ly_dau_gia;

-- 2. BẢNG LOẠI SẢN PHẨM
CREATE TABLE IF NOT EXISTS loai_san_pham (
    cid INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- 3. BẢNG SẢN PHẨM
CREATE TABLE IF NOT EXISTS san_pham (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(15, 2) NOT NULL CHECK (price >= 100000),
    status VARCHAR(50) NOT NULL DEFAULT 'Đang chờ',
    id_loai_sp INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_loai_sp) REFERENCES loai_san_pham(cid)
);

-- 4. TRIGGER BEFORE INSERT (dùng AUTO_INCREMENT để tạo name chứa id)
DELIMITER $$

CREATE TRIGGER trg_set_name_before_insert
BEFORE INSERT ON san_pham
FOR EACH ROW
BEGIN
    DECLARE next_id INT;

    SELECT AUTO_INCREMENT
    INTO next_id
    FROM INFORMATION_SCHEMA.TABLES
    WHERE TABLE_NAME = 'san_pham'
      AND TABLE_SCHEMA = DATABASE();

    SET NEW.name = CONCAT(next_id, ' - ', NEW.name);
END$$

DELIMITER ;

-- 5. DỮ LIỆU MẪU VÀO loai_san_pham
INSERT INTO loai_san_pham (name) VALUES
('Điện tử'),
('Nội thất'),
('Thời trang'),
('Nghệ thuật');

-- 6. THÊM SẢN PHẨM → Trigger sẽ tự thêm id vào đầu name
INSERT INTO san_pham (name, price, status, id_loai_sp) VALUES
('iPhone 15 Pro Max', 25000000, 'Đang đấu giá', 1),
('Sofa da cao cấp', 15000000, 'Sắp bắt đầu', 2),
('Tranh sơn dầu', 5000000, 'Đang đấu giá', 4),
('Áo khoác nam', 1200000, 'Đã kết thúc', 3);

-- 7. XEM KẾT QUẢ
SELECT * FROM san_pham;
