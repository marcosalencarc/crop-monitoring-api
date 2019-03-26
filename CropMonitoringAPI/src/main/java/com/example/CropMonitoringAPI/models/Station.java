package com.example.CropMonitoringAPI.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.CropMonitoringAPI.VO.StationVO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
@Entity
@Table(name="TB_STATION")
public class Station {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="description_station")
	private String descriptionStation;
	
	@ManyToMany(mappedBy="stations", cascade=CascadeType.ALL)
	private List<User> user;
	
	@OneToMany(mappedBy="station", fetch=FetchType.LAZY)
	private List<Sensor> sensors;

	
	public Station() {
		super();
	}
	
	public Station(int id, String descriptionStation) {
		super();
		this.id = id;
		this.descriptionStation = descriptionStation;
	}
	
	public Station(StationVO stationVO) {
		this.id = stationVO.getId();
		this.descriptionStation = stationVO.getDescription();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescriptionStation() {
		return descriptionStation;
	}

	public void setDescriptionStation(String descriptionStation) {
		this.descriptionStation = descriptionStation;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	public List<User> getUser() {
		if(this.user == null) return new ArrayList<User>();
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	private StringBuilder usersStatiton() {
		int cont = 1;
		StringBuilder result = new StringBuilder();
		result.append("[");
		for(User u: user) {
			if(cont<user.size())result.append(""+u.getId()+",");
			else result.append(""+u.getId());
		}
		result.append("]");
		return result;
	}
	@Override
	public String toString() {
		return "{\"id\":" + id + ",\"descriptionStation\":\"" + descriptionStation + "\", \"users\":" + usersStatiton() + ", \"sensors\":"
				+ sensors + "}";
	}


	
	 
}
