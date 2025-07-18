package com.codegym.repository;

import com.codegym.model.SanPham;
import com.codegym.model.LoaiSanPham;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class SanPhamRepository {
    private String jdbcURL = "jdbc:mysql://localhost:3306/quan_ly_dau_gia";
    private String jdbcUsername = "root";
    private String jdbcPassword = "582004"; // sửa lại nếu có mật khẩu

    private static final String SELECT_ALL =
            "SELECT sp.id, sp.name, sp.price, sp.status, sp.created_at, lsp.cid, lsp.name as loai_name " +
            "FROM san_pham sp JOIN loai_san_pham lsp ON sp.id_loai_sp = lsp.cid";

    public List<SanPham> findAll() {
        List<SanPham> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                BigDecimal price = rs.getBigDecimal("price");
                String status = rs.getString("status");
                Timestamp createdAt = rs.getTimestamp("created_at");
                int cid = rs.getInt("cid");
                String loaiName = rs.getString("loai_name");
                LoaiSanPham loai = new LoaiSanPham(cid, loaiName);
                SanPham sp = new SanPham(id, name, price, status, loai, createdAt);
                list.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addSanPham(String name, java.math.BigDecimal price, int loaiId) {
        String sql = "INSERT INTO san_pham (name, price, status, id_loai_sp) VALUES (?, ?, 'Đang chờ', ?)";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setBigDecimal(2, price);
            ps.setInt(3, loaiId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return false;
        StringBuilder sql = new StringBuilder("DELETE FROM san_pham WHERE id IN (");
        for (int i = 0; i < ids.size(); i++) {
            sql.append("?");
            if (i < ids.size() - 1) sql.append(",");
        }
        sql.append(")");
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < ids.size(); i++) {
                ps.setInt(i + 1, ids.get(i));
            }
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSanPham(int id, String name, java.math.BigDecimal price, int loaiId, String status) {
        String sql = "UPDATE san_pham SET name=?, price=?, id_loai_sp=?, status=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setBigDecimal(2, price);
            ps.setInt(3, loaiId);
            ps.setString(4, status);
            ps.setInt(5, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteById(int id) {
        String sql = "DELETE FROM san_pham WHERE id=?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SanPham> findByFilter(String keyword, Integer loaiId, java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice, int offset, int limit) {
        List<SanPham> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT sp.id, sp.name, sp.price, sp.status, sp.created_at, lsp.cid, lsp.name as loai_name FROM san_pham sp JOIN loai_san_pham lsp ON sp.id_loai_sp = lsp.cid WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND sp.name LIKE ?");
            params.add("%" + keyword + "%");
        }
        if (loaiId != null && loaiId > 0) {
            sql.append(" AND lsp.cid = ?");
            params.add(loaiId);
        }
        if (minPrice != null) {
            sql.append(" AND sp.price >= ?");
            params.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append(" AND sp.price <= ?");
            params.add(maxPrice);
        }
        sql.append(" ORDER BY sp.id DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                BigDecimal price = rs.getBigDecimal("price");
                String status = rs.getString("status");
                Timestamp createdAt = rs.getTimestamp("created_at");
                int cid = rs.getInt("cid");
                String loaiName = rs.getString("loai_name");
                LoaiSanPham loai = new LoaiSanPham(cid, loaiName);
                SanPham sp = new SanPham(id, name, price, status, loai, createdAt);
                list.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countByFilter(String keyword, Integer loaiId, java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM san_pham sp JOIN loai_san_pham lsp ON sp.id_loai_sp = lsp.cid WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND sp.name LIKE ?");
            params.add("%" + keyword + "%");
        }
        if (loaiId != null && loaiId > 0) {
            sql.append(" AND lsp.cid = ?");
            params.add(loaiId);
        }
        if (minPrice != null) {
            sql.append(" AND sp.price >= ?");
            params.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append(" AND sp.price <= ?");
            params.add(maxPrice);
        }
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
} 