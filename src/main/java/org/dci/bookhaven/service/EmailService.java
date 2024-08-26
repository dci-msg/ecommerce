package org.dci.bookhaven.service;

import org.dci.bookhaven.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAdminNotification(Book book) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("admin@example.com");
        message.setSubject("Book Out of Stock: " + book.getTitle());
        message.setText("The book \"" + book.getTitle() + "\" is out of stock. Please restock it as soon as possible.");
        mailSender.send(message);
    }

    public void sendCustomerNotification(Book book) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("customer@example.com");
        message.setSubject("Limited Stock Alert: " + book.getTitle());
        message.setText("Hurry! Only a few copies of \"" + book.getTitle() + "\" are left. Buy now before it’s too late!");
        mailSender.send(message);
    }

    public void sendCustomerOutOfStockNotification(Book book) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("customer@example.com");
        message.setSubject("Book Out of Stock: " + book.getTitle());
        message.setText("The book \"" + book.getTitle() + "\" is currently out of stock. You can sign up to be notified when it is back in stock.");
        mailSender.send(message);
    }
}
