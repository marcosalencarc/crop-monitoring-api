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
import javax.persistence.JoinTable;
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
	private List<User> users;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="station_has_sensor", joinColumns=
		{@JoinColumn(name="station_id")}, inverseJoinColumns=
		{@JoinColumn(name="sensor_id")})
	private List<Sensor> sensors;

	
	public Station() {
		super();
		this.sensors = new ArrayList<>();
		this.users = new ArrayList<>();
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

	public List<User> getUsers() {
		if(this.users == null) return new ArrayList<User>();
		return users;
	}

	public void setUsers(List<User> user) {
		this.users = user;
	}
	
	public boolean containStation(long id) {
		for(User u: users) if(u.getId() == id) return true;
		return false;
	}

	private StringBuilder usersStatiton() {
		int cont = 1;
		StringBuilder result = new StringBuilder();
		result.append("[");
		for(User u: users) {
			if(cont<users.size())result.append(""+u.getId()+",");
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descriptionStation == null) ? 0 : descriptionStation.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Station other = (Station) obj;
		if (descriptionStation == null) {
			if (other.descriptionStation != null)
				return false;
		} else if (!descriptionStation.equals(other.descriptionStation))
			return false;
		if (id != other.id)
			return false;
		return true;
	}


	
	 
}
