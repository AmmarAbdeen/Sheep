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
import com.saudi.sheeps.dto.PlacesDTO;
import com.saudi.sheeps.dto.PlacesFeedDTO;
import com.saudi.sheeps.service.JWTService;
import com.saudi.sheeps.service.PlacesService;

import io.jsonwebtoken.Claims;
import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep/places")
public class PlacesController  extends BaseController{
	@Autowired
	private PlacesService placesService;
	@Autowired
	protected JWTService jwtservice;
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/saveplace")
	public ResponseEntity<?> savePlace(@RequestHeader("session-token") String sessionToken,@RequestBody PlacesDTO request) {
		try {
			log.info("Enter savePlace API...with request :" + request.toString());
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			PlacesDTO response = placesService.addPlace(request);
			return success(new Gson().toJson(response));

		} catch (Exception e) {
			return wrapException(e, "savePlace");

		}
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/saveplacesfeed")
	public ResponseEntity<?> savePlaceFeed(@RequestHeader("session-token") String sessionToken,@RequestBody PlacesFeedDTO request) {
		try {
			log.info("Enter savePlaceFeed API...with request :" + request.toString());
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			PlacesFeedDTO response = placesService.addPlaceFeed(request);
			return success(new Gson().toJson(response));

		} catch (Exception e) {
			return wrapException(e, "savePlaceFeed");

		}
	}
	
	@GetMapping(value = "/getallplaces")
	public ResponseEntity<?> getAllPlaces(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(placesService.getAllPlaces());

		} catch (Exception e) {
			return wrapException(e, "getAllPlaces");

		}
	}
	
	@GetMapping(value = "/getallplacesfeed")
	public ResponseEntity<?> getAllPlacesFeed(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(placesService.getAllPlacesFeed());

		} catch (Exception e) {
			return wrapException(e, "getAllPlacesFeed");

		}
	}

}
