package io.github.towerdefense.logic;

/**
 * Helper para criar personagens por classe (pode ser estendido para ler JSON).
 */
public class CharacterFactory {

    public static Personagem createPlayer(String nome, ClassePersonagem classe) {

    if (classe == ClassePersonagem.ARQUEIRO)
        return new Personagem(nome, classe, 85, 5, 14, 25,
            new Equipamento("Arco Composto", 9), false, "arqueiro_sprite.png");

    if (classe == ClassePersonagem.GUERREIRO)
        return new Personagem(nome, classe, 120, 10, 6, 20,
            new Equipamento("Espada Longa", 12), false, "guerreiro_sprite.png");

    if (classe == ClassePersonagem.MAGO)
        return new Personagem(nome, classe, 70, 3, 10, 40,
            new Equipamento("Cajado Arcano", 7), false, "mago_sprite.png");

    if (classe == ClassePersonagem.BARDO)
        return new Personagem(nome, classe, 90, 6, 15, 30,
            new Equipamento("Violão Místico", 5), false, "bardo_sprite.png");

    throw new IllegalArgumentException("Classe inválida: " + classe);
}


    public static Personagem createEnemy(String nome, int vida, int def, int vel, int mana, int danoArma) {
        return new Personagem(
                nome,
                ClassePersonagem.ARQUEIRO, // Classe genérica
                vida, def, vel, mana,
                new Equipamento("Cutelo Goblin", danoArma),
                true,
                "goblin_sprite.png"
        );
    }
}
