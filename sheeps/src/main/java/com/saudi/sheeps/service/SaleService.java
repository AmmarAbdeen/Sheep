package com.saudi.sheeps.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saudi.sheeps.dao.LambsDAO;
import com.saudi.sheeps.dao.SalesDAO;
import com.saudi.sheeps.dao.SheepDAO;
import com.saudi.sheeps.dto.GeneralPaymentDTO;
import com.saudi.sheeps.dto.SalesDTO;
import com.saudi.sheeps.entity.Lambs;
import com.saudi.sheeps.entity.Sales;
import com.saudi.sheeps.entity.Sheep;
import com.saudi.sheeps.exception.BusinessException;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class SaleService {

	@Autowired
	private SalesDAO salesDAO;
	@Autowired
	private LambsDAO lambsDAO;
	@Autowired
	private SheepDAO sheepDAO;
	
	public SalesDTO saveSales(SalesDTO request) throws BusinessException {
		try {
			log.info("Enter saveSales Function..to buyer :  " + request.getBuyer());
			if (request.getAmount() == null || request.getAmount() == 0) {
				throw new BusinessException("sale amount is required and can't be zero");
			}
			if (request.getCode() == null || request.getCode().isEmpty()) {
				throw new BusinessException("sale code is required");
			}
			if (request.getColor() == null || request.getColor().isEmpty()) {
				throw new BusinessException("sale color is required");
			}
			if (request.getType() == null || request.getType().isEmpty()) {
				throw new BusinessException("sale type is required");
			}
			if (!request.getType().equals("sheep") && !request.getType().equals("lamb")) {
				throw new BusinessException("this type is not found");
			}
			if (request.getBuyer() == null || request.getBuyer().isEmpty()) {
				throw new BusinessException("sale buyer is required");
			}
			if(request.getType().equals("sheep")){
				Sheep sheep = sheepDAO.findByCodeAndColor(Long.parseLong(request.getCode()), request.getColor());
				if(sheep == null) {
					throw new BusinessException("this sheep not found");
				}else {
					sheep.setStatus("saled");
					sheepDAO.save(sheep);
				}
			}else {
				Lambs lamb = lambsDAO.findByCodeAndColor(Long.parseLong(request.getCode()), request.getColor());
				if(lamb == null) {
					throw new BusinessException("this lamb not found");
				}else {
					lamb.setStatus("saled");
					lambsDAO.save(lamb);
				}
				
			}
			salesDAO.save(mapToEntity(request));
			return request;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}

	}
	public String getAllSales() throws BusinessException {
		try {
			List<Sales> sales = salesDAO.findAll();
			return new Gson().toJson(mapToDTOList(sales));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public Sales mapToEntity(SalesDTO salesDTO) {
		Sales sales = new Sales();
		sales.setAmount(salesDTO.getAmount());
		sales.setCode(salesDTO.getCode());
		sales.setColor(salesDTO.getColor());
		sales.setType(salesDTO.getType());
		sales.setBuyer(salesDTO.getBuyer());
		sales.setDescription(salesDTO.getDescription());
		return sales;
		
	}
	
	public SalesDTO mapToDTO(Sales sales) {
		SalesDTO salesDTO = new SalesDTO();
		salesDTO.setAmount(sales.getAmount());
		salesDTO.setCreationDate(sales.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		salesDTO.setCode(sales.getCode());
		salesDTO.setColor(sales.getColor());
		salesDTO.setType(sales.getType());
		salesDTO.setBuyer(sales.getBuyer());
		salesDTO.setDescription(sales.getDescription());
		return salesDTO;
	}
	
	public List<SalesDTO> mapToDTOList(List<Sales> sales){
		List<SalesDTO> salesDTOs = new ArrayList<>();
		for (Sales sale : sales) {
				salesDTOs.add(mapToDTO(sale));
		}
		return salesDTOs;
	}
}
