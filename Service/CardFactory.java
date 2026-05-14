package Service;

import Model.*;

/**
 * Factory class for creating cards based on rank and a seed index.
 * Predefined card pools for each rank.
 */
public class CardFactory {
    // S-rank card templates (name, baseAttack, baseDefense, baseHp)
    private static final String[][] S_CARDS = {
        {"Dragon Knight", "100", "80", "200"},
        {"Phoenix", "120", "60", "180"},
        {"Titan", "90", "100", "220"},
        {"Angel", "110", "70", "190"}
    };

    // A-rank card templates
    private static final String[][] A_CARDS = {
        {"Swordsman", "60", "40", "100"},
        {"Archer", "70", "30", "90"},
        {"Mage", "80", "25", "85"},
        {"Knight", "55", "50", "110"}
    };

    // B-rank card templates
    private static final String[][] B_CARDS = {
        {"Slime", "20", "15", "50"},
        {"Goblin", "25", "10", "45"},
        {"Skeleton", "22", "12", "48"},
        {"Wolfman", "28", "8", "52"}
    };

    // Creates an S-rank card. The seed determines which card template to use (cyclic).
    public static Card createSCard(int index) {
        String[] data = S_CARDS[index % S_CARDS.length];
        return new SCard(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]));
    }

    // Creates an A-rank card.
    public static Card createACard(int index) {
        String[] data = A_CARDS[index % A_CARDS.length];
        return new ACard(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]));
    }

    // Creates a B-rank card.
    public static Card createBCard(int index) {
        String[] data = B_CARDS[index % B_CARDS.length];
        return new BCard(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]));
    }

    // Creates a random card of the given rank. The seed is used to cycle through templates.
    public static Card createRandomCard(String rank, int seed) {
        switch (rank) {
            case "S": return createSCard(seed);
            case "A": return createACard(seed);
            default:  return createBCard(seed);
        }
    }
}