package com.nhs.trust.repository;

/**
 * Created by arif.mohammed on 30/10/2015.
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nhs.trust.domain.Site;

//@Repository
public interface SiteRepository extends JpaRepository<Site, Long>{

	@Query(value = "SELECT s FROM Site s WHERE s.siteName = :siteName")
	Site findByName(@Param("siteName") String siteName);

	@Query(value = "SELECT s FROM Site s WHERE s.siteName = :site and s.locality = :locality and s.town= :town")
	Site findByLocalityAndTown(@Param("site") String site, @Param("locality") String locality, @Param("town") String town);
	
}