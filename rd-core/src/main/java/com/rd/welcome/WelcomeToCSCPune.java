package com.rd.welcome;

public class WelcomeToCSCPune {
    private String batchName;
    private String batchNo;


    WelcomeToCSCPune(IBatchGenerator batchGenerator) {
        if (batchGenerator != null) {
            batchNo = batchGenerator.GenerateBatchNumber();
            batchName = batchGenerator.GetTeamName();
        }
    }

    public String toString() {
        if (batchNo != null && !batchNo.isEmpty()) {
            return batchName + "; batch number: " + batchNo;
        }
        return "UnknownTeam";
    }

    public static void main(String[] args) {
        System.out.println("Welcome to CSC Pune - Interns Batch 2020");
    }
}
