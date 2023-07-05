import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainGameField extends JPanel {

    private static MainGameField instance = null;

    public static final int FIELD_SIZE = 450;

    static final int winCount = 5 ; //сколько нужно в ряд для победы

    public final String NOT_SIGN = "*";

    boolean gameOver = false;

    String gameOverMessage = "";
    static int linesCount = 8; //размер поля
    int cellSize;
    int x;
    int y;

    boolean nextTurn = false;

    Player player1;
    Player player2;

    int gameMode = 1;

    int aiLevel = 0;

    public String[][] cell;


    public static synchronized MainGameField getInstance() {
        if (instance == null)
            instance = new MainGameField();
        return instance;
    }


    void startNewGame() {
        gameOver = false;
        gameOverMessage = "";

        cellSize = FIELD_SIZE / linesCount;
        cell = new String[linesCount][linesCount];

        repaint();

        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                cell[i][j] = NOT_SIGN;
            }
        }
        gameMode = 1;
        aiLevel = 0;
        setVisible(true);
    }


    private MainGameField() {
        setVisible(false);
        player1 = new Player("X");
        player2 = new Player("O");


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                x = e.getX() / cellSize;
                y = e.getY() / cellSize;
                System.out.println("Mouse clicked on " + e.getX() + " " + e.getY());

                if (!gameOver) {
                    switch (gameMode) {
                        case 1: {

                            break;
                        }
                        case 2: {
                            modeAgainstAI();
                            break;
                        }
                    }
                }
            }
        });
    }



    void modeAgainstAI() {
        Player player = new Player("X");
        AI ai = new AI("O", aiLevel, player.sign);
        if (!gameOver) {
            if (player.shot(x, y)) {
                if (player.win()) {
                    System.out.println("Player WIN!!!");
                    gameOver = true;
                    gameOverMessage = "Player WIN!!!";
                }
                if (isFieldFull()) {
                    gameOver = true;
                    gameOverMessage = "DRAW!!!";
                }
                repaint();
                if (!gameOver) {
                    ai.shot(x, y);
                }
                if (ai.win()) {
                    System.out.println("AI WIN!!!");
                    gameOver = true;
                    gameOverMessage = "AI WIN!!!";
                }
                repaint();
                if (isFieldFull() && !ai.win()) {
                    gameOver = true;
                    gameOverMessage = "DRAW!!!";
                }
            }
        }
    }


    boolean isCellBusy(int x, int y) {
        if (x < 0 || y < 0 || x > linesCount - 1 || y > linesCount - 1) {
            return false;
        }
        return cell[x][y] != NOT_SIGN;
    }


    public boolean isFieldFull() {
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (cell[i][j] == NOT_SIGN)
                    return false;
            }
        }
        return true;
    }







    boolean checkWin(String sign) {

        for (int i = 0; i < linesCount; i++) {
            int countHorizontal = 0;
            int countVertical = 0;
            for (int j = 0; j < linesCount; j++) {
                if (cell[i][j].equals(sign)) {
                    countHorizontal++;
                } else {
                    countHorizontal = 0;
                }
                if (cell[j][i].equals(sign)) {
                    countVertical++;
                } else {
                    countVertical = 0;
                }
                if (countHorizontal == winCount || countVertical == winCount) {
                    return true;
                }
            }
        }

        for (int i = 0; i <= linesCount - winCount; i++) {
            for (int j = 0; j <= linesCount - winCount; j++) {
                int countDiagonal1 = 0;
                int countDiagonal2 = 0;
                for (int k = 0; k < winCount; k++) {
                    if (cell[i + k][j + k].equals(sign)) {
                        countDiagonal1++;
                    } else {
                        countDiagonal1 = 0;
                    }
                    if (cell[i + k][j + winCount - 1 - k].equals(sign)) {
                        countDiagonal2++;
                    } else {
                        countDiagonal2 = 0;
                    }
                    if (countDiagonal1 == winCount || countDiagonal2 == winCount) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i <= this.linesCount; i++) {
            g.drawLine(0, i * this.cellSize, FIELD_SIZE, i * this.cellSize);
            g.drawLine(i * this.cellSize, 0, i * this.cellSize, FIELD_SIZE);
        }
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (cell[i][j] != NOT_SIGN) {
                    if (cell[i][j] == "X") {

                        g.setColor(Color.RED);
                        g.drawLine((i * cellSize), (j * cellSize), (i + 1) * cellSize, (j + 1) * cellSize);
                        g.drawLine((i + 1) * cellSize, (j * cellSize), (i * cellSize), (j + 1) * cellSize);
                    } else if (cell[i][j] == "O") {

                        g.setColor(Color.BLUE);
                        g.drawOval((i * cellSize), (j * cellSize), cellSize, cellSize);
                    }
                }
            }
        }

        if (gameOver) {

            g.setColor(Color.BLACK);
            g.fillRect(0, FIELD_SIZE / 2, FIELD_SIZE, FIELD_SIZE / 8);
            g.setColor(Color.RED);
            g.setFont(new Font("Tahoma", 10, 40));
            g.drawString(gameOverMessage, 0, 19 * FIELD_SIZE / 32);
        }
    }
}