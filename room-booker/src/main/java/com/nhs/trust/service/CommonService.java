package com.nhs.trust.service;

import java.util.List;

import com.nhs.trust.common.TewvServiceException;

/**
 * Created by arif.mohammed on 30/10/2015.
 */
public interface CommonService<T> {

    T add(T t) throws TewvServiceException;

    List<T> findAll();

    T findById(long id);

}
