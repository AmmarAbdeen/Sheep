package com.saudi.sheeps.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saudi.sheeps.dao.MedicineDAO;
import com.saudi.sheeps.dao.MedicineDiseaseOfSheepDAO;
import com.saudi.sheeps.dao.SheepDAO;
import com.saudi.sheeps.dto.FeedDTO;
import com.saudi.sheeps.dto.MedicineDTO;
import com.saudi.sheeps.dto.MedicineDiseaseOfSheepDTO;
import com.saudi.sheeps.entity.Feed;
import com.saudi.sheeps.entity.Medicine;
import com.saudi.sheeps.entity.MedicineDiseaseOfSheep;
import com.saudi.sheeps.entity.Sheep;
import com.saudi.sheeps.entity.StoredFeed;
import com.saudi.sheeps.exception.BusinessException;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class MedicineService {

	@Autowired
	private SheepDAO sheepDAO;
	@Autowired
	private SheepService sheepService;
	@Autowired
	private MedicineDAO medicineDAO;
	@Autowired
	private MedicineDiseaseOfSheepDAO medicineDiseaseOfSheepDAO;

	public MedicineDTO addMedicine(MedicineDTO request) throws BusinessException {
		try {
			log.info("Enter addMedicine Function..with feedRequest = " + request.getName());
			if (request.getName() == null || request.getName().isEmpty()) {
				throw new BusinessException("Medicine name is required");
			}
			if (request.getQuantity() == null || request.getQuantity() == 0) {
				throw new BusinessException("Medicine Quantity is required");
			}
			if (request.getCost() == null || request.getCost().isEmpty()) {
				throw new BusinessException("Medicine Cost is required");
			}
			if (request.getDescription() == null || request.getDescription().isEmpty()) {
				throw new BusinessException("Medicine Description is required");
			}

			medicineDAO.save(mapToEntity(request));
			return request;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public MedicineDiseaseOfSheepDTO addMedicineDiseaseOfSheep(MedicineDiseaseOfSheepDTO request)throws BusinessException {
		try {
			log.info("Enter addMedicineDiseaseOfSheep Function..with request = " + request.getDisease());
			if (request.getDescription() == null || request.getDescription().isEmpty()) {
				throw new BusinessException("Medicine name is required");
			}
			if (request.getQuantity() == null || request.getQuantity() == 0) {
				throw new BusinessException("Medicine Quantity is required");
			}
			if (request.getDisease() == null || request.getDisease().isEmpty()) {
				throw new BusinessException("Medicine Cost is required");
			}
			if (request.getDescription() == null || request.getDescription().isEmpty()) {
				throw new BusinessException("Medicine Description is required");
			}
			if(request.getSheep() == null || request.getMedicine() == null ) {
				throw new BusinessException("Sheep and Medicine are required");
			}else {
				Sheep sheep = sheepDAO.findById(request.getSheep().getId()).get();
				Medicine medicine = medicineDAO.findById(request.getMedicine().getId()).get();
				if(sheep == null || medicine == null ) {
					throw new BusinessException("this Sheep or Medicine are not found");
				}else {
					medicine.setQuantity(medicine.getQuantity() - request.getQuantity());
					medicineDAO.save(medicine);
				}
			}

			medicineDiseaseOfSheepDAO.save(mapToEntity(request));
			return request;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	public String getAllMedicines() throws BusinessException {
		try {
			List<Medicine> medicines = medicineDAO.findAll();
			return new Gson().toJson(mapToDTOList(medicines));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	public String getAllValidMedicine() throws BusinessException {
		try {
			List<Medicine> medicines = medicineDAO.findAllByQuantityGreaterThanAndExpiryDateGreaterThan(0.0,
				new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			return new Gson().toJson(mapToDTOList(medicines));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	public Medicine mapToEntity(MedicineDTO medicineDTO) throws ParseException {
		Medicine medicine = new Medicine();
		medicine.setName(medicineDTO.getName());
		medicine.setQuantity(medicineDTO.getQuantity());
		medicine.setCost(medicineDTO.getCost());
		medicine.setDescription(medicineDTO.getDescription());
		medicine.setExpiryDate(new SimpleDateFormat("yyyy-MM-dd").parse(medicineDTO.getExpiryDate()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		return medicine;
	}
	
	public MedicineDiseaseOfSheep mapToEntity(MedicineDiseaseOfSheepDTO medicineDiseaseOfSheepDTO) throws ParseException {
		MedicineDiseaseOfSheep medicineDiseaseOfSheep = new MedicineDiseaseOfSheep();
		medicineDiseaseOfSheep.setQuantity(medicineDiseaseOfSheepDTO.getQuantity());
		medicineDiseaseOfSheep.setDisease(medicineDiseaseOfSheepDTO.getDisease());
		medicineDiseaseOfSheep.setDescription(medicineDiseaseOfSheepDTO.getDescription());
		medicineDiseaseOfSheep.setMedicineOnset(new SimpleDateFormat("yyyy-MM-dd").parse(medicineDiseaseOfSheepDTO.getMedicineOnset()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		medicineDiseaseOfSheep.setEndOfMedicine(new SimpleDateFormat("yyyy-MM-dd").parse(medicineDiseaseOfSheepDTO.getEndOfMedicine()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		Medicine medicine = medicineDAO.findById(medicineDiseaseOfSheepDTO.getMedicine().getId()).get();
		Sheep sheep = sheepDAO.findById(medicineDiseaseOfSheepDTO.getSheep().getId()).get();
		medicineDiseaseOfSheep.setSheep(sheep);
		medicineDiseaseOfSheep.setMedicine(medicine);
		return medicineDiseaseOfSheep;
	}

	public MedicineDTO mapToDTO(Medicine medicine) throws ParseException {
		MedicineDTO medicineDTO = new MedicineDTO();
		medicineDTO.setId(medicine.getId());
		medicineDTO.setName(medicine.getName());
		medicineDTO.setQuantity(medicine.getQuantity());
		medicineDTO.setCost(medicine.getCost());
		medicineDTO.setDescription(medicine.getDescription());
		medicineDTO.setExpiryDate(medicine.getExpiryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		return medicineDTO;
	}
	
	public MedicineDiseaseOfSheepDTO mapToDTO(MedicineDiseaseOfSheep medicineDiseaseOfSheep) throws ParseException {
		MedicineDiseaseOfSheepDTO medicineDiseaseOfSheepDTO = new MedicineDiseaseOfSheepDTO();
		medicineDiseaseOfSheepDTO.setQuantity(medicineDiseaseOfSheep.getQuantity());
		medicineDiseaseOfSheepDTO.setDisease(medicineDiseaseOfSheep.getDisease());
		medicineDiseaseOfSheepDTO.setDescription(medicineDiseaseOfSheep.getDescription());
		
		medicineDiseaseOfSheepDTO.setMedicineOnset(medicineDiseaseOfSheep.getMedicineOnset().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		medicineDiseaseOfSheepDTO.setEndOfMedicine(medicineDiseaseOfSheep.getEndOfMedicine().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		medicineDiseaseOfSheepDTO.setSheep(sheepService.mapToDTO(medicineDiseaseOfSheep.getSheep()));
		medicineDiseaseOfSheepDTO.setMedicine(mapToDTO(medicineDiseaseOfSheep.getMedicine()));
		return medicineDiseaseOfSheepDTO;
	}

	public List<MedicineDTO> mapToDTOList(List<Medicine> medicines) {
		List<MedicineDTO> medicineDTOs = new ArrayList<>();
		for (Medicine medicine : medicines) {
			try {
				medicineDTOs.add(mapToDTO(medicine));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return medicineDTOs;
	}

	public List<MedicineDiseaseOfSheepDTO> mapToMedicineDiseaseOfSheepDTOList(List<MedicineDiseaseOfSheep> medicines) {
		List<MedicineDiseaseOfSheepDTO> medicineDiseaseOfSheepDTOs = new ArrayList<>();
		for (MedicineDiseaseOfSheep medicine : medicines) {
			try {
				medicineDiseaseOfSheepDTOs.add(mapToDTO(medicine));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return medicineDiseaseOfSheepDTOs;
	}

}
