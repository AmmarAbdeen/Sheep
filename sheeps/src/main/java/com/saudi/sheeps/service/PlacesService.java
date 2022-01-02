package com.saudi.sheeps.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saudi.sheeps.dao.ErrorsDAO;
import com.saudi.sheeps.dao.FeedDAO;
import com.saudi.sheeps.dao.PlacesDAO;
import com.saudi.sheeps.dao.PlacesFeedDAO;
import com.saudi.sheeps.dao.StoredFeedDAO;
import com.saudi.sheeps.dto.FeedDTO;
import com.saudi.sheeps.dto.PlacesDTO;
import com.saudi.sheeps.dto.PlacesFeedDTO;
import com.saudi.sheeps.entity.BugsAndError;
import com.saudi.sheeps.entity.Feed;
import com.saudi.sheeps.entity.Places;
import com.saudi.sheeps.entity.PlacesFeed;
import com.saudi.sheeps.entity.StoredFeed;
import com.saudi.sheeps.exception.BusinessException;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class PlacesService {
	@Autowired
	private PlacesDAO placesDAO;
	@Autowired
	private PlacesFeedDAO placesFeedDAO;
	@Autowired
	private StoredFeedDAO storedFeedDAO;
	@Autowired
	private FeedService feedService;
	@Autowired
	private ErrorsDAO errorsDAO ;

	public PlacesDTO addPlace(PlacesDTO request) throws BusinessException {
		try {
			if (request.getCode() == null || request.getCode().isEmpty()) {
				throw new BusinessException("Place code is required");
			}
			if (request.getCapacity() == null || request.getCapacity().isEmpty()) {
				throw new BusinessException("Place Capacity is required");
			}
			if (request.getDescription() == null || request.getDescription().isEmpty()) {
				throw new BusinessException("Place Description is required");
			}

			placesDAO.save(mapToEntity(request));
			return request;
		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"addPlace",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}

	}
	
	public PlacesFeedDTO addPlaceFeed(PlacesFeedDTO request) throws BusinessException {
		try {
			if (request.getAmount() == null || request.getAmount().isEmpty()) {
				throw new BusinessException("PlacesFeed Amount is required");
			}
			if (request.getDate() == null || request.getDate().isEmpty()) {
				throw new BusinessException("PlacesFeed Date is required");
			}
			if (request.getPlace() == null || request.getFeed() == null) {
				throw new BusinessException("PlacesFeed place & feed are required");
			}else {
				Places place = placesDAO.findById(request.getPlace().getId()).get();
				StoredFeed storedFeed = storedFeedDAO.findById(request.getFeed().getId()).get();
				if(place == null || storedFeed == null) {
					throw new BusinessException("this place or this feed is not found");
				}else {
					Double restOfAmount = storedFeed.getQuantity() - Double.parseDouble(request.getAmount());
					if(restOfAmount < 0 ) {
						throw new BusinessException("this amount is not found");
					}
					else if(restOfAmount == 0 ) {
						storedFeedDAO.delete(storedFeed);
						placesFeedDAO.save(mapToEntity(request));
					}else {
						storedFeed.setQuantity(restOfAmount);
						storedFeedDAO.save(storedFeed);
						placesFeedDAO.save(mapToEntity(request));
					}
					
				}
			}
			return request;

		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"addPlaceFeed",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}

	}
	
	public String getAllPlaces() throws BusinessException{
		try {
			 List<Places> places = placesDAO.findAll();		
			return new Gson().toJson(mapToDTOList(places));
		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"getAllPlaces",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public String getAllPlacesFeed() throws BusinessException{
		try {
			 List<PlacesFeed> placesFeeds = placesFeedDAO.findAll();		
			return new Gson().toJson(mapToPlacesFeedDTOList(placesFeeds));
		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"getAllPlacesFeed",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}
	}

	public Places mapToEntity(PlacesDTO placeDTO) throws ParseException {
		Places place = new Places();
		place.setCode(placeDTO.getCode());
		place.setCapacity(placeDTO.getCapacity());
		place.setDescription(placeDTO.getDescription());
		return place;
	}

	public PlacesFeed mapToEntity(PlacesFeedDTO placeFeedDTO) throws ParseException {
		PlacesFeed placeFeed = new PlacesFeed();
		placeFeed.setAmount(placeFeedDTO.getAmount());
		placeFeed.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(placeFeedDTO.getDate()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		Places place = placesDAO.findById(placeFeedDTO.getPlace().getId()).get();
		StoredFeed storedFeed = storedFeedDAO.findById(placeFeedDTO.getFeed().getId()).get();
		placeFeed.setPlaces(place);
		placeFeed.setFeed(storedFeed);
		return placeFeed;
	}

	public PlacesDTO mapToDTO(Places place) throws ParseException {
		PlacesDTO placeDTO = new PlacesDTO();
		placeDTO.setId(place.getId());
		placeDTO.setCode(place.getCode());
		placeDTO.setCapacity(place.getCapacity());
		placeDTO.setDescription(place.getDescription());
		return placeDTO;
	}

	public PlacesFeedDTO mapToDTO(PlacesFeed placeFeed) throws ParseException {
		PlacesFeedDTO placeFeedDTO = new PlacesFeedDTO();
		placeFeedDTO.setAmount(placeFeed.getAmount());
		placeFeedDTO.setDate(placeFeed.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		placeFeedDTO.setPlace(mapToDTO(placeFeed.getPlaces()));
		placeFeedDTO.setFeed(feedService.mapToDTO(placeFeed.getFeed()));
		return placeFeedDTO;
	}

	public List<PlacesDTO> mapToDTOList(List<Places> places) {
		List<PlacesDTO> placesDTOs = new ArrayList<>();
		for (Places place : places) {
			try {
				placesDTOs.add(mapToDTO(place));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return placesDTOs;
	}

	public List<PlacesFeedDTO> mapToPlacesFeedDTOList(List<PlacesFeed> placesFeeds) {
		List<PlacesFeedDTO> placesFeedDTOs = new ArrayList<>();
		for (PlacesFeed placeFeed : placesFeeds) {
			try {
				placesFeedDTOs.add(mapToDTO(placeFeed));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return placesFeedDTOs;
	}

}
