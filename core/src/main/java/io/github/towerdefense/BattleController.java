package io.github.towerdefense;

import io.github.towerdefense.logic.Batalha;
import io.github.towerdefense.logic.Personagem;

import java.util.List;

/**
 * Adapter entre telas LibGDX e a lógica da Batalha.
 * Fornece métodos diretos para serem usados por botões de combate.
 */
public class BattleController {

    private final Batalha batalha;

    public BattleController(Batalha batalha) {
        this.batalha = batalha;
    }

    /* ==========================================================
       CONTROLE PRINCIPAL
    ========================================================== */

    public void start() {
        batalha.start();
    }

    public void update() {
        batalha.nextStep();
    }

    public boolean isRunning() {
        return batalha.isRunning();
    }

    public Personagem getCurrent() {
        return batalha.getCurrentActor();
    }

    /* ==========================================================
       MÉTODOS PARA BOTÕES DA UI
    ========================================================== */

    /** Botão ATACAR */
    public boolean playerAttack(int enemyAliveIndex) {
        int playerAliveIndex = batalha.getCurrentPlayerIndexAlive();
        if (playerAliveIndex < 0) return false;

        return batalha.performAttack(playerAliveIndex, enemyAliveIndex);
    }

    /** Botão SKILL */
    public boolean playerSkill() {
        int playerAliveIndex = batalha.getCurrentPlayerIndexAlive();
        if (playerAliveIndex < 0) return false;

        return batalha.performSkill(playerAliveIndex);
    }

    /** Botão DEFENDER */
    public boolean playerDefend() {
        int playerAliveIndex = batalha.getCurrentPlayerIndexAlive();
        if (playerAliveIndex < 0) return false;

        Personagem p = batalha.getPlayers().get(playerAliveIndex);

        p.defender();
        batalha.passTurn();
        return true;
    }

    /** Botão PASSAR */
    public void playerPass() {
        batalha.passTurn();
    }

    /* ==========================================================
       GETTERS PARA A UI
    ========================================================== */

    public List<Personagem> getPlayers() {
        return batalha.getPlayers();
    }

    public List<Personagem> getEnemies() {
        return batalha.getEnemies();
    }

    public boolean isPlayerTurn() {
        return batalha.isPlayerTurn();
    }

    public boolean isEnemyTurn() {
        return batalha.isEnemyTurn();
    }

    public int getCurrentPlayerIndex() {
        return batalha.getCurrentPlayerIndexAlive();
    }

    public int getCurrentEnemyIndex() {
        return batalha.getCurrentEnemyIndexAlive();
    }
}
