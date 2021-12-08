package com.saudi.sheeps.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
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
			
			List<Privileges> privileges = new ArrayList<>();
			for (Privileges privilege : privilegesDAO.findAllByUserIdOrderByPrivilegeorder(user.getId())) {
				if (privilege.isMainPrivilege() || privilege.isMenuItem())
					privileges.add(privilege);
			}
			UserDTO userDTO = mapToDTO(user,false,false);
			userDTO.setPrivileges(mapToDTOPrivilegesList(privileges));
			userDTO.setSessionToken(token);
			return userDTO;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		
	}
	
	public String getAllPrivilegesParentWithChildren (Long userId)throws BusinessException{
		try {
			List<PrivilegesDTO> privilegesDTOs =mapToDTOPrivilegesList(privilegesDAO.findAllByUserIdAndAdminPrivilegeOrderByPrivilegeorder(userId, false));
			List<PrivilegesDTO> parentprivileges = new ArrayList<>();
		
			for (PrivilegesDTO privilegeDTO : privilegesDTOs) {
				if(privilegeDTO.getParentPrivilegeId() == null) {
				privilegeDTO = getChildrenprivileges(privilegeDTO,privilegesDTOs);
				parentprivileges.add(privilegeDTO);
				}
				
			}
	
			return new Gson().toJson(parentprivileges);
		} catch (Exception e) {
			throw new BusinessException("At  getprivileges Fun " + e.getMessage(), e);
		}
	}
	
	public String getAllPrivileges(Long userId)throws BusinessException{
		try {
			List<PrivilegesDTO> privilegesDTOs =mapToDTOPrivilegesList(privilegesDAO.findAllByUserIdAndAdminPrivilegeOrderByPrivilegeorder(userId, false));
			return new Gson().toJson(privilegesDTOs);
		} catch (Exception e) {
			throw new BusinessException("At  getprivileges Fun " + e.getMessage(), e);
		}
	
		}
	
	public PrivilegesDTO getChildrenprivileges(PrivilegesDTO privilegeDTO,List<PrivilegesDTO> privilegesDTOs) throws BusinessException {

		List<PrivilegesDTO> childrenprivileges = new ArrayList<>();
		for (PrivilegesDTO privilege : privilegesDTOs) {
			if (privilege.getParentPrivilegeId() != null
					&& privilege.getParentPrivilegeId().equals(privilegeDTO.getId())) {
				childrenprivileges.add(privilege);
			}
		}
		sortChildrenPrivileges(childrenprivileges);
		for (PrivilegesDTO childrenprivilege : childrenprivileges) {
			childrenprivilege = getChildrenprivileges(childrenprivilege, privilegesDTOs);
		}
		privilegeDTO.setChildrenPrivilege(childrenprivileges);
		return privilegeDTO;
	}
	
	private void sortChildrenPrivileges(List<PrivilegesDTO> childrenprivileges) {
		if (childrenprivileges.size() > 1) {
			Collections.sort(childrenprivileges,
					(childrenprivilege1, childrenprivilege2) -> (int) (childrenprivilege1.getPrivilegeorder()
							- childrenprivilege2.getPrivilegeorder()));
		}
	}
	
	public String getAllUsers () throws BusinessException{
		return new Gson().toJson(mapToDTOList(userDAO.findAllUsersWithoutadmin(),true));
	}
	public String getUserById(Long id) throws BusinessException{
		Optional<User> user = userDAO.findById(id);
		return new Gson().toJson(mapToDTO(user.get(), true,true));

	}
	
	public UserDTO updateUserWithPrivileges(UserDTO userDTO ) throws BusinessException {
		try {
			if (userDTO.getPrivileges().isEmpty())
				throw new BusinessException("Error:  Privileges are empty.");
			User user = new User();
			Optional<User> userResult = userDAO.findById(userDTO.getId());
			if (userResult.isPresent()) {
				user = userResult.get();
			}
			if (userDTO.getUserName() == null)
				throw new BusinessException("At update  : Missing UserName");
			if (userDTO.getPassword() == null)
				throw new BusinessException("At update  : Missing Password");
			if (userDTO.getEmail() == null)
				throw new BusinessException("At update  : Missing Email");
			if (userDTO.getFullName() == null)
				throw new BusinessException("At update  : Missing FullName");
			if (userDTO.getMobileNumber() == null)
				throw new BusinessException("At update  : Missing MobileNumber");
			if (userDTO.getPrivileges() == null)
				throw new BusinessException("At update : no privileges chosen ");
			if (userDAO.findByUserNameContainingIgnoreCaseAndIdNot(userDTO.getUserName(),userDTO.getId()) != null) {
				throw new BusinessException("At Registration  : This  UserName already exists");
			}
			
			user.setUserName(userDTO.getUserName());
			user.setPassword(userDTO.getPassword());
			user.setEmail(userDTO.getEmail());
			user.setMobileNumber(userDTO.getMobileNumber());
			user.setFullName(userDTO.getFullName());
			user.setNationalId(userDTO.getNationalId());
			List<Privileges> privileges = new ArrayList<>();

			for (PrivilegesDTO privilege : userDTO.getPrivileges()) {
				privileges.add(mapPrivilegDTOToEntity(privilege));
			}
			user.setPrivilege(privileges);
			userDAO.save(user);
			return mapToDTO(user,false,false);
			
		}catch (Exception e) {
			throw new BusinessException("At  updateUserWithPrivileges Fun " + e.getMessage(), e);
		}
	}
	
	public UserDTO addNewUserWithPrivileges(UserDTO userDTO ) throws BusinessException {
		try {
			if (userDTO.getUserName() == null)
				throw new BusinessException("At creation  : Missing UserName");
			if (userDTO.getPassword() == null)
				throw new BusinessException("At creation  : Missing Password");
			if (userDTO.getEmail() == null)
				throw new BusinessException("At creation  : Missing Email");
			if (userDTO.getFullName() == null)
				throw new BusinessException("At creation  : Missing FullName");
			if (userDTO.getMobileNumber() == null)
				throw new BusinessException("At creation  : Missing MobileNumber");
			if (userDTO.getPrivileges() == null)
				throw new BusinessException("At creation : no privileges chosen ");
			if (userDAO.findByUserNameContainingIgnoreCase(userDTO.getUserName()) != null) {
				throw new BusinessException("At Registration  : This  UserName already exists");
			}
			 User user = userDAO.save(mapToEntity(userDTO));
			
			return mapToDTO(user,false,false);
		}catch (Exception e) {
			throw new BusinessException("At  addNewUserWithPrivileges Fun " + e.getMessage(), e);
		}
	}
	
	public List<PrivilegesDTO> mapToDTOPrivilegesList(List<Privileges> privileges){
		List<PrivilegesDTO> privilegesDTOs = new ArrayList<>();
		for(Privileges privilege : privileges) {
			privilegesDTOs.add(mapPrivilegToDTO(privilege));
		}
		return privilegesDTOs;
	}
	
	public List<UserDTO> mapToDTOList(List<User> users,boolean showSensitiveData){
		List<UserDTO> userDTOs = new ArrayList<>();
		for(User user : users) {
			userDTOs.add(mapToDTO(user,showSensitiveData,false));
		}
		return userDTOs;
	}

	public UserDTO mapToDTO(User user ,boolean showSesensitiveData,boolean addPrivileges) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setFullName(user.getFullName());
		userDTO.setUserName(user.getUserName());
		userDTO.setEmail(user.getEmail());
		userDTO.setMobileNumber(user.getMobileNumber());
		userDTO.setNationalId(user.getNationalId());
		if(showSesensitiveData) {
			userDTO.setPassword(user.getPassword());
		}
		if (addPrivileges) {
			for (Privileges privilege : user.getPrivilege()) {
				PrivilegesDTO privilegeDTO = new PrivilegesDTO();
				privilegeDTO.setId(privilege.getId());
				privilegeDTO.setCode(privilege.getCode());
				privilegeDTO.setArabicName(privilege.getArabicName());
				privilegeDTO.setEnglishName(privilege.getEnglishName());
				privilegeDTO.setAdminPrivilege(privilege.isAdminPrivilege());
				privilegeDTO.setMainPrivilege(privilege.isMainPrivilege());
				privilegeDTO.setIconPath(privilege.getIconPath());
				privilegeDTO.setBackendUrl(privilege.getBackendUrl());
				privilegeDTO.setRouterLink(privilege.getRouterLink());
				if (privilege.getParentPrivilege() != null) {
					privilegeDTO.setParentPrivilegeId(privilege.getParentPrivilege().getId());
				}
				userDTO.getPrivileges().add(privilegeDTO);
			}
		}
		return userDTO;
	}
	
	public User mapToEntity(UserDTO userDTO ) {
		User user = new User();
		user.setUserName(userDTO.getUserName());
		user.setFullName(userDTO.getFullName());
		user.setPassword(userDTO.getPassword());
		user.setEmail(userDTO.getEmail());
		user.setMobileNumber(userDTO.getMobileNumber());
		user.setNationalId(userDTO.getNationalId());
		List<Privileges> privileges = new ArrayList<>();
		for (PrivilegesDTO privilegesDTO : userDTO.getPrivileges()) {
			privileges.add(mapPrivilegDTOToEntity(privilegesDTO));
		}
		user.setPrivilege(privileges);
		return user;
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
	
	public Privileges mapPrivilegDTOToEntity(PrivilegesDTO privilegeDTO ) {
		Privileges privilege = new Privileges();
		privilege.setId(privilegeDTO.getId());
		privilege.setCode(privilegeDTO.getCode());
		privilege.setArabicName(privilegeDTO.getArabicName());
		privilege.setEnglishName(privilegeDTO.getEnglishName());
		privilege.setIconPath(privilegeDTO.getIconPath());
		privilege.setRouterLink(privilegeDTO.getRouterLink());
		privilege.setBackendUrl(privilegeDTO.getBackendUrl());
		privilege.setMainPrivilege(privilegeDTO.isMainPrivilege());
		privilege.setMenuItem(privilegeDTO.isMenuItem());
		privilege.setAdminPrivilege(privilegeDTO.isAdminPrivilege());
		privilege.setPrivilegeorder(privilegeDTO.getPrivilegeorder());
		if(privilegeDTO.getParentPrivilegeId() != null) {
		Privileges privileges = privilegesDAO.findById(privilegeDTO.getParentPrivilegeId()).get();
		privilege.setParentPrivilege(privileges);
		}
		return privilege;
	}

}
