package com.example.CropMonitoringAPI.resources;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.CropMonitoringAPI.DAO.StationDAO;
import com.example.CropMonitoringAPI.DAO.UserDAO;
import com.example.CropMonitoringAPI.VO.StationVO;
import com.example.CropMonitoringAPI.VO.UserVO;
import com.example.CropMonitoringAPI.models.Sensor;
import com.example.CropMonitoringAPI.models.Station;
import com.example.CropMonitoringAPI.models.User;
import com.example.CropMonitoringAPI.util.CustomErrorType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/cropMonitoringApi")
@Api(value="API Rest Crop Monitoring")
@CrossOrigin(origins="*")
public class StationResource {
	
	public static final Logger logger = LoggerFactory.getLogger(StationResource.class);

	
	@Autowired
	StationDAO stationDAO;
	@Autowired
	UserDAO userDAO;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/stations/get/{id}", method = RequestMethod.GET)
	@ApiOperation(value="Retorna a lista de estações de um usuário")
    public ResponseEntity<?> listAllStations(@PathVariable("id") long id) {
        List<Station> stations = userDAO.findById(id).getStations();
        if (stations.isEmpty()) {
            return new ResponseEntity(new CustomErrorType("Nenhum usuário encontrado"),HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(stations.toString(), HttpStatus.OK);
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/station/get/{id}", method=RequestMethod.GET)
	@ApiOperation(value="Retorna uma estação pelo id")
	public ResponseEntity<?> getStation(@PathVariable(value="id") int id){
		Station s = stationDAO.findById(id);
		if(s == null) {
			logger.error("Estação com id {} não encontrada.", id);
            return new ResponseEntity(new CustomErrorType("Estação com id " + id + " não encontrada"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(s.toString(), HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/station/post")
	@ApiOperation(value="Salva uma estação")
	public ResponseEntity<?> saveStation(@RequestBody StationVO stationVO) {
		//Inserir um nova estação
		Station station;
		if(stationVO.getId() == 0) {
			try {
				User user = userDAO.findById(stationVO.getIdUser());
				if(user==null) {
					logger.error("Não foi possivel adicionar a estação, usuário {} não encontrado.", stationVO.getIdUser());
		            return new ResponseEntity(new CustomErrorType("Não foi possivel adicionar a estação, usuário "+ stationVO.getIdUser()+" não encontrado."), HttpStatus.INTERNAL_SERVER_ERROR);
				}if(stationVO.getDescription() == null || stationVO.getDescription().isEmpty()) {
					logger.error("Não foi possivel adicionar a estação com a descrição vazia!", stationVO.getIdUser());
		            return new ResponseEntity(new CustomErrorType("Não foi possivel adicionar a estação com a descrição vazia!"), HttpStatus.INTERNAL_SERVER_ERROR);
				}
				station = new Station(stationVO);
				station.setUsers(new ArrayList<>());
				station.getUsers().add(user);
				station.setSensors(new ArrayList<Sensor>());
				user.getStations().add(station);
				user = userDAO.save(user);
				station = user.getStations().get(user.getStations().size()-1);
				return new ResponseEntity(station.toString(), HttpStatus.OK);
			}catch(Exception e){
				logger.error("Ocorreu um erro inesperado");
	            return new ResponseEntity(new CustomErrorType("Ocorreu um erro inesperado:"+e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		//Atuazalizar estação
		else {
			try {
				station = stationDAO.findById(stationVO.getId());
				if(station==null || station.containStation(stationVO.getIdUser())) {
					logger.error("A estação {} não existe para alterar.",stationVO.getId());
		            return new ResponseEntity(new CustomErrorType("A estação "+ stationVO.getId()+" não existe para alterar."), HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				station.setDescriptionStation(stationVO.getDescription());
				station = stationDAO.save(station);
				return new ResponseEntity(station.toString(), HttpStatus.OK);
			}catch (Exception e) {
				logger.error("Ocorreu um erro inesperado");
	            return new ResponseEntity(new CustomErrorType("Ocorreu um erro inesperado"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
	}
	
	@RequestMapping(value="/station/delete", method=RequestMethod.POST)
	@ApiOperation(value="Deleta uma estação")
	public ResponseEntity<?> deleteStation(@RequestBody StationVO stationVO) {
		Station station = stationDAO.findById(stationVO.getId());
		if(station==null) {
			logger.error("Estação não encotrada para a remoção.");
			return new ResponseEntity(new CustomErrorType("Estação não encotrada para a remoção."), HttpStatus.NOT_FOUND);
		}else {
			try {
				stationDAO.delete(station);
				return new ResponseEntity(station.toString(), HttpStatus.OK);
			}catch (Exception e){
				logger.error("Estação não encotrada para a remoção.");
				return new ResponseEntity(new CustomErrorType("Estação não encotrada para a remoção."), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

}
