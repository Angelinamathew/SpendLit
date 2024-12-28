package com.ExpenseTracker.SpendLit.controller;

import com.ExpenseTracker.SpendLit.dto.GraphDto;
import com.ExpenseTracker.SpendLit.service.stats.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/chart")
    public ResponseEntity<GraphDto> getChartData(){
        return ResponseEntity.ok(statsService.getGraphCharts());
    }
}
