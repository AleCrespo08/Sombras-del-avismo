package com.sombrasdelavismo.model;

/**
 * Clase que contiene la lógica central del juego.
 * Maneja turnos, juego de cartas, combate y condiciones de victoria.
 */
public class Game {
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private int turnNumber;
    private String lastAction;
    private Player winner;
    private boolean gameFinished;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.turnNumber = 0;
        this.lastAction = "Partida inicializada. En espera de comenzar...";
        this.winner = null;
        this.gameFinished = false;
    }

    // ===== INICIALIZACIÓN Y TURNOS =====

    /**
     * Inicia la partida:
     * - Reinicia ambos jugadores
     * - Mezcla los mazos
     * - Cada jugador roba 4 cartas
     * - El primer jugador comienza el turno
     */
    public void startGame() {
        player1.resetForNewGame();
        player2.resetForNewGame();
        player1.shuffleDeck();
        player2.shuffleDeck();

        for (int i = 0; i < 4; i++) {
            player1.drawCard();
            player2.drawCard();
        }

        turnNumber = 1;
        currentPlayer = player1;
        currentPlayer.startTurn();
        lastAction = currentPlayer.getName() + " comienza la partida.";
        gameFinished = false;
    }

    /**
     * Avanza al siguiente turno.
     * Cambia de jugador y restaura recursos.
     */
    public void nextTurn() {
        if (gameFinished) {
            return;
        }

        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        turnNumber++;
        currentPlayer.startTurn();
        lastAction = "Comienza el turno " + turnNumber + " de " + currentPlayer.getName() + ".";
    }

    // ===== ACCESORES BÁSICOS =====

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getWaitingPlayer() {
        return currentPlayer == player1 ? player2 : player1;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public String getLastAction() {
        return lastAction;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    // ===== VALIDACIONES DE ACCIONES =====

    /**
     * Verifica si una carta puede ser jugada.
     */
    public boolean canPlaySelectedCard(Card card) {
        return !gameFinished
                && card != null
                && currentPlayer.getHand().contains(card)
                && currentPlayer.canPlay(card);
    }

    /**
     * Verifica si una criatura puede atacar directamente.
     */
    public boolean canAttackDirectly(CreatureCard creatureCard) {
        return !gameFinished
                && creatureCard != null
                && currentPlayer.getBattlefield().contains(creatureCard)
                && creatureCard.isReadyToAttack();
    }

    /**
     * Verifica si se puede hacer combate entre dos criaturas.
     */
    public boolean canAttackTarget(CreatureCard attacker, CreatureCard defender) {
        return canAttackDirectly(attacker)
                && defender != null
                && getWaitingPlayer().getBattlefield().contains(defender);
    }

    // ===== ACCIONES DE JUEGO =====

    /**
     * El jugador actual juega una carta de su mano.
     */
    public String playCard(Card card) {
        if (gameFinished) {
            lastAction = "La partida ya ha terminado.";
            return lastAction;
        }

        if (card == null) {
            lastAction = "Selecciona una carta antes de jugar.";
            return lastAction;
        }

        if (!currentPlayer.getHand().contains(card)) {
            lastAction = "Solo puedes jugar cartas de tu mano.";
            return lastAction;
        }

        if (!currentPlayer.canPlay(card)) {
            lastAction = "No tienes mana suficiente para jugar " + card.getName() 
                    + ". Costo: " + card.getCost() + ", Mana disponible: " + currentPlayer.getMana();
            return lastAction;
        }

        // Gastar mana
        currentPlayer.spendMana(card.getCost());
        currentPlayer.removeFromHand(card);

        if (card instanceof CreatureCard creatureCard) {
            // Jugar criatura
            creatureCard.markPlayedThisTurn();
            currentPlayer.addToBattlefield(creatureCard);
            card.play();
            lastAction = currentPlayer.getName() + " invoca a " + card.getName() + ".";
            return lastAction;
        }

        if (card instanceof SpellCard spellCard) {
            // Resolver hechizo
            resolveSpell(spellCard);
            card.play();
            return lastAction;
        }

        lastAction = "Tipo de carta no reconocido.";
        return lastAction;
    }

    /**
     * El jugador actual ataca directamente al rival.
     */
    public String attackDirectly(CreatureCard creatureCard) {
        if (gameFinished) {
            lastAction = "La partida ya ha terminado.";
            return lastAction;
        }

        if (creatureCard == null) {
            lastAction = "Selecciona una criatura para atacar.";
            return lastAction;
        }

        if (!currentPlayer.getBattlefield().contains(creatureCard)) {
            lastAction = "Solo puedes atacar con criaturas de tu mesa.";
            return lastAction;
        }

        if (!creatureCard.isReadyToAttack()) {
            lastAction = creatureCard.getName() + " aún no puede atacar este turno.";
            return lastAction;
        }

        Player rival = getWaitingPlayer();
        int damage = creatureCard.getPower();
        rival.takeDamage(damage);
        creatureCard.consumeAttack();

        lastAction = currentPlayer.getName() + " ataca con " + creatureCard.getName()
                + " (" + damage + " daño) a " + rival.getName() + ". Vida: " + rival.getLife();

        updateWinner();
        return lastAction;
    }

    /**
     * Hace combate entre dos criaturas.
     */
    public String attackCreature(CreatureCard attacker, CreatureCard defender) {
        if (gameFinished) {
            lastAction = "La partida ya ha terminado.";
            return lastAction;
        }

        if (attacker == null) {
            lastAction = "Selecciona una criatura tuya para atacar.";
            return lastAction;
        }

        if (defender == null) {
            lastAction = "Selecciona una criatura rival para combatir.";
            return lastAction;
        }

        if (!currentPlayer.getBattlefield().contains(attacker)) {
            lastAction = "Solo puedes atacar con criaturas de tu mesa.";
            return lastAction;
        }

        Player rival = getWaitingPlayer();
        if (!rival.getBattlefield().contains(defender)) {
            lastAction = "Solo puedes atacar criaturas de la mesa rival.";
            return lastAction;
        }

        if (!attacker.isReadyToAttack()) {
            lastAction = attacker.getName() + " aún no puede atacar este turno.";
            return lastAction;
        }

        // Resolver combate
        boolean attackerDies = defender.getPower() >= attacker.getToughness();
        boolean defenderDies = attacker.getPower() >= defender.getToughness();

        attacker.consumeAttack();

        if (attackerDies) {
            currentPlayer.removeFromBattlefield(attacker);
            currentPlayer.addToGraveyard(attacker);
        }

        if (defenderDies) {
            rival.removeFromBattlefield(defender);
            rival.addToGraveyard(defender);
        }

        StringBuilder action = new StringBuilder(currentPlayer.getName())
                .append(" combate: ").append(attacker.getName())
                .append(" [").append(attacker.getPower()).append("/").append(attacker.getToughness()).append("]")
                .append(" vs ").append(defender.getName())
                .append(" [").append(defender.getPower()).append("/").append(defender.getToughness()).append("]");

        if (defenderDies && attackerDies) {
            action.append(" → Ambas criaturas son destruidas.");
        } else if (defenderDies) {
            action.append(" → ").append(defender.getName()).append(" es destruida.");
        } else if (attackerDies) {
            action.append(" → ").append(attacker.getName()).append(" es destruida.");
        } else {
            action.append(" → Ambas sobreviven al combate.");
        }

        lastAction = action.toString();
        return lastAction;
    }

    // ===== RESOLUCIÓN DE HECHIZOS =====

    /**
     * Resuelve los efectos de un hechizo.
     */
    private void resolveSpell(SpellCard spellCard) {
        Player rival = getWaitingPlayer();

        // Aplicar daño
        if (spellCard.getDamage() > 0) {
            rival.takeDamage(spellCard.getDamage());
        }

        // Aplicar curación
        if (spellCard.getHealing() > 0) {
            currentPlayer.heal(spellCard.getHealing());
        }

        // Robar cartas
        for (int i = 0; i < spellCard.getCardsToDraw(); i++) {
            currentPlayer.drawCard();
        }

        updateWinner();

        StringBuilder action = new StringBuilder(currentPlayer.getName())
                .append(" lanza ").append(spellCard.getName());

        if (spellCard.getDamage() > 0) {
            action.append(" → ").append(spellCard.getDamage()).append(" daño a ").append(rival.getName());
        }
        if (spellCard.getHealing() > 0) {
            action.append(" → Cura ").append(spellCard.getHealing()).append(" vidas");
        }
        if (spellCard.getCardsToDraw() > 0) {
            action.append(" → Roba ").append(spellCard.getCardsToDraw()).append(" carta");
            if (spellCard.getCardsToDraw() > 1) action.append("s");
        }

        lastAction = action.toString();
    }

    // ===== CONDICIONES DE VICTORIA =====

    /**
     * Verifica si alguien ha ganado la partida.
     */
    private void updateWinner() {
        if (player1.getLife() <= 0 && player2.getLife() <= 0) {
            // Empate
            winner = null;
            lastAction = "¡EMPATE! Ambos jugadores quedaron sin vida.";
            gameFinished = true;
        } else if (player1.getLife() <= 0) {
            winner = player2;
            lastAction = "¡" + player2.getName() + " gana la partida!";
            gameFinished = true;
        } else if (player2.getLife() <= 0) {
            winner = player1;
            lastAction = "¡" + player1.getName() + " gana la partida!";
            gameFinished = true;
        }
    }

    @Override
    public String toString() {
        return String.format("Turno %d - %s\nP1: %s | P2: %s", 
            turnNumber, currentPlayer.getName(), player1, player2);
    }
}
