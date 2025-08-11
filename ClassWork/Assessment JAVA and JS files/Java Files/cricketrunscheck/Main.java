package com.yogesh.cricketrunscheck;

public class Main {
    public static void main(String[] args) {
        Player[] players = new Player[4];
        players[0] = new Player(1, "Virat Kohli", 1, 250, 12000);
        players[1] = new Player(2, "Rohit Sharma", 2, 200, 9500);
        players[2] = new Player(3, "Steve Smith", 3, 180, 8000);
        players[3] = new Player(4, "Kane Williamson", 4, 150, 7000);

        int target = 150; 

        int[] averages = Solution.findAverageOfRuns(players, target);

        for (double avg : averages) {
            System.out.println("Average: " + avg);
            if (avg >= 80 && avg <= 100) {
                System.out.println("Grade A");
            } else if (avg >= 50 && avg < 80) {
                System.out.println("Grade B");
            } else {
                System.out.println("Grade C");
            }
            System.out.println("\n");
        }
    }
}