package io.github.towerdefense.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.towerdefense.Main;
import io.github.towerdefense.GameState;
import io.github.towerdefense.logic.Personagem;

import java.util.ArrayList;

public class CharacterScreen extends BaseScreen {

    private ArrayList<Personagem> personagens;
    private int index = 0;

    private Image imgPersonagem;
    private Label nomeLabel;

    private TextButton btnNext, btnPrev, btnSelect;

    public CharacterScreen(Main game) {
        super(game);
    }

    @Override
    protected void createUI() {
        personagens = GameState.I().getPersonagensDisponiveis();

        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(10);

        // Imagem inicial
        Texture t = new Texture(personagens.get(index).getSpritePath());
        imgPersonagem = new Image(t);

        nomeLabel = new Label(personagens.get(index).getNome(), skin);

        btnPrev = new TextButton("<", skin);
        btnNext = new TextButton(">", skin);
        btnSelect = new TextButton("Selecionar", skin);

        root.add(nomeLabel).colspan(3).row();

        root.add(btnPrev).width(60);
        root.add(imgPersonagem).width(150).height(150);
        root.add(btnNext).width(60).row();

        root.add(btnSelect).colspan(3).width(200).height(40);

        stage.addActor(root);
    }

    @Override
    protected void setupListeners() {

        btnNext.addListener(new ClickListener() {
            @Override public void clicked(InputEvent e, float x, float y) {
                index = (index + 1) % personagens.size();
                updateCharacter();
            }
        });

        btnPrev.addListener(new ClickListener() {
            @Override public void clicked(InputEvent e, float x, float y) {
                index = (index - 1 + personagens.size()) % personagens.size();
                updateCharacter();
            }
        });

        btnSelect.addListener(new ClickListener() {
            @Override public void clicked(InputEvent e, float x, float y) {
                GameState.I().setPersonagemSelecionado(personagens.get(index));
                game.setScreen(new GameScreen(game));
            }
        });
    }

    private void updateCharacter() {
        Personagem p = personagens.get(index);

        nomeLabel.setText(p.getNome());

        imgPersonagem.setDrawable(
            new Image(new Texture(p.getSpritePath())).getDrawable()
        );
    }
}
