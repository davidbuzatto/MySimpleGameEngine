package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;
import br.com.davidbuzatto.mysimplegameengine.utils.Utils;

import java.awt.Color;

/**
 * Classe para representação de um vetor de duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Vector implements Drawable {

    public double x;
    public double y;

    public Vector( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawLine( Utils.vector2DZero(), this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        throw new UnsupportedOperationException( "can'f fill a vector." );
    }

    @Override
    public String toString() {
        return String.format( "Vector[%.2f, %.2f]", x, y );
    }

}
