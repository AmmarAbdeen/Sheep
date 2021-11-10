package com.saudi.sheeps.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudi.sheeps.dao.UserDAO;
import com.saudi.sheeps.dto.UserDTO;
import com.saudi.sheeps.entity.User;
import com.saudi.sheeps.exception.BusinessException;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO; 
	@Autowired
	private JWTService jwtservice;
	
	public UserDTO login(UserDTO request) throws BusinessException{
		try {
			log.info("Enter login Function...with request :" + request);
			User user = userDAO.findByUserNameContainingIgnoreCaseAndPassword(request.getUserName(),
					request.getPassword());
			
			if (user == null) {
				throw new BusinessException("Invalid  UserName or password ");
			}
			Map<String, String> claims = new HashMap<>();
			claims.put("userName", request.getUserName());
			Map<String, Object> headers = new HashMap<>();
			String token = jwtservice.createJWT(headers, claims);
			request.setSessionToken(token);
			return request;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
	}

}
