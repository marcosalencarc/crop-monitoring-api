package com.example.CropMonitoringAPI.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CropMonitoringAPI.models.User;

public interface UserDAO extends JpaRepository<User,Long> {

	User findById(long id);
	
	
	@Query("Select u from User u where u.id = :id")
	User userWithStations(@Param("id") long id);
}
