package io.github.towerdefense.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.towerdefense.Main;
import io.github.towerdefense.GameState;
import io.github.towerdefense.logic.Raridade;

public class DifficultyScreen extends BaseScreen {

    private TextButton easy, normal, hard;

    public DifficultyScreen(Main game) { super(game); }

    @Override
    protected void createUI() {
        Table t = new Table();
        t.setFillParent(true);

        Label l = new Label("Escolha a Dificuldade", skin);

        easy = new TextButton("Fácil", skin);
        normal = new TextButton("Normal", skin);
        hard = new TextButton("Difícil", skin);

        t.add(l).padBottom(20);
        t.row();
        t.add(easy).width(200).pad(8).row();
        t.add(normal).width(200).pad(8).row();
        t.add(hard).width(200).pad(8);

        stage.addActor(t);
    }

    @Override
    protected void setupListeners() {
        easy.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                GameState.I().setDificuldade(Raridade.COMUM);
                game.setScreen(new GameScreen(game));
            }
        });
        normal.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                GameState.I().setDificuldade(Raridade.RARO);
                game.setScreen(new GameScreen(game));
            }
        });
        hard.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                GameState.I().setDificuldade(Raridade.EPICO);
                game.setScreen(new GameScreen(game));
            }
        });
    }
}
