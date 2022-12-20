package com.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.StateMasterEntity;
import com.user.entity.UserDtlsEntity;
@Repository
public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer> {
   public UserDtlsEntity findByEmailAndPassword(String email,String pwd);
   public UserDtlsEntity findByEmail(String email);
    public List<StateMasterEntity> findByCountryId(Integer countryId);
   
   
}
