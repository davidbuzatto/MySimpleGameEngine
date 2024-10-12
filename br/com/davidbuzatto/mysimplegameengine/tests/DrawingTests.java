package br.com.davidbuzatto.mysimplegameengine.tests;

import br.com.davidbuzatto.mysimplegameengine.core.Engine;
import br.com.davidbuzatto.mysimplegameengine.geom.*;

/**
 * Testes de desenho.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DrawingTests extends Engine {

    private Point point;
    private Vector vector;
    private Line line;
    private Rectangle rectangle;
    private RoundRectangle roundRectangle;
    private Circle circle;
    private Ellipse ellipse;
    private CircleSector circleSector;
    private EllipseSector ellipseSector;
    private Arc arc;
    private Ring ring;
    private Triangle triangle;
    private Polygon polygon;
    private QuadCurve quadCurve;
    private CubicCurve cubicCurve;

    public DrawingTests() {
        super( 800, 600, "Test Window", 60, true );
    }

    @Override
    public void create() {

        point = new Point( 50, 50 );
        vector = new Vector( 30, 30 );
        line = new Line( 60, 60, 100, 100 );
        rectangle = new Rectangle( 50, 120, 50, 100 );
        roundRectangle = new RoundRectangle( 50, 370, 80, 60, 20 );
        circle = new Circle( 250, 70, 30 );
        ellipse = new Ellipse( 250, 160, 60, 30 );
        circleSector = new CircleSector( 250, 220, 30, 0, 130 );
        ellipseSector = new EllipseSector( 250, 280, 60, 30, 0, 130 );
        arc = new Arc( 250, 350, 60, 30, 0, 130 );
        ring = new Ring( 250, 400, 10, 30, 0, 130, 50 );
        triangle = new Triangle( 400, 50, 440, 100, 360, 100 );
        polygon = new Polygon( 400, 160, 5, 35, 0 );
        quadCurve = new QuadCurve( 400, 220, 450, 270, 400, 320 );
        cubicCurve = new CubicCurve( 400, 340, 350, 380, 450, 420, 400, 460 );

    }

    @Override
    public void update() {
    }

    @Override
    public void draw() {

        drawFps( 10, 30 );
        //rotate( 90, getScreenWidth() / 2, getScreenHeight() / 2 );
        //translate( 100, 100 );
        //scale( 2, 2 );

        drawUsingPrimitives();
        drawUsingObjects();

    }

    @SuppressWarnings( "unused" )
    private void drawUsingPrimitives() {

        drawPixel( 50, 50, BLACK );
        drawLine( 60, 60, 100, 100, BLACK );

        fillRectangle( 50, 120, 50, 100, BLUE );
        drawRectangle( 50, 120, 50, 100, BLACK );

        fillRectangle( 50, 120, 50, 100, BLUE );
        drawRectangle( 50, 120, 50, 100, BLACK );

        fillRectanglePro( 50, 240, 50, 100, 50, 240, 15, BLUE );
        drawRectanglePro( 50, 240, 50, 100, 50, 240, 15, BLACK );

        fillRoundRectangle( 50, 370, 80, 60, 20, BLUE );
        drawRoundRectangle( 50, 370, 80, 60, 20, BLACK );

        fillRectangleGradientH( 50, 450, 100, 50, BLUE, GREEN );
        fillRectangleGradientV( 50, 520, 100, 50, BLUE, GREEN );

        fillCircle( 250, 70, 30, MAROON );
        drawCircle( 250, 70, 30, BLACK );

        fillEllipse( 250, 160, 60, 30, MAROON );
        drawEllipse( 250, 160, 60, 30, BLACK );

        fillCircleSector( 250, 220, 30, 0, 130, MAROON );
        drawCircleSector( 250, 220, 30, 0, 130, BLACK );

        fillEllipseSector( 250, 280, 60, 30, 0, 130, MAROON );
        drawEllipseSector( 250, 280, 60, 30, 0, 130, BLACK );

        fillArc( 250, 350, 60, 30, 0, 130, MAROON );
        drawArc( 250, 350, 60, 30, 0, 130, BLACK );

        fillRing( 250, 400, 10, 30, 0, 130, 50, MAROON );
        drawRing( 250, 400, 10, 30, 0, 130, 50, BLACK );

        fillTriangle( 400, 50, 440, 100, 360, 100, ORANGE );
        drawTriangle( 400, 50, 440, 100, 360, 100, BLACK );

        fillPolygon( 400, 160, 5, 35, 0, ORANGE );
        drawPolygon( 400, 160, 5, 35, 0, BLACK );

        fillQuadCurve( 400, 220, 450, 270, 400, 320, ORANGE );
        drawQuadCurve( 400, 220, 450, 270, 400, 320, BLACK );

        fillCubicCurve( 400, 340, 350, 380, 450, 420, 400, 460, ORANGE );
        drawCubicCurve( 400, 340, 350, 380, 450, 420, 400, 460, BLACK );
        
    }

    @SuppressWarnings( "unused" )
    private void drawUsingObjects() {

        point.draw( this, BLACK );
        vector.draw( this, BLACK );

        line.draw( this, BLACK );
        
        rectangle.fill( this, BLUE );
        rectangle.draw( this, BLACK );
        
        roundRectangle.fill( this, BLUE );
        roundRectangle.draw( this, BLACK );
        
        circle.fill( this, MAROON );
        circle.draw( this, BLACK );
        
        ellipse.fill( this, MAROON );
        ellipse.draw( this, BLACK );
        
        circleSector.fill( this, MAROON );
        circleSector.draw( this, BLACK );
        
        ellipseSector.fill( this, MAROON );
        ellipseSector.draw( this, BLACK );
        
        arc.fill( this, MAROON );
        arc.draw( this, BLACK );
        
        ring.fill( this, MAROON );
        ring.draw( this, BLACK );
        
        triangle.fill( this, ORANGE );
        triangle.draw( this, BLACK );
        
        polygon.fill( this, ORANGE );
        polygon.draw( this, BLACK );
        
        quadCurve.fill( this, ORANGE );
        quadCurve.draw( this, BLACK );
        
        cubicCurve.fill( this, ORANGE );
        cubicCurve.draw( this, BLACK );

    }

    public static void main( String[] args ) {
        new DrawingTests();
    }

}