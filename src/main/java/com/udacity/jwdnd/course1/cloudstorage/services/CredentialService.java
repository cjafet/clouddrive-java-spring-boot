package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private HashService hashService;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, HashService hashService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredentials() {
        return this.credentialMapper.getCredentials();
    }

    public List<Credential> getCredentialById(Integer userId) {
        return this.credentialMapper.getCredentialById(userId);
    }

    public int addCredential(Credential credential) {
        //hashPassword(credential);
        encryptPassword(credential);
        return this.credentialMapper.insert(credential);
    }

    public int updateCredential(Credential credential) {

        encryptPassword(credential);


        return this.credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(Integer credentialId) {
        this.credentialMapper.deleteCredential(credentialId);
    }

    private void encryptPassword(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getUserPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setUserPassword(encryptedPassword);
    }

    private void hashPassword(Credential credential) {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(credential.getUserPassword(), encodedSalt);
        credential.setUserPassword(hashedPassword);
    }

}
