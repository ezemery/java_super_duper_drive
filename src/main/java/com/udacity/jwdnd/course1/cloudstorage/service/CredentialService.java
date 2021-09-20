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

    public Credential getCredential(Integer id){
        return credentialMapper.getId(id);
    }

    public String encrypt(String password, String salt){
        String encryptedPassword = encryptionService.encryptValue(password, salt);
        return encryptedPassword;
    }


    public int createCredential(Credential credential, Integer userId){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encryptedPassword = encrypt(credential.getPassWord(),encodedSalt);
        return credentialMapper.insert(new Credential(null, credential.getUrl(),  credential.getUserName(),encodedSalt, encryptedPassword, userId));
    }

    public int updateCredential(Credential credential){
        Credential cred = credentialMapper.getId(credential.getCredentialId());
        String encryptedPassword = encrypt(credential.getPassWord(),cred.getKey());
        return credentialMapper.update(credential.getCredentialId(), credential.getUrl(), credential.getUserName(), encryptedPassword);
    }

    public boolean isUrlAvailable(String url) {
        return credentialMapper.getUrl(url) == null;
    }

    public void deleteCredential(Integer credentialId){
         credentialMapper.delete(credentialId);
    }
}
