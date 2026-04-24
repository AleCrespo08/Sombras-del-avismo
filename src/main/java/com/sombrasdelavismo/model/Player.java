package com.sombrasdelavismo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa un jugador en el juego de cartas.
 * Cada jugador tiene un mazo, mano, campo de batalla, vida y mana.
 */
public class Player {
    private static final int INITIAL_LIFE = 20;
    private static final int INITIAL_MANA = 3;
    private static final int MAX_MANA_CAP = 10;

    private final String name;
    private final List<Card> deck;          // Mazo del jugador
    private final List<Card> hand;          // Cartas en mano
    private final List<CreatureCard> battlefield;  // Criaturas en juego
    private final List<Card> graveyard;     // Cartas destruidas/descartadas
    private int life;                       // Puntos de vida actuales
    private int mana;                       // Mana disponible actualmente
    private int maxMana;                    // Mana máximo (aumenta cada turno)

    public Player(String name) {
        this.name = name;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.battlefield = new ArrayList<>();
        this.graveyard = new ArrayList<>();
        this.life = INITIAL_LIFE;
        this.mana = INITIAL_MANA;
        this.maxMana = INITIAL_MANA;
    }

    // ===== MÉTODOS DE MAZO =====

    public void addCardToDeck(Card card) {
        deck.add(card);
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public Card drawCard() {
        if (!deck.isEmpty()) {
            Card card = deck.remove(0);
            hand.add(card);
            return card;
        }
        return null;
    }

    public int getDeckSize() {
        return deck.size();
    }

    // ===== MÉTODOS DE MANO =====

    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }

    public boolean removeFromHand(Card card) {
        return hand.remove(card);
    }

    // ===== MÉTODOS DE CAMPO DE BATALLA =====

    public List<CreatureCard> getBattlefield() {
        return new ArrayList<>(battlefield);
    }

    public void addToBattlefield(CreatureCard creatureCard) {
        battlefield.add(creatureCard);
    }

    public boolean removeFromBattlefield(CreatureCard creatureCard) {
        return battlefield.remove(creatureCard);
    }

    public void clearBattlefield() {
        battlefield.clear();
    }

    // ===== MÉTODOS DE CEMENTERIO =====

    public List<Card> getGraveyard() {
        return new ArrayList<>(graveyard);
    }

    public void addToGraveyard(Card card) {
        graveyard.add(card);
    }

    public void clearGraveyard() {
        graveyard.clear();
    }

    // ===== MÉTODOS DE VIDA =====

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = Math.max(0, life);
    }

    public void takeDamage(int damage) {
        life = Math.max(0, life - damage);
    }

    public void heal(int amount) {
        life = Math.max(life, life + amount);
    }

    public boolean isAlive() {
        return life > 0;
    }

    // ===== MÉTODOS DE INFORMACIÓN =====

    public String getName() {
        return name;
    }

    // ===== MÉTODOS DE MANA =====

    public int getMana() {
        return mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    /**
     * Intenta gastar la cantidad especificada de mana.
     * @return true si se gastó correctamente, false si no hay suficiente mana
     */
    public boolean spendMana(int amount) {
        if (mana < amount) {
            return false;
        }
        mana -= amount;
        return true;
    }

    /**
     * Verifica si puede jugar una carta (tiene suficiente mana).
     */
    public boolean canPlay(Card card) {
        return card != null && mana >= card.getCost();
    }

    // ===== MÉTODOS DE TURNO =====

    /**
     * Al comenzar el turno:
     * 1. Aumenta mana máximo (hasta el límite)
     * 2. Restaura mana actual
     * 3. Roba una carta
     * 4. Prepara las criaturas para atacar
     */
    public void startTurn() {
        // Aumentar mana máximo si no ha alcanzado el límite
        if (maxMana < MAX_MANA_CAP) {
            maxMana++;
        }
        mana = maxMana;

        // Robar carta
        drawCard();

        // Preparar criaturas para atacar
        for (CreatureCard creature : battlefield) {
            creature.startOwnerTurn();
        }
    }

    /**
     * Reinicia el jugador para una nueva partida.
     */
    public void resetForNewGame() {
        hand.clear();
        battlefield.clear();
        graveyard.clear();
        life = INITIAL_LIFE;
        mana = INITIAL_MANA;
        maxMana = INITIAL_MANA;
    }

    @Override
    public String toString() {
        return String.format("%s [Vida: %d | Mana: %d/%d | Mano: %d]", 
            name, life, mana, maxMana, hand.size());
    }
}
