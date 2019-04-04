package com.example.CropMonitoringAPI;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.CropMonitoringAPI.VO.SensorReadVO;
import com.example.CropMonitoringAPI.resources.SensorReadResource;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CropMonitoringApiApplicationTests {

	 @Autowired

     private SensorReadResource sensorResource;

	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void listaSensorReadTest() {
		ResponseEntity<?> response =  sensorResource.listAllSensorRead(15, 2);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}

}
