package Service;

import Model.*;

public class CardFactory {
    
    // 预定义卡片池
    private static final String[][] S_CARDS = {
        {"龙骑士", "100", "80", "200"},
        {"凤凰", "120", "60", "180"},
        {"泰坦", "90", "100", "220"},
        {"天使", "110", "70", "190"}
    };
    
    private static final String[][] A_CARDS = {
        {"剑士", "60", "40", "100"},
        {"弓箭手", "70", "30", "90"},
        {"法师", "80", "25", "85"},
        {"骑士", "55", "50", "110"}
    };
    
    private static final String[][] B_CARDS = {
        {"史莱姆", "20", "15", "50"},
        {"哥布林", "25", "10", "45"},
        {"骷髅兵", "22", "12", "48"},
        {"狼人", "28", "8", "52"}
    };
    
    public static Card createSCard(int index) {
        String[] data = S_CARDS[index % S_CARDS.length];
        return new SCard(data[0], 
            Integer.parseInt(data[1]), 
            Integer.parseInt(data[2]), 
            Integer.parseInt(data[3]));
    }
    
    public static Card createACard(int index) {
        String[] data = A_CARDS[index % A_CARDS.length];
        return new ACard(data[0], 
            Integer.parseInt(data[1]), 
            Integer.parseInt(data[2]), 
            Integer.parseInt(data[3]));
    }
    
    public static Card createBCard(int index) {
        String[] data = B_CARDS[index % B_CARDS.length];
        return new BCard(data[0], 
            Integer.parseInt(data[1]), 
            Integer.parseInt(data[2]), 
            Integer.parseInt(data[3]));
    }
    
    public static Card createRandomCard(String rank, int seed) {
        switch(rank) {
            case "S":
                return createSCard(seed);
            case "A":
                return createACard(seed);
            default:
                return createBCard(seed);
        }
    }
}