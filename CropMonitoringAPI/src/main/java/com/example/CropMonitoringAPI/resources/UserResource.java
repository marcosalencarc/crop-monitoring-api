package com.example.CropMonitoringAPI.resources;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CropMonitoringAPI.DAO.StationDAO;
import com.example.CropMonitoringAPI.DAO.UserDAO;
import com.example.CropMonitoringAPI.models.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value="/cropMonitoringApi")
@Api(value="API Rest produtos")
@CrossOrigin(origins="*")
public class UserResource {

	@Autowired
	UserDAO userDAO;
	@Autowired
	StationDAO stationDAO;
	
	@GetMapping("/user/get")
	@ApiOperation(value="Retorna a lista de usuário do sistema")
	public List<User> listUsers(){
		return userDAO.findAll();
		
	}
	
	@GetMapping("/user/get/{id}")
	@ApiOperation(value="Retorna um usuário")
	public User getUser(@PathVariable(value="id") long id){
		return userDAO.findById(id);
	}

	@PostMapping("/user/post")
	@ApiOperation(value="Salva um usuário")
	public User saveUser(@RequestBody User user) {
		
		User u = userDAO.save(user);
		if(user.getStations()!=null && !user.getStations().isEmpty()) {
			for(Station s: user.getStations()) {
				s.setUser(user);
				stationDAO.save(s);
			}
		}
		return u;
	}
	
	@PostMapping("/user/postall")
	@ApiOperation(value="salva uma lista de usuários")
	public List<User> saveUsers(@RequestBody List<User> users) {
		return userDAO.saveAll(users);
	}
	
	@PostMapping("/detete")
	@ApiOperation(value="Deleta um usurário")
	public void deleteUser(@RequestBody User user) {
		userDAO.delete(user);
	}
	
	@PostMapping("/update")
	@ApiOperation(value="Atualiza um usuário")
	public User updateUser(@RequestBody User user) {
		return userDAO.save(user);
	}
	
}
