package com.example.CropMonitoringAPI.VO;

import com.example.CropMonitoringAPI.models.Sensor;

public class SensorVO {
	
	private int idSensor;
	private String description;
	private String name;
	private int idStation;
	private int unity;
	
	
	public SensorVO() {
		
	}
	
	public SensorVO(Sensor sensor, int station) {
		this.idSensor = sensor.getId();
		this.description = sensor.getDescriptionSensnor();
		this.name = sensor.getName();
		this.idStation = station;
	}
	
	public int getIdSensor() {
		return idSensor;
	}
	public void setIdSensor(int idSensor) {
		this.idSensor = idSensor;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdStation() {
		return idStation;
	}
	public void setIdStation(int idStation) {
		this.idStation = idStation;
	}
	public int getUnity() {
		return unity;
	}
	public void setUnity(int unity) {
		this.unity = unity;
	}
	
	

}
