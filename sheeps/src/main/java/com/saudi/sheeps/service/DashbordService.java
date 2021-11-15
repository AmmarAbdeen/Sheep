package com.saudi.sheeps.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saudi.sheeps.dao.LambsDAO;
import com.saudi.sheeps.dao.SheepDAO;
import com.saudi.sheeps.dao.StoredFeedDAO;
import com.saudi.sheeps.dto.Dashboard;
import com.saudi.sheeps.dto.DashboardDTO;
import com.saudi.sheeps.exception.BusinessException;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class DashbordService {

	@Autowired
	private SheepDAO sheepDAO;
	@Autowired
	private LambsDAO lambsDAO;
	@Autowired
	private StoredFeedDAO feedDAO;

	public String getAllSheepGroupByType() throws BusinessException {
		try {

			List<Dashboard> sheepsGroupByType = sheepDAO.getAllSheepsMaleAndFemale();
			List<DashboardDTO> sheepsGroupByTypeResponse = new ArrayList<>();
			for (Dashboard sheepType : sheepsGroupByType) {
				DashboardDTO lambTypeResponse = new DashboardDTO();
				lambTypeResponse.setType(sheepType.getType());
				lambTypeResponse.setValue(sheepType.getValue());
				sheepsGroupByTypeResponse.add(lambTypeResponse);
			}
			return new Gson().toJson(sheepsGroupByTypeResponse);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public String getAllAmountOfStoredFeed() throws BusinessException {
		try {

			List<Dashboard> amounts = feedDAO.getAllAmountOfStoredFeed();
			List<DashboardDTO> amountOfStoredFeed = new ArrayList<>();
			for (Dashboard amount : amounts) {
				DashboardDTO amountDTO = new DashboardDTO();
				amountDTO.setType(amount.getType());
				amountDTO.setValue(amount.getValue());
				amountOfStoredFeed.add(amountDTO);
			}
			return new Gson().toJson(amountOfStoredFeed);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public String getAllLambsGroupByType() throws BusinessException {
		try {

			List<Dashboard> lambsGroupByType = lambsDAO.getAllLambsMaleAndFemale();
			List<DashboardDTO> lambsGroupByTypeResponse = new ArrayList<>();
			for (Dashboard lambType : lambsGroupByType) {
				DashboardDTO sheepTypeResponse = new DashboardDTO();
				sheepTypeResponse.setType(lambType.getType());
				sheepTypeResponse.setValue(lambType.getValue());
				lambsGroupByTypeResponse.add(sheepTypeResponse);
			}
			return new Gson().toJson(lambsGroupByTypeResponse);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}


}
