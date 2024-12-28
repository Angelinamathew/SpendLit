package com.ExpenseTracker.SpendLit.service.stats;

import com.ExpenseTracker.SpendLit.dto.GraphDto;
import com.ExpenseTracker.SpendLit.dto.StatsDto;

public interface StatsService {

    GraphDto getGraphCharts();

    StatsDto getStats();
}
