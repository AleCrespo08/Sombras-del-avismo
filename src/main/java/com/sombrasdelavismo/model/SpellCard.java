package com.sombrasdelavismo.model;

/**
 * Representa una carta de hechizo en el juego.
 * Los hechizos tienen efectos instantáneos como daño, curación o robar cartas.
 */
public class SpellCard extends Card {
    private final String effect;        // Descripción del efecto
    private final int damage;           // Daño que causa el hechizo
    private final int healing;          // Curación que proporciona
    private final int cardsToDraw;      // Cartas que se roban

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
    public Card copy() {
        return new SpellCard(name, cost, effect, damage, healing, cardsToDraw, imagePath);
    }

    @Override
    public void play() {
        // No side effects needed here; resolution happens in Game.
    }

    @Override
    public String toString() {
        return String.format("%s - %s (Costo: %d)", name, effect, cost);
    }
}
