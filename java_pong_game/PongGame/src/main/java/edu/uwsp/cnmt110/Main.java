package edu.uwsp.cnmt110;

public class Main {
    public static void main(String[] args) {
        Game game = new PongGame(800, 600);
        // Game game = new DemoGame(800, 600);
        game.start();
    }
}
