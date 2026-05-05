import UI.GameUI;

/**
 * Entry point of the Gacha Battle game.
 * Creates a new GameUI instance and starts the game.
 */
public class Main {
    public static void main(String[] args) {
        GameUI game = new GameUI();
        game.start();
    }
}