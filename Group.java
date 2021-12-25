public class Group {
  Square group[] = new Square[3];

  Group(Square square1, Square square2, Square square3) {
    this.group[0] = square1;
    this.group[1] = square2;
    this.group[2] = square3;
  }

  public Square[] getGroup() {
    return this.group;
  }

  public char checkWin() {
    if (group[0].symbol != ' '
        && group[0].symbol == group[1].symbol
        && group[1].symbol == group[2].symbol) {
      return group[0].symbol;
    } else {
      return ' ';
    }
  }

  // Checks if it is possible to make a winning move for input symbol
  public Square possibleWin(char sym) {
    // if count(sym, group) == 2 && count(" ", group) == 1:
    //     return group[group.index(' ')]
    // return null

    if (sym == group[0].symbol && group[0].symbol == group[1].symbol && group[2].symbol == ' ') {
      return group[2];
    } else if (sym == group[0].symbol
        && group[0].symbol == group[2].symbol
        && group[1].symbol == ' ') {
      return group[1];
    } else if (sym == group[1].symbol
        && group[1].symbol == group[2].symbol
        && group[0].symbol == ' ') {
      return group[0];
    } else {
      return null;
    }
  }
}
