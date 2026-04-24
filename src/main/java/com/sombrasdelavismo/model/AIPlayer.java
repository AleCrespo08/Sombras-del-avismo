package com.sombrasdelavismo.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementa un jugador controlado por inteligencia artificial.
 * Toma decisiones automáticas sobre qué cartas jugar y cuándo atacar.
 */
public class AIPlayer {
    private final Player player;
    private static final String AI_NAME = "IA";

    public AIPlayer(Player aiPlayer) {
        this.player = aiPlayer;
    }

    /**
     * Ejecuta el turno de la IA.
     * La IA intenta:
     * 1. Jugar cartas que puede costear
     * 2. Usar hechizos para daño o curación
     * 3. Atacar con criaturas disponibles
     */
    public List<String> playTurn(Game game) {
        List<String> actions = new ArrayList<>();

        if (game.getCurrentPlayer() != player) {
            return actions;
        }

        // Fase 1: Jugar cartas mientras pueda
        playCards(game, actions);

        // Fase 2: Atacar con criaturas disponibles
        performAttacks(game, actions);

        return actions;
    }

    /**
     * Estrategia de juego de cartas: juega las cartas que pueda permitirse.
     * Prioriza:
     * 1. Hechizos de daño (si el rival tiene poca vida)
     * 2. Hechizos de curación (si la IA tiene poca vida)
     * 3. Criaturas (por poder)
     */
    private void playCards(Game game, List<String> actions) {
        boolean playedCard = true;

        while (playedCard) {
            playedCard = false;

            List<Card> hand = new ArrayList<>(player.getHand());
            Card cardToPlay = null;

            // Buscar la mejor carta para jugar
            if (shouldPlayDamageSpell(game)) {
                cardToPlay = findBestDamageSpell(hand);
            } else if (shouldPlayHealingSpell(game)) {
                cardToPlay = findBestHealingSpell(hand);
            } else {
                cardToPlay = findBestCreature(hand);
            }

            // Si encontramos una carta que podemos jugar
            if (cardToPlay != null && player.canPlay(cardToPlay)) {
                String action = game.playCard(cardToPlay);
                actions.add(action);
                playedCard = true;
            }
        }
    }

    /**
     * Estrategia de ataque: ataca directamente si puede.
     * Si no puede atacar directamente, intenta hacer combate.
     */
    private void performAttacks(Game game, List<String> actions) {
        List<CreatureCard> battlefield = new ArrayList<>(game.getCurrentPlayer().getBattlefield());

        for (CreatureCard creature : battlefield) {
            if (game.canAttackDirectly(creature)) {
                // Intentar atacar directamente
                String action = game.attackDirectly(creature);
                actions.add(action);
            }
        }
    }

    /**
     * Determina si la IA debe jugar un hechizo de daño.
     * Lo hace si el rival tiene poca vida (mayor riesgo).
     */
    private boolean shouldPlayDamageSpell(Game game) {
        Player rival = game.getWaitingPlayer();
        // Si el rival tiene 10 o menos vidas, considera daño
        return rival.getLife() <= 10 && findBestDamageSpell(player.getHand()) != null;
    }

    /**
     * Determina si la IA debe jugar un hechizo de curación.
     * Lo hace si su vida es baja.
     */
    private boolean shouldPlayHealingSpell(Game game) {
        // Si la IA tiene 8 o menos vidas, considera curación
        return player.getLife() <= 8 && findBestHealingSpell(player.getHand()) != null;
    }

    /**
     * Busca el mejor hechizo de daño que pueda permitirse.
     * Ordena por cantidad de daño en orden descendente.
     */
    private Card findBestDamageSpell(List<Card> hand) {
        return hand.stream()
                .filter(card -> card instanceof SpellCard)
                .map(card -> (SpellCard) card)
                .filter(spell -> spell.getDamage() > 0 && player.canPlay(spell))
                .max(Comparator.comparingInt(SpellCard::getDamage))
                .orElse(null);
    }

    /**
     * Busca el mejor hechizo de curación que pueda permitirse.
     * Ordena por cantidad de curación en orden descendente.
     */
    private Card findBestHealingSpell(List<Card> hand) {
        return hand.stream()
                .filter(card -> card instanceof SpellCard)
                .map(card -> (SpellCard) card)
                .filter(spell -> spell.getHealing() > 0 && player.canPlay(spell))
                .max(Comparator.comparingInt(SpellCard::getHealing))
                .orElse(null);
    }

    /**
     * Busca la mejor criatura que pueda permitirse.
     * Ordena por poder (ataque) en orden descendente.
     */
    private Card findBestCreature(List<Card> hand) {
        return hand.stream()
                .filter(card -> card instanceof CreatureCard)
                .map(card -> (CreatureCard) card)
                .filter(player::canPlay)
                .max(Comparator.comparingInt(CreatureCard::getPower))
                .orElse(null);
    }
}
