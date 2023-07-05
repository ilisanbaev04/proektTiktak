import java.util.Random;

public class AI extends AGamer {

    MainGameField gameField;
    String playerSign = "";





    public AI(String sign, int aiLevel, String playerSign)
    {
        this.sign = sign;
        this.playerSign = playerSign;

    }


    boolean shot(int x, int y) {
        gameField = MainGameField.getInstance();
        x = -1;
        y = -1;
        boolean ai_win = false;
        boolean user_win = false;

        for (int i = 0; i < MainGameField.linesCount; i++) {
            for (int j = 0; j < MainGameField.linesCount; j++) {
                if (!gameField.isCellBusy(i, j)) {
                    gameField.cell[i][j] = sign;
                    if (gameField.checkWin(sign)) {
                        x = i;
                        y = j;
                        ai_win = true;
                        break;
                    }
                    gameField.cell[i][j] = gameField.NOT_SIGN;
                }
            }
            if (ai_win) {
                break;
            }
        }
        if (!ai_win) {
            for (int i = 0; i < MainGameField.linesCount; i++) {
                for (int j = 0; j < MainGameField.linesCount; j++) {
                    if (!gameField.isCellBusy(i, j)) {
                        gameField.cell[i][j] = this.playerSign;
                        if (gameField.checkWin(this.playerSign)) {
                            x = i;
                            y = j;
                            user_win = true;
                            break;
                        }
                        gameField.cell[i][j] = gameField.NOT_SIGN;
                    }
                }
                if (user_win) {
                    break;
                }
            }
        }
        if (!ai_win && !user_win) {
            for (int i = 0; i < MainGameField.linesCount; i++) {
                for (int j = 0; j < MainGameField.linesCount; j++) {
                    if (!gameField.isCellBusy(i, j)) {
                        gameField.cell[i][j] = this.playerSign;
                        boolean blockWin = false;
                        for (int k = 0; k < MainGameField.linesCount; k++) {
                            for (int l = 0; l < MainGameField.linesCount; l++) {
                                if (!gameField.isCellBusy(k, l)) {
                                    gameField.cell[k][l] = this.playerSign;
                                    if (gameField.checkWin(this.playerSign)) {
                                        blockWin = true;
                                    }
                                    gameField.cell[k][l] = gameField.NOT_SIGN;
                                }
                            }
                        }
                        if (blockWin) {
                            x = i;
                            y = j;
                            user_win = true;
                        }
                        gameField.cell[i][j] = gameField.NOT_SIGN;
                    }
                }
            }
        }







        if (!ai_win && !user_win) {
            for (int i = 0; i < MainGameField.linesCount; i++) {
                for (int j = 0; j < MainGameField.linesCount; j++) {
                    if (!gameField.isCellBusy(i, j)) {
                        gameField.cell[i][j] = sign;
                        boolean blockWin = false;
                        for (int k = 0; k < MainGameField.linesCount; k++) {
                            for (int l = 0; l < MainGameField.linesCount; l++) {
                                if (!gameField.isCellBusy(k, l)) {
                                    gameField.cell[k][l] = sign;
                                    if (gameField.checkWin(sign) && MainGameField.winCount == 6) {
                                        blockWin = true;
                                        break;
                                    }
                                    gameField.cell[k][l] = gameField.NOT_SIGN;
                                }
                            }
                            if (blockWin) {
                                break;
                            }
                        }
                        if (!blockWin) {
                            for (int k = 0; k < MainGameField.linesCount; k++) {
                                for (int l = 0; l < MainGameField.linesCount; l++) {
                                    if (!gameField.isCellBusy(k, l)) {
                                        gameField.cell[k][l] = this.playerSign;
                                        if (gameField.checkWin(this.playerSign)) {
                                            blockWin = true;
                                            break;
                                        }
                                        gameField.cell[k][l] = gameField.NOT_SIGN;
                                    }
                                }
                                if (blockWin) {
                                    break;
                                }
                            }
                            if (!blockWin) {
                                x = i;
                                y = j;
                                break;
                            }
                        }
                        gameField.cell[i][j] = gameField.NOT_SIGN;
                    }
                }
                if (x != -1 && y != -1) {
                    break;
                }
            }
        }



        gameField.cell[x][y] = sign;
        return true;
    }

    boolean win()
    {
        gameField = MainGameField.getInstance();
        return gameField.checkWin(this.sign);
    }
}