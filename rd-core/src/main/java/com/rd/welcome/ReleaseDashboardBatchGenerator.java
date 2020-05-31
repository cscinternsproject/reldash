package com.rd.welcome;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReleaseDashboardBatchGenerator implements IBatchGenerator{

    public static final String TIMESTAMP_FORMAT = "YYYY";
    public String GenerateBatchNumber()
    {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT));
    }

    public String GetTeamName()
    {
        return "Release Dashboard Team";
    }

}
