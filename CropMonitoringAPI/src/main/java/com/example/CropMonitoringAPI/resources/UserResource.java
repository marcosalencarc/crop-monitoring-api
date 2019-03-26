package com.example.CropMonitoringAPI.resources;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
import com.example.CropMonitoringAPI.VO.UserVO;
import com.example.CropMonitoringAPI.models.*;
import com.example.CropMonitoringAPI.util.CustomErrorType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value="/cropMonitoringApi")
@Api(value="API Rest Crop Monitoring")
@CrossOrigin(origins="*")
public class UserResource {

	public static final Logger logger = LoggerFactory.getLogger(UserResource.class);
	
	@Autowired
	UserDAO userDAO;
	@Autowired
	StationDAO stationDAO;
	
	
	@RequestMapping(value="/user/get", method = RequestMethod.GET)
	@ApiOperation(value="Retorna a lista de usuário do sistema")
	public ResponseEntity<?>  listUsers(){
		List<User> users = userDAO.findAll();
		if(users == null || users.isEmpty()) {
			return new ResponseEntity<>(new CustomErrorType("Nenhum usuário encontrado"),HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/user/get/{id}", method = RequestMethod.GET)
	@ApiOperation(value="Retorna um usuário")
	public ResponseEntity<?> getUser(@PathVariable(value="id") long id){
		logger.info("Fetching User with id {}", id);
        User user = userDAO.findById(id);
        if (user == null) {
            logger.error("Usuário com id {} não encontrado.", id);
            return new ResponseEntity(new CustomErrorType("Usuário com id " + id + " não encontrado"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/user/post", method=RequestMethod.POST)
	@ApiOperation(value="Salva um usuário")
	public ResponseEntity<?> saveUser(@RequestBody UserVO userVO) {
		//Novo Usuário
		User user;
		if(userVO.getId() == 0) {
			
			try {
				user = new User(userVO);
				user.setStations(new ArrayList<Station>());
				user = userDAO.save(user);
				if(user != null) {
					return new ResponseEntity(user, HttpStatus.OK);
				}else {
					logger.error("Ocorreu um erro na hora de salvar o usuário.");
					return new ResponseEntity(new CustomErrorType("Ocorreu um erro na hora de salvar o usuário."), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}catch (Exception e) {
				logger.error("Ocorreu um erro na hora de salvar o usuário.");
				return new ResponseEntity(new CustomErrorType("Ocorreu um erro na hora de salvar o usuário."), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		//Update
		else{
			try {
				user = userDAO.findById(userVO.getId());
				if(userVO.getLogin()!=null && !userVO.getLogin().isEmpty()) user.setLogin(userVO.getLogin());
				if(userVO.getName()!=null && !userVO.getName().isEmpty()) user.setName(userVO.getName());
				if(userVO.getPassword()!=null && !userVO.getPassword().isEmpty()) user.setPassword(userVO.getPassword());
				user = userDAO.save(user);
				return new ResponseEntity(user, HttpStatus.OK);
			}catch (Exception e) {
				logger.error("Ocorreu um erro ao atualizar o usuário.");
				return new ResponseEntity(new CustomErrorType("Ocorreu um erro ao atualizar o usuário."), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
		
	@RequestMapping(value="/user/delete", method=RequestMethod.POST)
	@ApiOperation(value="Deleta um usurário")
	public ResponseEntity<?> deleteUser(@RequestBody UserVO userVO) {
		User user = userDAO.findById(userVO.getId());
		if(user==null) {
			logger.error("Usuário não encotrado para a remoção.");
			return new ResponseEntity(new CustomErrorType("Usuário não encotrado para a remoção."), HttpStatus.NOT_FOUND);
		}else {
			try {
				userDAO.delete(user);
				return new ResponseEntity(user, HttpStatus.OK);
			}catch (Exception e){
				logger.error("Ocorreu um erro ao remover o usuário.");
				return new ResponseEntity(new CustomErrorType("Ocorreu um erro ao remover o usuário."), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	
}
