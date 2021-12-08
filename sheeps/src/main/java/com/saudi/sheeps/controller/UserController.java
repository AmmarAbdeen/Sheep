package com.saudi.sheeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saudi.sheeps.dto.SheepDTO;
import com.saudi.sheeps.dto.UserDTO;
import com.saudi.sheeps.service.JWTService;
import com.saudi.sheeps.service.UserService;

import io.jsonwebtoken.Claims;
import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep/user")
public class UserController extends BaseController{
	
	@Autowired
	protected JWTService jwtservice;
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/getparentprivileges/{userId}")
	public ResponseEntity<?> getParentPrivilegesByUser(@RequestHeader("session-token") String sessionToken,@PathVariable("userId") Long userId) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(userService.getAllPrivilegesParentWithChildren(userId));

		} catch (Exception e) {
			return wrapException(e, "getParentPrivilegesByUser");

		}
	}
	
	@GetMapping(value = "/getallprivileges/{userId}")
	public ResponseEntity<?> getAllPrivilegesByUser(@RequestHeader("session-token") String sessionToken,@PathVariable("userId") Long userId) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(userService.getAllPrivileges(userId));

		} catch (Exception e) {
			return wrapException(e, "getAllPrivilegesByUser");

		}
	}
	
	@GetMapping(value = "/getallusers")
	public ResponseEntity<?> getAllUsers(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(userService.getAllUsers());

		} catch (Exception e) {
			return wrapException(e, "getAllUsers");

		}
	}
	
	@GetMapping(value = "/getuserinfo/{id}")
	public ResponseEntity<?> getUserById(@RequestHeader("session-token") String sessionToken, @PathVariable("id") Long id) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(userService.getUserById(id));

		} catch (Exception e) {
			return wrapException(e, "getUserById");

		}
	}
	
	@PostMapping(value = "/newuser")
	public ResponseEntity<?> addNewUser(@RequestHeader("session-token") String sessionToken,@RequestBody UserDTO uesrRequest ) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(userService.addNewUserWithPrivileges(uesrRequest));

		} catch (Exception e) {
			return wrapException(e, "addNewUser");

		}
	}
	
	@PostMapping(value = "/updateuser")
	public ResponseEntity<?> updateUser(@RequestHeader("session-token") String sessionToken,@RequestBody UserDTO uesrRequest ) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(userService.updateUserWithPrivileges(uesrRequest));

		} catch (Exception e) {
			return wrapException(e, "updateUser");

		}
	}

}
