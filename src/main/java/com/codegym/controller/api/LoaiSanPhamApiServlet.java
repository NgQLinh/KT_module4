package com.codegym.controller.api;

import com.codegym.model.LoaiSanPham;
import com.codegym.service.LoaiSanPhamService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoaiSanPhamApiServlet", urlPatterns = {"/api/loai-san-pham"})
public class LoaiSanPhamApiServlet extends HttpServlet {
    private LoaiSanPhamService loaiSanPhamService;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        loaiSanPhamService = new LoaiSanPhamService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<LoaiSanPham> loaiList = loaiSanPhamService.findAll();
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(gson.toJson(loaiList));
    }
} 