package com.example.CropMonitoringAPI.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.CropMonitoringAPI.enums.UnityEnum;

@Entity
@Table(name="TB_SENSOR_READ")
public class SensorRead {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private double value;
	private Date data;
	
	@OneToOne
	private Sensor sensor;
	
	@OneToOne
	private Station station;

	
	
	public SensorRead() {}
	
	public SensorRead(double value, Date data, Sensor sensor, Station station) {
		this(sensor,station);
		this.value = value;
		this.data = data;
		
	}
	public SensorRead(Sensor sensor, Station station) {
		super();
		this.sensor = sensor;
		this.station = station;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	
	public boolean isValid() {
		if(this.value < 0) return false;
		if(this.data == null) return false;
		if(sensor.getUnity().equals(UnityEnum.PORCETAGEM)) {
			if(this.value < 0 || this.value > 100){
				return false;
			}
		}
		if(sensor.getUnity().equals(UnityEnum.CELCIUS)) {
			if(this.value < 0){
				return false;
			}
		}
		return true;
	}
	
	
}
