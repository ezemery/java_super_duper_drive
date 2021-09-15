package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper,EncryptionService encryptionService){
        this.credentialMapper=credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentials(Integer userId ){
        return credentialMapper.getAll(userId );
    }

    public Credential getCredential(String userName){
        return credentialMapper.get(userName);
    }

    public int createCredential(Credential credential, Integer userId){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        int int_random = random.nextInt(Integer.MAX_VALUE);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassWord(), encodedSalt);
        return credentialMapper.insert(new Credential(int_random, credential.getUrl(),  credential.getUserName(),encodedSalt, encryptedPassword, userId));
    }

    public int updateCredential(Credential credential){
        return credentialMapper.update(credential.getUrl(), credential.getKey(), credential.getPassWord(), 1);
    }

    public void deleteCredential(Credential credential){
         credentialMapper.delete(credential.getUserName());
    }
}
