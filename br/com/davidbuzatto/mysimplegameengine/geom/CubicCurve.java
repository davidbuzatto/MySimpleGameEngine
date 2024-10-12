package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de uma curva Bézier cúbica.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CubicCurve implements Drawable {
    
    public double x1;
    public double y1;
    public double c1x;
    public double c1y;
    public double c2x;
    public double c2y;
    public double x2;
    public double y2;

    public CubicCurve() {
    }

    public CubicCurve( double x1, double y1, double c1x, double c1y, double c2x, double c2y, double x2, double y2 ) {
        this.x1 = x1;
        this.y1 = y1;
        this.c1x = c1x;
        this.c1y = c1y;
        this.c2x = c2x;
        this.c2y = c2y;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawCubicCurve( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillCubicCurve( this, color );
    }

    @Override
    public String toString() {
        return String.format( "CubicCurve[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x1, y1, c1x, c1y, c2x, c2y, x2, x2 );
    }

}
