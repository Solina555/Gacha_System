package Service;

import Model.*;
import java.util.Random;

/**
 * Handles single and 10x draws, pity system (A-pity and S-pity).
 */
public class GachaService {
    private static final int COST_PER_PULL = 100;
    private static final double S_RATE = 0.05;   // 5% base chance
    private static final double A_RATE = 0.15;   // 15% base chance

    private static final int A_PITY_LIMIT = 10;   // Guarantee A/S after 9 misses
    private static final int S_PITY_LIMIT = 80;   // Guarantee S after 79 misses

    private Random random;
    private int pullCount;               // Total draws since start (for card variation)
    private int consecutiveNoAS;         // Consecutive draws without A or S (for A-pity)
    private int consecutiveNoS;          // Consecutive draws without S (for S-pity)

    public GachaService() {
        this.random = new Random();
        this.pullCount = 0;
        this.consecutiveNoAS = 0;
        this.consecutiveNoS = 0;
    }

    /**
     * Performs a single draw, consumes coins, applies pity logic, creates a card, and updates inventory.
     * @param player The player performing the draw
     * @return The drawn card, or null if insufficient coins
     */
    public Card pull(Player player) {
        if (!player.spendCoins(COST_PER_PULL)) {
            System.out.println("[COIN] Not enough coins! Need " + COST_PER_PULL + " coins.");
            return null;
        }

        pullCount++;
        String rank = determineRankWithPity();

        Card newCard = CardFactory.createRandomCard(rank, pullCount);
        player.addCard(newCard);

        updatePityCounters(rank);

        System.out.println("*** DRAW RESULT ***");
        System.out.println("Cost: " + COST_PER_PULL + " coins");
        System.out.println("Obtained: " + newCard);
        System.out.println("[PITY] A-pity: " + consecutiveNoAS + "/" + A_PITY_LIMIT +
                " | S-pity: " + consecutiveNoS + "/" + S_PITY_LIMIT);

        return newCard;
    }

    /**
     * Determines the rank of the next card using pity and base probabilities.
     * Priority: S-pity > A-pity > normal rates.
     */
    private String determineRankWithPity() {
        // S-pity check
        if (consecutiveNoS >= S_PITY_LIMIT - 1) {
            System.out.println("[PITY] S-rank pity triggered! After " + consecutiveNoS +
                    " draws without S, you are guaranteed an S-rank card!");
            return "S";
        }
        // A-pity check (only if S-pity is not triggered)
        if (consecutiveNoAS >= A_PITY_LIMIT - 1) {
            boolean isS = random.nextDouble() < 0.2; // 20% chance to upgrade to S
            if (isS) {
                System.out.println("[PITY] A-pity upgraded to S! After " + consecutiveNoAS +
                        " draws without A/S, you get an S-rank card!");
                return "S";
            } else {
                System.out.println("[PITY] A-pity triggered! After " + consecutiveNoAS +
                        " draws without A/S, you get an A-rank card!");
                return "A";
            }
        }
        // Normal probability
        double r = random.nextDouble();
        if (r < S_RATE) return "S";
        else if (r < S_RATE + A_RATE) return "A";
        else return "B";
    }

    /**
     * Updates the pity counters based on the obtained rank.
     */
    private void updatePityCounters(String rank) {
        if (rank.equals("S")) {
            consecutiveNoAS = 0;
            consecutiveNoS = 0;
        } else if (rank.equals("A")) {
            consecutiveNoAS = 0;
            consecutiveNoS++;
        } else { // B
            consecutiveNoAS++;
            consecutiveNoS++;
        }
    }

    /**
     * Performs a 10x draw by calling pull() 10 times.
     */
    public void tenPull(Player player) {
        int cost = COST_PER_PULL * 10;
        if (player.getCoins() < cost) {
            System.out.println("[COIN] Not enough coins! Need " + cost + " coins.");
            return;
        }

        System.out.println("\n[FIRE] 10x DRAW START! [FIRE]");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + ". ");
            pull(player);
            try { Thread.sleep(300); } catch (InterruptedException e) {}
        }
        System.out.println("[FIRE] 10x DRAW END! [FIRE]\n");
    }

    public int getCostPerPull() { return COST_PER_PULL; }
    public int getAPityProgress() { return consecutiveNoAS; }
    public int getSPityProgress() { return consecutiveNoS; }
}