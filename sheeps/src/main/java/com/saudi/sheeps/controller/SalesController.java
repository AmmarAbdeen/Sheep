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
import com.saudi.sheeps.dto.SalesDTO;
import com.saudi.sheeps.service.JWTService;
import com.saudi.sheeps.service.SaleService;

import io.jsonwebtoken.Claims;
import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep/sales")
public class SalesController extends BaseController{

	@Autowired
	private SaleService saleService;
	@Autowired
	protected JWTService jwtservice;
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/savesale")
	public ResponseEntity<?> saveSales(@RequestHeader("session-token") String sessionToken,@RequestBody SalesDTO request){
		try {
			log.info("Enter saveSales API...with request :" + request.toString());
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			SalesDTO response =saleService.saveSales(request);
			return success(new Gson().toJson(response));

		} catch (Exception e) {
			return wrapException(e, "saveSales");

		}
	}
	
	@GetMapping(value = "/getallsales")
	public ResponseEntity<?> getAllSales(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(saleService.getAllSales());

		} catch (Exception e) {
			return wrapException(e, "getAllSales");

		}
	}
}
