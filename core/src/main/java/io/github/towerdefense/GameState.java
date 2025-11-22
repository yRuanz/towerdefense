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

    private Raridade dificuldade = Raridade.COMUM;
    private int andarAtual = 1;
    private ArrayList<Personagem> personagensDisponiveis = new ArrayList<>();
    
    // Sistema de múltiplos jogadores
    private int numeroJogadores = 1;
    private List<Personagem> jogadoresSelecionados = new ArrayList<>();
    private List<String> nomesJogadores = new ArrayList<>();
    private int jogadorAtualSelecionando = 0;
    private String nomeJogadorAtual = "";


    private GameState() {}

    public static GameState I() { return I; }

    // ==================== Configuração de Jogadores ====================
    
    public void setNumeroJogadores(int num) {
        numeroJogadores = Math.min(4, Math.max(1, num));
        jogadoresSelecionados.clear();
        jogadorAtualSelecionando = 0;
    }

    public int getNumeroJogadores() {
        return numeroJogadores;
    }

    public void selecionarPersonagem(Personagem p) {
        jogadoresSelecionados.add(p);
        personagensDisponiveis.remove(p); // Remove da lista disponível
        jogadorAtualSelecionando++;
    }

    public boolean todosJogadoresEscolheram() {
        return jogadoresSelecionados.size() >= numeroJogadores;
    }

    public int getJogadorAtualSelecionando() {
        return jogadorAtualSelecionando;
    }

    public void setNomeJogadorAtual(String nome) {
    nomeJogadorAtual = nome;
    nomesJogadores.add(nome);
}

    public String getNomeJogadorAtual() {
        return nomeJogadorAtual;
    }

    public List<String> getNomesJogadores() {
        return new ArrayList<>(nomesJogadores);
    }

    public List<Personagem> getJogadoresSelecionados() {
        return new ArrayList<>(jogadoresSelecionados);
    }

    public void resetarSelecao() {
        jogadoresSelecionados.clear();
        jogadorAtualSelecionando = 0;
        // Recriar personagens disponíveis
        personagensDisponiveis.clear();
    }

    // ==================== Lista de Jogadores ====================
    
    public List<Personagem> createPlayersList() {
        return new ArrayList<>(jogadoresSelecionados);
    }

    // ==================== Personagens Disponíveis ====================
    
    public ArrayList<Personagem> getPersonagensDisponiveis() {
        return personagensDisponiveis;
    }

    public void addPersonagem(Personagem p) {
        personagensDisponiveis.add(p);
    }

    // ==================== Outros ====================
    
    public void setDificuldade(Raridade r) {
        dificuldade = r;
    }

    public Raridade getDificuldade() {
        return dificuldade;
    }

    public int getAndarAtual() {
        return andarAtual;
    }

    public void incrementarAndar() {
        andarAtual++;
    }
}