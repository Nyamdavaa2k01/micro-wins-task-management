package com.micro_wins.service;

import com.micro_wins.model.Result;

import java.util.Date;
import java.util.List;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 19/05/2022 - 7:18 PM
 * @purpose
 * @definition
 */
public interface ResultService {
    List<Result> findByTaskCompletedDate (Date date) ;
}
