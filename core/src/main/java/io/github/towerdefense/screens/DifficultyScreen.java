package io.github.towerdefense.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.towerdefense.Main;
import io.github.towerdefense.GameState;
import io.github.towerdefense.logic.Raridade;
import io.github.towerdefense.BattleController;
import io.github.towerdefense.logic.Batalha;
import io.github.towerdefense.logic.Personagem;
import io.github.towerdefense.logic.Torre;
import java.util.List;

public class DifficultyScreen extends BaseScreen {

    private TextButton easy, normal, hard;

    public DifficultyScreen(Main game) { super(game); }

    @Override
    protected void createUI() {
        Table t = new Table();
        t.setFillParent(true);

        Label l = new Label("Escolha a Dificuldade", skin);

        easy = new TextButton("Facil", skin);
        normal = new TextButton("Normal", skin);
        hard = new TextButton("Dificil", skin);

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
                startGame();
            }
        });
        normal.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                GameState.I().setDificuldade(Raridade.RARO);
                startGame();
            }
        });
        hard.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                GameState.I().setDificuldade(Raridade.EPICO);
                stage.clear();
                startGame();
            }
        });
    }

    private void startGame() {
        List<Personagem> players = GameState.I().createPlayersList();
        List<Personagem> enemies = Torre.gerarAndar(GameState.I().getAndarAtual(), players.size());
        
        Batalha batalha = new Batalha(players, enemies);
        BattleController controller = new BattleController(batalha);
        controller.start();
        
        game.setScreen(new GameScreen(game, controller));
    }
}