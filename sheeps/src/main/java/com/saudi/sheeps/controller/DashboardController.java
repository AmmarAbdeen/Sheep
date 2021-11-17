package com.saudi.sheeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saudi.sheeps.service.DashbordService;
import com.saudi.sheeps.service.JWTService;

import io.jsonwebtoken.Claims;
import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep/dashboard")
public class DashboardController extends BaseController {
	@Autowired
	private DashbordService dashbordService;
	@Autowired
	protected JWTService jwtservice;
	
	
	@GetMapping(value = "/getallsheepgroupbytype")
	public ResponseEntity<?> getAllSheepGroupByType(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(dashbordService.getAllSheepGroupByType());

		} catch (Exception e) {
			return wrapException(e, "getAllSheepGroupByType");

		}
	}
	
	@GetMapping(value = "/getalllambspermonth")
	public ResponseEntity<?> getAllLambsPerMonth(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(dashbordService.getAllLambsPerMonth());

		} catch (Exception e) {
			return wrapException(e, "getAllLambsPerMonth");

		}
	}
	
	@GetMapping(value = "/getalllambsgroupbytype")
	public ResponseEntity<?> getAllLambsGroupByType(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(dashbordService.getAllLambsGroupByType());

		} catch (Exception e) {
			return wrapException(e, "getAllLambsGroupByType");

		}
	}
	
	@GetMapping(value = "/getallamountofstoredfeed")
	public ResponseEntity<?> getAllAmountOfStoredFeed(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(dashbordService.getAllAmountOfStoredFeed());

		} catch (Exception e) {
			return wrapException(e, "getAllAmountOfStoredFeed");

		}
	}

	@GetMapping(value = "/getallsheepperage")
	public ResponseEntity<?> getAllSheepsPerAge(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(dashbordService.getAllSheepPerAge());

		} catch (Exception e) {
			return wrapException(e, "getAllSheepsPerAge");

		}
	}

}
