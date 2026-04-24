package com.sombrasdelavismo.model;package com.sombrasdelavismo.model;



















































}    }        return String.format("%s - %s (Costo: %d)", name, effect, cost);    public String toString() {    @Override    }        // No side effects needed here; resolution happens in Game.    public void play() {    @Override    }        return new SpellCard(name, cost, effect, damage, healing, cardsToDraw, imagePath);    public Card copy() {    @Override    }        return cardsToDraw;    public int getCardsToDraw() {    }        return healing;    public int getHealing() {    }        return damage;    public int getDamage() {    }        return effect;    public String getEffect() {    }        this.cardsToDraw = cardsToDraw;        this.healing = healing;        this.damage = damage;        this.effect = effect;        super(name, "Spell", cost, effect, imagePath);    public SpellCard(String name, int cost, String effect, int damage, int healing, int cardsToDraw, String imagePath) {    private final int cardsToDraw;      // Cartas que se roban    private final int healing;          // Curación que proporciona    private final int damage;           // Daño que causa el hechizo    private final String effect;        // Descripción del efectopublic class SpellCard extends Card { */ * Los hechizos tienen efectos instantáneos como daño, curación o robar cartas. * Representa una carta de hechizo en el juego./**
/**
 * Representa una carta de criatura en el juego.
 * Posee poder (ataque) y resistencia (defensa).
 * Las criaturas pueden atacar, pero necesitan esperar un turno después de ser invocadas.
 */
public class CreatureCard extends Card {
    private final int power;      // Daño que hace la criatura al atacar
    private final int toughness;  // Resistencia de la criatura
    private boolean readyToAttack;  // Puede atacar este turno
    private boolean justPlayed;     // Fue invocada este turno

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

    /**
     * Marca que la criatura fue invocada este turno.
     * No puede atacar hasta el siguiente turno del propietario.
     */
    public void markPlayedThisTurn() {
        readyToAttack = false;
        justPlayed = true;
    }

    /**
     * Al comienzo del turno del propietario:
     * - Si fue invocada este turno, ahora puede atacar próximamente
     * - En caso contrario, está lista para atacar
     */
    public void startOwnerTurn() {
        if (justPlayed) {
            justPlayed = false;
            readyToAttack = false; // Espera hasta el próximo turno
        } else {
            readyToAttack = true;
        }
    }

    /**
     * Consume el ataque de la criatura.
     * No podrá atacar nuevamente hasta el próximo turno.
     */
    public void consumeAttack() {
        readyToAttack = false;
    }

    @Override
    public Card copy() {
        return new CreatureCard(name, cost, power, toughness, description, imagePath);
    }

    @Override
    public void play() {
        // No side effects needed here; resolution happens in Game.
    }

    @Override
    public String toString() {
        return String.format("%s [%s/%s] (Costo: %d)", name, power, toughness, cost);
    }
}
