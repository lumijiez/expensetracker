package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.model.BasicEntity;
import com.faf223.expensetrackerfaf.repository.BasicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/getData")
public class BasicController {
    private final BasicRepository basicRepository;

    @Autowired
    public BasicController(BasicRepository basicRepository) {
        this.basicRepository = basicRepository;
    }

    @GetMapping()
    public List<BasicEntity> listDepartments() {
        return basicRepository.findAll();
    }
}
