================================================================================
                    Gacha Battle System - Game Guide & Rules
================================================================================

1. Game Overview
-------------------------------------------------------------------------------
A simple RPG game that combines gacha collecting and turn-based battles.
Draw cards to get characters, build a team, defeat enemies, earn coins,
and unlock harder stages.


2. Game Flow
-------------------------------------------------------------------------------
Start → Free first draw → Main Menu
    ├─ Draw → Get cards → Add to inventory
    ├─ Battle → Choose stage → Turn-based combat → Win coins
    └─ Check inventory / status


3. Card System
-------------------------------------------------------------------------------
1. Rarity & Probability
   ┌──────┬──────────┬─────────────────────────────────┬──────────────┐
   │ Rank │ Chance   │ Bonus Stats                     │ Skill Damage │
   ├──────┼──────────┼─────────────────────────────────┼──────────────┤
   │ S    │ 5%       │ Attack+50  Defense+30  HP+100   │ 2.0x         │
   │ A    │ 15%      │ Attack+20  Defense+15  HP+50    │ 1.5x         │
   │ B    │ 80%      │ No bonus                        │ 1.2x         │
   └──────┴──────────┴─────────────────────────────────┴──────────────┘

2. Pity System (Guaranteed Cards)
   - A pity: After 9 draws with no A or S, the 10th draw is guaranteed A or S
     (80% A, 20% S).
   - S pity: After 79 draws with no S, the 80th draw is guaranteed S.
   Single draws and 10x draws share the same pity counter.

3. Card Stats
   - Attack  : Determines damage dealt.
   - Defense : Reduces damage taken (Damage = Enemy ATK - Your DEF, min 1).
   - HP      : When HP reaches 0, the card cannot fight.


4. Drawing Rules
-------------------------------------------------------------------------------
- Single draw : 100 coins -> 1 random card.
- 10x draw    : 1000 coins -> 10 cards (pity works across all 10).
- Drawn cards go straight to your inventory. Duplicates are kept.


5. Combat System
-------------------------------------------------------------------------------
1. Battle Team
   The first 3 cards in your inventory form your team (in order received).
   If you have fewer than 3 cards, you fight with whatever you have.

2. Turn-Based Actions
   Each turn you can choose:
      1) Normal Attack  : Deal damage equal to your card's Attack.
      2) Special Skill  : Deal multiplied damage (S:2x, A:1.5x, B:1.2x).
      3) Switch Card    : Swap to the next card (no turn cost).
   The enemy attacks one of your alive cards each turn at random.

3. Win / Lose
   - Win  : Enemy HP reaches 0.
   - Lose : All your cards have 0 HP.

4. Battle Rewards
   ┌───────┬─────────────────┬───────────────────┐
   │ Stage │ First Clear     │ Repeat Clear      │
   ├───────┼─────────────────┼───────────────────┤
   │ 1     │ 100 coins       │ 50 coins          │
   │ 2     │ 200 coins       │ 100 coins         │
   │ 3     │ 300 coins       │ 150 coins         │
   │ 4     │ 400 coins       │ 200 coins         │
   │ 5     │ 500 coins       │ 250 coins         │
   └───────┴─────────────────┴───────────────────┘
   First clear unlocks the next stage. Already cleared stages can be
   replayed, but rewards are halved.


6. Coins - How to Get & Use
-------------------------------------------------------------------------------
【How to get】
   - Starting gift : 1000 coins.
   - Battle wins   : Reward coins (first or repeat clear).

【How to use】
   - Draw cards : 100 for single, 1000 for 10x draw.


7. Stages & Enemies (Stage 1 to 5)
-------------------------------------------------------------------------------
┌───────┬───────────────┬──────┬────────┬─────────┬──────┐
│ Stage │ Enemy Name    │ Rank │ Attack │ Defense │ HP   │
├───────┼───────────────┼──────┼────────┼─────────┼──────┤
│ 1     │ Goblin Chief  │ B    │ 35     │ 20      │ 80   │
│ 2     │ Shadow Assassin│ B    │ 45     │ 15      │ 100  │
│ 3     │ Flame Mage    │ A    │ 60     │ 25      │ 120  │
│ 4     │ Frost Dragon  │ A    │ 70     │ 30      │ 150  │
│ 5     │ Demon Lord    │ S    │ 100    │ 40      │ 200  │
└───────┴───────────────┴──────┴────────┴─────────┴──────┘


8. Controls
-------------------------------------------------------------------------------
【Main Menu】
   1 - Draw Cards (single / 10x)
   2 - View Inventory
   3 - Battle (choose a stage)
   4 - View Status (coins, pity progress, unlocked stages)
   0 - Exit game

【In Battle】
   1 - Normal Attack
   2 - Special Skill
   3 - Switch Card

Type the number and press Enter to select.


9. Important Notes
-------------------------------------------------------------------------------
- The game gives you one free draw at the start.
- Pity counters reset when you draw an A or S card (both reset to 0 for S,
  A resets A pity, S pity resets only when you draw S).
- During a 10x draw, pity updates after each card.
- After first clear of a stage, replaying gives half reward and does NOT unlock
  a new stage.
- If your inventory is empty, you cannot start a battle. Draw some cards first!


================================================================================
                         Have fun and good luck!
================================================================================