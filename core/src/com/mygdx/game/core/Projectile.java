package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    private Vector2 position;
    private Vector2 velocity;
    private TextureRegion textureRegion;

    public Projectile(Vector2 startPosition, float angle) {
        this.position = startPosition;
        velocity = new Vector2();
        velocity.set(400.0f * MathUtils.cosDeg(angle), 400.0f * MathUtils.sinDeg(angle));
        textureRegion = new TextureAtlas(Gdx.files.internal("game.pack")).findRegion("bullet");
    }

    public void update(float dt) {
        // position.x += velocity.x * dt;
        // position.y += velocity.y * dt;
        position.mulAdd(velocity, dt);
    }

    public void render(SpriteBatch batch) {
        batch.draw(textureRegion, position.x - 40, position.y - 40, 40, 40, 80, 80, 1, 1, 0);
    }

    public boolean isInBounds() {
        return !(position.x < -30) && !(position.y < -30) && !(position.x > 1330) && !(position.y > 740);
    }
}
