package com.micro_wins.service.impl;

import com.micro_wins.model.Dict;
import com.micro_wins.model.DictType;
import com.micro_wins.repository.DictRepo;
import com.micro_wins.service.CrudService;
import com.micro_wins.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 13/05/2022 - 2:02 AM
 */
@Service
public class DictServiceImpl implements CrudService<Dict>, DictService {
    @Autowired
    private DictRepo dictRepo;

    @Override
    public Dict save(Dict entity)
    {
        return dictRepo.save(entity);
    }

    @Override
    public Dict update(Dict entity)
    {
        return dictRepo.save(entity);
    }

    @Override
    public void delete(Dict entity)
    {
        dictRepo.delete(entity);
    }

    @Override
    public void deleteById(Integer id)
    {
        dictRepo.deleteById(id);
    }

    @Override
    public Dict findById(Integer id)
    {
        return dictRepo.findById(id).orElse(null);
    }

    @Override
    public List<Dict> findAll()
    {
        return dictRepo.findAll();
    }

    @Override
    public Dict findByDictName(String dictName)
    {
        return dictRepo.findByDictName(dictName);
    }

    @Override
    public List<Dict> getDictByDictTypeNo(int dictTypeNo){
        List<Dict> resDictList = new ArrayList<>();
        List<Dict> allDict = findAll();

        allDict.forEach(dict -> {
            if(dict.getDictTypeNo() == dictTypeNo){
                resDictList.add(dict);
            }
        });

        return resDictList;
    }

    @Override
    public void deleteInBatch(List<Dict> dictList)
    {
        dictRepo.deleteInBatch(dictList);
    }
}