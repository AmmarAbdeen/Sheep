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
import com.saudi.sheeps.dto.MedicineDTO;
import com.saudi.sheeps.dto.MedicineDiseaseOfSheepDTO;
import com.saudi.sheeps.service.JWTService;
import com.saudi.sheeps.service.MedicineService;

import io.jsonwebtoken.Claims;
import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep/medicine")
public class MedicineController extends BaseController{
	
	@Autowired
	private MedicineService medicineService;
	@Autowired
	protected JWTService jwtservice;
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/savemedicine")
	public ResponseEntity<?> saveMedicine(@RequestHeader("session-token") String sessionToken,@RequestBody MedicineDTO request) {
		try {
			log.info("Enter saveMedicine API...with request :" + request.toString());
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			MedicineDTO response = medicineService.addMedicine(request);
			return success(new Gson().toJson(response));

		} catch (Exception e) {
			return wrapException(e, "addSheep");

		}
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/savemedicinesheep")
	public ResponseEntity<?> saveMedicineSheep(@RequestHeader("session-token") String sessionToken,@RequestBody MedicineDiseaseOfSheepDTO request) {
		try {
			log.info("Enter saveMedicineSheep API...with request :" + request.toString());
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			MedicineDiseaseOfSheepDTO response = medicineService.addMedicineDiseaseOfSheep(request);
			return success(new Gson().toJson(response));

		} catch (Exception e) {
			return wrapException(e, "saveMedicineSheep");

		}
	}
	
	@GetMapping(value = "/getmedicines")
	public ResponseEntity<?> getAllMedicines(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(medicineService.getAllMedicines());

		} catch (Exception e) {
			return wrapException(e, "getAllMedicines");

		}
	}
	
	@GetMapping(value = "/getallvalidmedicine")
	public ResponseEntity<?> getAllValidMedicine(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(medicineService.getAllValidMedicine());

		} catch (Exception e) {
			return wrapException(e, "getAllValidMedicine");

		}
	}
	

}
