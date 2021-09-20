package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;
    //private final HashService hashService;
    private final EncryptionService encryptionService;

    public UserService(UserMapper userMapper, EncryptionService encryptionService){
        this.encryptionService = encryptionService;
        this.userMapper = userMapper;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.get(username) == null;
    }

    public List<User> getAllUsers() {
        return userMapper.getAll();
    }

    public User getUser(String username) {
        return userMapper.get(username);
    }

    public int createUser(User user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encryptedPassword = encryptionService.encryptValue(user.getPassWord(), encodedSalt);
        return userMapper.insert(new User(null, user.getUserName(), encodedSalt, encryptedPassword, user.getFirstName(), user.getLastName()));
    }

    public void deleteUser(int userId){
         userMapper.delete(userId);
    }

}
