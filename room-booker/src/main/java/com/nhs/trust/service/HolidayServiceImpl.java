package com.nhs.trust.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhs.trust.common.TewvServiceException;
import com.nhs.trust.domain.Holiday;
import com.nhs.trust.repository.HolidayRepository;

@Service
@Transactional
public class HolidayServiceImpl implements HolidayService{

	@Autowired
	private HolidayRepository holidayRepository;

	@Override
	public Holiday add(Holiday t) throws TewvServiceException {
		return holidayRepository.save(t);
	}

	@Override
	public List<Holiday> findAll() {
		return holidayRepository.findAll();
	}

	@Override
	public Holiday findById(long id) {
		return holidayRepository.findOne(id);
	}

	@Override
	public Set<Holiday> addBatch(Set<Holiday> holidays) throws Exception {

		List<Holiday> existingHolidays = findAll();

		if(existingHolidays != null && !existingHolidays.isEmpty()){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if(holidays != null && !holidays.isEmpty()){
				for(Holiday holiday : holidays){
					Date existingHolidayDate = holiday.getDate();
					String apiFormat = dateFormat.format(existingHolidayDate);
					for(Holiday existingholiday : existingHolidays){
						if(existingholiday.getName().equalsIgnoreCase(holiday.getName()) && 
								apiFormat.equals(dateFormat.format(existingholiday.getDate()))){
							holiday.setHid(existingholiday.getHid());
						}
					}
				}
			}
		}
		if(holidays != null){
			holidayRepository.save(holidays);
		}
		return holidays;
	}

	@Override
	public List<Holiday> findByYear(Integer year) {
		return holidayRepository.findByYear(year);
	}
	
	@Override
	public List<Holiday> findByYearAndMonth(Integer year, Integer month) {
		return holidayRepository.findByYearMonth(year, month);
	}

	@Override
	public List<Holiday> findByYearMonthAndDay(Integer year, Integer month, Integer day) {
		return holidayRepository.findByYearMonthDay(year, month, day);
	}
}
