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

import com.example.CropMonitoringAPI.VO.SensorVO;
import com.example.CropMonitoringAPI.enums.UnityEnum;

@Entity
@Table(name="TB_SENSOR")
public class Sensor {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String name;
	
	@Column(name="description_sensor")
	private String descriptionSensnor;
	
	private UnityEnum unity;
	
	public Sensor() {
		
	}
	public Sensor(SensorVO sensorVO) {
		this.id = sensorVO.getIdSensor();
		this.name = sensorVO.getName();
		this.descriptionSensnor = sensorVO.getDescription();
		this.unity = UnityEnum.getById(sensorVO.getUnity());
				
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	
	
}
