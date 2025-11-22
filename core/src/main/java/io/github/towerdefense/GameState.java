package io.github.towerdefense;

import io.github.towerdefense.logic.ClassePersonagem;
import io.github.towerdefense.logic.Personagem;
import io.github.towerdefense.logic.CharacterFactory;
import io.github.towerdefense.logic.Raridade;

import java.util.ArrayList;
import java.util.List;

/**
 * Estado global simples (Singleton) usado pelas telas.
 */
public class GameState {
    private static final GameState I = new GameState();

    private Personagem player;
    private Raridade dificuldade = Raridade.COMUM;
    private int andarAtual = 1;
    private ArrayList<Personagem> personagensDisponiveis = new ArrayList<>();
    private Personagem personagemSelecionado;

    private GameState() {}

    public static GameState I() { return I; }

    public void createPlayer(String nome, ClassePersonagem classe) {
        this.player = CharacterFactory.createPlayer(nome, classe);
    }

    public Personagem getPlayer() { return player; }
    public void setDificuldade(Raridade r) { dificuldade = r; }
    public Raridade getDificuldade() { return dificuldade; }

    public int getAndarAtual() { return andarAtual; }
    public void incrementarAndar() { andarAtual++; }

    // cria lista de jogadores (agora só 1 jogador, mas pode expandir)
    public List<Personagem> createPlayersList() {
        List<Personagem> list = new ArrayList<>();
        list.add(player);
        return list;
    }
    public ArrayList<Personagem> getPersonagensDisponiveis() { return personagensDisponiveis; }

    public void addPersonagem(Personagem p) {
        personagensDisponiveis.add(p);
    }

    public void setPersonagemSelecionado(Personagem p) {
        personagemSelecionado = p;
    }

    public Personagem getPersonagemSelecionado() {
        return personagemSelecionado;
}
}
