package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;
import br.com.davidbuzatto.mysimplegameengine.utils.MathUtils;

import java.awt.Color;

/**
 * Classe para representação de um vetor de duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Vector2D implements Drawable {

    public double x;
    public double y;

    public Vector2D( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawLine( MathUtils.vector2DZero(), this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.drawLine( MathUtils.vector2DZero(), this, color );
    }

    @Override
    public String toString() {
        return String.format( "Vector2D[%.2f, %.2f]", x, y );
    }

}
