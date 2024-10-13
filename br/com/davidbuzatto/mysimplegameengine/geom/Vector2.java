package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de um vetor de duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Vector2 implements Drawable {

    public double x;
    public double y;

    public Vector2() {
    }
    
    public Vector2( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawLine( 0, 0, x, y, color );
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
