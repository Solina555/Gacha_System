package Service;

import Model.*;
import java.util.Random;

public class GachaService {
    private static final int COST_PER_PULL = 100;
    private static final double S_RATE = 0.05;   // 5%
    private static final double A_RATE = 0.15;   // 15%
    private static final double B_RATE = 0.80;   // 80%
    
    private Random random;
    private int pullCount;  // 累计抽卡次数，用于产生不同卡片
    
    public GachaService() {
        this.random = new Random();
        this.pullCount = 0;
    }
    
    public Card pull(Player player) {
        if (!player.spendCoins(COST_PER_PULL)) {
            System.out.println("💰 游戏币不足！需要 " + COST_PER_PULL + " 游戏币");
            return null;
        }
        
        pullCount++;
        String rank = determineRank();
        Card newCard = CardFactory.createRandomCard(rank, pullCount);
        player.addCard(newCard);
        
        System.out.println("✨✨✨ 抽卡结果 ✨✨✨");
        System.out.println("消耗: " + COST_PER_PULL + " 游戏币");
        System.out.println("获得: " + newCard);
        
        return newCard;
    }
    
    public void tenPull(Player player) {
        if (player.getCoins() < COST_PER_PULL * 10) {
            System.out.println("💰 游戏币不足！需要 " + (COST_PER_PULL * 10) + " 游戏币");
            return;
        }
        
        System.out.println("\n🔥🔥🔥 十连抽开始！ 🔥🔥🔥");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + ". ");
            pull(player);
            try {
                Thread.sleep(500);  // 增加抽卡仪式感
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("🔥🔥🔥 十连抽结束！ 🔥🔥🔥\n");
    }
    
    private String determineRank() {
        double r = random.nextDouble();
        if (r < S_RATE) {
            return "S";
        } else if (r < S_RATE + A_RATE) {
            return "A";
        } else {
            return "B";
        }
    }
    
    public int getCostPerPull() {
        return COST_PER_PULL;
    }
}