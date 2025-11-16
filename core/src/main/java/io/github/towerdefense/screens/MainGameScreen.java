package io.github.towerdefense.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.towerdefense.Main;
import io.github.towerdefense.ui.Button;

import io.github.towerdefense.Main;
public class MainGameScreen implements Screen{

Main game;

    private Texture mapaJogo;

    public MainGameScreen(Main game){
        this.game = game;

        mapaJogo = new Texture("map.png");

    }

    @Override    
    public void show() {
    }

    @Override
    public void render(float delta){
    ScreenUtils.clear(0, 0, 0, 1);

        game.batch.begin();
        game.batch.draw(mapaJogo, 0, 0, 640,480 );
        game.batch.end();
    }

    @Override
    public void resize(int width,int height){

    }
    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
    // batch.dispose();
    // menuPerson.dispose();
    // tMago.dispose();
    // tGuerreiro.dispose();
    // tBardo.dispose();
    // tArqueiro.dispose();

}