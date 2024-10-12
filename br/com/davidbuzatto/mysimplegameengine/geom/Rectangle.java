package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de um retângulo em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Rectangle implements Drawable {

    public double x;
    public double y;
    public double width;
    public double height;

    public Rectangle() {
    }

    public Rectangle( double x, double y, double width, double height ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawRectangle( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillRectangle( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Rectangle[%.2f, %.2f, %.2f, %.2f]", x, y, width, height );
    }

}
