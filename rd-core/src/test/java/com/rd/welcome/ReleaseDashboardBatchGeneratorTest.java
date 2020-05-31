package com.rd.welcome;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReleaseDashboardBatchGeneratorTest {

    @org.junit.jupiter.api.Test
    void generateBatchNoTest()
    {
        assertEquals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy")), new ReleaseDashboardBatchGenerator().GenerateBatchNumber());
    }

    @org.junit.jupiter.api.Test
    void getTeamNameTest()
    {
        assertEquals("Release Dashboard Team", new ReleaseDashboardBatchGenerator().GetTeamName());
    }
}
