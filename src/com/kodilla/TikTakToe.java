package com.kodilla;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TikTakToe extends Application {
    private boolean gameOn = true;
    private boolean turnX = true;
    private Tile[][] board = new Tile[3][3];
    private List<WinCondition> conditionList = new ArrayList<>();
    Stage window;

    Pane root = new Pane();
    private Parent createParent() {
        root.setPrefSize(605, 605);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);
                root.getChildren().add(tile);

                board[j][i] = tile;
            }
        }

        for(int y = 0; y < 3; y++){
            conditionList.add(new WinCondition(board[0][y], board[1][y], board[2][y]));
        }
        for(int x = 0; x< 3; x++) {
            conditionList.add(new WinCondition(board[x][0], board[x][1], board[x][2]));
        }

        conditionList.add(new WinCondition(board[2][0], board[1][1], board[0][2]));
        conditionList.add(new WinCondition(board[0][0], board[1][1], board[2][2]));
        return root;
    }

    public void start(Stage primaryStage) {
        window = primaryStage;
        primaryStage.setTitle("TikTakToe");
        primaryStage.setScene(new Scene(createParent()));
        primaryStage.show();
    }
    private void checkStatus() {
        for (WinCondition condition : conditionList) {
            if (condition.gameEnd()) {
                gameOn = false;
                gameEndWindow(condition);
                break;
            }
        }
    }

    private void gameEndWindow(WinCondition condition) {
        String winner;
        if(turnX) {
            winner = "O";
        }else{
            winner = "X";
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Play Again?");
        alert.setHeaderText("Game end " + winner + " win.");
        alert.setContentText("Start New Game?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            window.close();
            Platform.runLater( () -> new TikTakToe().start( new Stage() ) );
        } else {
            Platform.exit();
        }
    }

    private class WinCondition {
        private Tile[] tiles;
        public WinCondition(Tile... tiles) {
            this.tiles = tiles;
        }
        public boolean gameEnd() {
            if (tiles[0].getValue().isEmpty())
                return false;
            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class Tile extends StackPane {
        Text text = new Text();

        public Tile() {
            Rectangle rect = new Rectangle(200, 200, Color.SNOW);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(5);
            text.setFont(Font.font(100));
            setAlignment(Pos.CENTER);

            setOnMouseClicked(event -> {
                if(!gameOn)
                    return;
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!turnX) {
                        text.setText("O");
                        turnX = true;
                        checkStatus();
                    } else {
                        text.setText("X");
                        turnX = false;
                        checkStatus();
                    }
                }
            });
            getChildren().addAll(rect, text);
        }
        public String getValue() {
            return text.getText();
        }
    }

}