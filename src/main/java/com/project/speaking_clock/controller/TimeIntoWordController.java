package com.project.speaking_clock.controller;

import com.project.speaking_clock.exception.InvalidTimeFormatException;
import com.project.speaking_clock.sevice.TimeConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/time")
public class TimeIntoWordController {

    @Autowired
    TimeConverterService timeConverterService;

    @GetMapping("/getWords")
    public ResponseEntity<String> getTimeInWords(@RequestParam String time) {
        try {
            return timeConverterService.convertTimeToWords(time);
        } catch (InvalidTimeFormatException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An unexpected error occurred: " + ex.getMessage());
        }
    }

    @GetMapping("/getCurrentTime")
    public ResponseEntity<String> getCurrentTimeInWords() {

        try {
            String currentTime = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
            return timeConverterService.convertTimeToWords(currentTime);
        } catch (InvalidTimeFormatException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An unexpected error occurred: " + ex.getMessage());
        }
    }
}
