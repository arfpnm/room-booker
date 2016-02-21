package com.nhs.trust.service;

/**
 * Created by arif.mohammed on 30/10/2015.
 */

import java.util.List;
import java.util.Set;

import com.nhs.trust.domain.Site;

public interface SiteService extends CommonService<Site> {
	Site findByName(String siteName) throws Exception;

	List<Site> addBatch(Set<Site> sites);

	Site findByLocationAndTown(String site, String locality, String town)
			throws Exception;
}
