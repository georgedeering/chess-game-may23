package com.example.chessjavafx;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class MainGameController extends Main {
    public Pane voteForfeitPane, saveGamePane, drawGamePane, resignGamePane, checkMateWinGamePane, pawnPromotionPane, replayGamePane;
    public Label player1Label, player2Label, voteForfeitPaneLabel, saveGameDefaultPFileName, saveGamePaneWinLabel, winByResignLabel;
    public TextField player1TextField, player2TextField, saveGameTextField, drawGameTextField, winByResignTextField, winCheckMateGameTextField;
    public VBox replayGamePaneVBox;
    private double pieceXPos, pieceYPos;
    private int col, row, newCol, newRow, turnNum;
    public ImageView pieceImageView = new ImageView();
    public GridPane chessBoard;
    @FXML
    AnchorPane anchorPane;

    /**
     * calls the '.continueGame' method from the instantiated 'Game'
     * object to start the previously closed, unfinished game
     */
    public void continueGame() {
        game.continueGame("pfiles/PreviousGame.csv");
        Board continueGameBoard = new Board(game.getBoard().getBoard());
        createNextBoard(continueGameBoard, chessBoard);
        if (game.pFile.getCurrentTurn() == WhiteOrBlack.WHITE) {
            turnNum++;
        }
        turnChange();
    }

    public Game replayGame = new Game();

    /**
     * The inputted Label object becomes clickable. On mouse click, the
     * selected finished game file will become viewable in the 'ReplayGame'
     * scene
     *
     * @param label the name/file-location of the pFile (saved game)
     */
    public void addSaveGameLabelController(Label label) {
        label.setOnMouseClicked(mouseEvent -> {
            replayGamePane.setVisible(false);

            FXMLLoader replayGameLoader = new FXMLLoader(getClass().getResource("ReplayGame.fxml"));
            Parent root;
            try {
                root = replayGameLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ReplayGameController replayGameController = replayGameLoader.getController();
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            String css = Objects.requireNonNull(this.getClass().getResource("StartMenuStyling.css")).toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
            replayGame.newGame();
            startBoardSetup(replayGameController.getReplayChessboard());
            replayGame.replay.getFileBoards(label.getText());
            replayGame.board.setBoard(replayGame.replay.getBoardState());
            createNextBoard(replayGame.board, replayGameController.getReplayChessboard());


        });

    }

    /**
     * prompts the first player to make a name
     */
    public void player1Enter() {


        String player1Name = player1TextField.getText();
        player1Label.setText(player1Name);

        player1TextField.visibleProperty().set(false);
    }

    /**
     * prompts second player for a name, then starts a new game
     */
    public void player2Enter() {
        String player2Name = player2TextField.getText();
        player2Label.setText(player2Name);

        player2TextField.visibleProperty().set(false);
        game.newGame();
        turnChange();

        startBoardSetup(chessBoard);
    }

    /**
     * @param rank         enum denoting piece rank
     * @param whiteOrBlack enum denoting piece colour
     * @param col          column of chessboard
     * @param row          row of chessboard
     */
    public void placePiece(Rank rank, WhiteOrBlack whiteOrBlack, int col, int row, GridPane mainChessboard) {
        String pieceName;
        switch (rank) {
            case PAWN -> pieceName = (whiteOrBlack.equals(WhiteOrBlack.WHITE) ? "White" : "Black") + "Pawn";
            case ROOK -> pieceName = (whiteOrBlack.equals(WhiteOrBlack.WHITE) ? "White" : "Black") + "Rook";
            case KNIGHT -> pieceName = (whiteOrBlack.equals(WhiteOrBlack.WHITE) ? "White" : "Black") + "Knight";
            case BISHOP -> pieceName = (whiteOrBlack.equals(WhiteOrBlack.WHITE) ? "White" : "Black") + "Bishop";
            case QUEEN -> pieceName = (whiteOrBlack.equals(WhiteOrBlack.WHITE) ? "White" : "Black") + "Queen";
            case KING -> pieceName = (whiteOrBlack.equals(WhiteOrBlack.WHITE) ? "White" : "Black") + "King";
            default -> {
                pieceName = "error";
                System.out.println("invalid rank");
            }//ADD PROPER ERROR
        }
        Image pieceImage = new Image("file:src\\main\\resources\\com\\example\\chessjavafx\\Pieces\\" + pieceName + ".png");
        ImageView piece = new ImageView(pieceImage);
        piece.setFitHeight(50);
        piece.setFitWidth(50);
        piece.setId(whiteOrBlack.name() + rank.name());

        mainChessboard.add(piece, col, row);

        //debug testing, shows all id's placed
        System.out.println(piece.getId());
        piece.toFront();
        addPieceEventHandler(piece);
    }

    /**
     * creates and places all pieces in default starting layout
     */
    public void startBoardSetup(GridPane mainChessboard) {
        for (int tempCol = 0; tempCol <= 7; tempCol++) {
            for (int tempRow = 0; tempRow <= 7; tempRow++) {
                switch (tempRow) {
                    case 0 -> {
                        switch (tempCol) {
                            case 0, 7 -> placePiece(Rank.ROOK, WhiteOrBlack.BLACK, tempCol, tempRow, mainChessboard);
                            case 1, 6 -> placePiece(Rank.KNIGHT, WhiteOrBlack.BLACK, tempCol, tempRow, mainChessboard);
                            case 2, 5 -> placePiece(Rank.BISHOP, WhiteOrBlack.BLACK, tempCol, tempRow, mainChessboard);
                            case 3 -> placePiece(Rank.QUEEN, WhiteOrBlack.BLACK, tempCol, tempRow, mainChessboard);
                            case 4 -> placePiece(Rank.KING, WhiteOrBlack.BLACK, tempCol, tempRow, mainChessboard);
                        }
                    }
                    case 1 -> placePiece(Rank.PAWN, WhiteOrBlack.BLACK, tempCol, tempRow, mainChessboard);
                    case 6 -> placePiece(Rank.PAWN, WhiteOrBlack.WHITE, tempCol, tempRow, mainChessboard);
                    case 7 -> {
                        switch (tempCol) {
                            case 0, 7 -> placePiece(Rank.ROOK, WhiteOrBlack.WHITE, tempCol, tempRow, mainChessboard);
                            case 1, 6 -> placePiece(Rank.KNIGHT, WhiteOrBlack.WHITE, tempCol, tempRow, mainChessboard);
                            case 2, 5 -> placePiece(Rank.BISHOP, WhiteOrBlack.WHITE, tempCol, tempRow, mainChessboard);
                            case 3 -> placePiece(Rank.QUEEN, WhiteOrBlack.WHITE, tempCol, tempRow, mainChessboard);
                            case 4 -> placePiece(Rank.KING, WhiteOrBlack.WHITE, tempCol, tempRow, mainChessboard);
                        }
                    }
                }
            }
        }
    }

    /**
     * Used after the chessBoard GridPane is cleared. Adds chess tiles
     * as well as pieces to chessBoard as dictated by the param 'board'
     *
     * @param board new board that needs to be displayed
     */
    public void createNextBoard(Board board, GridPane mainChessBoard) {
        Piece[][] currentBoard = board.getBoard();
        for (int tempCol = 0; tempCol <= 7; tempCol++) {
            for (int tempRow = 0; tempRow <= 7; tempRow++) {

                Rectangle square = new Rectangle(50, 50);
                square.setArcHeight(5);
                square.setArcWidth(5);
                square.setFill((tempRow + tempCol) % 2 == 0 ? Color.MISTYROSE : Color.SIENNA);
                mainChessBoard.toFront();
                mainChessBoard.add(square, tempCol, tempRow);

                if (currentBoard[tempRow][tempCol] != null) { //if this board tile has a piece:
                    placePiece(currentBoard[tempRow][tempCol].getRank(), currentBoard[tempRow][tempCol].getWhiteOrBlack(), tempCol, tempRow, mainChessBoard); //place the corresponding ImageView piece to the one in the currentBoard
                    if (board.checkForCheck(currentBoard[tempRow][tempCol].getWhiteOrBlack(), currentBoard)
                            && currentBoard[tempRow][tempCol].getRank() == Rank.KING) { //if this piece is a King AND in check:
                        //adds a red tile behind the King piece
                        Rectangle checkSquare = new Rectangle(50, 50);
                        checkSquare.setArcHeight(5);
                        checkSquare.setArcWidth(5);
                        checkSquare.setFill(Color.RED);
                        mainChessBoard.getChildren().remove(square);
                        mainChessBoard.add(checkSquare, tempCol, tempRow);
                        checkSquare.toBack();

                        if (board.checkForMate(currentBoard[tempRow][tempCol].getWhiteOrBlack())) { //if the King piece is in checkmate:
                            checkMateWinGamePane.setVisible(true);
                            checkMateWinGamePane.toFront();
                            saveGamePaneWinLabel.setText(getCurrentPlayerLabel() + " WINS!");
                        }
                    }
                }
            }

        }

    }

    /**
     * Shows the 'replay game' menu and loads all the saved games to
     * the ScrollPane object within the 'menu' Pane
     */
    public void replayGame() {
        replayGamePane.setVisible(true);

        File replayFolder = new File("replays/");
        File[] replayFiles = replayFolder.listFiles();

        if (replayFiles != null) {
            for (File replayFile : replayFiles) {
                if (replayFile.isFile()) {
                    String replayGameName = replayFile.getName();
                    Label replayGameLabel = new Label();
                    replayGameLabel.setText("replays/" + replayGameName);
                    replayGameLabel.setId("saveGameLabelId");
                    addSaveGameLabelController(replayGameLabel);
                    replayGamePaneVBox.getChildren().add(replayGameLabel);
                }
            }
        }
    }

    /**
     * Switches the current scene to the start menu scene
     *
     * @param e the current stage
     * @throws IOException in the event there is no start menu fxml document
     */
    public void toStartMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartMenu.fxml")));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        String css = Objects.requireNonNull(this.getClass().getResource("StartMenuStyling.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * checks whether a game has started. If true, user is prompted
     * to save game before exiting program
     */
    public void exitGame() {
        if (turnNum == 0) {
            System.exit(0);
        }
        //save game-state if game has started, then exit
        if (turnNum > 0) {
            game.quit("pfiles/PreviousGame.csv");
        }
        System.exit(0);
    }

    /**
     * Swaps player label's text colours every time this method is called
     * after the turnNum is incremented
     */
    public void turnChange() {

        if (turnNum % 2 == 0) {
            player1Label.setTextFill(Color.YELLOW);
            player2Label.setTextFill(Color.BLACK);
        } else {
            player2Label.setTextFill(Color.YELLOW);
            player1Label.setTextFill(Color.BLACK);
        }

    }

    /**
     * checks if there is a piece at the given tile;
     * if true, the showPiecePossibleMoves function is called
     * for the piece's rank
     *
     * @param col column value of piece
     * @param row row value of piece
     */
    public void getPiecePossibleMoves(int col, int row) {
        if (board.getPiece(col, row) == null) {
            return;
        }
        showPiecePossibleMoves(board.getPiece(col, row).generateMoves(board));
    }

    /**
     * @param whiteOrBlack The colour of the Pawn Piece
     */
    public void promotePiecePane(WhiteOrBlack whiteOrBlack) {
        pawnPromotionPane.setVisible(true);
        pawnPromotionPane.toFront();

        String pieceColour;
        if (whiteOrBlack == WhiteOrBlack.WHITE) {
            pieceColour = "WHITE";
        } else {
            pieceColour = "BLACK";
        }
        //creates, sizes, locates, and adds the ImageView object to the 'Promotion' Pane object
        Image rookPieceImage = new Image("file:src\\main\\resources\\com\\example\\chessjavafx\\Pieces\\" + pieceColour + "ROOK.png");
        ImageView rookBigPiece = new ImageView(rookPieceImage);
        rookBigPiece.setFitHeight(50);
        rookBigPiece.setFitWidth(50);
        rookBigPiece.setOpacity(0.3);
        rookBigPiece.setId("ROOK");
        rookBigPiece.setX(3);
        rookBigPiece.setY(3);
        addPromotionPieceEventHandler(rookBigPiece);
        pawnPromotionPane.getChildren().add(rookBigPiece);

        Image knightPieceImage = new Image("file:src\\main\\resources\\com\\example\\chessjavafx\\Pieces\\" + pieceColour + "Knight.png");
        ImageView knightBigPiece = new ImageView(knightPieceImage);
        knightBigPiece.setFitHeight(50);
        knightBigPiece.setFitWidth(50);
        knightBigPiece.setOpacity(0.3);
        knightBigPiece.setId("KNIGHT");
        knightBigPiece.setX(47);
        knightBigPiece.setY(3);
        addPromotionPieceEventHandler(knightBigPiece);
        pawnPromotionPane.getChildren().add(knightBigPiece);

        Image bishopPieceImage = new Image("file:src\\main\\resources\\com\\example\\chessjavafx\\Pieces\\" + pieceColour + "Bishop.png");
        ImageView bishopBigPiece = new ImageView(bishopPieceImage);
        bishopBigPiece.setFitHeight(50);
        bishopBigPiece.setFitWidth(50);
        bishopBigPiece.setOpacity(0.3);
        bishopBigPiece.setId("BISHOP");
        bishopBigPiece.setX(3);
        bishopBigPiece.setY(47);
        addPromotionPieceEventHandler(bishopBigPiece);
        pawnPromotionPane.getChildren().add(bishopBigPiece);

        Image queenPieceImage = new Image("file:src\\main\\resources\\com\\example\\chessjavafx\\Pieces\\" + pieceColour + "Queen.png");
        ImageView queenBigPiece = new ImageView(queenPieceImage);
        queenBigPiece.setFitHeight(50);
        queenBigPiece.setFitWidth(50);
        queenBigPiece.setOpacity(0.3);
        queenBigPiece.setId("QUEEN");
        queenBigPiece.setX(47);
        queenBigPiece.setY(47);
        addPromotionPieceEventHandler(queenBigPiece);
        pawnPromotionPane.getChildren().add(queenBigPiece);
    }

    /**
     * Adds mouse interactivity to promotionPiece. The piece becomes opaque
     * when the mouse is placed over the image. Promotes the pawn piece to the rank of
     * the promotionPiece's rank on mouse click
     *
     * @param promotionPiece Piece that can be selected for promotion
     */
    public void addPromotionPieceEventHandler(ImageView promotionPiece) {
        promotionPiece.setOnMouseEntered(mouseEvent -> promotionPiece.setOpacity(1));

        promotionPiece.setOnMouseExited(mouseEvent -> promotionPiece.setOpacity(0.5));

        promotionPiece.setOnMouseClicked(mouseEvent -> {
            pawnPromotionPane.setVisible(false);
            String pieceId = promotionPiece.getId();
            switch (pieceId) {
                case "ROOK" -> promotionRank = Rank.ROOK;
                case "KNIGHT" -> promotionRank = Rank.KNIGHT;
                case "BISHOP" -> promotionRank = Rank.BISHOP;
                case "QUEEN" -> promotionRank = Rank.QUEEN;
                default -> System.out.println("invalid pieceID String (line 331)");
            }
            game.promote(col, row, newCol, newRow, promotionRank);
            chessBoard.getChildren().clear();
            createNextBoard(board, chessBoard);

            checkMateWinGamePane.toFront();//if player checkmates, puts win screen in front of chessboard
            System.out.println(board.toString());//prints board layout to command line

            turnNum++;
            System.out.println("The turn number is: " + turnNum);
            turnChange();
        });
    }

    /**
     * adds Rectangle objects to all tiles which are 'true' in the possibleMovesBooleans' 2D array
     *
     * @param possibleMovesBooleans 2D array of all possible moves
     *                              for a specified piece at a specific location on a board
     */
    public void showPiecePossibleMoves(boolean[][] possibleMovesBooleans) {
        for (int col = 0; col <= 7; col++) {
            for (int row = 0; row <= 7; row++) {
                if (possibleMovesBooleans[row][col]) {
                    //creates a highlighted square for every possible move
                    Rectangle possibleMoveSquare = new Rectangle(50, 50, Color.LIGHTBLUE);
                    possibleMoveSquare.setOpacity(0.5);
                    possibleMoveSquare.setArcHeight(5);
                    possibleMoveSquare.setArcWidth(5);
                    chessBoard.add(possibleMoveSquare, col, row);
                }
            }
        }
    }

    /**
     * The inputted ImageView object becomes clickable, draggable and drag-drop capable. On mouse click, the
     * selected piece's possible moves are shown using the 'getPiecePossibleMoves(int, int)' method. The piece
     * is, on drag-drop, checked if it is within an area it can be placed. The piece is then placed and the turn ends.
     * @param piece enables piece object to move on mouse click, drag, and drop
     */
    public void addPieceEventHandler(ImageView piece) {
        piece.setOnMousePressed(mouseEvent -> {
            board = game.getBoard();
            pieceImageView = (ImageView) mouseEvent.getSource(); //imageView object clicked is instantiated as piece variable
            pieceXPos = mouseEvent.getSceneX();
            pieceYPos = mouseEvent.getSceneY();
            col = GridPane.getColumnIndex(pieceImageView);
            row = GridPane.getRowIndex(pieceImageView);
            if ((board.getPiece(col, row).getWhiteOrBlack().equals(WhiteOrBlack.WHITE) && player1Label.getTextFill() == Color.YELLOW)
                    || board.getPiece(col, row).getWhiteOrBlack().equals(WhiteOrBlack.BLACK) && player2Label.getTextFill() == Color.YELLOW) {

                getPiecePossibleMoves(col, row);
                pieceImageView.toFront();
            }
            pieceImageView.toFront();
        });

        piece.setOnMouseDragged(mouseEvent -> {
            pieceImageView.setTranslateX(mouseEvent.getSceneX() - pieceXPos);
            pieceImageView.setTranslateY(mouseEvent.getSceneY() - pieceYPos);
        });

        piece.setOnMouseReleased((MouseEvent mouseEvent) -> {
            board = game.getBoard();


            double newPieceXPos = mouseEvent.getSceneX();
            double newPieceYPos = mouseEvent.getSceneY();

            newCol = (int) ((newPieceXPos - 200) / 50);
            newRow = (int) ((newPieceYPos - 100) / 50);


            if ((mouseEvent.getSceneX() < 200) || (mouseEvent.getSceneX() > 600) ||
                    (mouseEvent.getSceneY() < 100) || (mouseEvent.getSceneY() > 500) || (newCol == col && newRow == row) ||
                    (!(board.getPiece(col, row).getWhiteOrBlack().equals(WhiteOrBlack.WHITE) && player1Label.getTextFill() == Color.YELLOW)) &&
                            !(board.getPiece(col, row).getWhiteOrBlack().equals(WhiteOrBlack.BLACK) && player2Label.getTextFill() == Color.YELLOW)
                    || !(board.getPiece(col, row).generateMoves(board)[newRow][newCol])) {
                TranslateTransition snapBack = new TranslateTransition(Duration.millis(200), pieceImageView);
                snapBack.setByX(pieceXPos - newPieceXPos);
                snapBack.setByY(pieceYPos - newPieceYPos);
                snapBack.play();
                snapBack.setOnFinished(e -> {
                    chessBoard.getChildren().clear();
                    createNextBoard(board, chessBoard);
                });
                //turn does NOT end
                return;
            }

            chessBoard.getChildren().clear(); //clear board
            chessBoard.getChildren().remove(pieceImageView); //remove selected piece
            chessBoard.add(pieceImageView, newCol, newRow); //add piece at new tile
            pieceImageView.setTranslateX(0);
            pieceImageView.setTranslateY(0);

            if (game.board.getPiece(col, row).getRank() == Rank.PAWN && (newRow == 7 || newRow == 0)) { //if the piece is a pawn AND it is moving to the topmost/bottommost row
                createNextBoard(game.getBoard(), chessBoard);
                promotePiecePane(game.board.getPiece(col, row).getWhiteOrBlack());
            } else {
                game.makeMove(col, row, newCol, newRow); //else place piece in new tile

                createNextBoard(board, chessBoard);

                checkMateWinGamePane.toFront();//if player checkmates, puts win screen in front of chessboard
                System.out.println(board.toString());//prints board layout to command line

                turnNum++;
                System.out.println("The turn number is: " + turnNum);
                turnChange();
            }
        });

    }

    /**
     * @return Player label of player whose current turn it is
     */
    public String getCurrentPlayerLabel() {
        if (player1Label.getTextFill() == Color.YELLOW) {
            return player1Label.getText();
        } else {
            return player2Label.getText();
        }
    }

    /**
     * @return Player label of player whose current turn it isn't
     */
    public String getOpponentPlayerLabel() {
        if (player1Label.getTextFill() == Color.WHITE) {
            return player1Label.getText();
        } else {
            return player2Label.getText();
        }
    }

    /**
     * shows voteForfeit Pane which allows other player to agree or disagree to a draw
     */
    public void voteForfeit() {
        voteForfeitPane.setVisible(true);
        voteForfeitPane.toFront();
        voteForfeitPaneLabel.setText("Does player " + getOpponentPlayerLabel()
                + " agree to player " + getCurrentPlayerLabel() + "'s forfeit draw?");
    }

    /**
     * Shows the 'draw' Pane object, prompting the user to save the game for future replays
     */
    public void forfeitGameAgree() {
        voteForfeitPane.setVisible(false);
        drawGamePane.setVisible(true);
        drawGamePane.toFront();
    }


    /**
     * continues the game and the turn does not end
     */
    public void forfeitGameDisagree() {
        voteForfeitPane.setVisible(false);
    }

    /**
     * shows saveGamePane panel
     */
    public void saveGame() {
        saveGamePane.setVisible(true);
        saveGamePane.toFront();
        saveGameDefaultPFileName.setText(getCurrentPlayerLabel() + "_battle");
    }

    /**
     * Changes the name of the pFile, calls the 'quit' game method and ends the game
     * @param e mouse click
     * @throws IOException IOException
     */
    public void saveGameSaveNewName(ActionEvent e) throws IOException {
        //save NAMED game to PFile
        Label saveGameLabel = new Label();
        saveGameLabel.setText("pfiles/" + saveGameTextField.getText() + ".csv");
        saveGameLabel.setId("saveGameLabelId");
        addSaveGameLabelController(saveGameLabel);
        replayGamePaneVBox.getChildren().add(saveGameLabel);
        saveGameLabel.toFront();
        game.quit("pfiles/" + saveGameTextField.getText() + ".csv");
        toStartMenu(e);
    }

    /**
     * Retains the name of the pFile, calls the 'quit' game method and ends the game
     * @param e mouse click
     * @throws IOException IOException
     */
    public void saveGameSaveOldName(ActionEvent e) throws IOException {
        //save DEFAULT NAMED game to PFile
        Label saveGameLabel = new Label();
        saveGameLabel.setText("pfiles/" + saveGameDefaultPFileName.getText() + ".csv");
        saveGameLabel.setId("saveGameLabelId");
        addSaveGameLabelController(saveGameLabel);
        replayGamePaneVBox.getChildren().add(saveGameLabel);
        saveGameLabel.toFront();
        game.quit("pfiles/" + saveGameDefaultPFileName.getText() + ".csv");
        toStartMenu(e);
    }

    /**
     * Continues game
     */
    public void cancelSaveGame() {
        saveGamePane.setVisible(false);
    }

    /**
     * Resign game end pane opened
     */
    public void resign() {
        resignGamePane.setVisible(true);
        resignGamePane.toFront();
        winByResignLabel.setText(getOpponentPlayerLabel() + " wins by RESIGN!");
    }

    public void setReplaySaveNameOnResign(ActionEvent e) throws IOException {
        Label saveGameLabel = new Label();
        saveGameLabel.setText("replays/" + winByResignTextField.getText() + ".csv");
        saveGameLabel.setId("saveGameLabelId");
        addSaveGameLabelController(saveGameLabel);
        replayGamePaneVBox.getChildren().add(saveGameLabel);
        saveGameLabel.toFront();
        game.quit("replays/" + winByResignTextField.getText() + ".csv");
        toStartMenu(e);
    }

    public void setReplaySaveNameOnDraw(ActionEvent e) throws IOException {
        Label saveGameLabel = new Label();
        saveGameLabel.setText("replays/" + drawGameTextField.getText() + ".csv");
        saveGameLabel.setId("saveGameLabelId");
        addSaveGameLabelController(saveGameLabel);
        replayGamePaneVBox.getChildren().add(saveGameLabel);
        saveGameLabel.toFront();
        game.quit("replays/" + drawGameTextField.getText() + ".csv");
        toStartMenu(e);
    }

    public void setReplaySaveNameOnCheckMate(ActionEvent e) throws IOException {
        Label saveGameLabel = new Label();
        saveGameLabel.setText("replays/" + winCheckMateGameTextField.getText() + ".csv");
        saveGameLabel.setId("saveGameLabelId");
        addSaveGameLabelController(saveGameLabel);
        replayGamePaneVBox.getChildren().add(saveGameLabel);
        saveGameLabel.toFront();
        game.quit("replays/" + winCheckMateGameTextField.getText() + ".csv");
        toStartMenu(e);
    }

    public void cancelSaveReplayGame(ActionEvent e) throws IOException {
        toStartMenu(e);
    }
}
