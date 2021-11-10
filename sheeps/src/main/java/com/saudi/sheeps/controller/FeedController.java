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
import com.saudi.sheeps.dto.FeedDTO;
import com.saudi.sheeps.dto.LambsDTO;
import com.saudi.sheeps.service.FeedService;
import com.saudi.sheeps.service.JWTService;

import io.jsonwebtoken.Claims;
import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep/feed")
public class FeedController extends BaseController {

	@Autowired
	private FeedService feedService ;
	@Autowired
	protected JWTService jwtservice;
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/savefeed")
	public ResponseEntity<?> saveFeed(@RequestHeader("session-token") String sessionToken,@RequestBody FeedDTO feedRequest) {
		try {
			log.info("Enter saveFeed API...with request :" + feedRequest.toString());
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			FeedDTO feedResponse = feedService.addFeed(feedRequest);
			return success(new Gson().toJson(feedResponse));

		} catch (Exception e) {
			return wrapException(e, "addSheep");

		}
	}
	
	@GetMapping(value = "/getallfeed")
	public ResponseEntity<?> getAllFeed(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(feedService.getAllFeed());

		} catch (Exception e) {
			return wrapException(e, "getAllFeed");

		}
	}
	
	@GetMapping(value = "/getallstoredfeed")
	public ResponseEntity<?> getAllStoredFeed(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(feedService.getAllStoredFeed());

		} catch (Exception e) {
			return wrapException(e, "getAllStoredFeed");

		}
	}

}
