package io.github.towerdefense;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import io.github.towerdefense.screens.MenuScreen;
import io.github.towerdefense.logic.ClassePersonagem;
import io.github.towerdefense.logic.CharacterFactory;

public class Main extends Game {
    public SpriteBatch batch;
    public AssetManager assets;

    @Override
    public void create() {
        batch = new SpriteBatch();
        assets = new AssetManager();

        // carregar assets essenciais
        assets.load("ui/uiskin.json", Skin.class);
        assets.finishLoading();

        // Inicializar personagens disponíveis
        inicializarPersonagens();

        this.setScreen(new MenuScreen(this));
    }

private void inicializarPersonagens() {
    // Limpar e recriar personagens
    GameState.I().resetarSelecao();
    
    // Criar personagens disponíveis (sem nomes, pois serão definidos pelos jogadores)
    GameState.I().addPersonagem(CharacterFactory.createPlayer("Arqueiro", ClassePersonagem.ARQUEIRO));
    GameState.I().addPersonagem(CharacterFactory.createPlayer("Guerreiro", ClassePersonagem.GUERREIRO));
    GameState.I().addPersonagem(CharacterFactory.createPlayer("Mago", ClassePersonagem.MAGO));
    GameState.I().addPersonagem(CharacterFactory.createPlayer("Bardo", ClassePersonagem.BARDO));
}

    @Override
    public void dispose() {
        super.dispose();
        if (batch != null) batch.dispose();
        if (assets != null) assets.dispose();
    }
}