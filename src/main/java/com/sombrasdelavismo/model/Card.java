package com.sombrasdelavismo.model;

/**
 * Clase abstracta que representa una carta en el juego.
 * Todas las cartas tienen nombre, tipo, costo de mana y descripción.
 */
public abstract class Card {
    protected final String name;
    protected final String type;
    protected final int cost;
    protected final String description;
    protected final String imagePath;

    public Card(String name, String type, int cost, String description, String imagePath) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    /**
     * Crea una copia independiente de la carta.
     * Cada copia tiene su propio estado.
     */
    public abstract Card copy();

    /**
     * Ejecuta el efecto de la carta cuando se juega.
     * La lógica específica depende del tipo de carta.
     */
    public abstract void play();

    @Override
    public String toString() {
        return String.format("%s [%s - Costo: %d]", name, type, cost);
    }
}
