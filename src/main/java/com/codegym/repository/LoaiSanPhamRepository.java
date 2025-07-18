package com.codegym.repository;

import com.codegym.model.LoaiSanPham;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoaiSanPhamRepository {
    private String jdbcURL = "jdbc:mysql://localhost:3306/quan_ly_dau_gia";
    private String jdbcUsername = "root";
    private String jdbcPassword = "582004"; // sửa lại nếu có mật khẩu

    private static final String SELECT_ALL = "SELECT cid, name FROM loai_san_pham";

    public List<LoaiSanPham> findAll() {
        List<LoaiSanPham> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("cid");
                String name = rs.getString("name");
                list.add(new LoaiSanPham(cid, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
} 