package com.kodilla;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class TikTakToe extends Application {

    private boolean turnX = true;
    Pane root = new Pane();
    private Parent createParent() {
        root.setPrefSize(605, 605);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);
                root.getChildren().add(tile);
            }
        }
        return root;
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("TikTakToe");
        primaryStage.setScene(new Scene(createParent()));
        primaryStage.show();
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
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!turnX) {
                        text.setText("O");
                        turnX = true;
                    } else {
                        text.setText("X");
                        turnX = false;
                    }
                }
            });
            getChildren().addAll(rect, text);
        }
    }
}