package io.github.towerdefense.logic;

import java.util.ArrayList;
import java.util.List;

public class Personagem {

    private String nome;
    private ClassePersonagem classe;
    private boolean inimigo;
    private String spritePath;

    private int vida;
    private int defesa;
    private int velocidade;
    private int mana;
    private Equipamento arma;


    // Buffs temporários
    private int buffAtaque = 0;
    private int buffVelocidade = 0;
    private int turnosBuffAtaque = 0;
    private int turnosBuffVelocidade = 0;

    private List<Item> itens = new ArrayList<>();

    public Personagem(String nome, ClassePersonagem classe,
                      int vida, int defesa, int velocidade, int mana, Equipamento arma, boolean inimigo, String spritePath) {
        this.nome = nome;
        this.classe = classe;
        this.vida = vida;
        this.defesa = defesa;
        this.velocidade = velocidade;
        this.mana = mana;
        this.arma = arma;
        this.inimigo = inimigo;
        this.spritePath = spritePath;
    }

    // GETTERS
    public String getNome() { return nome; }
    public boolean isInimigo() { return inimigo; }
    public int getVida() { return vida; }
    public int getDefesa() { return defesa; }
    public int getMana() { return mana; }
    public Equipamento getArma() { return arma; }
    public ClassePersonagem getClasse() { return classe; }
    public String getSpritePath() { return spritePath; }    

    // ===================== Buffs =====================

    public void aplicarBuffAtaque(int valor, int turnos) {
        buffAtaque += valor;
        turnosBuffAtaque = Math.max(turnosBuffAtaque, turnos);
    }

    public void aplicarBuffVelocidade(int valor, int turnos) {
        buffVelocidade += valor;
        turnosBuffVelocidade = Math.max(turnosBuffVelocidade, turnos);
    }

    public void atualizarBuffs() {
        if (turnosBuffAtaque > 0) {
            turnosBuffAtaque--;
            if (turnosBuffAtaque == 0) buffAtaque = 0;
        }

        if (turnosBuffVelocidade > 0) {
            turnosBuffVelocidade--;
            if (turnosBuffVelocidade == 0) buffVelocidade = 0;
        }
    }

    // =================================================

    public boolean isAlive() {
        return vida > 0;
    }

    public void takeDamage(int rawDamage) {
        int danoFinal = Math.max(0, rawDamage - defesa);
        vida = Math.max(0, vida - danoFinal);
        System.out.printf("%s recebe %d de dano (após defesa). Vida agora: %d%n", nome, danoFinal, vida);
    }

    public int getVelocidade() {
        return velocidade + buffVelocidade;
    }

    public int ataqueBasico() {
        return arma != null ? arma.getDano() + buffAtaque : 2 + buffAtaque;
    }

    public void atacar(Personagem alvo) {
        int dano = ataqueBasico();
        System.out.printf("%s ataca %s com dano %d%n", nome, alvo.getNome(), dano);
        alvo.takeDamage(dano);

    }
    public void defender() {
        System.out.println(nome + " assume postura defensiva! (+5 DEF por 1 turno)");
        aplicarBuffAtaque(0, 0); // não muda ataque, só para manter consistência
        aplicarBuffVelocidade(0, 0); // sem buff de velocidade
        this.defesa += 5;
        // Defesa extra dura somente 1 turno (reset após atualizarBuffs)
        turnosBuffAtaque = 0;
        turnosBuffVelocidade = 0;
    }

    public void mostrarStatus() {
        System.out.printf(
                "%s - Classe:%s Vida:%d Def:%d Vel:%d(+%d) Mana:%d Arma:%s Dano:%d(+%d)%n",
                nome, classe.name(), vida, defesa, velocidade, buffVelocidade, mana,
                (arma == null ? "Nenhuma" : arma.getNome()),
                arma != null ? arma.getDano() : 0,
                buffAtaque
        );
    }

    /**
     * Implementação das habilidades diferenciadas por classe.
     * Recebe arrays (cópias) de aliados e inimigos.
     */
    public void usarHabilidade(Personagem[] aliados, Personagem[] inimigos) {
        switch (classe) {
            case ARQUEIRO:
                habilidadeArqueiro(aliados, inimigos);
                break;
            case GUERREIRO:
                habilidadeGuerreiro(aliados, inimigos);
                break;
            case MAGO:
                habilidadeMago(aliados, inimigos);
                break;
            case BARDO:
                habilidadeBardo(aliados, inimigos);
                break;
        }
    }

    private void habilidadeArqueiro(Personagem[] aliados, Personagem[] inimigos) {
        if (mana < 8) {
            System.out.println(nome + " tentou usar Flecha Dupla, mas não tem mana suficiente!");
            return;
        }
        mana -= 8;
        System.out.println(nome + " dispara Flecha Dupla!");
        int ataques = 2;
        for (int a = 0; a < ataques; a++) {
            Personagem alvo = null;
            for (Personagem i : inimigos) {
                if (i != null && i.isAlive()) { alvo = i; break; }
            }
            if (alvo == null) return;
            int dano = ataqueBasico();
            System.out.printf("%s acerta %s causando %d de dano!\n", nome, alvo.getNome(), dano);
            alvo.takeDamage(dano);
        }
    }

    private void habilidadeGuerreiro(Personagem[] aliados, Personagem[] inimigos) {
        if (mana < 5) {
            System.out.println(nome + " tentou usar Golpe Poderoso, mas não tem mana suficiente!");
            return;
        }
        mana -= 5;
        Personagem alvo = null;
        for (Personagem i : inimigos) {
            if (i != null && i.isAlive()) {
                if (alvo == null || i.getVida() < alvo.getVida()) {
                    alvo = i;
                }
            }
        }
        if (alvo == null) return;
        int dano = ataqueBasico() + 8;
        System.out.printf("%s usa Golpe Poderoso em %s causando %d de dano!\n", nome, alvo.getNome(), dano);
        alvo.takeDamage(dano);
    }

    private void habilidadeMago(Personagem[] aliados, Personagem[] inimigos) {
        if (mana < 12) {
            System.out.println(nome + " tentou usar Bola de Fogo, mas não tem mana suficiente!");
            return;
        }
        mana -= 12;
        Personagem alvo = null;
        for (Personagem i : inimigos) {
            if (i != null && i.isAlive()) { alvo = i; break; }
        }
        if (alvo == null) return;
        int dano = ataqueBasico() + 15;
        System.out.printf("%s lança Bola de Fogo em %s causando %d de dano!\n", nome, alvo.getNome(), dano);
        alvo.takeDamage(dano);
    }

    private void habilidadeBardo(Personagem[] aliados, Personagem[] inimigos) {
        if (mana < 10) {
            System.out.println(nome + " tentou usar Canção de Inspiração, mas não tem mana suficiente!");
            return;
        }
        mana -= 10;
        System.out.println(nome + " usa Canção de Inspiração! Todos os aliados recebem +5 ATK e +3 VEL por 1 turno!");
        for (Personagem p : aliados) {
            if (p != null && p.isAlive()) {
                p.aplicarBuffAtaque(5, 1);
                p.aplicarBuffVelocidade(3, 1);
            }
        }
    }
}
