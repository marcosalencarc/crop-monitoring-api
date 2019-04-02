package com.example.CropMonitoringAPI.VO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.CropMonitoringAPI.models.Sensor;
import com.example.CropMonitoringAPI.models.SensorRead;
import com.example.CropMonitoringAPI.models.Station;

public class SensorReadVO {

	
	private int idSensor;
	private int idStation;
	private double value;
	private Date dtRead;
	
	
	public SensorReadVO() {
		
	}
	public SensorReadVO(SensorRead sensorRead) {
		this.idSensor = sensorRead.getSensor().getId();
		this.idStation = sensorRead.getStation().getId();
		this.value = sensorRead.getValue();
		this.dtRead = sensorRead.getData();
	}
	
	public SensorReadVO(int idSensor, int idStation, double value) {
		super();
		this.idSensor = idSensor;
		this.idStation = idStation;
		this.value = value;
	}

	public SensorReadVO(Sensor sensor, Station station) {
		this.idSensor = sensor.getId();
		this.idStation = station.getId();
	}
	
	public int getIdSensor() {
		return idSensor;
	}
	public void setIdSensor(int idSensor) {
		this.idSensor = idSensor;
	}
	public int getIdStation() {
		return idStation;
	}
	public void setIdStation(int idStation) {
		this.idStation = idStation;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public Date getDtRead() {
		return dtRead;
	}
	public void setDtRead(Date dtRead) {
		this.dtRead = dtRead;
	}
	
	public static List<SensorReadVO> getListVO(List<SensorRead> list) {
		List<SensorReadVO> result = new ArrayList<>();
		for(SensorRead s: list) {
			result.add(new SensorReadVO(s));
		}
		return result;
	}
	
	
}
