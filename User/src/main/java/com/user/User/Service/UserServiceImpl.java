package com.user.User.Service;

//import com.user.User.Exception.UserNotFoundException;
import com.user.User.Exception.UserNotFoundException;
import com.user.User.Model.User;
import com.user.User.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(int id) {
        try {
            return userRepository.findById(id);
        }catch(Exception exception){
            //throw new UserNotFoundException(id);
            //new UserNotFoundException()
            LOGGER.error("User not found with request id: {}", id);
            return null;
        }
    }
}
