package com.project.speaking_clock.sevice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TimeConverterService {

    ResponseEntity<String> convertTimeToWords(String time);
}
