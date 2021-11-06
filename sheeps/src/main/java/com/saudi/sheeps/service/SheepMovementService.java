package com.saudi.sheeps.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saudi.sheeps.dao.LambsDAO;
import com.saudi.sheeps.dao.PlacesDAO;
import com.saudi.sheeps.dao.SheepDAO;
import com.saudi.sheeps.dao.SheepMovementDAO;
import com.saudi.sheeps.dto.SheepMovementDTO;
import com.saudi.sheeps.entity.Lambs;
import com.saudi.sheeps.entity.Places;
import com.saudi.sheeps.entity.Sheep;
import com.saudi.sheeps.entity.SheepMovement;
import com.saudi.sheeps.exception.BusinessException;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class SheepMovementService {
	@Autowired
	private PlacesDAO placesDAO;
	@Autowired
	private SheepMovementDAO sheepMovementDAO;
	@Autowired
	private SheepDAO sheepDAO;
	@Autowired
	private LambsDAO lambsDAO;
	@Autowired
	private PlacesService placesService;
	@Autowired
	private SheepService sheepService;
	@Autowired
	private LambsService lambsService;
	@PersistenceContext
	public EntityManager em;

	public SheepMovementDTO addSheepMovement(SheepMovementDTO request) throws BusinessException {
		try {
			if (request.getDescription() == null || request.getDescription().isEmpty()) {
				throw new BusinessException("SheepMovement description is required");
			}
			if (request.getDate() == null || request.getDate().isEmpty()) {
				throw new BusinessException("SheepMovement date is required");
			}
			if (request.getSheep() == null && request.getLamb() == null) {
				throw new BusinessException("sheep or lamb mustn't be null");
			}
			if (request.getSheep() != null && request.getLamb() != null) {
				throw new BusinessException("sheep or lamb must be null");
			}
			if (request.getSheep() != null) {
				SheepMovement sheepMovementInSpecificPlace = sheepMovementDAO.findBySheepIdAndPlacesIdAndDateBetween(
						request.getSheep().getId(), request.getPlace().getId(),
						new SimpleDateFormat("yyyy-MM-dd").parse(request.getDate()).toInstant()
								.atZone(ZoneId.systemDefault()).toLocalDate().minusDays(3),
						new SimpleDateFormat("yyyy-MM-dd").parse(request.getDate()).toInstant()
								.atZone(ZoneId.systemDefault()).toLocalDate());
				if (sheepMovementInSpecificPlace != null) {
					throw new BusinessException("this movement of sheep is already added");
				} else {
					SheepMovement sheepMovement = sheepMovementDAO
							.findFirstBySheepIdOrderByDateDesc(request.getSheep().getId());
					if (sheepMovement != null) {
						sheepMovement.setOutDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getDate()).toInstant()
								.atZone(ZoneId.systemDefault()).toLocalDate());
						sheepMovementDAO.save(mapToEntity(request));
						sheepMovementDAO.save(sheepMovement);
					} else {
						sheepMovementDAO.save(mapToEntity(request));
					}
				}
			}
			if (request.getLamb() != null) {
				SheepMovement lambMovementInSpecificPlace = sheepMovementDAO.findByLambsIdAndPlacesIdAndDateBetween(
						request.getLamb().getId(), request.getPlace().getId(),
						new SimpleDateFormat("yyyy-MM-dd").parse(request.getDate()).toInstant()
								.atZone(ZoneId.systemDefault()).toLocalDate().minusDays(2),
						new SimpleDateFormat("yyyy-MM-dd").parse(request.getDate()).toInstant()
								.atZone(ZoneId.systemDefault()).toLocalDate());
				if (lambMovementInSpecificPlace != null) {
					throw new BusinessException("this movement of lamb is already added");
				} else {
					SheepMovement lambMovement = sheepMovementDAO
							.findFirstByLambsIdOrderByDateDesc(request.getLamb().getId());
					if (lambMovement != null) {
						lambMovement.setOutDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getDate()).toInstant()
								.atZone(ZoneId.systemDefault()).toLocalDate());
						sheepMovementDAO.save(mapToEntity(request));
						sheepMovementDAO.save(lambMovement);
					} else {
						sheepMovementDAO.save(mapToEntity(request));
					}
				}
			}

			return request;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	public String sheepsMovementSearch(SheepMovementDTO movementDTO) throws BusinessException {
		try {
			Map<String, Object> params = new HashMap();
			StringBuilder query = new StringBuilder();
			query.append("SELECT s FROM SheepMovement s WHERE s.id > 0 ");
			if (movementDTO.getFromDate() != null && !movementDTO.getFromDate().isEmpty()) {
				LocalDate fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(movementDTO.getFromDate()).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
				params.put("fromDate", fromDate);
				query.append(" and s.date >= :fromDate ");
			}
			if (movementDTO.getToDate() != null && !movementDTO.getToDate().isEmpty()) {
				LocalDate toDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(movementDTO.getToDate()).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate();
				params.put("toDate", toDate);
				query.append(" and s.date <= :toDate ");
			}
			if (movementDTO.getSheep() != null) {
				if (movementDTO.getSheep().getId() != null) {
					params.put("sheepId", movementDTO.getSheep().getId());
					query.append(" and s.sheep.id = :sheepId");
				}
			}
			if (movementDTO.getLamb() != null) {
				if (movementDTO.getLamb().getId() != null) {
					params.put("lambId", movementDTO.getLamb().getId());
					query.append(" and s.lamb.id = :lambId");
				}
			}
			List<SheepMovement> sheepsMovement = getSheepsMovement(em, query.toString(), params);
			return new Gson().toJson(mapToDTOList(sheepsMovement));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}

	}

	private List<SheepMovement> getSheepsMovement(EntityManager em, String q, Map<String, Object> params) {
		TypedQuery<SheepMovement> query = em.createQuery(q, SheepMovement.class);
		if (params != null) {
			for (Entry<String, Object> entry : params.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.getResultList();
	}

	public SheepMovementDTO mapToDTO(SheepMovement sheepMovement) throws ParseException {
		SheepMovementDTO sheepMovementDTO = new SheepMovementDTO();
		sheepMovementDTO.setDescription(sheepMovement.getDescription());
		sheepMovementDTO.setNotes(sheepMovement.getNotes());
		sheepMovementDTO.setDate(sheepMovement.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		if (sheepMovement.getOutDate() != null) {
			sheepMovementDTO.setOutDate(sheepMovement.getOutDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		}
		if (sheepMovement.getLambs() != null) {
			sheepMovementDTO.setLamb(lambsService.mapToDTO(sheepMovement.getLambs()));
		}
		if (sheepMovement.getSheep() != null) {
			sheepMovementDTO.setSheep(sheepService.mapToDTO(sheepMovement.getSheep()));
		}
		if (sheepMovement.getPlaces() != null) {
			sheepMovementDTO.setPlace(placesService.mapToDTO(sheepMovement.getPlaces()));
		}
		return sheepMovementDTO;
	}

	public SheepMovement mapToEntity(SheepMovementDTO sheepMovementDTO) throws ParseException {
		SheepMovement sheepMovement = new SheepMovement();
		sheepMovement.setDescription(sheepMovementDTO.getDescription());
		sheepMovement.setNotes(sheepMovementDTO.getNotes());
		sheepMovement.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(sheepMovementDTO.getDate()).toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());
		if (sheepMovementDTO.getPlace() != null) {
			Places place = placesDAO.findById(sheepMovementDTO.getPlace().getId()).get();
			sheepMovement.setPlaces(place);
		}
		if (sheepMovementDTO.getSheep() != null) {
			Sheep sheep = sheepDAO.findById(sheepMovementDTO.getSheep().getId()).get();
			sheepMovement.setSheep(sheep);

		}
		if (sheepMovementDTO.getLamb() != null) {
			Lambs lamb = lambsDAO.findById(sheepMovementDTO.getLamb().getId()).get();
			sheepMovement.setLambs(lamb);
		}

		return sheepMovement;
	}

	public List<SheepMovementDTO> mapToDTOList(List<SheepMovement> movements) {
		List<SheepMovementDTO> movementDTOs = new ArrayList<>();
		for (SheepMovement movement : movements) {
			try {
				movementDTOs.add(mapToDTO(movement));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return movementDTOs;
	}

}
