package com.saudi.sheeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saudi.sheeps.service.JWTService;
import com.saudi.sheeps.service.LookupsService;

import io.jsonwebtoken.Claims;
import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep/lookups")
public class LookupsController extends BaseController{
	
	@Autowired
	protected JWTService jwtservice;
	@Autowired
	protected LookupsService lookupsService;
	
	@GetMapping(value = "/alllookups/{type}")
	public ResponseEntity<?> getAllLookupsByType(@RequestHeader("session-token") String sessionToken, @PathVariable("type") String type) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(lookupsService.getAllLookupsByType(type));

		} catch (Exception e) {
			return wrapException(e, "searchSheeps");

		}
	}

}
