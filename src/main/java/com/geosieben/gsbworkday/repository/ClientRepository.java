package com.geosieben.gsbworkday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.geosieben.gsbworkday.entity.Clients;

public interface ClientRepository extends JpaRepository<Clients,Integer> {

}
