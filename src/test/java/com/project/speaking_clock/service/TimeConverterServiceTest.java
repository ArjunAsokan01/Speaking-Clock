package com.project.speaking_clock.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.speaking_clock.exception.InvalidTimeFormatException;
import com.project.speaking_clock.service.serviceimpl.TimeConverterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class TimeConverterServiceTest {

    private TimeConverterServiceImpl timeConverterService;

    @BeforeEach
    public void setUp() {
        timeConverterService = new TimeConverterServiceImpl();
    }

    @Test
    public void testConvertTimeToWords_Midnight() {
        ResponseEntity<String> response = timeConverterService.convertTimeToWords("00:00");
        assertEquals("It's Midnight", response.getBody());
    }

    @Test
    public void testConvertTimeToWords_Midday() {
        ResponseEntity<String> response = timeConverterService.convertTimeToWords("12:00");
        assertEquals("It's Midday", response.getBody());
    }

    @Test
    public void testConvertTimeToWords_NormalTime() {
        ResponseEntity<String> response = timeConverterService.convertTimeToWords("08:34");
        assertEquals("It's eight thirty four", response.getBody());
    }

    @Test
    public void testConvertTimeToWords_SingleDigitMinutes() {
        ResponseEntity<String> response = timeConverterService.convertTimeToWords("02:05");
        assertEquals("It's two five", response.getBody());
    }

    @Test
    public void testConvertTimeToWords_TeenMinutes() {
        ResponseEntity<String> response = timeConverterService.convertTimeToWords("03:16");
        assertEquals("It's three sixteen", response.getBody());
    }

    @Test
    public void testConvertTimeToWords_TensMinutes() {
        ResponseEntity<String> response = timeConverterService.convertTimeToWords("07:20");
        assertEquals("It's seven twenty", response.getBody());
    }

    @Test
    public void testConvertTimeToWords_InvalidTimeFormat() {
        assertThrows(InvalidTimeFormatException.class, () -> {
            timeConverterService.convertTimeToWords("25:00");
        });
        assertThrows(InvalidTimeFormatException.class, () -> {
            timeConverterService.convertTimeToWords("12:60");
        });
        assertThrows(InvalidTimeFormatException.class, () -> {
            timeConverterService.convertTimeToWords("invalid");
        });
    }
}