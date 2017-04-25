package com.paulclegg.system.debug;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulclegg.common.Mappers;
import com.paulclegg.component.BoundsComponent;

/**
 * Created by cle99 on 20/04/2017.
 */

public class DebugRenderSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all( BoundsComponent.class ).get();

    private final Viewport viewport;
    private final ShapeRenderer renderer;

    public DebugRenderSystem( Viewport viewport, ShapeRenderer renderer ) {
        super( FAMILY );
        this.viewport = viewport;
        this.renderer = renderer;
    }

    @Override
    public void update( float delta ) {

        Color oldColor = renderer.getColor();

        viewport.apply();
        renderer.setProjectionMatrix( viewport.getCamera().combined );
        renderer.begin( ShapeRenderer.ShapeType.Line );
        renderer.setColor( Color.SKY );

        super.update( delta );

        renderer.end();
        renderer.setColor( oldColor );
    }

    @Override
    public void processEntity( Entity entity, float delta ) {

        BoundsComponent bc = Mappers.BOUNDS.get( entity );
        renderer.circle( bc.bounds.x, bc.bounds.y, bc.bounds.radius, 30 );

    }
}
