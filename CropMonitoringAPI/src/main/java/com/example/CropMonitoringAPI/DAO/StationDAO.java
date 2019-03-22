package com.example.CropMonitoringAPI.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CropMonitoringAPI.models.Station;

public interface StationDAO extends JpaRepository<Station, Integer> {

	public List<Station> findByUserId(long id);
}
