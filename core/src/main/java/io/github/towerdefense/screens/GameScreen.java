package io.github.towerdefense.screens;

import com.badlogic.gdx.utils.ScreenUtils;
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

/**
 * Tela única do jogo de turnos.
 */
public class GameScreen extends BaseScreen {

    private BattleController controller;

    private Label statusLabel;
    private TextButton btnAtacar;
    private TextButton btnDefender;
    private TextButton btnSkill;
    private TextButton btnPassar;

    // Seleção de inimigos
    private List<TextButton> enemyButtons;
    private int selectedEnemy = -1;

    public GameScreen(Main game) {
        super(game);
        initBatalha();
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

        // --- STATUS ---
        statusLabel = new Label("Iniciando combate...", skin);
        root.add(statusLabel).padBottom(30);
        root.row();

        // --- PAINEL DE INIMIGOS ---
        Table enemyTable = new Table();
        enemyButtons = new ArrayList<>();

        List<Personagem> enemies = controller.getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            final int idx = i;

            TextButton btn = new TextButton(enemies.get(i).getNome(), skin);

            btn.addListener(new ClickListener() {
                @Override public void clicked(InputEvent event, float x, float y) {
                    selectedEnemy = idx;
                    updateEnemySelection();
                }
            });

            enemyButtons.add(btn);
            enemyTable.add(btn).width(150).pad(4);
        }

        root.add(enemyTable).padBottom(20);
        root.row();

        // --- BOTÕES DO JOGADOR ---
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

    /** Atualiza destaque do inimigo selecionado */
    private void updateEnemySelection() {
        for (int i = 0; i < enemyButtons.size(); i++) {
            if (i == selectedEnemy)
                enemyButtons.get(i).setColor(0, 1, 0, 1); // Verde
            else
                enemyButtons.get(i).setColor(1, 1, 1, 1); // Normal
        }
    }

    @Override
    protected void setupListeners() {

        // --- ATACAR ---
        btnAtacar.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {

                if (!controller.isPlayerTurn()) return;
                if (selectedEnemy < 0) return;

                controller.playerAttack(selectedEnemy);
            }
        });

        // --- DEFENDER ---
        btnDefender.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                if (!controller.isPlayerTurn()) return;
                controller.playerDefend();
            }
        });

        // --- SKILL ---
        btnSkill.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                if (!controller.isPlayerTurn()) return;
                controller.playerSkill();
            }
        });

        // --- PASSAR ---
        btnPassar.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                if (!controller.isPlayerTurn()) return;
                controller.playerPass();
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        controller.update();

    // Resetar seleção se inimigo morreu
    if (selectedEnemy >= controller.getEnemies().size()) {
        selectedEnemy = -1;
        updateEnemySelection();
    }


        // Atualizar interface
        boolean playerTurn = controller.isPlayerTurn();

        btnAtacar.setDisabled(!playerTurn);
        btnDefender.setDisabled(!playerTurn);
        btnSkill.setDisabled(!playerTurn);
        btnPassar.setDisabled(!playerTurn);

        if (!playerTurn && selectedEnemy != -1) {
            selectedEnemy = -1;
            updateEnemySelection();
        }

        // HUD
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

    // Atualizar cor ou remover botão de inimigo morto
    for (int i = 0; i < controller.getEnemies().size(); i++) {
        Personagem e = controller.getEnemies().get(i);

        if (e.getVida() <= 0) {
            enemyButtons.get(i).setDisabled(true);
            enemyButtons.get(i).setColor(0.5f, 0.5f, 0.5f, 1); // cinza
        }
    }


        statusLabel.setText(sb.toString());

        super.render(delta);
    }
}
