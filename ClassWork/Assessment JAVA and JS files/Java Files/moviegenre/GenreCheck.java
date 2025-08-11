package com.yogesh.moviegenre;

public class GenreCheck {
    public static Movie[] getMovieByGenre(Movie[] movies, String searchGenre) {
        Movie[] temp = new Movie[movies.length];
        int count = 0;

        for (Movie m : movies) {
            if (m.getGenre().equalsIgnoreCase(searchGenre)) {
                temp[count] = m;
                count++;
            }
        }

        Movie[] result = new Movie[count];
        for (int i = 0; i < count; i++) {
            result[i] = temp[i];
        }
        return result;
    }
}