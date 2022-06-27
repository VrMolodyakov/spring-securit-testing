package com.project.finance.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${stock.name}", url =  "${stock.baseURL}")
public interface StockService {
    @GetMapping("/{code}")
    ResponseEntity<?> getExchangeRates(@PathVariable String code);

}
