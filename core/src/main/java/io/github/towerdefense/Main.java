package io.github.towerdefense;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import io.github.towerdefense.screens.MenuScreen;

public class Main extends Game {
    public SpriteBatch batch;
    public AssetManager assets;

    @Override
    public void create() {
        batch = new SpriteBatch();
        assets = new AssetManager();

        // carregar assets essenciais (coloque uiskin.json e menu_bg.png em android/assets/)
        assets.load("ui/uiskin.json", Skin.class);
        assets.finishLoading();

        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        if (batch != null) batch.dispose();
        if (assets != null) assets.dispose();
    }
}
