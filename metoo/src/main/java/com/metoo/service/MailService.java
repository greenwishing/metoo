package com.metoo.service;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/4
 */
public interface MailService {

    void sendMail(String to, String subject, String content) throws Exception;
}
