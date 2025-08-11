package com.yogesh.moviegenre;


public class Main {
    public static void main(String[] args) {

        Movie[] movies = {
            new Movie("Inception", "Warner Bros", "Sci-Fi", 160000000),
            new Movie("Avengers: Endgame", "Marvel Studios", "Action", 356000000),
            new Movie("Joker", "Warner Bros", "Drama", 80000001),
            new Movie("Interstellar", "Paramount Pictures", "Sci-Fi", 79999999)
        };

        String searchGenre = "Sci-Fi";

        Movie[] filteredMovies = GenreCheck.getMovieByGenre(movies, searchGenre);

        if (filteredMovies.length == 0) {
            System.out.println("No movies found for the given genre.");
        } else {
            for (Movie m : filteredMovies) {
                System.out.println("Movie: " + m.getMovieName() +" | Company: " + m.getCompany() +" | Genre: " + m.getGenre() + " | Budget: " + m.getBudget());

                if (m.getBudget() > 80000000) {
                    System.out.println("→ High Budget Movie");
                } else {
                    System.out.println("→ Low Budget Movie");
                }
                System.out.println();
            }
        }
    }
}