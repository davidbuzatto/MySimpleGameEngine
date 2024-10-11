package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de um triângulo em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Triangle implements Drawable {

    public double x1;
    public double y1;
    public double x2;
    public double y2;
    public double x3;
    public double y3;

    public Triangle( double x1, double y1, double x2, double y2, double x3, double y3 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawTriangle( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillTriangle( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Triangle2D[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x1, y1, x2, y2, x3, y3 );
    }

}
