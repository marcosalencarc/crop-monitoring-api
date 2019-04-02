package com.example.CropMonitoringAPI.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.CropMonitoringAPI.models.Sensor;
import com.example.CropMonitoringAPI.models.SensorRead;
import com.example.CropMonitoringAPI.models.Station;


public interface SensorReadDAO extends JpaRepository<SensorRead, Integer> {

	public SensorRead findById(int id);
	
	public List<SensorRead> findBySensorAndStation(Sensor sensor, Station station);
	
	@Query("SELECT s FROM SensorRead s WHERE s.sensor.id = :sensorId and s.station.id = :stationId")
	public List<SensorRead> findAllBySensorAndStation(@Param("sensorId") int sensorId, @Param("stationId") int stationId);
}
