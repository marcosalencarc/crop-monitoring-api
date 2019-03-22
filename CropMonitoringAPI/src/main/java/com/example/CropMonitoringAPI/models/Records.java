package com.example.CropMonitoringAPI.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_RECORDS")
public class Records {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private SensorRead sensorRead;
	
	@OneToOne
	private Recommendation recomendation;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SensorRead getSensorRead() {
		return sensorRead;
	}

	public void setSensorRead(SensorRead sensorRead) {
		this.sensorRead = sensorRead;
	}

	public Recommendation getRecomendation() {
		return recomendation;
	}

	public void setRecomendation(Recommendation recomendation) {
		this.recomendation = recomendation;
	}

	
	
	
}
