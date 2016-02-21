package com.nhs.trust.service;

/**
 * Created by arif.mohammed on 30/10/2015.
 */
public interface MailService {

    void sendMail(String from, String to, String subject, String msg) throws Exception;

}
