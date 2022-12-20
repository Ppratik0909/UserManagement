package com.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.CityMasterEntity;

@Repository
public interface CityMasterRepo extends JpaRepository<CityMasterEntity, Integer> {
	public List<CityMasterEntity> findByStateId(Integer stateId);
}
