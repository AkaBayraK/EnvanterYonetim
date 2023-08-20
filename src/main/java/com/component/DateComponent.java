package com.component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

//@Component
//@Data
public class DateComponent {

    @Value("${spring.jackson.time-zone}")
    public String timeZoneDefault;
    
    @Value("${local:date:language:upper}")
    public String localLanguageUpper;
    
    @Value("${local:date:language:lower}")
    public String localLanguageLower;
	/*
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZoneDefault));
        Locale.setDefault(new Locale(localLanguageLower, localLanguageUpper)); // Opsiyonel olarak dil ayarı
    } 
    */
    public String getFormattedDateAsStr(LocalDateTime startDate) {
    	ZoneId istanbul = ZoneId.of(timeZoneDefault);    	
        return startDate.atZone(istanbul).toInstant().toString();
    }
    
}