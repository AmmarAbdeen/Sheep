package com.saudi.sheeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.saudi.sheeps.dto.FeedDTO;
import com.saudi.sheeps.dto.LambsDTO;
import com.saudi.sheeps.service.FeedService;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@RequestMapping("/api/sheep/feed")
public class FeedController extends BaseController {

	@Autowired
	private FeedService feedService ;
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/savefeed")
	public ResponseEntity<?> saveFeed(@RequestBody FeedDTO feedRequest) {
		try {
			log.info("Enter saveFeed API...with request :" + feedRequest.toString());
			FeedDTO feedResponse = feedService.addFeed(feedRequest);
			return success(new Gson().toJson(feedResponse));

		} catch (Exception e) {
			return wrapException(e, "addSheep");

		}
	}
	
	@GetMapping(value = "/getallfeed")
	public ResponseEntity<?> getAllFeed() {
		try {

			return success(feedService.getAllFeed());

		} catch (Exception e) {
			return wrapException(e, "getAllFeed");

		}
	}
	
	@GetMapping(value = "/getallstoredfeed")
	public ResponseEntity<?> getAllStoredFeed() {
		try {

			return success(feedService.getAllStoredFeed());

		} catch (Exception e) {
			return wrapException(e, "getAllStoredFeed");

		}
	}

}
