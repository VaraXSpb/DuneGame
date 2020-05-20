package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class DuneGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Tank tank;
    private Ball ball;
    GameHelper gameHelper;

    @Override
    public void create() {
        batch = new SpriteBatch();
        tank = new Tank(200, 200);
        ball = new Ball(500, 500);
        //В идеале нужен список типа Array List и интерфейсы, но для простоты сделал явным способом
        gameHelper = new GameHelper(tank, ball);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(0, 0.4f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        tank.render(batch);
        ball.render(batch);
        batch.end();
    }

    public void update(float dt) {
        tank.update(dt);
        gameHelper.update(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
        tank.dispose();
    }

    private static class Tank {
        private Texture texture;
        private float angle;
        private Vector2 position;

        public Tank(float x, float y) {
            this.position = new Vector2(x, y);
            this.texture = new Texture("tank.png");
        }

        public void render(SpriteBatch batch) {
            batch.draw(texture, position.x - 40, position.y - 40, 40, 40, 80, 80, 1, 1, angle, 0, 0, 80, 80, false, false);
        }

        public void update(float dt) {

            if (position.x <= 45f) {
                position.x = 45f;
            }
            if (position.x >= 1235f) {
                position.x = 1235f;
            }

            if (position.y <= 45f) {
                position.y = 45f;
            }
            if (position.y >= 680f) {
                position.y = 680f;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                angle += 180.0f * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                angle -= 180.0f * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                position.x += 200.0f * MathUtils.cosDeg(angle + 90) * dt;
                position.y += 200.0f * MathUtils.sinDeg(angle + 90) * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                position.x -= 200.0f * MathUtils.cosDeg(angle + 90) * dt;
                position.y -= 200.0f * MathUtils.sinDeg(angle + 90) * dt;
            }
        }

        public void dispose() {
            texture.dispose();
        }
    }

    private static class Ball {
        private Texture texture;
        private float angle;
        private Vector2 position;

        public Ball(float x, float y) {
            this.position = new Vector2(x, y);
            this.texture = new Texture("ball.png");
        }

        public void render(SpriteBatch batch) {
            batch.draw(texture, position.x - 40, position.y - 40, 40, 40, 80, 80, 1, 1, angle, 0, 0, 80, 80, false, false);
        }

        public void update(float dt) {
            position.x = MathUtils.random(1100) + 40;
            position.y = MathUtils.random(600) + 40;
        }

        public void dispose() {
            texture.dispose();
        }
    }

    //В идеале должен принимать коллекцию и делить ее внутри себя на классы. Но времени мало и пока так.
    private static class GameHelper {
        Tank tank;
        Ball ball;

        public GameHelper(Tank tank, Ball ball) {
            this.tank = tank;
            this.ball = ball;
        }

        public void update(float dt) {
            if (Math.abs(tank.position.x - ball.position.x) < 80 && Math.abs(tank.position.y - ball.position.y) < 80) {
                ball.update(dt);
            }
        }
    }
}
