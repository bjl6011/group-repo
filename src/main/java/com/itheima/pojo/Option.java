package com.itheima.pojo;

import lombok.Data;

@Data
public class Option {
    public String optionSelect;
    public String optionValue;
    public Integer pageNumber;
    public Integer pageSize;
}