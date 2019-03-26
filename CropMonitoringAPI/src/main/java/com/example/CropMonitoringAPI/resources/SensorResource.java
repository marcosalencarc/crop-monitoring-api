package com.example.CropMonitoringAPI.resources;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.CropMonitoringAPI.DAO.SensorDAO;
import com.example.CropMonitoringAPI.DAO.StationDAO;
import com.example.CropMonitoringAPI.VO.SensorVO;
import com.example.CropMonitoringAPI.models.Sensor;
import com.example.CropMonitoringAPI.models.Station;
import com.example.CropMonitoringAPI.util.CustomErrorType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/cropMonitoringApi")
@Api(value="API Rest Crop Monitoring")
@CrossOrigin(origins="*")
public class SensorResource {

public static final Logger logger = LoggerFactory.getLogger(SensorResource.class);

	
	@Autowired
	StationDAO stationDAO;
	@Autowired
	SensorDAO sensorDAO;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sensors/get", method = RequestMethod.GET)
	@ApiOperation(value="Retorna a lista de sensorers cadastrados")
    public ResponseEntity<?> listAllSensors() {
        List<Sensor> sensors = sensorDAO.findAll();
        if (sensors == null || sensors.isEmpty()) {
            return new ResponseEntity(new CustomErrorType("Nenhum sensor encontrado"),HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(sensors, HttpStatus.OK);
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/sensor/get/{id}", method=RequestMethod.GET)
	@ApiOperation(value="Retorna um sensor pelo id")
	public ResponseEntity<?> getSensor(@PathVariable(value="id") int id){
		Sensor sensor = sensorDAO.findById(id);
		if(sensor == null) {
			logger.error("Sensor com id {} não encontrada.", id);
            return new ResponseEntity(new CustomErrorType("Sensor com id " + id + " não encontrada"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(sensor, HttpStatus.OK);
	}
//
	@SuppressWarnings("unchecked")
	@PostMapping("/sensor/post")
	@ApiOperation(value="Salva um sensor novo sensor ou adiciona na estação")
	public ResponseEntity<?> saveSnesor(@RequestBody SensorVO sensorVO) {
		//Inserir um novo sensor
		Sensor sensor;
		if(sensorVO.getIdSensor() == 0) {
			if(sensorVO.getIdStation() != 0) {
				Station station = stationDAO.findById(sensorVO.getIdStation());
				sensor = new Sensor(sensorVO);
				station.getSensors().add(sensor);
				stationDAO.save(station);
				sensor = station.getSensors().get(station.getSensors().size()-1);
				return new ResponseEntity(sensor, HttpStatus.OK);
			}
//			try {
//				User user = userDAO.findById(stationVO.getIdUser());
//				if(user==null) {
//					logger.error("Não foi possivel adicionar a estação, usuário {} não encontrado.", stationVO.getIdUser());
//		            return new ResponseEntity(new CustomErrorType("Não foi possivel adicionar a estação, usuário "+ stationVO.getIdUser()+" não encontrado."), HttpStatus.INTERNAL_SERVER_ERROR);
//				}
//				station = new Station(stationVO);
//				station.setUsers(new ArrayList<>());
//				station.getUsers().add(user);
//				station.setSensors(new ArrayList<Sensor>());
//				user.getStations().add(station);
//				user = userDAO.save(user);
//				station = user.getStations().get(user.getStations().size()-1);
//				return new ResponseEntity(station.toString(), HttpStatus.OK);
//			}catch(Exception e){
//				logger.error("Ocorreu um erro inesperado");
//	            return new ResponseEntity(new CustomErrorType("Ocorreu um erro inesperado:"+e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//			}
		}
		//Atuazalizar estação
		else {
//			try {
//				station = stationDAO.findById(stationVO.getId());
//				if(station==null || station.containStation(stationVO.getIdUser())) {
//					logger.error("A estação {} não existe para alterar.",stationVO.getId());
//		            return new ResponseEntity(new CustomErrorType("A estação "+ stationVO.getId()+" não existe para alterar."), HttpStatus.INTERNAL_SERVER_ERROR);
//				}
//				
//				station.setDescriptionStation(stationVO.getDescription());
//				station = stationDAO.save(station);
//				return new ResponseEntity(station.toString(), HttpStatus.OK);
//			}catch (Exception e) {
//				logger.error("Ocorreu um erro inesperado");
//	            return new ResponseEntity(new CustomErrorType("Ocorreu um erro inesperado"), HttpStatus.INTERNAL_SERVER_ERROR);
//			}
			
		}
		return null;
	}
//	
//	@RequestMapping(value="/station/delete", method=RequestMethod.POST)
//	@ApiOperation(value="Deleta uma estação")
//	public ResponseEntity<?> deleteStation(@RequestBody StationVO stationVO) {
//		Station station = stationDAO.findById(stationVO.getId());
//		if(station==null) {
//			logger.error("Estação não encotrada para a remoção.");
//			return new ResponseEntity(new CustomErrorType("Estação não encotrada para a remoção."), HttpStatus.NOT_FOUND);
//		}else {
//			try {
//				stationDAO.delete(station);
//				return new ResponseEntity(station.toString(), HttpStatus.OK);
//			}catch (Exception e){
//				logger.error("Estação não encotrada para a remoção.");
//				return new ResponseEntity(new CustomErrorType("Estação não encotrada para a remoção."), HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		}
//	}
	
}
