package com.example.chessjavafx;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class Main extends Application {
   Rank promotionRank;
   Board board;
   public Game game = new Game();

   /**
    * @param stage the window in which the scenes are displayed
    * @throws IOException coverage
    */
   @Override
   public void start(Stage stage) throws IOException {

      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartMenu.fxml")));
      Scene scene = new Scene(root);

      String css = Objects.requireNonNull(this.getClass().getResource("StartMenuStyling.css")).toExternalForm();
      scene.getStylesheets().add(css);

      stage.setResizable(false);

      stage.setTitle("Group 01");
      stage.setScene(scene);
      stage.show();

   }

   public static void main(String[] args) {
      launch();
   }


}
