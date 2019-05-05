package com.zanghongtu.servicedemo.dto;

import lombok.Data;

@Data
public class DemoDTO {
    private String resultStr;

    public DemoDTO(String resultStr) {
        this.resultStr = resultStr;
    }
}
