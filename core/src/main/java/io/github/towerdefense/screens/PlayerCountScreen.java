package io.github.towerdefense.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.towerdefense.Main;
import io.github.towerdefense.GameState;

public class PlayerCountScreen extends BaseScreen {

    private TextButton btn1, btn2, btn3, btn4;

    public PlayerCountScreen(Main game) {
        super(game);
    }

    @Override
    protected void createUI() {
        Table t = new Table();
        t.setFillParent(true);

        Label titulo = new Label("Quantos jogadores?", skin);
        Label info = new Label("(Cada jogador escolherá um personagem)", skin);

        btn1 = new TextButton("1 Jogador", skin);
        btn2 = new TextButton("2 Jogadores", skin);
        btn3 = new TextButton("3 Jogadores", skin);
        btn4 = new TextButton("4 Jogadores", skin);

        t.add(titulo).padBottom(10).row();
        t.add(info).padBottom(30).row();
        t.add(btn1).width(200).pad(8).row();
        t.add(btn2).width(200).pad(8).row();
        t.add(btn3).width(200).pad(8).row();
        t.add(btn4).width(200).pad(8);

        stage.addActor(t);
    }

    @Override
    protected void setupListeners() {
        btn1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarSelecao(1);
            }
        });

        btn2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarSelecao(2);
            }
        });

        btn3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarSelecao(3);
            }
        });

        btn4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarSelecao(4);
            }
        });
    }

    private void iniciarSelecao(int numJogadores) {
        GameState.I().setNumeroJogadores(numJogadores);
        stage.clear();
        game.setScreen(new CharacterScreen(game)); // ← VAI DIRETO PARA SELEÇÃO
    }
}