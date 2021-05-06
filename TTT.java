import java.util.Random;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TTT extends Application {

  Square squares[][];
  Text t;
  boolean gameOver;
  Text playerScore;
  Text computerScore;
  int computer = 0;
  int player = 0;

  @Override
  public void start(Stage primarystage) {
    initialize(primarystage);
  }

  public void initialize(Stage stage) {
    gameOver = false;
    VBox parent = new VBox();
    HBox info = new HBox();
    GridPane grid = new GridPane();

    Button restartbutton = new Button("New Game");
    restartbutton.setMinSize(100, 50);
    restartbutton.setTranslateX(498);
    restartbutton.setTranslateY(-70);
    restartbutton.setOnAction(
        e -> {
          initialize(stage);
        });

    computerScore = new Text("Computer: " + computer);
    computerScore.setFont(new Font(20));
    computerScore.setTranslateX(88);
    computerScore.setTranslateY(80);

    playerScore = new Text("Player: " + player);
    playerScore.setFont(new Font(20));
    playerScore.setTranslateX(5);
    playerScore.setTranslateY(55);

    t = new Text("Pick a square");
    t.setFont(new Font(60));
    grid.setGridLinesVisible(true);

    squares = new Square[3][3];
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        Square button = new Square(' ');
        button.setOnAction(new SquareClickHandler());
        grid.add(button, col, row);
        squares[col][row] = button;
      }
    }

    info.getChildren().add(t);
    info.getChildren().add(restartbutton);
    info.getChildren().add(computerScore);
    info.getChildren().add(playerScore);
    parent.getChildren().add(grid);
    parent.getChildren().add(info);
    parent.getChildren().add(restartbutton);
    Scene scene = new Scene(parent);
    stage.setScene(scene);
    stage.setTitle("Tic tac toe");
    stage.show();
  }

  private void computerMove() {
    if (fullBoard()) {
      gameDraw();
    } else {
      Random rn = new Random();
      int randomNumber1 = rn.nextInt(3);
      int randomNumber2 = rn.nextInt(3);

      while (squares[randomNumber1][randomNumber2].isTaken()) {
        randomNumber1 = rn.nextInt(3);
        randomNumber2 = rn.nextInt(3);
      }
      squares[randomNumber1][randomNumber2].playMove('X');
      gameOver = checkWinner('X');
      if (gameOver) {
        computer++;
      }
    }
  }

  private boolean fullBoard() {
    int counter = 0;

    for (Square[] row : squares) {
      for (Square s : row) {
        if (s.isTaken()) {
          counter++;
        }
      }
    }
    return counter == 9;
  }

  private void gameDraw() {
    t.setText("It's a draw!");
    gameOver = true;
  }

  private boolean checkWinner(char symbol) {
    int winValue, rowCount, colCount;
    if (symbol == 'O') {
      winValue = 3;
    } else {
      winValue = -3;
    }

    // Checks for winner in each row and column
    for (int row = 0; row < 3; row++) {
      colCount = 0;
      rowCount = 0;
      for (int col = 0; col < 3; col++) {
        rowCount += squares[row][col].getValue();
        colCount += squares[col][row].getValue();
      }
      if (rowCount == winValue || colCount == winValue) {
        announceWinner(symbol);
        return true;
      }
    }

    // Checks for diagonal or antidiagonal winner
    int diaCount = 0;
    int antiCount = 0;
    for (int i = 0; i < 3; i++) {
      diaCount += squares[i][i].getValue();
      antiCount += squares[2 - i][i].getValue();
    }
    if (diaCount == winValue || antiCount == winValue) {
      announceWinner(symbol);
      return true;
    }
    return false;
  }

  private void announceWinner(char symbol) {
    t.setText(symbol + " won!");
    gameOver = true;
  }

  private class SquareClickHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {

      if (!gameOver) {
        if (!((Square) event.getSource()).isTaken()) {
          ((Square) event.getSource()).playMove('O');
          gameOver = checkWinner('O');
          if (gameOver) {
            player++;
          } else {
            computerMove();
          }
        }
      }
    }
  }
}
