package com.example.CropMonitoringAPI.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CropMonitoringAPI.models.Station;

public interface StationDAO extends JpaRepository<Station, Integer> {

	/*@Query("Select s from Station s where s.user.id = :id")
	public List<Station> findByUser(@Param(value="id") long id);*/
	
	public Station findById(int id);
	
	public Station findByIdAndUser(int id, long userId);
	
}
