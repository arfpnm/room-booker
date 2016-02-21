package com.nhs.trust.service;
/**
 * Created by arif.mohammed on 30/10/2015.
 */

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhs.trust.common.TewvServiceException;
import com.nhs.trust.domain.Site;
import com.nhs.trust.repository.SiteRepository;

@Service
@Transactional
public class SiteServiceImpl implements SiteService {

	private static Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);
	@Autowired
	private SiteRepository sitesRepository;

	@Override
	public Site add(Site t) throws TewvServiceException {
		return sitesRepository.save(t);
	}
	
	@Override
	public List<Site> addBatch(Set<Site> sites) {
			return sitesRepository.save(sites);
	}

	@Override
	public List<Site> findAll() {
		return sitesRepository.findAll();
	}

	@Override
	public Site findById(long id) {
		return sitesRepository.findOne(id);
	}

	@Override
	public Site findByName(String siteName) throws TewvServiceException{
		try {
			return sitesRepository.findByName(siteName);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new TewvServiceException(e);
		}
	}
	
	@Override
	public Site findByLocationAndTown(String site, String locality, String town) throws TewvServiceException{
		try {
			return sitesRepository.findByLocalityAndTown(site, locality, town);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new TewvServiceException(e);
		}
	}
}
