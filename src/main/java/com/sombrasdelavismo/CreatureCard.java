package com.sombrasdelavismo;

public class CreatureCard extends Card {
    private final int power;
    private final int toughness;
    private boolean readyToAttack;
    private boolean justPlayed;

    public CreatureCard(String name, int cost, int power, int toughness, String description, String imagePath) {
        super(name, "Creature", cost, description, imagePath);
        this.power = power;
        this.toughness = toughness;
        this.readyToAttack = false;
        this.justPlayed = true;
    }

    public int getPower() {
        return power;
    }

    public int getToughness() {
        return toughness;
    }

    public boolean isReadyToAttack() {
        return readyToAttack;
    }

    public boolean isJustPlayed() {
        return justPlayed;
    }

    public void markPlayedThisTurn() {
        readyToAttack = false;
        justPlayed = true;
    }

    public void startOwnerTurn() {
        if (justPlayed) {
            justPlayed = false;
            readyToAttack = false;
        } else {
            readyToAttack = true;
        }
    }

    public void consumeAttack() {
        readyToAttack = false;
    }

    @Override
    public void play() {
        System.out.println("Playing creature: " + name);
    }
}
