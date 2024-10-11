package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de um arco em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Arc implements Drawable {
    
    public double x;
    public double y;
    public double radiusH;
    public double radiusV;
    public double startAngle;
    public double endAngle;

    public Arc( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle ) {
        this.x = x;
        this.y = y;
        this.radiusH = radiusH;
        this.radiusV = radiusV;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    public Arc( double x, double y, double radius, double startAngle, double endAngle ) {
        this( x, y, radius, radius, startAngle, endAngle );
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawArc( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.drawArc( this, color );
    }

    @Override
    public String toString() {
        return String.format( "Arc2D[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x, y, radiusH, radiusV, startAngle, endAngle );
    }

}
