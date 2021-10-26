package com.saudi.sheeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.saudi.sheeps.dto.LambsDTO;
import com.saudi.sheeps.dto.SheepDTO;
import com.saudi.sheeps.service.LambsService;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep/lambs")
public class LambsController extends BaseController{
	
	@Autowired
	private LambsService lambsService;
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/addlamb")
	public ResponseEntity<?> addLamb(@RequestBody LambsDTO lambsRequest){
		try {
			log.info("Enter addLamb API...with request :" + lambsRequest.toString());
			LambsDTO LambsResponse;
			if(lambsRequest.getId() !=null && lambsRequest.getId() !=0) {
		        LambsResponse =lambsService.updateLamb(lambsRequest);
			}else {
				LambsResponse =lambsService.addNewLamb(lambsRequest);
			}		
			
			return success(new Gson().toJson(LambsResponse));

		} catch (Exception e) {
			return wrapException(e, "addSheep");

		}
	}
	
	@PostMapping(value = "/getlambs")
	public ResponseEntity<?> searchLambs(@RequestBody LambsDTO lambDTO ) {
		try {

			return success(lambsService.lambsSearch(lambDTO));

		} catch (Exception e) {
			return wrapException(e, "searchSheeps");

		}
	}
	
	@PostMapping(value = "/getlambbydata")
	public ResponseEntity<?> getLambByData(@RequestBody LambsDTO lambDTO ) {
		try {

			return success(lambsService.getLambBySpecificData(lambDTO));

		} catch (Exception e) {
			return wrapException(e, "getLambByData");

		}
	}

}
