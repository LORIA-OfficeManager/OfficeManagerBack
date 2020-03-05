package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.daos.IUserDAO;
import com.OfficeManager.app.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private IUserDAO userDao;

    public User saveUser(User user) {
        return userDao.save(user);
    }

    public User findByUsername(String username){
        return userDao.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
