package com.yogesh.cricketrunscheck;

class Solution {

    public static int[] findAverageOfRuns(Player[] players, int target) {
        int count = 0;
        for (Player p : players) {
            if (p.getMatchesPlayed() >= target) {
                count++;
            }
        }

        int[] averages = new int[count];
        int index = 0;
        for (Player p : players) {
            if (p.getMatchesPlayed() >= target) {
                averages[index] = (int) p.getRunsScored() / p.getMatchesPlayed();
                index++;
            }
        }

        return averages;
    }
}