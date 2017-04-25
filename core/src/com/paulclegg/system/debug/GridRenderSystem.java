package com.paulclegg.system.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulclegg.Util.ViewportUtils;

/**
 * Created by cle99 on 19/04/2017.
 */

public class GridRenderSystem extends EntitySystem {

    private final Viewport viewport;
    private final ShapeRenderer renderer;

    public GridRenderSystem( Viewport viewport, ShapeRenderer renderer ) {
        this.viewport = viewport;
        this.renderer = renderer;
    }

    @Override
    public void update( float deltaTime ) {
        // called every frame by engine.update() in GameScreen class
        viewport.apply();
        ViewportUtils.drawGrid( viewport, renderer );
    }
}
