package com.saudi.sheeps.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;

import com.saudi.sheeps.dto.SheepResponse;
import com.saudi.sheeps.exception.ExternalSystemException;

import lombok.extern.apachecommons.CommonsLog;

@Component
@CommonsLog
public abstract class BaseController {

	

	public <D extends com.saudi.sheeps.dto.BaseDTO> ResponseEntity<SheepResponse> success() {
		return new ResponseEntity<SheepResponse>(HttpStatus.OK);
	}

	public <D extends com.saudi.sheeps.dto.BaseDTO> ResponseEntity<com.saudi.sheeps.dto.BaseDTO> success(D results) {
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(results);
	}

	public ResponseEntity<String> success(String results) {
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(results);
	}

	@Deprecated
	public ResponseEntity<SheepResponse> wrapException(String msg, String funName) {
		log.error("Returned from " + funName + " API With error: " + msg);
		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(new SheepResponse("1", msg));
	}

	public ResponseEntity<String> wrapException(Exception e, String functionName) {
		log.error("Returned from " + functionName + " API With error: " + e.getMessage(), e);
		if (e instanceof ExternalSystemException) {
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(
					new SheepResponse("1", e.getMessage(), ((ExternalSystemException) e).getExternalSystemResponse())
							.toString());
		} else if (e.getCause() instanceof JpaSystemException
				|| e.getCause() instanceof IncorrectResultSizeDataAccessException
				|| e.getCause() instanceof ConstraintViolationException) {
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
					.body(new SheepResponse("1", " General Error, try again later").toString());
		}
		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
				.body(new SheepResponse("1", e.getMessage()).toString());
	}
	

}
