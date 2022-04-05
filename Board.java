import java.awt.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board implements ActionListener{

    int[][] board = new int[3][3];
    Scanner scan;
    boolean playerTurn = ((int)(Math.random()*2)) == 1;
    JButton[][] buttons;


    public void makeBoard(){
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                board[i][j] = 0;
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new Board();
    }
    public Board() throws InterruptedException {
        createWindow();
        makeBoard();
        int y = 0;
        while(y == 0){
            y = checkWin();
            if(!playerTurn && y == 0){
                compTurn();
                playerTurn = true;
                updateBoard();
            }
            Thread.sleep(250);
            if(y == 1){
                System.out.println("You won! My code doesn't work yet!");
                break;
            }else if(y == -1){
                System.out.println("Computer won! How could you let this happen!");
                break;
            }else if(y == -4) {
                System.out.println("Tie game! Try harder");
                break;
            }
        }
    }

    public void playerTurn(int row, int col){
            if(board[row][col] == 0) {
                board[row][col] = 1;
                playerTurn = false;
            }
    }

    public int checkWin(){
        //check hz
        int sum = 0;
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++) {
                sum += board[i][j];
            }
            if(sum == -3)
                return -1;
            if(sum == 3)
                return 1;
            sum = 0;
        }
        //check vt
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                sum += board[j][i];
            }
            if(sum == -3)
                return -1;
            if(sum == 3)
                return 1;
            sum = 0;
        }
        //check diag
        int i = 0;
        int j = 0;
        while(i < 3){
            sum += board[i][j];
            i++;
            j++;
        }
        if(sum == -3)
            return -1;
        if(sum == 3)
            return 1;
        sum = 0;
        i = 0;
        j = 2;
        while(i < 3){
            sum += board[i][j];
            i++;
            j--;
        }
        if(sum == -3)
            return -1;
        if(sum == 3)
            return 1;
        if(checkFull()){
            return -4;
        }
        return 0;
    }

    private void createWindow(){
        JFrame frame = new JFrame("Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocation(50,50);
        frame.setSize(750,750);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.white);

        buttons = new SpaceButton[3][3];
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                buttons[i][j] = new SpaceButton(i,j);
                buttons[i][j].setFont(new Font("Arial",Font. PLAIN, 150));
                buttons[i][j].setBounds((frame.getWidth()/3)*j,
                        (frame.getHeight()/3)*i,
                        frame.getWidth()/3,frame.getHeight()/3);
                buttons[i][j].setBackground(Color.white);
                buttons[i][j].addActionListener(this);
                frame.add(buttons[i][j]);
            }
        }
    }

    public void actionPerformed(ActionEvent e){
        SpaceButton s = (SpaceButton) e.getSource();
        int row = s.getRow();
        int col = s.getCol();
        playerTurn(row, col);
        updateBoard();
    }

    public void updateBoard(){
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                if(board[i][j] == 1)
                    buttons[i][j].setText("X");
                if(board[i][j] == -1)
                    buttons[i][j].setText("O");
            }
        }
    }


    public void printBoard(){
            System.out.println("   0 1 2");
            for(int i = 0;i < board.length;i++){
                System.out.print(i + " |");
                for(int j = 0;j < board[i].length;j++){
                    switch (board[i][j]) {
                        case 0:
                        System.out.print(" |");
                        case 1:
                        System.out.print("x|");
                        case -1: 
                        System.out.print("0|");
                    }
                }
                System.out.println();}
    }


    public boolean checkFull(){
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                if(board[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }
 
    public void compTurn(){
        int bestScore = Integer.MIN_VALUE;
        int score = 0;
        int bestRow = -1;
        int bestCol = -1;
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                if(board[i][j] == 0){
                    board[i][j] = -1;
                    score = miniMax(0,false);
                    if(score > bestScore){
                        bestRow = i;
                        bestCol = j;
                        bestScore = score;
                    }
                    board[i][j] = 0;
                }
            }
        }
        board[bestRow][bestCol] = -1;
    }

    public int miniMax(int depth, boolean isMaximising){
        switch(checkWin()){
            case 0 : {
                break;
            }
            case -4 : {
                return 0;
            }
            case -1 : {
                return 10 - depth;
            }
            case 1 : {
                return -100;
            }
        }
        if(isMaximising){
            int bestScore = Integer.MIN_VALUE;
            int score = 0;
            for(int i = 0;i < 3;i++){
                for(int j = 0;j < 3;j++){
                    if(board[i][j] == 0){
                        board[i][j] = -1;
                        score = miniMax(depth + 1, false);
                        if(score > bestScore){
                            bestScore = score;
                        }
                        board[i][j] = 0;
                    }
                }
            }
            return bestScore;
        }else{
            int worstScore = Integer.MAX_VALUE;
            int score = 0;
            for(int i = 0;i < 3;i++){
                for(int j = 0;j < 3;j++){
                    if(board[i][j] == 0){
                        board[i][j] = 1;
                        score = miniMax(depth + 1, true);
                        if(score < worstScore){
                            worstScore = score;
                        }
                        board[i][j] = 0;
                    }
                }
            }
            return worstScore;
        }
    }
}