package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de um retângulo com cantos arrendondados em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class RoundRectangle implements Drawable {

    public double x;
    public double y;
    public double width;
    public double height;
    public double roundness;

    public RoundRectangle() {
    }

    public RoundRectangle( double x, double y, double width, double height, double roundness ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.roundness = roundness;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawRoundRectangle( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillRoundRectangle( this, color );
    }

    @Override
    public String toString() {
        return String.format( "RoundRectangle[%.2f, %.2f, %.2f, %.2f, %.2f]", x, y, width, height, roundness );
    }

}
