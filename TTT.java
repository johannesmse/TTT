import java.util.Random;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TTT extends Application {

  Square squares[][];
  Text t;
  boolean finished = false;

  @Override
  public void start(Stage stage) {
    VBox parent = new VBox();
    GridPane grid = new GridPane();
    t = new Text("Velg en rute");
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

    parent.getChildren().add(grid);
    parent.getChildren().add(t);
    Scene scene = new Scene(parent);
    stage.setScene(scene);
    stage.setTitle("Tre på rad");
    stage.show();
  }

  private void computerMove() {
    if (fullBoard()) {
      gameDraw();
    } else {
      Random rn = new Random();
      int number1 = rn.nextInt(3) + 0;
      int number2 = rn.nextInt(3) + 0;

      while (squares[number1][number2].isTaken()) {
        number1 = rn.nextInt(3) + 0;
        number2 = rn.nextInt(3) + 0;
      }
      squares[number1][number2].playMove('X');
      checkWinner('X');
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
    t.setText("Det ble uavgjort!");
    finished = true;
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
    t.setText(symbol + " vant!");
    finished = true;
  }

  private class SquareClickHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {

      if (!finished) {
        if (!((Square) event.getSource()).isTaken()) {
          ((Square) event.getSource()).playMove('O');
          if (!checkWinner('O')) {
            computerMove();
          }
        }
      }
    }
  }
}
