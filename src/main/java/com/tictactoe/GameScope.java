package com.tictactoe;

public class GameScope {
    private int noughtScope;
    private int crossScope;
    private int drawScope;

    public int getDrawScope() {
        return drawScope;
    }

    public int getNoughtScope() {
        return noughtScope;
    }

    public int getCrossScope() {
        return crossScope;
    }

    public void countScope(Sign winner) {
        if (Sign.CROSS == winner) {
            crossScope++;
        } else if (Sign.NOUGHT == winner) {
            noughtScope++;
        }
    }

    public void countDraw() {
        drawScope++;
    }
}
