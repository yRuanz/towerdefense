package io.github.towerdefense.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

public class Button {

    private Texture inactive;
    private Texture active;

    private float x, y;
    private Rectangle bounds;

    private boolean hovered = false;

    public int width, height;

    public Button(Texture inactive, Texture active, float x, float y) {
        this.inactive = inactive;
        this.active = active;
        this.x = x;
        this.y = y;

        this.width = inactive.getWidth();
        this.height = inactive.getHeight();

        bounds = new Rectangle(x, y, width, height);
    }

    public void update(float mouseX, float mouseY) {
        hovered = bounds.contains(mouseX, mouseY);
    }

    public boolean isHovered() {
        return hovered;
    }

    public boolean isClicked() {
        return hovered && Gdx.input.justTouched();
    }

    public void draw(SpriteBatch batch) {
        Texture tex = hovered ? active : inactive;
        batch.draw(tex, x, y, width, height);
    }

    public void dispose() {
        inactive.dispose();
        active.dispose();
    }

    private void applySize(int width, int height) {
        this.width = width;
        this.height = height;
        bounds.setSize(width, height);
    }

    public void resizeForScreen(int width, int height) {
        applySize(width, height);
    }

    public float getX() { return x; }
    public float getY() { return y; }
}
