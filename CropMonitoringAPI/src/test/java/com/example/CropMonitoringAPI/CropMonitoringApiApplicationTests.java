package com.example.CropMonitoringAPI;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.CropMonitoringAPI.VO.SensorReadVO;
import com.example.CropMonitoringAPI.models.SensorRead;
import com.example.CropMonitoringAPI.resources.SensorReadResource;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CropMonitoringApiApplicationTests {

	 @Autowired

     private SensorReadResource sensorResource;

	
	
	@Test
	public void listaSensorReadTest() {
		//Testar se recupera as leituras da Estação 1 e Sensor 1
		ResponseEntity<?> response =  sensorResource.listAllSensorRead(1, 1);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void listaSensorReadInválidoTest() {
		//Testar se recupera as leituras da Estação 2 e Sensor 3
		ResponseEntity<?> response =  sensorResource.listAllSensorRead(2, 3);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	@Test
	public void enviaLeituraTestTest() {
		//Testar enviar uma leitura com dados válidos
		//Leitura da estação 1 sensor 1
		SensorReadVO mockSensorTemperatura = new SensorReadVO(1,1,27.0);
		//Leitura da estação 1 sensor 2
		SensorReadVO mockSensorUmidade = new SensorReadVO(2,1,80.0);
		//Leitura da estação 1 sensor 2
		SensorReadVO mockSensorPrecipitacao = new SensorReadVO(3,1,50.0);
		
		ArrayList<SensorReadVO> leitura = new ArrayList<>();
		leitura.add(mockSensorTemperatura);
		leitura.add(mockSensorUmidade);
		leitura.add(mockSensorPrecipitacao);
		ResponseEntity<?> response =  sensorResource.saveSensorRead(leitura);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void enviaLeituraDadosInvalidosTest() {
		//Testar enviar uma leitura com dados válidos
		//Leitura do sensor 3 da estação 1
		SensorReadVO mockSensorTemperatura = new SensorReadVO(1,1,27.0);
		//Leitura do sensor 2 da estação 1
		SensorReadVO mockSensorUmidade = new SensorReadVO(2,1,110.0);
		//Leitura do sensor 3 da estação 1
		SensorReadVO mockSensorPrecipitacao = new SensorReadVO(3,1,50.0);
		
		ArrayList<SensorReadVO> leitura = new ArrayList<>();
		leitura.add(mockSensorTemperatura);
		leitura.add(mockSensorUmidade);
		leitura.add(mockSensorPrecipitacao);
		ResponseEntity<?> response =  sensorResource.saveSensorRead(leitura);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

}
