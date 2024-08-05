package com.project.speaking_clock.service.serviceimpl;

import com.project.speaking_clock.exception.InvalidTimeFormatException;
import com.project.speaking_clock.service.TimeConverterService;
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

        String hourInWords = convertHourToWords(hours);
        String minuteInWords = convertMinutesToWords(minutes);

        return ResponseEntity.ok("It's " + hourInWords + " " + minuteInWords);
    }

    private String convertHourToWords(int hour) {
        String[] hours = {"twelve", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven"};
        if (hour < 0 || hour > 23) {
            throw new InvalidTimeFormatException("Invalid hour value. Please provide a valid time.");
        }
        return hours[hour % 24];
    }

    private String convertMinutesToWords(int minutes) {
        if (minutes < 0 || minutes > 59) {
            throw new InvalidTimeFormatException("Invalid minute value. Please provide a valid time.");
        }

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
    }

    private boolean isValidTimeFormat(String time) {
        return time.matches("([01]\\d|2[0-3]):[0-5]\\d");
    }
}
