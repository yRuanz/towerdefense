package io.github.towerdefense.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.towerdefense.Main;

public class MenuScreen extends BaseScreen {

    private TextButton playButton, exitButton;

    public MenuScreen(Main game) {
        super(game);
    }

    @Override
    protected void createUI() {
        Table t = new Table();
        t.setFillParent(true);

        playButton = new TextButton("Jogar", skin);
        exitButton = new TextButton("Sair", skin);

        t.add(playButton).width(200).pad(10).row();
        t.add(exitButton).width(200).pad(10);

        stage.addActor(t);
    }

    @Override
    protected void setupListeners() {
        playButton.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                stage.dispose();
                game.setScreen(new PlayerCountScreen(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }
}
