package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de um círculo em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Circle implements Drawable {

    public double x;
    public double y;
    public double radius;

    public Circle() {
    }

    public Circle( double x, double y, double radius ) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawCircle( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillCircle( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Circle[%.2f, %.2f, %.2f]", x, y, radius );
    }

}
