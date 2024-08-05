package com.project.speaking_clock.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface TimeConverterService {

    ResponseEntity<String> convertTimeToWords(String time);
}
