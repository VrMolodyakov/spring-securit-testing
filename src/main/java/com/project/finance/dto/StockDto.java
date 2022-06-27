package com.project.finance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StockDto {

    @JsonProperty("timestamp")
    private Object timestamp;

    @JsonProperty("quote")
    private Map<String,List<String>> quote;
}
