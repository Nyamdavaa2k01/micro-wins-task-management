package com.micro_wins.service.impl;

import com.micro_wins.model.Result;
import com.micro_wins.repository.ResultRepo;
import com.micro_wins.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements CrudService<Result> {

    @Autowired
    private ResultRepo resultRepo ;

    @Override
    public Result save(Result entity) {
        return resultRepo.save(entity) ;
    }

    @Override
    public Result update(Result entity) {
        return resultRepo.save(entity) ;
    }

    @Override
    public void delete(Result entity) {
        resultRepo.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        resultRepo.deleteById(id);
    }

    @Override
    public void deleteInBatch(List<Result> entities) {

    }

    @Override
    public Result findById(Integer id) {
        return resultRepo.findById(id).orElse(null) ;
    }

    @Override
    public List<Result> findAll() {
        return resultRepo.findAll() ;
    }
}
