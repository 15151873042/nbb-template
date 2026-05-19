package com.nbb.template.admin.controller;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TestPageDTO {

    private String str;

    private LocalDateTime localDateTime;

    private LocalDate localDate;

    private Date date;

    private Long long1;

    private long long2;

    private BigDecimal bigDecimal;


}
