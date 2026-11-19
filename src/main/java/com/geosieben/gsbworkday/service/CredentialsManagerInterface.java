package com.geosieben.gsbworkday.service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface CredentialsManagerInterface {
public ResponseEntity<Map<String,Boolean>> authenticateUser(String password) throws NoSuchAlgorithmException;

}
