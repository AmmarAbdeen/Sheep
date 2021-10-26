package com.saudi.sheeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.saudi.sheeps.dto.PlacesDTO;
import com.saudi.sheeps.dto.PlacesFeedDTO;
import com.saudi.sheeps.service.PlacesService;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep/places")
public class PlacesController  extends BaseController{
	@Autowired
	private PlacesService placesService;
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/saveplace")
	public ResponseEntity<?> savePlace(@RequestBody PlacesDTO request) {
		try {
			log.info("Enter savePlace API...with request :" + request.toString());
			PlacesDTO response = placesService.addPlace(request);
			return success(new Gson().toJson(response));

		} catch (Exception e) {
			return wrapException(e, "savePlace");

		}
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/saveplacesfeed")
	public ResponseEntity<?> savePlaceFeed(@RequestBody PlacesFeedDTO request) {
		try {
			log.info("Enter savePlaceFeed API...with request :" + request.toString());
			PlacesFeedDTO response = placesService.addPlaceFeed(request);
			return success(new Gson().toJson(response));

		} catch (Exception e) {
			return wrapException(e, "savePlaceFeed");

		}
	}
	
	@GetMapping(value = "/getallplaces")
	public ResponseEntity<?> getAllPlaces() {
		try {

			return success(placesService.getAllPlaces());

		} catch (Exception e) {
			return wrapException(e, "getAllPlaces");

		}
	}
	
	@GetMapping(value = "/getallplacesfeed")
	public ResponseEntity<?> getAllPlacesFeed() {
		try {

			return success(placesService.getAllPlacesFeed());

		} catch (Exception e) {
			return wrapException(e, "getAllPlacesFeed");

		}
	}

}
