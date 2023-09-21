package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.BasicEntity;
import com.faf223.expensetrackerfaf.repository.BasicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicServiceImpl implements BasicService {
    private final BasicRepository basicRepository;

    @Autowired
    public BasicServiceImpl(BasicRepository basicRepository) {
        this.basicRepository = basicRepository;
    }

    @Override
    public List<BasicEntity> getAllData() {
        return basicRepository.findAll();
    }
}
