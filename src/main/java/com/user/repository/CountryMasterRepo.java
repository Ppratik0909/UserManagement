package com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.CountryMasterEntity;

public interface CountryMasterRepo extends JpaRepository<CountryMasterEntity, Integer> {

}
