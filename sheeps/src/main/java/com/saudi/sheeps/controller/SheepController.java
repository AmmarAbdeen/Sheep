package com.saudi.sheeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.saudi.sheeps.dto.SheepDTO;
import com.saudi.sheeps.service.SheepService;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep")
public class SheepController extends BaseController {
	
	@Autowired
	private SheepService sheepService;
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/addsheep")
	public ResponseEntity<?> addSheep(@RequestBody SheepDTO sheepRequest){
		try {
			log.info("Enter addSheep API...with request :" + sheepRequest.toString());
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
	public ResponseEntity<?> searchSheeps(@RequestBody SheepDTO sheepRequest ) {
		try {

			return success(sheepService.sheepsSearch(sheepRequest));

		} catch (Exception e) {
			return wrapException(e, "searchSheeps");

		}
	}
	
	@PostMapping(value = "/getsheepbydata")
	public ResponseEntity<?> getSheepByData(@RequestBody SheepDTO sheepRequest ) {
		try {

			return success(sheepService.getSheepBySpecificData(sheepRequest));

		} catch (Exception e) {
			return wrapException(e, "getSheepForEdit");

		}
	}
	
	@GetMapping(value = "/getewes")
	public ResponseEntity<?> getAllEwes() {
		try {

			return success(sheepService.getEwes());

		} catch (Exception e) {
			return wrapException(e, "searchSheeps");

		}
	}

}
