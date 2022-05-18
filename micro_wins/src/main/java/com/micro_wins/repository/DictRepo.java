package com.micro_wins.repository;

import com.micro_wins.model.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 13/05/2022 - 1:34 AM
 */

@Repository
public interface DictRepo extends JpaRepository<Dict, Integer> {
    Dict findByDictName(String dictName);
    List<Dict> findDictByDictTypeNo(int dictTypeNo);
}
