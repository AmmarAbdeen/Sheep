package com.saudi.sheeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.saudi.sheeps.dto.BaseDTO;
import com.saudi.sheeps.dto.SheepDTO;
import com.saudi.sheeps.dto.UserDTO;
import com.saudi.sheeps.service.JWTService;
import com.saudi.sheeps.service.SheepService;
import com.saudi.sheeps.service.UserService;

import io.jsonwebtoken.Claims;
import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep")
public class SheepController extends BaseController {
	
	@Autowired
	private SheepService sheepService;
	@Autowired
	private UserService userService;
	@Autowired
	protected JWTService jwtservice;
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody BaseDTO encodedRequest) {
		try {
			log.info("Enter Login API...with request :" + encodedRequest.toString());
			encodedRequest.setEncryptedDataType(UserDTO.class);
			UserDTO user = getDecodedrequest(encodedRequest);
			return success(userService.login(user));

		} catch (Exception e) {
			return wrapException(e, "merchantLogin");

		}
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/addsheep")
	public ResponseEntity<?> addSheep(@RequestHeader("session-token") String sessionToken,@RequestBody SheepDTO sheepRequest){
		try {
			log.info("Enter addSheep API...with request :" + sheepRequest.toString());
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			SheepDTO sheepResponse;
			if(sheepRequest.getId() !=null && sheepRequest.getId() !=0) {
				 sheepResponse =sheepService.updateSheep(sheepRequest);
			}else {
				sheepResponse =sheepService.addNewSheep(sheepRequest);
			}
			return success(new Gson().toJson(sheepResponse));

		} catch (Exception e) {
			return wrapException(e, "addSheep");

		}
	}
	
	
	@PostMapping(value = "/getsheeps")
	public ResponseEntity<?> searchSheeps(@RequestHeader("session-token") String sessionToken,@RequestBody SheepDTO sheepRequest ) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(sheepService.sheepsSearch(sheepRequest));

		} catch (Exception e) {
			return wrapException(e, "searchSheeps");

		}
	}
	
	@PostMapping(value = "/getsheepbydata")
	public ResponseEntity<?> getSheepByData(@RequestHeader("session-token") String sessionToken,@RequestBody SheepDTO sheepRequest ) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(sheepService.getSheepBySpecificData(sheepRequest));

		} catch (Exception e) {
			return wrapException(e, "getSheepForEdit");

		}
	}
	
	@GetMapping(value = "/getewes")
	public ResponseEntity<?> getAllEwes(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(sheepService.getEwes());

		} catch (Exception e) {
			return wrapException(e, "searchSheeps");

		}
	}
	@GetMapping(value = "/getallsheeps")
	public ResponseEntity<?> getAllSheeps(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(sheepService.getAllSheeps());

		} catch (Exception e) {
			return wrapException(e, "getAllSheeps");

		}
	}

}
