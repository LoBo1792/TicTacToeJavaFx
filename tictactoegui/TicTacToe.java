package com.example.tictactoegui;

import java.util.Scanner;

public class TicTacToe {
    private String[][] board;
    private int moveMade = 0;

    public TicTacToe() {
        // initialize the board with an empty space
        board = new String[3][3];
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                board[row][column] = " ";
            }
        }
    }

    public void displayBoard() {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (column == 0) {
                    System.out.print("| ");
                }
                System.out.print(board[row][column] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    // returns "X", "O", or "no winner"
    public String verticalCheck() {
        // we want to compare every row of each column, to see if they are equal
        for (int column = 0; column < board[0].length; column++) {
            if (!board[0][column].equals(" ")
                    && board[0][column].equals(board[1][column])
                    && board[1][column].equals(board[2][column])) {
                return board[0][column]; // returns "X" or "O"
            }
        }
        return "no winner";
    }

    // returns "X", "O", or "no winner"
    public String horizontalCheck() {
        // we want to compare every row of each column, to see if they are equal
        for (int row = 0; row < board[0].length; row++) {
            if (!board[row][0].equals(" ")
                    && board[row][0].equals(board[row][1])
                    && board[row][1].equals(board[row][2])) {
                return board[row][0]; // returns "X" or "O"
            }
        }
        return "no winner";
    }

    // returns "X", "O", or "no winner"
    public String diagonalCheck() {
        // top left to bottom right
        if (!board[0][0].equals(" ")
                && board[0][0].equals(board[1][1])
                && board[1][1].equals(board[2][2])) {
            return board[0][0];
        }
        // top right to bottom left
        else if (!board[0][2].equals(" ")
                && board[0][2].equals(board[1][1])
                && board[1][1].equals(board[2][0])) {
            return board[0][2];
        } else {
            return "no winner";
        }
    }

    public boolean makeMove(String move, int row, int column) {
        // check if the row and column are outside of range
        if (row < 0 || row >= board.length || column < 0 || column >= board[0].length) {
            return false;
        }

        // check if the position in the board is empty or not
        if (board[row][column].equals(" ")) {
            board[row][column] = move;
            return true;
        } else {
            return false;
        }
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public int getMoveMade() {
        return moveMade;
    }

    public void setMoveMade(int moveMade) {
        this.moveMade = moveMade;
    }
}