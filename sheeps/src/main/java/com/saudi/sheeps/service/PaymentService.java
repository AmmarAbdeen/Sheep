package com.saudi.sheeps.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saudi.sheeps.dao.ErrorsDAO;
import com.saudi.sheeps.dao.GeneralPaymentsDAO;
import com.saudi.sheeps.dao.IncomesDAO;
import com.saudi.sheeps.dto.GeneralPaymentDTO;
import com.saudi.sheeps.dto.IncomeDTO;
import com.saudi.sheeps.dto.SheepDTO;
import com.saudi.sheeps.entity.BugsAndError;
import com.saudi.sheeps.entity.GeneralPayment;
import com.saudi.sheeps.entity.Income;
import com.saudi.sheeps.entity.Sheep;
import com.saudi.sheeps.exception.BusinessException;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class PaymentService {
	
	@Autowired
	private GeneralPaymentsDAO paymentsDAO;
	@Autowired
	private IncomesDAO incomesDAO;
	@Autowired
	private ErrorsDAO errorsDAO ;
	
	public GeneralPaymentDTO savePayment(GeneralPaymentDTO request) throws BusinessException {
		try {
			log.info("Enter savePayment Function..with named= " + request.getNamed());
			if (request.getAmount() == null || request.getAmount() == 0) {
				throw new BusinessException("Payment amount is required and can't be zero");
			}
			if (request.getNamed() == null || request.getNamed().isEmpty()) {
				throw new BusinessException("Payment named is required");
			}
			paymentsDAO.save(mapToEntity(request));
			return request;
		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"savePayment",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}

	}
	
	public IncomeDTO saveIncomes(IncomeDTO request) throws BusinessException {
		try {
			log.info("Enter saveIncomes Function..from  " + request.getSource());
			if (request.getAmount() == null || request.getAmount() == 0) {
				throw new BusinessException("Income amount is required and can't be zero");
			}
			if (request.getSource() == null || request.getSource().isEmpty()) {
				throw new BusinessException("Income source is required");
			}
			incomesDAO.save(mapToEntity(request));
			return request;
		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"saveIncomes",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}

	}
	
	
	public String getAllPayments() throws BusinessException {
		try {
			List<GeneralPayment> payments = paymentsDAO.findAll();
			return new Gson().toJson(mapToDTOList(payments));
		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"getAllPayments",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public String getAllIncomes() throws BusinessException {
		try {
			List<Income> incomes = incomesDAO.findAll();
			return new Gson().toJson(mapToIncomeDTOList(incomes));
		} catch (Exception e) {
			errorsDAO.save(new BugsAndError(LocalDateTime.now(),"getAllIncomes",e.getMessage()));
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	
	public GeneralPayment mapToEntity(GeneralPaymentDTO generalPaymentDTO) {
		GeneralPayment generalPayment = new GeneralPayment();
		generalPayment.setAmount(generalPaymentDTO.getAmount());
		generalPayment.setNotes(generalPaymentDTO.getNotes());
		generalPayment.setNamed(generalPaymentDTO.getNamed());
		generalPayment.setDescription(generalPaymentDTO.getDescription());
		return generalPayment;
		
	}
	
	public Income mapToEntity(IncomeDTO incomeDTO) {
		Income income = new Income();
		income.setAmount(incomeDTO.getAmount());
		income.setSource(incomeDTO.getSource());
		income.setDescription(incomeDTO.getDescription());
		return income;
		
	}
	
	public GeneralPaymentDTO  mapToDTO(GeneralPayment generalPayment) {
		GeneralPaymentDTO generalPaymentDTO = new GeneralPaymentDTO();
		generalPaymentDTO.setNamed(generalPayment.getNamed());
		generalPaymentDTO.setNotes(generalPayment.getNotes());
		generalPaymentDTO.setAmount(generalPayment.getAmount());
		generalPaymentDTO.setCreationDate(generalPayment.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		generalPaymentDTO.setDescription(generalPayment.getDescription());
		return generalPaymentDTO;
	}
	
	public IncomeDTO  mapToDTO(Income income) {
		IncomeDTO incomeDTO = new IncomeDTO();
		incomeDTO.setAmount(income.getAmount());
		incomeDTO.setSource(income.getSource());
		incomeDTO.setCreationDate(income.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		incomeDTO.setDescription(income.getDescription());
		return incomeDTO;
	}
	
	public List<GeneralPaymentDTO> mapToDTOList(List<GeneralPayment> payments){
		List<GeneralPaymentDTO> generalPaymentDTOs = new ArrayList<>();
		for (GeneralPayment payment : payments) {
			generalPaymentDTOs.add(mapToDTO(payment));
		}
		return generalPaymentDTOs;
	}
	
	public List<IncomeDTO> mapToIncomeDTOList(List<Income> incomes){
		List<IncomeDTO> incomeDTOs = new ArrayList<>();
		for (Income income : incomes) {
			incomeDTOs.add(mapToDTO(income));
		}
		return incomeDTOs;
	}

}
