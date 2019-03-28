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
import com.example.CropMonitoringAPI.enums.UnityEnum;
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
			try{
				if(sensorVO.getIdStation() != 0) {
					Station station = stationDAO.findById(sensorVO.getIdStation());
					if(station == null) {
						logger.error("Estação {} não encotrada", sensorVO.getIdStation());
			            return new ResponseEntity(new CustomErrorType("Estação "+ sensorVO.getIdStation() + " não encontrada: "), HttpStatus.NO_CONTENT);
					}
					sensor = new Sensor(sensorVO);
					station.getSensors().add(sensor);
					stationDAO.save(station);
					sensor = station.getSensors().get(station.getSensors().size()-1);
					return new ResponseEntity(new SensorVO(sensor, station.getId()), HttpStatus.OK);
				}else {
					sensor = new Sensor(sensorVO);
					sensor = sensorDAO.save(sensor);
					return new ResponseEntity(sensor, HttpStatus.OK);
				}
			}catch (Exception e) {
				logger.error("Ocorreu um erro inesperado");
	            return new ResponseEntity(new CustomErrorType("Ocorreu um erro inesperado: "+e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		//Atuazalizar sensor ou inserir em uma estação
		else {
			boolean sensorAlterado=false;
			try {
				sensor = sensorDAO.findById(sensorVO.getIdSensor());
				if(sensor==null) {
					logger.error("O sensor {} não existe para alterar.",sensorVO.getIdSensor());
		            return new ResponseEntity(new CustomErrorType("O sensor "+ sensorVO.getIdSensor()+" não existe para alterar."), HttpStatus.NO_CONTENT);
				}
				if(!sensorVO.getDescription().equals(sensor.getDescriptionSensnor()) || 
						!sensorVO.getName().equals(sensor.getName()) || 
						!UnityEnum.getById(sensorVO.getUnity()).equals(sensor.getUnity())) 
				{
					sensorAlterado = true;
					sensor.setDescriptionSensnor(sensorVO.getDescription());
					sensor.setName(sensorVO.getName());
					sensor.setUnity(UnityEnum.getById(sensorVO.getUnity()));
				}
				
				if(sensorAlterado)sensor = sensorDAO.save(sensor);
				if(sensorVO.getIdStation()!=0) {
					Station station = stationDAO.findById(sensorVO.getIdStation());
					if(station == null) {
						logger.error("Estação {} não encotrada", sensorVO.getIdStation());
			            return new ResponseEntity(new CustomErrorType("Estação "+ sensorVO.getIdStation() + " não encontrada: "), HttpStatus.NO_CONTENT);
					}
					station.getSensors().add(sensor);
					stationDAO.save(station);
					sensor = station.getSensors().get(station.getSensors().size()-1);
					return new ResponseEntity(new SensorVO(sensor, station.getId()), HttpStatus.OK);
				}
				return new ResponseEntity(sensor, HttpStatus.OK);
			}catch (Exception e) {
				logger.error("Ocorreu um erro inesperado");
	            return new ResponseEntity(new CustomErrorType("Ocorreu um erro inesperado"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
	}
//	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/sensor/delete", method=RequestMethod.POST)
	@ApiOperation(value="Deleta um sensor")
	public ResponseEntity<?> deleteStation(@RequestBody SensorVO sensorVO) {
		Sensor sensor = sensorDAO.findById(sensorVO.getIdSensor());
		
		if(sensor==null) {
			logger.error("Snesor não encotrado para a remoção.");
			return new ResponseEntity(new CustomErrorType("Sensor não encotrado para a remoção."), HttpStatus.NO_CONTENT);
		}else {
			try {
				if(sensorVO.getIdStation()!=0) {
					Station station = stationDAO.findById(sensorVO.getIdStation());
					if(station == null) {
						logger.error("Estação {} não encotrada", sensorVO.getIdStation());
			            return new ResponseEntity(new CustomErrorType("Estação "+ sensorVO.getIdStation() + " não encontrada: "), HttpStatus.NO_CONTENT);
					}
					if(station.getSensors().contains(sensor)) {
						station.getSensors().remove(sensor);
						stationDAO.save(station);
						return new ResponseEntity(new CustomErrorType("Sensor foi removido da estação "), HttpStatus.OK);
					}
					logger.error("Sensor {} não está presente na estação {}", sensorVO.getIdSensor(), sensorVO.getIdStation());
		            return new ResponseEntity(new CustomErrorType("Sensor "+sensorVO.getIdSensor()+" não está presente na estação "+ sensorVO.getIdStation() + " não encontrada: "), HttpStatus.NO_CONTENT);
					
				}else {
					sensorDAO.delete(sensor);
					return new ResponseEntity(sensor, HttpStatus.OK);
				}
				
			}catch (Exception e){
				logger.error("Ocorreu um erro na remoção do sensor.");
				return new ResponseEntity(new CustomErrorType("Ocorreu um erro na remoção do sensor."), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
}
