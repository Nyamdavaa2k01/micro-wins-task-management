package com.micro_wins.repository;

import com.micro_wins.model.DictType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 13/05/2022 - 1:37 AM
 */
@Repository
public interface DictTypeRepo extends JpaRepository<DictType, Integer> {
    DictType findByDtTypeName(String dtTypeName);
}