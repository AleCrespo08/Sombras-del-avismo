package com.sombrasdelavismo;

public class SpellCard extends Card {
    private final String effect;
    private final int damage;
    private final int healing;
    private final int cardsToDraw;

    public SpellCard(String name, int cost, String effect, int damage, int healing, int cardsToDraw, String imagePath) {
        super(name, "Spell", cost, effect, imagePath);
        this.effect = effect;
        this.damage = damage;
        this.healing = healing;
        this.cardsToDraw = cardsToDraw;
    }

    public String getEffect() {
        return effect;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealing() {
        return healing;
    }

    public int getCardsToDraw() {
        return cardsToDraw;
    }

    @Override
    public void play() {
        System.out.println("Casting spell: " + name + " - " + effect);
    }
}
