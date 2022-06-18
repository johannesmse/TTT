import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class Square extends Button {
  char symbol = ' ';

  Square() {
    setMinWidth(200);
    setMinHeight(200);
    Font font = new Font(72);
    setFont(font);
  }

  public void playMove(char sym) {
    if (sym == 'O') {
      setStyle("-fx-text-fill: red");
    } else {
      setStyle("-fx-text-fill: blue");
    }
    this.symbol = sym;
    setText("" + sym);
  }

  public void resetSquare() {
    this.symbol = ' ';
    setText(" ");
    setStyle(null);
  }

  public char getSymbol() {
    return symbol;
  }

  public boolean isTaken() {
    return this.symbol != ' ';
  }
}
