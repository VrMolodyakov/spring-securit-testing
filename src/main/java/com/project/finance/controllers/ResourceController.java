package com.project.finance.controllers;

import com.project.finance.dbServices.ClientServiceImpl;
import com.project.finance.dto.StockDto;
import com.project.finance.feign.StockService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ResourceController {

    private final StockService stockService;
    private final static Logger logger = LoggerFactory.getLogger(ResourceController.class);
    @GetMapping("/api/{code}")
    public ResponseEntity<?> allAccess(@PathVariable String code) {
        logger.info("try to find code {}",code);
        return stockService.getExchangeRates(code);
    }
}
