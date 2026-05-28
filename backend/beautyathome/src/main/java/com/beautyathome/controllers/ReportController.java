package com.beautyathome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Llama a la vista creada en data.sql
    @GetMapping("/categorias")
    public List<Map<String, Object>> getCategoriasReservadas() {
        String sql = "SELECT * FROM CategoriasReservadas";
        return jdbcTemplate.queryForList(sql);
    }
}