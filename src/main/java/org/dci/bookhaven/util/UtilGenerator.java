package org.dci.bookhaven.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UtilGenerator {

    // Constructor
    public UtilGenerator() {
    }

    // Generate a random LocalDateTime between 1/1/1900 and 1/1/2024
    public LocalDateTime generateRandomLocalDateTime() {
        int year = (int) (Math.random() * 124) + 1900;
        int month = (int) (Math.random() * 12) + 1;
        int day = (int) (Math.random() * 28) + 1;
        int hour = (int) (Math.random() * 24);
        int minute = (int) (Math.random() * 60);
        int second = (int) (Math.random() * 60);

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    // Generate a random LocalDate between 1/1/1900 and 1/1/2024
    public LocalDate generateRandomLocalDate() {
        int year = (int) (Math.random() * 124) + 1900;
        int month = (int) (Math.random() * 12) + 1;
        int day = (int) (Math.random() * 28) + 1;

        return LocalDate.of(year, month, day);
    }

    // Generate a random price in BigDecimal type for books between 0 and 1000
    public BigDecimal generateRandomPrice() {
        return new BigDecimal(Math.random() * 1000);
    }
}
