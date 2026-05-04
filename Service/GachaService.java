package Service;

import Model.*;
import java.util.Random;

public class GachaService {
    private static final int COST_PER_PULL = 100;
    private static final double S_RATE = 0.05;
    private static final double A_RATE = 0.15;

    private static final int A_PITY_LIMIT = 10;
    private static final int S_PITY_LIMIT = 80;

    private Random random;
    private int pullCount;
    private int consecutiveNoAS;
    private int consecutiveNoS;

    public GachaService() {
        this.random = new Random();
        this.pullCount = 0;
        this.consecutiveNoAS = 0;
        this.consecutiveNoS = 0;
    }

    public Card pull(Player player) {
        if (!player.spendCoins(COST_PER_PULL)) {
            System.out.println("💰 Not enough coins! Need " + COST_PER_PULL + " coins.");
            return null;
        }

        pullCount++;
        String rank = determineRankWithPity();

        Card newCard = CardFactory.createRandomCard(rank, pullCount);
        player.addCard(newCard);

        updatePityCounters(rank);

        System.out.println("✨✨✨ DRAW RESULT ✨✨✨");
        System.out.println("Cost: " + COST_PER_PULL + " coins");
        System.out.println("Obtained: " + newCard);
        System.out.println("📊 Pity progress | A-pity: " + consecutiveNoAS + "/" + A_PITY_LIMIT +
                " | S-pity: " + consecutiveNoS + "/" + S_PITY_LIMIT);

        return newCard;
    }

    private String determineRankWithPity() {
        // S-pity (highest priority)
        if (consecutiveNoS >= S_PITY_LIMIT - 1) {
            System.out.println("🎉 S-rank pity triggered! After " + consecutiveNoS +
                    " draws without S, you are guaranteed an S-rank card!");
            return "S";
        }

        // A-pity
        if (consecutiveNoAS >= A_PITY_LIMIT - 1) {
            boolean isS = random.nextDouble() < 0.2;
            if (isS) {
                System.out.println("🎉 A-pity upgraded to S! After " + consecutiveNoAS +
                        " draws without A/S, you get an S-rank card!");
                return "S";
            } else {
                System.out.println("🎁 A-pity triggered! After " + consecutiveNoAS +
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

    public void tenPull(Player player) {
        int cost = COST_PER_PULL * 10;
        if (player.getCoins() < cost) {
            System.out.println("💰 Not enough coins! Need " + cost + " coins.");
            return;
        }

        System.out.println("\n🔥🔥🔥 10x DRAW START! 🔥🔥🔥");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + ". ");
            pull(player);
            try { Thread.sleep(300); } catch (InterruptedException e) {}
        }
        System.out.println("🔥🔥🔥 10x DRAW END! 🔥🔥🔥\n");
    }

    public int getCostPerPull() { return COST_PER_PULL; }
    public int getAPityProgress() { return consecutiveNoAS; }
    public int getSPityProgress() { return consecutiveNoS; }
}