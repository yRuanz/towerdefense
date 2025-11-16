package io.github.towerdefense.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.towerdefense.ui.Button;
import io.github.towerdefense.Main;

public class CaractersScreen implements Screen {

    Main game;

    private Sprite Mago, Guerreiro, Bardo, Arqueiro;
    private Texture menuPerson, tMago, tGuerreiro, tBardo, tArqueiro;
    Button playButton;
    Button ReturnButton;

    public CaractersScreen(Main game) {
        this.game = game;

        // Texturas dos botões
        Texture playInactive = new Texture("botaoPlay_Inactive.png");
        Texture playActive = new Texture("botaoPlay_Active.png");
        Texture ReturnInactive = new Texture("botaoReturn_Inactive.png");
        Texture ReturnActive = new Texture("botaoReturn_Active.png");

        // Alinhamento dos botões
        float centerY = 20;
        float playX = 30;
        float exitX = 490;

        playButton = new Button(playInactive, playActive, playX, centerY);
        ReturnButton = new Button(ReturnInactive, ReturnActive, exitX, centerY);

        // Carregar personagens e menu
        menuPerson = new Texture("menu_personagens.png");
        tMago = new Texture("mago_sprite.png");
        tGuerreiro = new Texture("guerreiro_sprite.png");
        tBardo = new Texture("bardo_sprite.png");
        tArqueiro = new Texture("arqueiro_sprite.png");

        Mago = new Sprite(tMago);
        Guerreiro = new Sprite(tGuerreiro);
        Bardo = new Sprite(tBardo);
        Arqueiro = new Sprite(tArqueiro);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        // Atualiza hover dos botões
        playButton.update(mouseX, mouseY);
        ReturnButton.update(mouseX, mouseY);

        game.batch.begin();

        // Desenha botões
        playButton.resizeForScreen(120, 60);
        ReturnButton.resizeForScreen(120, 60);

        playButton.draw(game.batch);
        ReturnButton.draw(game.batch);

        // Desenha menu e personagens
        game.batch.draw(menuPerson, 50, 60);
        game.batch.draw(Mago, 80, 230);
        game.batch.draw(Guerreiro, 220, 230);
        game.batch.draw(Bardo, 350, 230);
        game.batch.draw(Arqueiro, 480, 230);

        game.batch.end();

        // Clique -> mudar de tela
        if (playButton.isClicked()) {
            System.out.println("Play clicado!");
            game.setScreen(new MainGameScreen(game));
        }

        // Clique -> sair
        if (ReturnButton.isClicked()) {
            System.out.println("Return clicado!");
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        playButton.dispose();
        ReturnButton.dispose();

        menuPerson.dispose();
        tMago.dispose();
        tGuerreiro.dispose();
        tBardo.dispose();
        tArqueiro.dispose();
    }
}
