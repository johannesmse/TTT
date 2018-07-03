import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.event.*;

public class Square extends Button{
  char symbol;
  boolean taken = false;

  Square(char text){
    super("" + text);
    symbol = text;
    setText("" + text);
    setMinWidth(200);
    setMinHeight(200);
    Font font = new Font(72);
    setFont(font);
  }

  public void playMove(char symbol){
    if (this.taken == false){
      setText("" + symbol);
      this.symbol = symbol;
      this.taken = true;
    }
  }

  public char getSymbol(){
    return symbol;
  }

  public boolean isTaken(){
    return taken;
  }
}
