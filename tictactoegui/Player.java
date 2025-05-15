package com.example.tictactoegui;

public class Player {
    private String name;
    private String symbol;

    public Player(String name, String symbol)
    {
        this.name = name;
        this.symbol = symbol;
    }

    public void printInfo()
    {
        System.out.println("Player's name: " + name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
