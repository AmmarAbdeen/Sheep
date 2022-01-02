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
import com.saudi.sheeps.dao.FeedLookupsDAO;
import com.saudi.sheeps.dao.StoredFeedDAO;
import com.saudi.sheeps.dto.FeedDTO;
import com.saudi.sheeps.dto.SheepDTO;
import com.saudi.sheeps.entity.BugsAndError;
import com.saudi.sheeps.entity.Feed;
import com.saudi.sheeps.entity.Sheep;
import com.saudi.sheeps.entity.StoredFeed;
import com.saudi.sheeps.exception.BusinessException;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class FeedService {

	@Autowired
	private FeedDAO feedDAO;
	@Autowired
	private FeedLookupsDAO lookupsDAO;
	@Autowired
	private StoredFeedDAO storedFeedDAO;
	@Autowired
	private ErrorsDAO errorsDAO ;
	
	public FeedDTO addFeed(FeedDTO feedRequest) throws BusinessException {
		try {
			log.info("Enter addFeed Function..with feedRequest = " + feedRequest.getName());
			if (feedRequest.getName() == null || feedRequest.getName().isEmpty()) {
				throw new BusinessException("food name is required");
			}
			if (feedRequest.getQuantity() == null || feedRequest.getQuantity() == 0 ) {
				throw new BusinessException("Food Quantity is required");
			}
			if (feedRequest.getWeight() == null || feedRequest.getWeight().isEmpty()) {
				throw new BusinessException("Food Weight is required");
			}
			if (feedRequest.getPrice() == null || feedRequest.getPrice().isEmpty()) {
				throw new BusinessException("Food Price is required");
			}

			feedDAO.save(mapToEntity(feedRequest));
			
			 StoredFeed  storedFeed= storedFeedDAO.findByName(feedRequest.getName());
			 if(storedFeed != null) {
				 double quantity = feedRequest.getQuantity()+ storedFeed.getQuantity();
				 double weigth = Double.parseDouble(feedRequest.getWeight()) + Double.parseDouble(storedFeed.getWeight());
				 storedFeed.setQuantity(quantity);
				 storedFeed.setWeight(String.valueOf(weigth));
				 storedFeedDAO.save(storedFeed);
			 }else {
				 storedFeedDAO.save(mapToStoredFeedEntity(feedRequest));
			 }
			
			return feedRequest;
		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"addFeed",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public String getAllFeed() throws BusinessException{
		try {
			 List<Feed> feed = feedDAO.findAll();		
			return new Gson().toJson(mapToDTOList(feed));
		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"getAllFeed",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public String getAllStoredFeed() throws BusinessException{
		try {
			 List<StoredFeed> storedFeed = storedFeedDAO.findAllByQuantityGreaterThan(0.0);		
			return new Gson().toJson(mapStoredFeedToDTOList(storedFeed));
		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"getAllStoredFeed",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}
	}

	public Feed mapToEntity(FeedDTO feedDTO) throws ParseException {
		Feed feed = new Feed();
		feed.setName(feedDTO.getName());
		feed.setQuantity(feedDTO.getQuantity());
		feed.setPrice(feedDTO.getPrice());
		feed.setWeight(feedDTO.getWeight());
		feed.setStorageDate(new SimpleDateFormat("yyyy-MM-dd").parse(feedDTO.getStorageDate()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		feed.setFeedLookups(lookupsDAO.findByName(feedDTO.getName()));
		return feed;
	}
	
	public StoredFeed mapToStoredFeedEntity(FeedDTO feedDTO) throws ParseException {
		StoredFeed storedFeed = new StoredFeed();
		storedFeed.setName(feedDTO.getName());
		storedFeed.setQuantity(feedDTO.getQuantity());
		storedFeed.setWeight(feedDTO.getWeight());
		storedFeed.setFeedLookups(lookupsDAO.findByName(feedDTO.getName()));
		return storedFeed;
	}
	
	
	public FeedDTO mapToDTO(Feed food) throws ParseException {
		FeedDTO feedDTO = new FeedDTO();
		feedDTO.setName(food.getName());
		feedDTO.setQuantity(food.getQuantity());
		feedDTO.setWeight(food.getWeight());
		feedDTO.setPrice(food.getPrice());
		feedDTO.setStorageDate(food.getStorageDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		return feedDTO;
	}
	
	public FeedDTO mapToDTO(StoredFeed food) throws ParseException {
		FeedDTO feedDTO = new FeedDTO();
		feedDTO.setId(food.getId());
		feedDTO.setName(food.getName());
		feedDTO.setQuantity(food.getQuantity());
		feedDTO.setWeight(food.getWeight());
		return feedDTO;
	}
	
	public List<FeedDTO> mapToDTOList(List<Feed> feed){
		List<FeedDTO> feedDTOs = new ArrayList<>();
		for(Feed food : feed) {
			try {
				feedDTOs.add(mapToDTO(food));
			} catch (ParseException e) {
				errorsDAO.save(new BugsAndError(LocalDateTime.now(),"mapToFeedDTOList",e.getMessage()));
				e.printStackTrace();
			}
		}
		return feedDTOs;
	}
	
	public List<FeedDTO> mapStoredFeedToDTOList(List<StoredFeed> storedfeed){
		List<FeedDTO> feedDTOs = new ArrayList<>();
		for(StoredFeed food : storedfeed) {
			try {
				feedDTOs.add(mapToDTO(food));
			} catch (ParseException e) {
				errorsDAO.save(new BugsAndError(LocalDateTime.now(),"mapStoredFeedToDTOList",e.getMessage()));
				e.printStackTrace();
			}
		}
		return feedDTOs;
	}

}
