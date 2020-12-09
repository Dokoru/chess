package chess.view;

import chess.model.Cell;
import chess.model.pieces.Piece;
import chess.model.pieces.PieceType;
import chess.service.BoardService;
import chess.service.ColorService;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AppView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public final int CELL_SIZE = 50;
    public final int BOARD_SIZE = 8;

    private  ImageView[][] imgPieces = new ImageView[BOARD_SIZE][BOARD_SIZE];

    private Group cellGroup = new Group();
    private Group pieceGroup = new Group();

    private BoardService boardService = new BoardService();

    public Rectangle createCellRec(Cell cell) {
        Rectangle cellRec = new Rectangle();
        cellRec.setWidth(CELL_SIZE);
        cellRec.setHeight(CELL_SIZE);
        cellRec.relocate(cell.getX() * CELL_SIZE, cell.getY() * CELL_SIZE);
        cellRec.setFill((cell.getX() + cell.getY()) % 2 == 0 ? Color.valueOf("#f5ebcf") : Color.valueOf("#443702"));
        return cellRec;
    }

    private double mouseX;
    private double mouseY;

    public ImageView createPieceImg(Piece piece, int y, int x) {
        ImageView imgView = new ImageView();
        if (piece != null) {
            Image pieceImg = new Image("resources/" + piece.getColor().getName().toLowerCase() + '-' +
                    piece.getType().getName().toLowerCase() + ".png");
            imgView = new ImageView(pieceImg);
        }
        imgView.setFitHeight(50);
        imgView.setFitWidth(50);
        imgView.setX(x * CELL_SIZE);
        imgView.setY(y * CELL_SIZE);

        return imgView;
    }

    public void initMouseEvent(ImageView imgView) {
        imgView.setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        imgView.setOnMouseDragged(e -> {
            int prevY = toBoard(mouseX);
            int prevX = toBoard(mouseY);
            if (boardService.getBoard()[prevX][prevY].getPiece().getColor() == boardService.getCurrentPlayer()) {
                imgPieces[prevX][prevY].relocate(e.getSceneX() - mouseX + prevY * CELL_SIZE, e.getSceneY() - mouseY + prevX * CELL_SIZE);
            }
        });

        imgView.setOnMouseReleased(e -> {
            int newY = toBoard(e.getSceneX());
            int newX = toBoard(e.getSceneY());

            int prevY = toBoard(mouseX);
            int prevX = toBoard(mouseY);

            if (boardService.tryMove(boardService.getCell(prevX, prevY), boardService.getCell(newX, newY))) {
                if (imgPieces[newX][newY] != null) {
                    imgPieces[newX][newY].setImage(null);
                }
                imgPieces[prevX][prevY].relocate(newY * CELL_SIZE, newX * CELL_SIZE);
                imgPieces[newX][newY] = imgPieces[prevX][prevY];
                imgPieces[prevX][prevY] = null;

                resetPieceImg();

                if (((newX == 0) || (newX == BOARD_SIZE)) &&
                        (boardService.getBoard()[newX][newY].getPiece().getType() == PieceType.PAWN)) {
                    int a = 0;
                    imgPieces[prevX][prevY] = new ImageView(new Image("resources/" +
                            boardService.getCurrentPlayer().getName().toLowerCase() +
                            "-queen.png"));
                }

                ColorService colorService = new ColorService();
                if (boardService.isCheck(boardService.getCurrentPlayer())) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Check!");
                    alert.setContentText(String.format("Check for %s!",
                            colorService.reversColor(colorService.reversColor(boardService.getCurrentPlayer())).getName()));

                    alert.showAndWait();
                }

                if (boardService.isCheckmate(boardService.getCurrentPlayer())) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Game over!");
                    alert.setContentText(String.format("Game over! %s player win!",
                            colorService.reversColor(boardService.getCurrentPlayer()).getName()));

                    alert.showAndWait();
                }

            }
            else {
                imgPieces[prevX][prevY].relocate(prevY * CELL_SIZE, prevX * CELL_SIZE);
            }
        });
    }

    public void resetPieceImg() {
        Cell[][] board = boardService.getBoard();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                if (board[x][y].getPiece() == null && this.imgPieces[x][y] != null) {
                    this.imgPieces[x][y].setImage(null);
                    pieceGroup.getChildren().remove(this.imgPieces[x][y]);
                }
                if (board[x][y].getPiece() != null && this.imgPieces[x][y] == null) {
                    ImageView imageView = createPieceImg(board[x][y].getPiece(), x, y);
                    initMouseEvent(imageView);
                    this.imgPieces[x][y] = imageView;
                    pieceGroup.getChildren().add(imageView);
                }
            }
        }
    }

    private int toBoard(double coord) {
        return (int)(coord) / CELL_SIZE;
    }

    public void createBoard() {
        boardService.initBoard();
        boardService.fillBoard();
        Cell[][] board = boardService.getBoard();

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                Rectangle rectangle = createCellRec(board[x][y]);
                cellGroup.getChildren().add(rectangle);
                if (board[x][y].getPiece() != null) {
                    ImageView imageView = createPieceImg(board[x][y].getPiece(), x, y);
                    initMouseEvent(imageView);
                    this.imgPieces[x][y] = imageView;
                    pieceGroup.getChildren().add(imageView);
                }
            }
        }
    }

    public Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE);
        createBoard();
        root.getChildren().addAll(cellGroup, pieceGroup);
        return root;
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createContent(), 400, 400);
        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
