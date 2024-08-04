package com.project.speaking_clock.sevice.serviceimpl;

import com.project.speaking_clock.exception.InvalidTimeFormatException;
import com.project.speaking_clock.sevice.TimeConverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TimeConverterServiceImpl implements TimeConverterService {

    @Override
    public ResponseEntity<String> convertTimeToWords(String time) {

        if (!isValidTimeFormat(time)) {
            throw new InvalidTimeFormatException("Invalid time format. Please use HH:mm format.");
        }

        String[] timeParts = time.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);

        if (hours == 0 && minutes == 0) {
            return ResponseEntity.ok("It's Midnight");
        } else if (hours == 12 && minutes == 0) {
            return ResponseEntity.ok("It's Midday");
        }
        String hourInWords;
        try {
            hourInWords = convertHourToWords(hours);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error converting hours: " + e.getMessage());
        }

        String minuteInWords;
        try {
            minuteInWords = convertMinutesToWords(minutes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error converting minutes: " + e.getMessage());
        }

        return ResponseEntity.ok("It's " + hourInWords + " " + minuteInWords);
    }

    private String convertHourToWords(int hour) {
        try {
            String[] hours = {"twelve", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven"};
            return hours[hour % 24];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidTimeFormatException("Invalid hour value. Please provide a valid time.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during hour conversion: " + e.getMessage());
        }
    }

    private String convertMinutesToWords(int minutes) {
        try {
            if (minutes == 0) {
                return "o'clock";
            }
            String[] minuteTens = {"", "", "twenty", "thirty", "forty", "fifty"};
            String[] minuteUnits = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

            if (minutes < 10) {
                return minuteUnits[minutes];
            } else if (minutes < 20) {
                String[] teens = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
                return teens[minutes - 10];
            } else {
                int tens = minutes / 10;
                int units = minutes % 10;
                return minuteTens[tens] + (units != 0 ? " " + minuteUnits[units] : "");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidTimeFormatException("Invalid minute value. Please provide a valid time.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during minute conversion: " + e.getMessage());
        }
    }

    private boolean isValidTimeFormat(String time) {
        return time.matches("([01]\\d|2[0-3]):[0-5]\\d");
    }
}
