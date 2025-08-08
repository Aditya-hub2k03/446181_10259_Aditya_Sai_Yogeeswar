package com.yogesh.musicsystem;

import java.util.*;

public class MusicLibrary {
	private ArrayList<Song> songs;

    public MusicLibrary() {
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public Song playRandomSong() {
        if (songs.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(songs.size());
        return songs.get(randomIndex);
    }

}
