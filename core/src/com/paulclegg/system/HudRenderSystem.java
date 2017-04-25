package com.paulclegg.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.Util.ViewportUtils;
import com.paulclegg.common.GameManager;

/**
 * Created by cle99 on 23/04/2017.
 */

public class HudRenderSystem extends EntitySystem {

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );

    private final Viewport hudViewport;
    private final SpriteBatch batch;
    private final BitmapFont font;

    private final GlyphLayout layout = new GlyphLayout();

    public HudRenderSystem( Viewport hudViewport, SpriteBatch batch, BitmapFont font ) {
        this.hudViewport = hudViewport;
        this.batch = batch;
        this.font = font;
    }

    @Override
    public void update( float deltaTime ) {
        hudViewport.apply();
        batch.setProjectionMatrix( hudViewport.getCamera().combined );
        batch.begin();

        draw();

        batch.end();
    }

    private void draw() {

        String livesString = "LIVES: " + GameManager.INSTANCE.getLives();
        layout.setText( font, livesString );
        font.draw(
                batch,
                livesString,
                20f,
                GameConfig.HUD_HEIGHT - layout.height * 2f
        );

        String scoreString = "SCORE: " + GameManager.INSTANCE.getScore();
        layout.setText( font, scoreString );
        font.draw(
                batch,
                scoreString,
                GameConfig.HUD_WIDTH - layout.width - 20f,
                GameConfig.HUD_HEIGHT - layout.height * 2f
        );
    }
}
