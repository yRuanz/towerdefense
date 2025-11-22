package io.github.towerdefense.ui;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UIFactory {
    public static TextButton createButton(String text, Skin skin) {
        return new TextButton(text, skin);
    }
    public static Label createLabel(String text, Skin skin) {
        return new Label(text, skin);
    }
}
