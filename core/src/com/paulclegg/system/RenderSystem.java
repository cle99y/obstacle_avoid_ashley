package com.paulclegg.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulclegg.common.Mappers;
import com.paulclegg.component.DimensionComponent;
import com.paulclegg.component.PositionComponent;
import com.paulclegg.component.TextureComponent;

/**
 * Created by cle99 on 23/04/2017.
 */

public class RenderSystem extends EntitySystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            TextureComponent.class,
            DimensionComponent.class
    ).get();

    private Viewport viewport;
    private SpriteBatch batch;

    private Array<Entity> renderQueue = new Array<Entity>();

    public RenderSystem( Viewport viewport, SpriteBatch batch ) {
        this.batch = batch;
        this.viewport = viewport;
    }

    @Override
    public void update( float deltaTime ) {
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor( FAMILY );
        renderQueue.addAll( entities.toArray() );

        viewport.apply();
        batch.setProjectionMatrix( viewport.getCamera().combined );
        batch.begin();

        draw();

        batch.end();
        renderQueue.clear();
    }

    private void draw() {
        for ( Entity entity : renderQueue ) {
            PositionComponent position = Mappers.POSITION.get( entity );
            DimensionComponent dimension = Mappers.DIMENSION.get( entity );
            TextureComponent texture = Mappers.TEXTURE.get( entity );

            batch.draw(
                    texture.region,
                    position.x, position.y,
                    dimension.width, dimension.height
            );
        }
    }
}
