package com.yogesh.bookstore;


class Books {
    private String title;
    private String author;
    private int cost; 
    private int noOfBooks;

    public Books(String title, String author, int cost, int noOfBooks) {
        this.title = title;
        this.author = author;
        this.cost = cost;
        this.noOfBooks = noOfBooks;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getNoOfBooks() {
        return noOfBooks;
    }
    public void setNoOfBooks(int noOfBooks) {
        this.noOfBooks = noOfBooks;
    }

    public void displayInfo() {
        System.out.printf("Title: %s, Author: %s, Cost: %d, Available: %d%n",
                title, author, cost, noOfBooks);
    }
}