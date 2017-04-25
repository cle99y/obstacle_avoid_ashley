package com.paulclegg.system.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.paulclegg.Util.debug.DebugCameraController;

/**
 * Created by cle99 on 20/04/2017.
 */

public class DebugCameraSystem extends EntitySystem {

    private static final DebugCameraController DEBUG_CAMERA_CONTROLLER = new DebugCameraController();

    // -- attributes --
    private final OrthographicCamera camera;

    // -- constructors --
    public DebugCameraSystem( OrthographicCamera camera, float startX, float startY ) {
        this.camera = camera;
        DEBUG_CAMERA_CONTROLLER.setStartPosition( startX, startY );
    }

    @Override
    public void update( float delta ) {
        DEBUG_CAMERA_CONTROLLER.handleDebugInput( delta );
        DEBUG_CAMERA_CONTROLLER.applyTo( camera );
    }
}
