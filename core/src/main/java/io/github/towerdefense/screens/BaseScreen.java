package io.github.towerdefense.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
        this.game = game;

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        
        // Usa a fonte que você gerou no Hiero
        BitmapFont font = new BitmapFont(Gdx.files.internal("ui/fonts/fonte-pt.fnt"));
        skin.remove("default-font", BitmapFont.class);
        skin.add("default-font", font, BitmapFont.class);

        Gdx.input.setInputProcessor(stage);
        createUI();
        setupListeners();
    }

    protected abstract void createUI();
    protected abstract void setupListeners();

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}