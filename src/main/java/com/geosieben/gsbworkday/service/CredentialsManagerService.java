package com.geosieben.gsbworkday.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


import com.geosieben.gsbworkday.addon.AddonServ;
import com.geosieben.gsbworkday.entity.EncryptionKey;
import com.geosieben.gsbworkday.repository.EncryptionKeyRepository;

import jakarta.servlet.http.HttpSession;
@Service
public class CredentialsManagerService implements CredentialsManagerInterface {
    private EncryptionKeyRepository encryptionKeyRepository;
    private HttpSession httpSession;


    public CredentialsManagerService(EncryptionKeyRepository encryptionKeyRepository, HttpSession httpSession) {
        this.encryptionKeyRepository = encryptionKeyRepository;
        this.httpSession = httpSession;
    }


    @Override
    public ResponseEntity<Map<String, Boolean>> authenticateUser(String password) throws NoSuchAlgorithmException  {
        String eid=(String) httpSession.getAttribute("eid");
        Map<String,Boolean> response=new HashMap<>();
        Optional<EncryptionKey> cred=encryptionKeyRepository.getCredByEidType(eid, "cmanager");
        if(cred.isPresent()){

  		MessageDigest md5=MessageDigest.getInstance("MD5");
		byte[] md5Digest=md5.digest(password.getBytes());
		String encPassword =AddonServ.bytesToHexa(md5Digest);
            if(cred.get().getSaltKey().toString().equals(encPassword)){
                            response.put("authenticated", true);
            }

        }else{
            response.put("authenticated", false);
        }

    
        return ResponseEntity.ok(response);

    }

}
