package com.example.CropMonitoringAPI.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CropMonitoringAPI.models.Sensor;

public interface SensorDAO extends JpaRepository<Sensor, Integer> {
	
	public Sensor findById(int id);
}
