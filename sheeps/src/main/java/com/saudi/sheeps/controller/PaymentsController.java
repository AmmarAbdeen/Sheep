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
import com.saudi.sheeps.dto.GeneralPaymentDTO;
import com.saudi.sheeps.dto.IncomeDTO;
import com.saudi.sheeps.service.JWTService;
import com.saudi.sheeps.service.PaymentService;

import io.jsonwebtoken.Claims;
import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep/payments")
public class PaymentsController extends BaseController{
	
	@Autowired
	private PaymentService paymentsService;
	@Autowired
	protected JWTService jwtservice;
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/savepayment")
	public ResponseEntity<?> savePayment(@RequestHeader("session-token") String sessionToken,@RequestBody GeneralPaymentDTO request){
		try {
			log.info("Enter savePayment API...with request :" + request.toString());
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			GeneralPaymentDTO response =paymentsService.savePayment(request);
			return success(new Gson().toJson(response));

		} catch (Exception e) {
			return wrapException(e, "savePayment");

		}
	}
	
	@PostMapping(value = "/saveincome")
	public ResponseEntity<?> saveIncomes(@RequestHeader("session-token") String sessionToken,@RequestBody IncomeDTO request){
		try {
			log.info("Enter saveIncomes API...with request :" + request.toString());
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			IncomeDTO response =paymentsService.saveIncomes(request);
			return success(new Gson().toJson(response));

		} catch (Exception e) {
			return wrapException(e, "saveIncomes");

		}
	}
	
	@GetMapping(value = "/getallpayments")
	public ResponseEntity<?> getAllPayments(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(paymentsService.getAllPayments());

		} catch (Exception e) {
			return wrapException(e, "getAllPayments");

		}
	}
	
	@GetMapping(value = "/getallincomes")
	public ResponseEntity<?> getAllIncomes(@RequestHeader("session-token") String sessionToken) {
		try {
			Claims tokenInfo = jwtservice.decodeJWT(sessionToken).getBody();
			return success(paymentsService.getAllIncomes());

		} catch (Exception e) {
			return wrapException(e, "getAllIncomes");

		}
	}

}
