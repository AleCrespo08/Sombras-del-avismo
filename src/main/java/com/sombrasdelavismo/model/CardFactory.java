package com.sombrasdelavismo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Factoría para crear mazos predefinidos con cartas balanceadas.
 * Proporciona métodos para crear diferentes tipos de mazos.
 */
public class CardFactory {

    /**
     * Crea un mazo estándar con cartas balanceadas.
     * Incluye una mezcla de criaturas y hechizos.
     */
    public static List<Card> createStandardDeck() {
        List<Card> deck = new ArrayList<>();

        // Criaturas básicas
        for (int i = 0; i < 3; i++) {
            deck.add(new CreatureCard("Goblin", 1, 1, 1, "Criatura", "cards/goblin.jpg"));
            deck.add(new CreatureCard("Mosquetero", 2, 2, 2, "Criatura", "cards/mosquetero.jpg"));
            deck.add(new CreatureCard("Caballero", 3, 3, 2, "Criatura", "cards/caballero.jpg"));
            deck.add(new CreatureCard("Dragón", 5, 4, 4, "Criatura", "cards/dragon.jpg"));
        }

        // Hechizos de daño
        for (int i = 0; i < 3; i++) {
            deck.add(new SpellCard("Rayo", 1, "Daño", 2, 0, 0, "cards/rayo.jpg"));
            deck.add(new SpellCard("Bola de Fuego", 2, "Daño", 4, 0, 0, "cards/bola.jpg"));
            deck.add(new SpellCard("Ráfaga de Fuego", 3, "Daño", 5, 0, 0, "cards/rafaga.jpg"));
        }

        // Hechizos de curación
        for (int i = 0; i < 2; i++) {
            deck.add(new SpellCard("Curación", 2, "Curación", 0, 3, 0, "cards/curacion.jpg"));
            deck.add(new SpellCard("Cura Mayor", 4, "Curación", 0, 6, 0, "cards/cura_mayor.jpg"));
        }

        // Hechizos de robo
        for (int i = 0; i < 2; i++) {
            deck.add(new SpellCard("Buscar", 1, "Robo", 0, 0, 1, "cards/buscar.jpg"));
            deck.add(new SpellCard("Conocimiento", 3, "Robo", 0, 0, 2, "cards/conocimiento.jpg"));
        }

        // Hechizos mixtos
        deck.add(new SpellCard("Sanación Ofensiva", 3, "Mixto", 3, 3, 0, "cards/sanacion.jpg"));

        return deck;
    }

    /**
     * Crea un mazo agresivo con más criaturas y hechizos de daño.
     */
    public static List<Card> createAggressiveDeck() {
        List<Card> deck = new ArrayList<>();

        // Muchas criaturas de bajo costo
        for (int i = 0; i < 4; i++) {
            deck.add(new CreatureCard("Goblin", 1, 1, 1, "Criatura", "cards/goblin.jpg"));
            deck.add(new CreatureCard("Orco", 2, 2, 1, "Criatura", "cards/orco.jpg"));
            deck.add(new CreatureCard("Berserker", 3, 3, 2, "Criatura", "cards/berserker.jpg"));
        }

        // Más hechizos de daño
        for (int i = 0; i < 4; i++) {
            deck.add(new SpellCard("Rayo", 1, "Daño", 2, 0, 0, "cards/rayo.jpg"));
            deck.add(new SpellCard("Bola de Fuego", 2, "Daño", 4, 0, 0, "cards/bola.jpg"));
            deck.add(new SpellCard("Ráfaga de Fuego", 3, "Daño", 5, 0, 0, "cards/rafaga.jpg"));
        }

        // Poco robo y poca curación
        deck.add(new SpellCard("Buscar", 1, "Robo", 0, 0, 1, "cards/buscar.jpg"));
        deck.add(new SpellCard("Curación", 2, "Curación", 0, 2, 0, "cards/curacion.jpg"));

        return deck;
    }

    /**
     * Crea un mazo defensivo con más curación y criaturas fuertes.
     */
    public static List<Card> createDefensiveDeck() {
        List<Card> deck = new ArrayList<>();

        // Criaturas fuertes
        for (int i = 0; i < 3; i++) {
            deck.add(new CreatureCard("Escudo", 2, 1, 3, "Criatura", "cards/escudo.jpg"));
            deck.add(new CreatureCard("Muro", 3, 1, 4, "Criatura", "cards/muro.jpg"));
            deck.add(new CreatureCard("Coloso", 4, 3, 5, "Criatura", "cards/coloso.jpg"));
        }

        // Más hechizos de curación
        for (int i = 0; i < 4; i++) {
            deck.add(new SpellCard("Curación", 2, "Curación", 0, 3, 0, "cards/curacion.jpg"));
            deck.add(new SpellCard("Cura Mayor", 4, "Curación", 0, 6, 0, "cards/cura_mayor.jpg"));
        }

        // Robo moderado
        for (int i = 0; i < 2; i++) {
            deck.add(new SpellCard("Buscar", 1, "Robo", 0, 0, 1, "cards/buscar.jpg"));
            deck.add(new SpellCard("Conocimiento", 3, "Robo", 0, 0, 2, "cards/conocimiento.jpg"));
        }

        // Poco daño
        for (int i = 0; i < 2; i++) {
            deck.add(new SpellCard("Rayo", 1, "Daño", 1, 0, 0, "cards/rayo.jpg"));
        }

        return deck;
    }

    /**
     * Crea un mazo equilibrado para pruebas.
     */
    public static List<Card> createTestDeck() {
        List<Card> deck = new ArrayList<>();

        // Distribuir uniformemente
        deck.add(new CreatureCard("Guerrero", 2, 2, 2, "Test", "cards/guerrero.jpg"));
        deck.add(new CreatureCard("Arquero", 1, 3, 1, "Test", "cards/arquero.jpg"));
        deck.add(new SpellCard("Ataque Rápido", 1, "Daño", 3, 0, 0, "cards/ataque.jpg"));
        deck.add(new SpellCard("Sanación", 2, "Curación", 0, 4, 0, "cards/sanacion.jpg"));

        // Relleno
        for (int i = 0; i < 8; i++) {
            deck.add(new CreatureCard("Soldado " + i, 1, 1, 1, "Test", "cards/soldado.jpg"));
        }

        return deck;
    }
}
