package com.rd.welcome;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WelcomeToCSCPuneTest {

    @org.junit.jupiter.api.Test
    void releaseDashboardTeamNameTest() {
        WelcomeToCSCPune welcomeToCSCPune = new WelcomeToCSCPune(new ReleaseDashboardBatchGenerator());
        assertEquals(welcomeToCSCPune.toString(),"Release Dashboard Team; batch number: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy")));
    }

    @org.junit.jupiter.api.Test
    void unknownTeamTest() {
        WelcomeToCSCPune welcomeToCSCPune = new WelcomeToCSCPune(null);
        assertEquals(welcomeToCSCPune.toString(),"UnknownTeam");
    }
}