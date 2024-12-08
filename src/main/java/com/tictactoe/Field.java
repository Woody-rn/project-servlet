package com.tictactoe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Field {
    private final Map<Integer, Sign> field;
    private final List<List<Integer>> winPossibilities = List.of(
            List.of(0, 1, 2),
            List.of(3, 4, 5),
            List.of(6, 7, 8),
            List.of(0, 3, 6),
            List.of(1, 4, 7),
            List.of(2, 5, 8),
            List.of(0, 4, 8),
            List.of(2, 4, 6)
    );

    public Field() {
        field = new HashMap<>();
        field.put(0, Sign.EMPTY);
        field.put(1, Sign.EMPTY);
        field.put(2, Sign.EMPTY);
        field.put(3, Sign.EMPTY);
        field.put(4, Sign.EMPTY);
        field.put(5, Sign.EMPTY);
        field.put(6, Sign.EMPTY);
        field.put(7, Sign.EMPTY);
        field.put(8, Sign.EMPTY);
    }

    public Map<Integer, Sign> getField() {
        return field;
    }

    private int getEmptyFieldIndex() {
        return field.entrySet().stream()
                .filter(e -> e.getValue() == Sign.EMPTY)
                .map(Map.Entry::getKey)
                .findFirst().orElse(-1);
    }

    public List<Sign> getFieldData() {
        return field.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public List<Integer> getListIndexWinCell(){
        for (List<Integer> winPossibility : winPossibilities) {
            if (field.get(winPossibility.get(0)) == field.get(winPossibility.get(1))
                    && field.get(winPossibility.get(0)) == field.get(winPossibility.get(2))
                    && field.get(winPossibility.get(0)) != Sign.EMPTY) {
                return winPossibility;
            }
        }
        throw new RuntimeException();
    }

    public Sign checkWin() {
        for (List<Integer> winPossibility : winPossibilities) {
            if (field.get(winPossibility.get(0)) == field.get(winPossibility.get(1))
                    && field.get(winPossibility.get(0)) == field.get(winPossibility.get(2))
                    && field.get(winPossibility.get(0)) != Sign.EMPTY) {
                return field.get(winPossibility.get(0));
            }
        }
        return Sign.EMPTY;
    }

    public int botMove() {
        Sign bot = Sign.NOUGHT;
        Sign player = Sign.CROSS;
        int winMove = findWinMove(bot);
        if (winMove != -1) {
            return winMove;
        }
        int blockMoveEnemy = findWinMove(player);
        if (blockMoveEnemy != -1) {
            return blockMoveEnemy;
        }
        return getEmptyFieldIndex();
    }

    private int findWinMove(Sign user) {
        for (int i = 0; i < field.size(); i++) {
            if (field.get(i) == Sign.EMPTY) {
                field.put(i, user);
                if (checkWin() == user) {
                    field.put(i, Sign.EMPTY);
                    return i;
                }
                field.put(i, Sign.EMPTY);
            }
        }
        return -1;
    }
}