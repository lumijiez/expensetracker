package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.BasicEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicRepository extends JpaRepository<BasicEntity, Short> {
    // You can define custom query methods here if needed
}

