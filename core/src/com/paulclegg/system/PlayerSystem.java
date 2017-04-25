package com.paulclegg.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Logger;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.Util.ViewportUtils;
import com.paulclegg.common.Mappers;
import com.paulclegg.component.MovementComponent;
import com.paulclegg.component.PlayerComponent;

/**
 * Created by cle99 on 20/04/2017.
 */

public class PlayerSystem extends IteratingSystem {

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );

    private static final Family FAMILY = Family.all(
            PlayerComponent.class,
            MovementComponent.class
    ).get();

    public PlayerSystem() {
        super( FAMILY );
    }

    @Override
    public void processEntity( Entity entity, float delta ) {
        MovementComponent movement = Mappers.MOVEMENT.get( entity );

        movement.xSpeed = 0;  // default speed

        if ( Gdx.input.isKeyPressed( Input.Keys.RIGHT ) ) {
            movement.xSpeed = GameConfig.MAX_PLAYER_X_SPEED;
        } else if ( Gdx.input.isKeyPressed( ( Input.Keys.LEFT ) ) ) {
            movement.xSpeed = -GameConfig.MAX_PLAYER_X_SPEED;
        }
    }
}
