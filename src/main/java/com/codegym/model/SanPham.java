package com.codegym.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SanPham {
    private int id;
    private String name;
    private BigDecimal price;
    private String status;
    private LoaiSanPham loaiSanPham;
    private Timestamp createdAt;

    public SanPham() {}

    public SanPham(int id, String name, BigDecimal price, String status, LoaiSanPham loaiSanPham, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.loaiSanPham = loaiSanPham;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoaiSanPham getLoaiSanPham() {
        return loaiSanPham;
    }

    public void setLoaiSanPham(LoaiSanPham loaiSanPham) {
        this.loaiSanPham = loaiSanPham;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "SanPham{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", loaiSanPham=" + loaiSanPham +
                ", createdAt=" + createdAt +
                '}';
    }
} 