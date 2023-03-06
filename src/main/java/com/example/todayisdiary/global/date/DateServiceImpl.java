package com.example.todayisdiary.global.date;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

@Service
public class DateServiceImpl implements DateService {
    @Override
    public String betweenDate(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(dateTime.toLocalTime(), now.toLocalTime());
        if (diff.getSeconds() >= 60) {
            if (diff.toMinutes() >= 60) {
                if (diff.toHours() >= 24) {
                    Period period = Period.between(dateTime.toLocalDate(), now.toLocalDate());
                    if (period.getDays() >= 30) {
                        if (period.getMonths() >= 12) {
                            return period.getYears() + "년전";
                        } else return period.getMonths() + "달전";
                    } else return period.getDays() + "일전";
                } else return diff.toHours() + "시간전";
            } else return diff.toMinutes() + "분전";
        } else return "방금전";
    }

    @Override
    public boolean invalidCode(LocalDateTime dateTime){
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(dateTime.toLocalTime(), now.toLocalTime());
        return diff.toMinutes() >= 3;
    }
}
