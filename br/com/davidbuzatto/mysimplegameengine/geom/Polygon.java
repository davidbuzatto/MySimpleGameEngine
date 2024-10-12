package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de um polígono regular em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Polygon implements Drawable {

    public double x;
    public double y;
    public int sides;
    public double radius;
    public double rotation;

    public Polygon() {
    }

    public Polygon( double x, double y, int sides, double radius, double rotation ) {
        this.x = x;
        this.y = y;
        this.sides = sides;
        this.radius = radius;
        this.rotation = rotation;
    }

    public Polygon( double x, double y, int sides, double radius ) {
        this( x, y, sides, radius, 0.0 );
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawPolygon( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillPolygon( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Polygon[%.2f, %.2f, %d, %.2f, %.2f]", x, y, sides, radius, rotation );
    }

}
