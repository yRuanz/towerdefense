package io.github.towerdefense.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.towerdefense.Main;
import io.github.towerdefense.GameState;
import io.github.towerdefense.BattleController;
import io.github.towerdefense.logic.Batalha;
import io.github.towerdefense.logic.Personagem;
import io.github.towerdefense.logic.Torre;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends BaseScreen {

    private BattleController controller;

    private Label statusLabel;
    private TextButton btnAtacar;
    private TextButton btnDefender;
    private TextButton btnSkill;
    private TextButton btnPassar;

    private List<TextButton> enemyButtons;
    private int selectedEnemy = -1;

    // Construtor que recebe controller já pronto (usado pelo DifficultyScreen)
    public GameScreen(Main game, BattleController controller) {
        super(game, false);
        this.controller = controller;
        createUI();
        setupListeners();
    }

    // Construtor alternativo (se precisar)
    public GameScreen(Main game) {
        super(game, false);
        initBatalha();
        createUI();
        setupListeners();
    }

    private void initBatalha() {
        List<Personagem> players = GameState.I().createPlayersList();
        List<Personagem> enemies = Torre.gerarAndar(GameState.I().getAndarAtual(), players.size());

        Batalha batalha = new Batalha(players, enemies);
        this.controller = new BattleController(batalha);
        this.controller.start();
    }

    @Override
    protected void createUI() {
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        statusLabel = new Label("Iniciando combate...", skin);
        root.add(statusLabel).padBottom(30);
        root.row();

        Table enemyTable = new Table();
        enemyButtons = new ArrayList<>();

        List<Personagem> enemies = controller.getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            final int idx = i;
            TextButton btn = new TextButton(enemies.get(i).getNome(), skin);

            btn.addListener(new ClickListener() {
                @Override 
                public void clicked(InputEvent event, float x, float y) {
                    selectedEnemy = idx;
                    updateEnemySelection();
                }
            });

            enemyButtons.add(btn);
            enemyTable.add(btn).width(150).pad(4);
        }

        root.add(enemyTable).padBottom(20);
        root.row();

        Table btnTable = new Table();

        btnAtacar = new TextButton("Atacar", skin);
        btnDefender = new TextButton("Defender", skin);
        btnSkill = new TextButton("Habilidade", skin);
        btnPassar = new TextButton("Passar Turno", skin);

        btnTable.add(btnAtacar).width(160).pad(5);
        btnTable.add(btnDefender).width(160).pad(5);
        btnTable.add(btnSkill).width(160).pad(5);
        btnTable.row();
        btnTable.add(btnPassar).colspan(3).width(300).padTop(10);

        root.add(btnTable);
    }

    private void updateEnemySelection() {
        for (int i = 0; i < enemyButtons.size(); i++) {
            if (i == selectedEnemy)
                enemyButtons.get(i).setColor(0, 1, 0, 1);
            else
                enemyButtons.get(i).setColor(1, 1, 1, 1);
        }
    }

    @Override
    protected void setupListeners() {
        btnAtacar.addListener(new ClickListener() {
            @Override 
            public void clicked(InputEvent event, float x, float y) {
                if (!controller.isPlayerTurn()) return;
                if (selectedEnemy < 0) return;
                controller.playerAttack(selectedEnemy);
            }
        });

        btnDefender.addListener(new ClickListener() {
            @Override 
            public void clicked(InputEvent event, float x, float y) {
                if (!controller.isPlayerTurn()) return;
                controller.playerDefend();
            }
        });

        btnSkill.addListener(new ClickListener() {
            @Override 
            public void clicked(InputEvent event, float x, float y) {
                if (!controller.isPlayerTurn()) return;
                controller.playerSkill();
            }
        });

        btnPassar.addListener(new ClickListener() {
            @Override 
            public void clicked(InputEvent event, float x, float y) {
                if (!controller.isPlayerTurn()) return;
                controller.playerPass();
            }
        });
    }

    @Override
    public void render(float delta) {
        controller.update();

        if (selectedEnemy >= controller.getEnemies().size()) {
            selectedEnemy = -1;
            updateEnemySelection();
        }

        boolean playerTurn = controller.isPlayerTurn();

        btnAtacar.setDisabled(!playerTurn);
        btnDefender.setDisabled(!playerTurn);
        btnSkill.setDisabled(!playerTurn);
        btnPassar.setDisabled(!playerTurn);

        if (!playerTurn && selectedEnemy != -1) {
            selectedEnemy = -1;
            updateEnemySelection();
        }

        StringBuilder sb = new StringBuilder();
        Personagem atual = controller.getCurrent();
        sb.append("Turno de: ").append(atual != null ? atual.getNome() : "Ninguém").append("\n\n");

        sb.append("--- Jogadores ---\n");
        for (Personagem p : controller.getPlayers()) {
            sb.append(p.getNome()).append("  HP: ").append(p.getVida()).append("\n");
        }

        sb.append("\n--- Inimigos ---\n");
        for (Personagem e : controller.getEnemies()) {
            sb.append(e.getNome()).append("  HP: ").append(e.getVida()).append("\n");
        }

        for (int i = 0; i < controller.getEnemies().size(); i++) {
            Personagem e = controller.getEnemies().get(i);
            if (e.getVida() <= 0) {
                enemyButtons.get(i).setDisabled(true);
                enemyButtons.get(i).setColor(0.5f, 0.5f, 0.5f, 1);
            }
        }

        statusLabel.setText(sb.toString());
        super.render(delta);
    }
}