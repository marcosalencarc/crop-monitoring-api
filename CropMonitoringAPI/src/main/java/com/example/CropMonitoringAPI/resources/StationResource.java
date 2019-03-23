package com.example.CropMonitoringAPI.resources;

import java.util.ArrayList;
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
import com.example.CropMonitoringAPI.models.Station;
import com.example.CropMonitoringAPI.models.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/cropMonitoringApi")
@Api(value="API Rest Crop Monitoring")
@CrossOrigin(origins="*")
public class StationResource {
	
	@Autowired
	StationDAO stationDAO;
	@Autowired
	UserDAO userDAO;
	
	@GetMapping("/station/get/{id}")
	@ApiOperation(value="Retorna a lista de de estações de um usuário")
	public String listStations(@PathVariable(value="id") long id){
		JSONObject result = new JSONObject();
		JSONArray stations = new JSONArray();
		JSONObject station;
		for(Station s: stationDAO.findByUser(id)) {
			station = new JSONObject();
			station.put("id", s.getId());
			station.put("descriptionStation", s.getDescriptionStation());
			station.put("user", s.getUser().getId());
			station.put("sensors", s.getSensors());
			stations.put(station);
		}
		result.put("stations", stations);
		return result.toString();
		
	}
	
	@GetMapping("/station/get/{id}&{userId}")
	@ApiOperation(value="Retorna um usuário")
	public Station getUser(@PathVariable(value="id") int id, @PathVariable(value="userId") long userId){
		return stationDAO.findByIdAndUser(id, userId);
	}

	
//	@PostMapping("/user/post")
//	@ApiOperation(value="Salva um usuário")
//	public User saveUser(@RequestBody User user) {
//		User u = userDAO.save(user);
//		if(user.getStations()!=null && !user.getStations().isEmpty()) {
//			for(Station s: user.getStations()) {
//				s.setUser(user);
//				stationDAO.save(s);
//			}
//		}
//		return u;
//	}
//		
//	@PostMapping("/user/delete")
//	@ApiOperation(value="Deleta um usurário")
//	public void deleteUser(@RequestBody User user) {
//		userDAO.delete(user);
//	}
//	
//	@PostMapping("/user/update")
//	@ApiOperation(value="Atualiza um usuário")
//	public User updateUser(@RequestBody User user) {
//		return userDAO.save(user);
//	}
	

}
