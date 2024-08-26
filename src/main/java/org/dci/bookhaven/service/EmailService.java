package org.dci.bookhaven.service;

public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
