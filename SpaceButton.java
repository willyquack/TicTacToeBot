import javax.swing.*;

public class SpaceButton extends JButton {
    int row;
    int col;
    public SpaceButton(int row, int col){
        super(" ");
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }
    public int getCol(){
        return col;
    }
}
