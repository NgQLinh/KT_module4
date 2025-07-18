package com.codegym.service;

import com.codegym.model.LoaiSanPham;
import com.codegym.repository.LoaiSanPhamRepository;
import java.util.List;

public class LoaiSanPhamService {
    private LoaiSanPhamRepository loaiSanPhamRepository = new LoaiSanPhamRepository();

    public List<LoaiSanPham> findAll() {
        return loaiSanPhamRepository.findAll();
    }
} 