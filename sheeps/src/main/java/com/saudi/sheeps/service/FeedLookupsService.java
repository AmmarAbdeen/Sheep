package com.saudi.sheeps.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saudi.sheeps.dao.FeedLookupsDAO;
import com.saudi.sheeps.dto.FeedLookupsDTO;
import com.saudi.sheeps.entity.FeedLookups;
import com.saudi.sheeps.exception.BusinessException;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class FeedLookupsService {
	
	@Autowired
	private FeedLookupsDAO lookupsDAO;
	
	
	public FeedLookupsDTO addFeedLookups(FeedLookupsDTO feedLookupsRequest) throws BusinessException {
		try {
			log.info("Enter addFeedLookups Function..with FeedLookupsRequest = " + feedLookupsRequest.getName());
			if (feedLookupsRequest.getName() == null || feedLookupsRequest.getName().isEmpty()) {
				throw new BusinessException("lookups name is required");
			}
			if (feedLookupsRequest.getBenefitOfIt() == null || feedLookupsRequest.getBenefitOfIt().isEmpty()) {
				throw new BusinessException("BenefitOfIt is required");
			}
			if (feedLookupsRequest.getDryAmount() == null || feedLookupsRequest.getDryAmount().isEmpty()) {
				throw new BusinessException("DryAmount is required");
			}
			if (feedLookupsRequest.getEnergyMj() == null || feedLookupsRequest.getEnergyMj().isEmpty()) {
				throw new BusinessException("EnergyMj is required");
			}
			if (feedLookupsRequest.getCalcium() == null || feedLookupsRequest.getCalcium().isEmpty()) {
				throw new BusinessException("Calcium is required");
			}
			if (feedLookupsRequest.getPhosphorous() == null || feedLookupsRequest.getPhosphorous().isEmpty()) {
				throw new BusinessException("Phosphorous is required");
			}
			if (feedLookupsRequest.getProtein() == null || feedLookupsRequest.getProtein().isEmpty()) {
				throw new BusinessException("Protein is required");
			}
			if(feedLookupsRequest.getId() != null && feedLookupsRequest.getId() !=0) {
			    FeedLookups feedLookups = lookupsDAO.findById(feedLookupsRequest.getId()).get();
			    if(feedLookups != null) {
			    	feedLookups.setId(feedLookupsRequest.getId());
			    	feedLookups.setName(feedLookupsRequest.getName());
			    	feedLookups.setBenefitOfIt(feedLookupsRequest.getBenefitOfIt());
			    	feedLookups.setDryAmount(feedLookupsRequest.getDryAmount());
			    	feedLookups.setEnergyMj(feedLookupsRequest.getEnergyMj());
			    	feedLookups.setCalcium(feedLookupsRequest.getCalcium());
			    	feedLookups.setPhosphorous(feedLookupsRequest.getPhosphorous());
			    	feedLookups.setProtein(feedLookupsRequest.getProtein());
			    	lookupsDAO.save(feedLookups);
			    }else {
			    	lookupsDAO.save(mapToEntity(feedLookupsRequest));
			    }
			}else {
				lookupsDAO.save(mapToEntity(feedLookupsRequest));
			}
			

			

			return feedLookupsRequest;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public String getAllFeedLookups() throws BusinessException{
		try {
			 List<FeedLookups> feedLookups = lookupsDAO.findAll();		
			return new Gson().toJson(mapToDTOList(feedLookups));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public FeedLookups mapToEntity(FeedLookupsDTO feedLookupsDTO) throws ParseException {
		FeedLookups feedLookups = new FeedLookups();
		feedLookups.setName(feedLookupsDTO.getName());
		feedLookups.setBenefitOfIt(feedLookupsDTO.getBenefitOfIt());
		feedLookups.setDryAmount(feedLookupsDTO.getDryAmount());
		feedLookups.setEnergyMj(feedLookupsDTO.getEnergyMj());
		feedLookups.setCalcium(feedLookupsDTO.getCalcium());
		feedLookups.setPhosphorous(feedLookupsDTO.getPhosphorous());
		feedLookups.setProtein(feedLookupsDTO.getProtein());
		return feedLookups;
	}
	
	public FeedLookupsDTO mapToDTO(FeedLookups feedLookups) throws ParseException {
		FeedLookupsDTO feedLookupsDTO = new FeedLookupsDTO();
		feedLookupsDTO.setId(feedLookups.getId());
		feedLookupsDTO.setName(feedLookups.getName());
		feedLookupsDTO.setBenefitOfIt(feedLookups.getBenefitOfIt());
		feedLookupsDTO.setDryAmount(feedLookups.getDryAmount());
		feedLookupsDTO.setEnergyMj(feedLookups.getEnergyMj());
		feedLookupsDTO.setCalcium(feedLookups.getCalcium());
		feedLookupsDTO.setPhosphorous(feedLookups.getPhosphorous());
		feedLookupsDTO.setProtein(feedLookups.getProtein());
		return feedLookupsDTO;
	}
	
	public List<FeedLookupsDTO> mapToDTOList(List<FeedLookups> feedLookups){
		List<FeedLookupsDTO> feedLookupsDTOs = new ArrayList<>();
		for(FeedLookups lookup : feedLookups) {
			try {
				feedLookupsDTOs.add(mapToDTO(lookup));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return feedLookupsDTOs;
	}

}
