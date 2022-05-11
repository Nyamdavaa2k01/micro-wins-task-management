package com.example.micro_wins.service.impl;

import com.example.micro_wins.model.User;
import com.example.micro_wins.repository.UserRepository;
import com.example.micro_wins.service.CrudService;
import com.example.micro_wins.service.UserService;
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
    private UserRepository userRepository;

    @Override
    public User save(User entity)
    {
        return userRepository.save(entity);
    }

    @Override
    public User update(User entity)
    {
        return userRepository.save(entity);
    }

    @Override
    public void delete(User entity)
    {
        userRepository.delete(entity);
    }

    @Override
    public void deleteById(Integer id)
    {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Integer id)
    {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll()
    {
        return userRepository.findAll();
    }

    @Override
    public boolean authenticate(String userName, Integer deviceId)
    {
        User user = this.findByUserName(userName);
        if (user == null)
        {
            return false;
        } else
        {
            return deviceId.equals(user.getDeviceId());
        }
    }

    @Override
    public User findByUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void deleteInBatch(List<User> users)
    {
        userRepository.deleteInBatch(users);
    }

}
