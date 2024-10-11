package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de um ponto em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Point implements Drawable {

    public double x;
    public double y;

    public Point( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawPixel( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.drawPixel( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Point2D[%.2f, %.2f]", x, y );
    }

}
