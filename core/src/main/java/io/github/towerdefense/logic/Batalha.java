package io.github.towerdefense.logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sistema de batalha em turnos, não-bloqueante.
 * A UI chama nextStep() até ser turno do jogador.
 * A UI chama performAttack(), performSkill() ou passTurn().
 */
public class Batalha {

    private final List<Personagem> jogadores;
    private final List<Personagem> inimigos;

    private List<Personagem> ordem;   // ordem de turno atual
    private int indexAtual = 0;
    private boolean ativa = false;

    public Batalha(List<Personagem> jogadores, List<Personagem> inimigos) {
        this.jogadores = new ArrayList<>(jogadores);
        this.inimigos = new ArrayList<>(inimigos);
    }

    /* ---------------------------------------------------------
       INÍCIO DA BATALHA
    --------------------------------------------------------- */

    public void start() {
        rebuildOrder();
        ativa = true;
    }

    /**
     * Recalcula a ordem de turno com base na velocidade dos vivos.
     */
    private void rebuildOrder() {
        ordem = new ArrayList<>();
        ordem.addAll(jogadores.stream().filter(Personagem::isAlive).collect(Collectors.toList()));
        ordem.addAll(inimigos.stream().filter(Personagem::isAlive).collect(Collectors.toList()));

        // Turnos por velocidade (maior primeiro)
        ordem.sort(Comparator.comparingInt(Personagem::getVelocidade).reversed());
        indexAtual = 0;
    }

    /* ---------------------------------------------------------
       AVANÇO DO LOOP DE BATALHA
    --------------------------------------------------------- */

    /**
     * nextStep() avança a batalha até ser turno do jogador.
     * Se for inimigo, ele age automaticamente.
     * Se for jogador, a UI deve decidir o que fazer.
     */
    public void nextStep() {
        if (!ativa) return;
        if (ordem == null || ordem.isEmpty()) return;

        // fim da rodada -> recalcula ordem & atualiza buffs
        if (indexAtual >= ordem.size()) {
            for (Personagem p : ordem) p.atualizarBuffs();
            rebuildOrder();
            return;
        }

        Personagem atual = ordem.get(indexAtual);

        // se morreu antes do turno, pula
        if (!atual.isAlive()) {
            indexAtual++;
            nextStep();
            return;
        }

        // turno automático do inimigo
        if (atual.isInimigo()) {
            Personagem alvo = jogadores.stream()
                    .filter(Personagem::isAlive)
                    .findFirst()
                    .orElse(null);

            if (alvo != null) {
                atual.atacar(alvo);
            }

            indexAtual++;
            checkEnd();
            return;
        }

        // se chegou aqui, é turno do jogador -> UI deve agir
    }

    /* ---------------------------------------------------------
       AÇÕES REALIZADAS PELO JOGADOR
    --------------------------------------------------------- */

    public boolean performAttack(int playerIdxAliveList, int enemyIdxAliveList) {
        Personagem jogador = getAliveFromList(jogadores, playerIdxAliveList);
        Personagem alvo = getAliveFromList(inimigos, enemyIdxAliveList);

        if (jogador == null || alvo == null) return false;

        jogador.atacar(alvo);

        indexAtual++;
        checkEnd();
        return true;
    }

    public boolean performSkill(int playerIdxAliveList) {
        Personagem jogador = getAliveFromList(jogadores, playerIdxAliveList);
        if (jogador == null) return false;

        jogador.usarHabilidade(
                jogadores.toArray(new Personagem[0]),
                inimigos.toArray(new Personagem[0])
        );

        indexAtual++;
        checkEnd();
        return true;
    }

    public void passTurn() {
        indexAtual++;
        checkEnd();
    }

    /* ---------------------------------------------------------
       AUXILIARES
    --------------------------------------------------------- */

    private Personagem getAliveFromList(List<Personagem> list, int idx) {
        List<Personagem> vivos = list.stream().filter(Personagem::isAlive).collect(Collectors.toList());
        if (idx < 0 || idx >= vivos.size()) return null;
        return vivos.get(idx);
    }

    /**
     * Verifica fim da batalha ou redireciona nova rodada.
     */
    private void checkEnd() {
        boolean anyPlayers = jogadores.stream().anyMatch(Personagem::isAlive);
        boolean anyEnemies = inimigos.stream().anyMatch(Personagem::isAlive);

        // fim da batalha
        if (!anyPlayers || !anyEnemies) {
            ativa = false;
            return;
        }

        // fim da rodada → recalcula
        if (indexAtual >= ordem.size()) {
            for (Personagem p : ordem) p.atualizarBuffs();
            rebuildOrder();
        }
    }

    /* ---------------------------------------------------------
       GETTERS
    --------------------------------------------------------- */

    public boolean isRunning() { return ativa; }

    public Personagem getCurrentActor() {
        if (ordem == null || ordem.isEmpty() || indexAtual >= ordem.size()) return null;
        return ordem.get(indexAtual);
    }

    public List<Personagem> getPlayers() { return new ArrayList<>(jogadores); }
    public List<Personagem> getEnemies() { return new ArrayList<>(inimigos); }

    /* ---------------------------------------------------------
       NOVOS MÉTODOS PARA A UI
    --------------------------------------------------------- */

    public boolean isPlayerTurn() {
        Personagem p = getCurrentActor();
        return p != null && !p.isInimigo();
    }

    public boolean isEnemyTurn() {
        Personagem p = getCurrentActor();
        return p != null && p.isInimigo();
    }

    /** Retorna o índice do personagem atual entre jogadores vivos */
    public int getCurrentPlayerIndexAlive() {
        Personagem current = getCurrentActor();
        if (current == null || current.isInimigo()) return -1;

        List<Personagem> vivos = jogadores.stream().filter(Personagem::isAlive).collect(Collectors.toList());
        return vivos.indexOf(current);
    }

    /** Retorna o índice do personagem atual entre inimigos vivos */
    public int getCurrentEnemyIndexAlive() {
        Personagem current = getCurrentActor();
        if (current == null || !current.isInimigo()) return -1;

        List<Personagem> vivos = inimigos.stream().filter(Personagem::isAlive).collect(Collectors.toList());
        return vivos.indexOf(current);
    }
}
