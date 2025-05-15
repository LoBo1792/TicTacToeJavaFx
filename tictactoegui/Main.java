/*
          Name: Alexandra LoBosco
          Email: a.lobo1792@gmail.com
          Collaboration with: N/A
          References:  Class material
*/
package com.example.tictactoegui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    // GUI components
    private Button[][] positions; // each button represent a position in the game board.
    private GridPane GUIBoardPane; // to group the buttons in 'positions' in a 2D grid
    private ImageView[][] imageViews; //This is a parallel array to the 'positions' array, to view the image of a button.
    private Image EMPTY; // an empty image
    private Image OImage; // image for player 1
    private Image XImage; // image for player 2
    private Image currentPlayerImage; // pointer to either OImage or XImage
    private Text currentPlayerDisplay;
    private Text winner; // display the winner or if there is a tie.

    // Game components
    private boolean gameOver;
    private TicTacToe ticTacToe; // contain the attributes and method to play the tic-tac-toe game
    private Player player1;
    private Player player2;
    private Player currentPlayer; // pointer to either player1 or player2
    private boolean currentTurn; // true means the player1's turn. false means player2's turn

    // constructor to initialize attributes
    public Main() {
        // initialize GUI components
        GUIBoardPane = new GridPane();
        positions = new Button[3][3];
        imageViews = new ImageView[3][3];
        currentPlayerDisplay = new Text("Player's turn: O"); // O moves first
        currentPlayerDisplay.setFont(new Font("Arial", 50));
        winner = new Text();
        winner.setFont(new Font(10));

        // Initialize game components
        gameOver = false;
        ticTacToe = new TicTacToe();
        player1 = new Player("Player 1", "O");
        player2 = new Player("Player 2", "X");
        currentPlayer = player1; // O plays first
        currentTurn = true;
    }


    // the start of the program.
    @Override
    public void start(Stage stage) throws Exception {
        setUpGUIBoard();
        FlowPane flowPane = new FlowPane(Orientation.VERTICAL, currentPlayerDisplay, GUIBoardPane);
        flowPane.setVgap(10);
        flowPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(flowPane, 400, 450);
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();
    }

    // set up the GUI Board
    public void setUpGUIBoard() {

        String currentPath = System.getProperty("user.dir") + "\\images\\";

        File o = new File(currentPath + "O.png");
        File x = new File(currentPath + "X.png");
        File empty = new File(currentPath + "EMPTY.png");
        System.out.println(o.exists());


        // loading images
        OImage = new Image(o.toURI().toString());
        XImage = new Image(x.toURI().toString());
        EMPTY = new Image(empty.toURI().toString());
        currentPlayerImage = OImage;

        // create a 2D array of image views that associate with the positions.
        for (int row = 0; row < imageViews.length; row++) {
            for (int column = 0; column < imageViews[row].length; column++) {
                ImageView view = new ImageView(EMPTY);
                view.setFitHeight(100);
                view.setPreserveRatio(true);
                imageViews[row][column] = view;
            }
        }

        // creates positions in the board and fill them empty images at first
        // also, we make sure every button click will call the handleClick() method.
        for (int row = 0; row < positions.length; row++) {
            for (int column = 0; column < positions[row].length; column++) {
                positions[row][column] = new Button();
                positions[row][column].setPrefSize(100, 100);
                positions[row][column].setGraphic(imageViews[row][column]);
                positions[row][column].setOnAction(this::handleClick);
                GUIBoardPane.add(positions[row][column], column, row); //  GridPane is column, row
            }
        }
    }

    //Game Logic goes here - Everytime the user clicked on a button of the board, this method get called.
    // Called every time a player clicks a button
    public void handleClick(ActionEvent event) {
        if (!gameOver) {
            for (int row = 0; row < positions.length; row++) {
                for (int column = 0; column < positions[row].length; column++) {
                    if (event.getSource() == positions[row][column]) {
                        boolean moveValid = ticTacToe.makeMove(currentPlayer.getSymbol(), row, column);

                        if (moveValid) {
                            imageViews[row][column].setImage(currentPlayerImage);
                            ticTacToe.setMoveMade(ticTacToe.getMoveMade() + 1);
                            currentTurn = !currentTurn;

                            handleEndOfTurn();
                            return;
                        }
                    }
                }
            }
        }
    }

    // Handles winner check, tie check, switching players, and updating display
    void handleEndOfTurn() {
        String winnerSymbol = "no winner";

        String hCheck = ticTacToe.horizontalCheck();
        String vCheck = ticTacToe.verticalCheck();
        String dCheck = ticTacToe.diagonalCheck();

        if (!hCheck.equals("no winner")) {
            winnerSymbol = hCheck;
        } else if (!vCheck.equals("no winner")) {
            winnerSymbol = vCheck;
        } else if (!dCheck.equals("no winner")) {
            winnerSymbol = dCheck;
        }

        if (!winnerSymbol.equals("no winner")) {
            gameOver = true;
            String winningPlayer;

            if (winnerSymbol.equals(player1.getSymbol())) {
                winningPlayer = player1.getName();
            } else {
                winningPlayer = player2.getName();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("Winner!");
            alert.setContentText(winningPlayer + " wins the game!");
            alert.show();
        } else if (ticTacToe.getMoveMade() == 9) {
            gameOver = true;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("Draw!");
            alert.setContentText("It's a tie!");
            alert.show();
        }

        if (!gameOver) {
            if (currentPlayer == player1) {
                currentPlayer = player2;
                currentPlayerImage = XImage;
            } else {
                currentPlayer = player1;
                currentPlayerImage = OImage;
            }

            currentPlayerDisplay.setText("Player's turn: " + currentPlayer.getSymbol());
        }
    }
}