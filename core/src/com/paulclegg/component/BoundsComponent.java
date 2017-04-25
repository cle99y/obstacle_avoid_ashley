package com.paulclegg.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Logger;
import com.paulclegg.Util.ViewportUtils;

/**
 * Created by cle99 on 20/04/2017.
 */

public class BoundsComponent implements Component {

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );

    public Circle bounds = new Circle();
}
