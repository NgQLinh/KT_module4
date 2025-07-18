package com.codegym.service;

import com.codegym.model.SanPham;
import com.codegym.repository.SanPhamRepository;
import java.util.List;

public class SanPhamService {
    private SanPhamRepository sanPhamRepository = new SanPhamRepository();

    public List<SanPham> findAll() {
        return sanPhamRepository.findAll();
    }

    public boolean addSanPham(String name, java.math.BigDecimal price, int loaiId) {
        return sanPhamRepository.addSanPham(name, price, loaiId);
    }

    public boolean deleteByIds(List<Integer> ids) {
        return sanPhamRepository.deleteByIds(ids);
    }

    public boolean updateSanPham(int id, String name, java.math.BigDecimal price, int loaiId, String status) {
        return sanPhamRepository.updateSanPham(id, name, price, loaiId, status);
    }
    public boolean deleteById(int id) {
        return sanPhamRepository.deleteById(id);
    }

    public java.util.List<com.codegym.model.SanPham> findByFilter(String keyword, Integer loaiId, java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice, int offset, int limit) {
        return sanPhamRepository.findByFilter(keyword, loaiId, minPrice, maxPrice, offset, limit);
    }
    public int countByFilter(String keyword, Integer loaiId, java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice) {
        return sanPhamRepository.countByFilter(keyword, loaiId, minPrice, maxPrice);
    }
} 