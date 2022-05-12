package com.micro_wins.service;

import com.micro_wins.model.DictType;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 13/05/2022 - 1:45 AM
 */
public interface DictTypeService {
    DictType findByDictTypeName(String dictTypeName);
}
