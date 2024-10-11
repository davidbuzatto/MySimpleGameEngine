package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de uma elipse em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Ellipse implements Drawable {
    
    public double x;
    public double y;
    public double radiusH;
    public double radiusV;

    public Ellipse( double x, double y, double radiusH, double radiusV ) {
        this.x = x;
        this.y = y;
        this.radiusH = radiusH;
        this.radiusV = radiusV;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawEllipse( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillEllipse( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Ellipse2D[%.2f, %.2f, %.2f, %.2f]", x, y, radiusH, radiusV );
    }

}
