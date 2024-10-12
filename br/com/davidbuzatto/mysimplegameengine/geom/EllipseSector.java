package br.com.davidbuzatto.mysimplegameengine.geom;

import br.com.davidbuzatto.mysimplegameengine.core.Drawable;
import br.com.davidbuzatto.mysimplegameengine.core.Engine;

import java.awt.Color;

/**
 * Classe para representação de um arco em duas dimensões.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class EllipseSector implements Drawable {
    
    public double x;
    public double y;
    public double radiusH;
    public double radiusV;
    public double startAngle;
    public double endAngle;

    public EllipseSector( double x, double y, double radiusH, double radiusV, double startAngle, double endAngle ) {
        this.x = x;
        this.y = y;
        this.radiusH = radiusH;
        this.radiusV = radiusV;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    @Override
    public void draw( Engine engine, Color color ) {
        engine.drawEllipseSector( this, color );
    }

    @Override
    public void fill( Engine engine, Color color ) {
        engine.fillEllipseSector( this, color );
    }

    @Override
    public String toString() {
        return String.format( "EllipseSector[%.2f, %.2f, %.2f, %.2f, %.2f, %.2f]", x, y, radiusH, radiusV, startAngle, endAngle );
    }

}
