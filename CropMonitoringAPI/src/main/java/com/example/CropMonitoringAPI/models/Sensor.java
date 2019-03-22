package com.example.CropMonitoringAPI.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.CropMonitoringAPI.enums.UnityEnum;

@Entity
@Table(name="TB_SENSOR")
public class Sensor {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idSensor;
	
	private String name;
	
	@Column(name="description_sensor")
	private String descriptionSensnor;
	
	private UnityEnum unity;
	
	@OneToOne
	private Station station;

	public int getIdSensor() {
		return idSensor;
	}

	public void setIdSensor(int idSensor) {
		this.idSensor = idSensor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptionSensnor() {
		return descriptionSensnor;
	}

	public void setDescriptionSensnor(String descriptionSensnor) {
		this.descriptionSensnor = descriptionSensnor;
	}

	public UnityEnum getUnity() {
		return unity;
	}

	public void setUnity(UnityEnum unity) {
		this.unity = unity;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	
	
}
