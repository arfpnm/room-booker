package com.nhs.trust.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nhs.trust.domain.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long>, JpaSpecificationExecutor<Holiday>{
	
	@Query(value = "SELECT holiday FROM Holiday holiday WHERE  year(holiday.date) = :year")
	List<Holiday> findByYear(@Param("year") int year);
	
	@Query(value = "SELECT holiday FROM Holiday holiday WHERE  year(holiday.date) = :year and month(holiday.date) = :month")
	List<Holiday> findByYearMonth(@Param("year") int year, @Param("month") int month);
	
	@Query(value = "SELECT holiday FROM Holiday holiday WHERE  year(holiday.date) = :year and "
			+ "month(holiday.date) = :month and day(holiday.date) = :day")
	List<Holiday> findByYearMonthDay(@Param("year") int year, @Param("month") int month, @Param("day") int day);

}
