package com.example.CropMonitoringAPI.resources;

import java.util.ArrayList;
import java.util.Date;
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
import com.example.CropMonitoringAPI.DAO.SensorReadDAO;
import com.example.CropMonitoringAPI.DAO.StationDAO;
import com.example.CropMonitoringAPI.VO.SensorReadVO;
import com.example.CropMonitoringAPI.models.Sensor;
import com.example.CropMonitoringAPI.models.SensorRead;
import com.example.CropMonitoringAPI.models.Station;
import com.example.CropMonitoringAPI.util.CustomErrorType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value="/cropMonitoringApi")
@Api(value="API Rest Crop Monitoring")
@CrossOrigin(origins="*")
public class SensorReadResource {

public static final Logger logger = LoggerFactory.getLogger(SensorReadResource.class);

	
	@Autowired
	SensorReadDAO sensorReadDAO;
	@Autowired
	SensorDAO sensorDAO;
	@Autowired
	StationDAO stationDAO;
//	
//	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sensorRead/station/{idStation}/sensor/{idSensor}", method = RequestMethod.GET)
	@ApiOperation(value="Retorna a lista de leituras de um sensor de uma determinada estação")
    public ResponseEntity<?> listAllSensorRead(@PathVariable("idStation") int idStation, @PathVariable("idSensor") int idSensor) {
		if(idStation <=0 || idSensor<=0) {
			logger.error("Erro nos valores");
            return new ResponseEntity(new CustomErrorType("Algum dos campos da leitura veio inválido"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<SensorRead> sensorReads = sensorReadDAO.findAllBySensorAndStation(idSensor, idStation);
		if (sensorReads.isEmpty()) {
			return new ResponseEntity(new CustomErrorType("Nenhuma leitura encontrada"),HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity(SensorReadVO.getListVO(sensorReads), HttpStatus.OK);
        
    }
//
//
	@SuppressWarnings("unchecked")
	@PostMapping("/sensorRead/post")
	@ApiOperation(value="Salva uma lista de leitura")
	public ResponseEntity<?> saveSensorRead(@RequestBody List<SensorReadVO> sensorReadList) {
		List<SensorReadVO> response = new ArrayList<>();
		List<SensorRead> persist = new ArrayList<>();
		if(sensorReadList.isEmpty()) {
			logger.error("Sem leituras na requisição");
            return new ResponseEntity(new CustomErrorType("Sem leituras na requisição"), HttpStatus.NO_CONTENT);
		}
		for(SensorReadVO sensorRead: sensorReadList) {
			if(sensorRead.getIdSensor() == 0 || sensorRead.getIdStation() == 0) {
				logger.error("Não foi possivel adicionar a leitura, estação ou sensor não encontrado.");
	            return new ResponseEntity(new CustomErrorType("Não foi possivel adicionar a leitura, estação ou sensor não encontrado."), HttpStatus.NO_CONTENT);
			}else {
				try {
					Station station = stationDAO.findById(sensorRead.getIdStation());
					Sensor sensor = sensorDAO.findById(sensorRead.getIdSensor());
					if(sensorReadDAO.findAllBySensorAndStation(sensorRead.getIdSensor(), sensorRead.getIdStation()).isEmpty() || sensor == null || station == null ) {
						logger.error("Não foi possivel adicionar a leitura, estação ou sensor não encontrado.");
			            return new ResponseEntity(new CustomErrorType("Não foi possivel adicionar a leitura, estação ou sensor não encontrado."), HttpStatus.NO_CONTENT);
					}
					SensorRead sensorReadSave = new SensorRead(sensorRead.getValue(), new Date(), sensor, station);
					if(!sensorReadSave.isValid()) {
						logger.error("Erro nos valores");
			            return new ResponseEntity(new CustomErrorType("Algum dos campos da leitura veio inválido"), HttpStatus.INTERNAL_SERVER_ERROR);
					}
					persist.add(sensorReadSave);
				}catch (Exception e ) {
					logger.error("Ocorreu um erro inesperado {}", e.getMessage());
					e.printStackTrace();
		            return new ResponseEntity(new CustomErrorType("Ocorreu um erro inesperado:"+e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		persist = sensorReadDAO.saveAll(persist);
		for(SensorRead s: persist) response.add(new SensorReadVO(s));
		return new ResponseEntity(response, HttpStatus.OK);
		
	}
//	

	
}
