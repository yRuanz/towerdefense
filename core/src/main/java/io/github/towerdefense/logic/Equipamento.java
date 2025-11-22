package io.github.towerdefense.logic;

/** Versão simples de Equipamento usada pela Personagem. */
public class Equipamento {
    private final String nome;
    private final int dano;

    public Equipamento(String nome, int dano) {
        this.nome = nome;
        this.dano = dano;
    }

    public String getNome() { return nome; }
    public int getDano() { return dano; }
}
