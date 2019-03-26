package com.example.CropMonitoringAPI.models;


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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.CropMonitoringAPI.VO.UserVO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
@Entity
@Table(name="TB_USER")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;
	
	@Column
	private String login;
	@Column
	private String password;
	@Column
	private String name;
	
	
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="user_has_station", joinColumns=
		{@JoinColumn(name="user_id")}, inverseJoinColumns=
		{@JoinColumn(name="station_id")})
	private List<Station> stations;

	public User() {
		super();
	}
	
	public User(UserVO userVO) {
		this();
		this.id = userVO.getId();
		this.name = userVO.getName();
		this.login = userVO.getLogin();
		this.password = userVO.getPassword();
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Station> getStations() {
		return stations;
	}

	public void setStations(List<Station> stations) {
		this.stations = stations;
	}

	@Override
	public String toString() {
		return "{\"id:\"" + id + ", \"login\": \"" + login + "\", \"name\": \"" + name + "\"}";
	}



		
	
}
