package io.github.towerdefense.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.github.towerdefense.Main;
import io.github.towerdefense.GameState;
import io.github.towerdefense.logic.Personagem;

import java.util.ArrayList;

public class CharacterScreen extends BaseScreen {

    private ArrayList<Personagem> personagens;
    private int index = 0;

    private Image imgPersonagem;
    private Label nomePersonagemLabel;
    private Label jogadorLabel;
    private Label infoLabel;
    private Label erroLabel;
    private TextField nomeJogadorField;
    private Texture currentTexture;

    private TextButton btnNext, btnPrev, btnSelect;

    public CharacterScreen(Main game) {
        super(game);
    }

    @Override
    protected void createUI() {
        personagens = GameState.I().getPersonagensDisponiveis();

        if (personagens.isEmpty()) {
            // Não há mais personagens disponíveis
            game.setScreen(new DifficultyScreen(game));
            return;
        }

        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(10);

        // Labels informativos
        int jogadorAtual = GameState.I().getJogadorAtualSelecionando() + 1;
        int totalJogadores = GameState.I().getNumeroJogadores();
        
        jogadorLabel = new Label("Jogador " + jogadorAtual + " de " + totalJogadores, skin);
        infoLabel = new Label("Escolha seu personagem e digite seu nome", skin);
        nomePersonagemLabel = new Label(personagens.get(index).getNome(), skin);

        // Imagem inicial
        currentTexture = new Texture(personagens.get(index).getSpritePath());
        imgPersonagem = new Image(currentTexture);

        // Campo de texto para nome do jogador
        nomeJogadorField = new TextField("", skin);
        nomeJogadorField.setMessageText("Digite seu nome");

        // Label de erro (inicialmente vazia)
        erroLabel = new Label("", skin);
        erroLabel.setColor(1, 0, 0, 1); // Vermelho

        btnPrev = new TextButton("<", skin);
        btnNext = new TextButton(">", skin);
        btnSelect = new TextButton("Confirmar", skin);

        // Layout
        root.add(jogadorLabel).colspan(3).padBottom(5).row();
        root.add(infoLabel).colspan(3).padBottom(20).row();
        root.add(nomePersonagemLabel).colspan(3).padBottom(15).row();

        root.add(btnPrev).width(60);
        root.add(imgPersonagem).width(150).height(150);
        root.add(btnNext).width(60).row();

        root.add(nomeJogadorField).colspan(3).width(300).height(50).padTop(20).row();
        root.add(erroLabel).colspan(3).padBottom(10).row();
        root.add(btnSelect).colspan(3).width(200).height(50);

        stage.addActor(root);
    }

    @Override
    protected void setupListeners() {

        btnNext.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                index = (index + 1) % personagens.size();
                updateCharacter();
            }
        });

        btnPrev.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                index = (index - 1 + personagens.size()) % personagens.size();
                updateCharacter();
            }
        });

        btnSelect.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                String nomeJogador = nomeJogadorField.getText().trim();

                // Validar nome
                if (nomeJogador.isEmpty()) {
                    erroLabel.setText("Por favor, digite seu nome!");
                    return;
                }

                if (nomeJogador.length() < 2) {
                    erroLabel.setText("Nome muito curto! (mínimo 2 caracteres)");
                    return;
                }

                if (nomeJogador.length() > 15) {
                    erroLabel.setText("Nome muito longo! (máximo 15 caracteres)");
                    return;
                }

                // Salvar nome e personagem
                Personagem selecionado = personagens.get(index);
                GameState.I().setNomeJogadorAtual(nomeJogador);
                GameState.I().selecionarPersonagem(selecionado);

                // Verificar se todos já escolheram
                if (GameState.I().todosJogadoresEscolheram()) {
                    stage.clear();
                    game.setScreen(new DifficultyScreen(game));
                } else {
                    // Próximo jogador escolhe
                    game.setScreen(new CharacterScreen(game));
                }
            }
        });
    }

    private void updateCharacter() {
        Personagem p = personagens.get(index);

        // Atualizar nome do personagem
        nomePersonagemLabel.setText(p.getNome());

        // Limpar mensagem de erro
        erroLabel.setText("");

        // IMPORTANTE: Dispose da textura antiga antes de criar nova
        if (currentTexture != null) {
            currentTexture.dispose();
        }

        // Carregar nova textura
        currentTexture = new Texture(p.getSpritePath());
        
        // Atualizar imagem com nova textura
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(currentTexture));
        imgPersonagem.setDrawable(drawable);
        
        // Forçar atualização do tamanho e layout
        imgPersonagem.setSize(150, 150);
        imgPersonagem.invalidate();
    }

    @Override
    public void dispose() {
        super.dispose();
        // Limpar textura ao sair da tela
        if (currentTexture != null) {
            currentTexture.dispose();
        }
    }
}