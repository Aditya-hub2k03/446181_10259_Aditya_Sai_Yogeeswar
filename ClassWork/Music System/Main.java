package com.yogesh.musicsystem;


public class Main {

	public static void main(String[] args) {
		 MusicLibrary musicLibrary = new MusicLibrary();

	        Song song1 = new Song("Dil Se", "AR Rahaman");
	        Song song2 = new Song("Believer", "Imagine Dragons");
	        Song song3 = new Song("F1", "Hans Zimmer");

	        musicLibrary.addSong(song1);
	        musicLibrary.addSong(song2);
	        musicLibrary.addSong(song3);

	        System.out.println("List of all songs in the library:");
	        for (Song song : musicLibrary.getSongs()) {
	            System.out.println(song);
	        }

	        System.out.println("\nPlaying random songs:");
	        for (int i = 0; i < 3; i++) {
	            Song randomSong = musicLibrary.playRandomSong();
	            System.out.println("Now playing: " + randomSong);
	        }
		

	}

}
