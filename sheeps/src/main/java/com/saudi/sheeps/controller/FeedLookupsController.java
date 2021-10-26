package com.saudi.sheeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.saudi.sheeps.dto.FeedLookupsDTO;
import com.saudi.sheeps.service.FeedLookupsService;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep/feedlookups")
public class FeedLookupsController extends BaseController{
	
	@Autowired
	private FeedLookupsService feedLookupsService ;
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/savelookups")
	public ResponseEntity<?> saveFeedLookups(@RequestBody FeedLookupsDTO feedLookupsRequest) {
		try {
			log.info("Enter saveFeedLookups API...with request :" + feedLookupsRequest.toString());
			FeedLookupsDTO feedLookupsResponse = feedLookupsService.addFeedLookups(feedLookupsRequest);
			return success(new Gson().toJson(feedLookupsResponse));

		} catch (Exception e) {
			return wrapException(e, "saveFeedLookups");

		}
	}
	
	@GetMapping(value = "/getallfeedlookups")
	public ResponseEntity<?> getAllFeedLookups() {
		try {

			return success(feedLookupsService.getAllFeedLookups());

		} catch (Exception e) {
			return wrapException(e, "getAllFeedLookups");

		}
	}

}
