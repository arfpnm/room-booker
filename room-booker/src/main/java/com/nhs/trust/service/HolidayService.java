package com.nhs.trust.service;

import java.util.List;
import java.util.Set;

import com.nhs.trust.domain.Holiday;

public interface HolidayService extends CommonService<Holiday> {

	Set<Holiday> addBatch(Set<Holiday> holidaySet) throws Exception;

	List<Holiday> findByYear(Integer year);

	List<Holiday> findByYearAndMonth(Integer year, Integer month);

	List<Holiday> findByYearMonthAndDay(Integer year, Integer month, Integer day);

}
