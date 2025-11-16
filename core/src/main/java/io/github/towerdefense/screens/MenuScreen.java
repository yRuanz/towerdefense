package io.github.towerdefense.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.towerdefense.Main;
import io.github.towerdefense.ui.Button;

public class MenuScreen implements Screen {

    Main game;

    Button playButton;
    Button exitButton;

    public MenuScreen(Main game){
        this.game = game;

        // Texturas
        Texture playInactive = new Texture("botaoPlay_Inactive.png");
        Texture playActive = new Texture("botaoPlay_Active.png");
        Texture exitInactive = new Texture("botaoExit_Inactive.png");
        Texture exitActive = new Texture("botaoExit_Active.png");

        // Centralização
        float centerX = (Gdx.graphics.getWidth() / 2f) - (playInactive.getWidth() / 2f);

        float playY = (Gdx.graphics.getHeight() / 2f) + 50;
        float exitY = (Gdx.graphics.getHeight() / 2f) - 100;

        // Cria os botões reutilizando a classe Button
        playButton = new Button(playInactive, playActive, centerX, playY);
        exitButton = new Button(exitInactive, exitActive, centerX, exitY);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0,0,0,1);

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        // Atualiza hover
        playButton.update(mouseX, mouseY);
        exitButton.update(mouseX, mouseY);

        game.batch.begin();
        playButton.draw(game.batch);
        exitButton.draw(game.batch);
        game.batch.end();

        // Clique -> ir para outra tela
        if (playButton.isClicked()) {
            System.out.println("Play clicado!");
            game.setScreen(new CaractersScreen(game));
        }

        // Clique -> sair do jogo
        if (exitButton.isClicked()) {
            System.out.println("Exit clicado!");
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        playButton.dispose();
        exitButton.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
