package com.saudi.sheeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.saudi.sheeps.dto.SheepMovementDTO;
import com.saudi.sheeps.service.SheepMovementService;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep/movement")
public class SheepMovementController extends BaseController{
	
	@Autowired
	private SheepMovementService movementService;
	
	@PostMapping(value = "/savemovements")
	public ResponseEntity<?> addSheepMovement(@RequestBody SheepMovementDTO request){
		try {
			log.info("Enter addSheepMovement API...with request :" + request.toString());
			SheepMovementDTO sheepResponse;
				 sheepResponse =movementService.addSheepMovement(request);
			return success(new Gson().toJson(sheepResponse));

		} catch (Exception e) {
			return wrapException(e, "addSheepMovement");

		}
	}
	
	@PostMapping(value = "/getsheepmovements")
	public ResponseEntity<?> searchSheepsMovements(@RequestBody SheepMovementDTO request ) {
		try {

			return success(movementService.sheepsMovementSearch(request));

		} catch (Exception e) {
			return wrapException(e, "searchSheepsMovements");

		}
	}

}
