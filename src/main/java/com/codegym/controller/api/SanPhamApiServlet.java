package com.codegym.controller.api;

import com.codegym.model.SanPham;
import com.codegym.model.LoaiSanPham;
import com.codegym.service.SanPhamService;
import com.codegym.service.LoaiSanPhamService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "SanPhamApiServlet", urlPatterns = {"/api/sanpham"})
public class SanPhamApiServlet extends HttpServlet {
    private SanPhamService sanPhamService;
    private LoaiSanPhamService loaiSanPhamService;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        sanPhamService = new SanPhamService();
        loaiSanPhamService = new LoaiSanPhamService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword = request.getParameter("keyword");
        String loaiStr = request.getParameter("loai");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        String pageStr = request.getParameter("page");
        Integer loaiId = null;
        BigDecimal minPrice = null, maxPrice = null;
        int page = 1, pageSize = 5;
        try { if (loaiStr != null && !loaiStr.isEmpty()) loaiId = Integer.parseInt(loaiStr); } catch (Exception ignored) {}
        try { if (minPriceStr != null && !minPriceStr.isEmpty()) minPrice = new BigDecimal(minPriceStr); } catch (Exception ignored) {}
        try { if (maxPriceStr != null && !maxPriceStr.isEmpty()) maxPrice = new BigDecimal(maxPriceStr); } catch (Exception ignored) {}
        try { if (pageStr != null) page = Integer.parseInt(pageStr); } catch (Exception ignored) {}
        int offset = (page - 1) * pageSize;
        int total = sanPhamService.countByFilter(keyword, loaiId, minPrice, maxPrice);
        List<SanPham> sanPhamList = sanPhamService.findByFilter(keyword, loaiId, minPrice, maxPrice, offset, pageSize);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(gson.toJson(new ApiListResult(sanPhamList, total)));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        SanPhamInput input = gson.fromJson(reader, SanPhamInput.class);
        boolean success = sanPhamService.addSanPham(input.name, input.price, input.loaiId);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("{\"success\":" + success + "}");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        BufferedReader reader = request.getReader();
        SanPhamInput input = gson.fromJson(reader, SanPhamInput.class);
        boolean success = sanPhamService.updateSanPham(id, input.name, input.price, input.loaiId, input.status);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("{\"success\":" + success + "}");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean success = sanPhamService.deleteById(id);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("{\"success\":" + success + "}");
    }

    static class SanPhamInput {
        public String name;
        public BigDecimal price;
        public int loaiId;
        public String status;
    }
    static class ApiListResult {
        public List<SanPham> data;
        public int total;
        public ApiListResult(List<SanPham> data, int total) {
            this.data = data;
            this.total = total;
        }
    }
} 