package com.micro_wins.service.impl;

import com.micro_wins.model.User;
import com.micro_wins.repository.UserRepo;
import com.micro_wins.service.CrudService;
import com.micro_wins.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 11/05/2022 - 10:23 AM
 */

@Service
public class UserServiceImpl implements CrudService<User>, UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public User save(User entity)
    {
        return userRepo.save(entity);
    }

    @Override
    public User update(User entity)
    {
        return userRepo.save(entity);
    }

    @Override
    public void delete(User entity)
    {
        userRepo.delete(entity);
    }

    @Override
    public void deleteById(Integer id)
    {
        userRepo.deleteById(id);
    }

    @Override
    public User findById(Integer id)
    {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll()
    {
        return userRepo.findAll();
    }

    @Override
    public boolean authenticate(String userName, Integer deviceId)
    {
        User user = this.findByUserNameAndDeviceId(userName, deviceId);
        if (user == null)
        {
            return false;
        } else
        {
            return deviceId.equals(user.getDeviceId());
        }
    }

    @Override
    public User findByUserNameAndDeviceId(String userName, Integer deviceId)
    {
        return userRepo.findByUserNameAndDeviceId(userName, deviceId);
    }

    @Override
    public void deleteInBatch(List<User> users)
    {
        userRepo.deleteInBatch(users);
    }

}
