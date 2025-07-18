package com.codegym.model;

public class LoaiSanPham {
    private int cid;
    private String name;

    public LoaiSanPham() {}

    public LoaiSanPham(int cid, String name) {
        this.cid = cid;
        this.name = name;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LoaiSanPham{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                '}';
    }
} 