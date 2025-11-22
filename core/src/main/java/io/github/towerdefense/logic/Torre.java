package io.github.towerdefense.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Gera inimigos (como antes) mas retorna List<Personagem>.
 */
public class Torre {

    public static List<Personagem> gerarAndar(int andar, int qtdJogadores) {
        List<Personagem> inimigos = new ArrayList<>();
        Random r = new Random();
        int qtdInimigos = Math.max(1, qtdJogadores + r.nextInt(2)); // 1 extra às vezes

        for (int i = 1; i <= qtdInimigos; i++) {
            int vida = 40 + andar * 10 + r.nextInt(10);
            int def = 3 + andar;
            int vel = 5 + r.nextInt(4);
            int mana = 10 + andar;
            int danoArma = 6 + andar;

            inimigos.add(CharacterFactory.createEnemy("Monstro " + i, vida, def, vel, mana, danoArma));
        }
        return inimigos;
    }
}
