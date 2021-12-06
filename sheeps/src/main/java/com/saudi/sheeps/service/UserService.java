package com.saudi.sheeps.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saudi.sheeps.dao.PrivilegesDAO;
import com.saudi.sheeps.dao.UserDAO;
import com.saudi.sheeps.dto.PrivilegesDTO;
import com.saudi.sheeps.dto.UserDTO;
import com.saudi.sheeps.entity.Privileges;
import com.saudi.sheeps.entity.User;
import com.saudi.sheeps.exception.BusinessException;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO; 
	@Autowired
	private PrivilegesDAO privilegesDAO; 
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
			
			List<Privileges> privileges = new ArrayList<>();
			for (Privileges privilege : privilegesDAO.findAllByUserIdOrderByPrivilegeorder(user.getId())) {
				if (privilege.isMainPrivilege() || privilege.isMenuItem())
					privileges.add(privilege);
			}
			request.setPrivileges(mapToDTOPrivilegesList(privileges));
			return request;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
	}
	
	public List<PrivilegesDTO> mapToDTOPrivilegesList(List<Privileges> privileges){
		List<PrivilegesDTO> privilegesDTOs = new ArrayList<>();
		for(Privileges privilege : privileges) {
			privilegesDTOs.add(mapPrivilegToDTO(privilege));
		}
		return privilegesDTOs;
	}
	
	public PrivilegesDTO mapPrivilegToDTO(Privileges privilege ) {
		PrivilegesDTO privilegeDTO = new PrivilegesDTO();
		privilegeDTO.setId(privilege.getId());
		privilegeDTO.setCode(privilege.getCode());
		privilegeDTO.setArabicName(privilege.getArabicName());
		privilegeDTO.setEnglishName(privilege.getEnglishName());
		privilegeDTO.setIconPath(privilege.getIconPath());
		privilegeDTO.setRouterLink(privilege.getRouterLink());
		privilegeDTO.setBackendUrl(privilege.getBackendUrl());
		privilegeDTO.setMainPrivilege(privilege.isMainPrivilege());
		privilegeDTO.setMenuItem(privilege.isMenuItem());
		privilegeDTO.setAdminPrivilege(privilege.isAdminPrivilege());
		privilegeDTO.setPrivilegeorder(privilege.getPrivilegeorder());
		privilegeDTO.setParentPrivilegeId(privilege.getParentPrivilege() != null ?privilege.getParentPrivilege().getId():null);
		return privilegeDTO;
	}

}
