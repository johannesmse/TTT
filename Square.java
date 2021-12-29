import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class Square extends Button {
  char symbol;
  int value = 0;

  Square(char symbol) {
    this.symbol = symbol;
    setMinWidth(200);
    setMinHeight(200);
    Font font = new Font(72);
    setFont(font);
  }

  public void playMove(char symbol) {
    this.symbol = symbol;
    if (symbol == 'O') {
      this.value = 1;
      setStyle("-fx-text-fill: red");
    } else {
      setStyle("-fx-text-fill: blue");
      this.value = -1;
    }
    setText("" + symbol);
  }

  public void resetSquare() {
    this.symbol = ' ';
    this.value = 0;
    setText(" ");
    setStyle(null);
  }

  public char getSymbol() {
    return symbol;
  }

  public int getValue() {
    return value;
  }

  public boolean isTaken() {
    return value != 0;
  }
}
