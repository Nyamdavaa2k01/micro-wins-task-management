package com.micro_wins.service;

import com.micro_wins.model.Dict;

import java.util.List;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 13/05/2022 - 1:43 AM
 */
public interface DictService {
    List<Dict> getDictByDictTypeNo(int dictTypeNo);
    Dict findByDictName(String dictName);
}
