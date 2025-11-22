package io.github.towerdefense.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.towerdefense.Main;

public abstract class BaseScreen implements Screen {

    protected Main game;
    protected Stage stage;
    protected Skin skin;

    public BaseScreen(Main game) {
        this(game, true);
    }

    public BaseScreen(Main game, boolean autoInit) {
        this.game = game;

        stage = new Stage(new ScreenViewport());
        skin = game.assets.get("ui/uiskin.json", Skin.class);
        
        BitmapFont font = new BitmapFont(Gdx.files.internal("ui/default.fnt"));
        skin.remove("default-font", BitmapFont.class);
        skin.add("default-font", font, BitmapFont.class);

        Gdx.input.setInputProcessor(stage);

        if (autoInit) {
            createUI();
            setupListeners();
        }
    }

    protected abstract void createUI();
    protected abstract void setupListeners();

    @Override
    public void render(float delta) {
        // Limpa a tela completamente antes de desenhar
        Gdx.gl.glClearColor(0, 0, 0, 1); // Fundo preto
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(delta);
        stage.draw();
    }

    @Override 
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override 
    public void dispose() {
        stage.dispose();
    }

    @Override 
    public void hide() {
        stage.clear();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
}