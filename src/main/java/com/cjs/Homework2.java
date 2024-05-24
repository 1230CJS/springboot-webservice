package com.cjs;

public class Homework2 {

    public static void main(String[] args) {
        BaseBallGame baseBallGame = new BaseBallGame(false);
        baseBallGame.getScore("123","321");
        baseBallGame.getScore("231","231");
        baseBallGame.getScore("123","123");
        baseBallGame.getScore("312","321");
        baseBallGame.getScore("123","456");
    }
}
