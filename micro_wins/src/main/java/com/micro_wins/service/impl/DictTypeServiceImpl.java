package com.micro_wins.service.impl;

import com.micro_wins.model.DictType;
import com.micro_wins.repository.DictTypeRepo;
import com.micro_wins.service.CrudService;
import com.micro_wins.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 13/05/2022 - 1:55 AM
 */
@Service
public class DictTypeServiceImpl implements CrudService<DictType>, DictTypeService {
    @Autowired
    private DictTypeRepo dictTypeRepo;

    @Override
    public DictType save(DictType entity)
    {
        return dictTypeRepo.save(entity);
    }

    @Override
    public DictType update(DictType entity)
    {
        return dictTypeRepo.save(entity);
    }

    @Override
    public void delete(DictType entity)
    {
        dictTypeRepo.delete(entity);
    }

    @Override
    public void deleteById(Integer id)
    {
        dictTypeRepo.deleteById(id);
    }

    @Override
    public DictType findById(Integer id)
    {
        return dictTypeRepo.findById(id).orElse(null);
    }

    @Override
    public List<DictType> findAll()
    {
        return dictTypeRepo.findAll();
    }

    @Override
    public DictType findByDictTypeName(String dictTypeName)
    {
        return dictTypeRepo.findByDictTypeName(dictTypeName);
    }

    @Override
    public void deleteInBatch(List<DictType> dictTypesList)
    {
        dictTypeRepo.deleteInBatch(dictTypesList);
    }
}
