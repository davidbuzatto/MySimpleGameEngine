package br.com.davidbuzatto.mysimplegameengine.utils;

import br.com.davidbuzatto.mysimplegameengine.geom.*;

import java.awt.Color;

/**
 * Classe com métodos estáticos utilitários.
 * As implementações são baseadas na raylib e em seus módulos (www.raylib.com)
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Utils {
    
    private static double FLT_EPSILON = 2.2204460492503131e-16;

    /**
     * Realiza a interpolação linear entre dois valores.
     * 
     * @param start valor inicial.
     * @param end valor final.
     * @param amount quantidade (0 a 1)
     * @return A interpolação linear entre dois valores.
     */
    public static double lerp( double start, double end, double amount ) {
        return start + amount * ( end - start );
    }

    /**
     * Limita um valor entre dois valores.
     * 
     * @param value O valor.
     * @param min O valor mínimo.
     * @param max O valor máximo.
     * @return O valor fixado entre mínimo e máximo.
     */
    public static double clamp( double value, double min, double max ) {
        double result = value < min ? min : value;
        if ( result > max ) result = max;
        return result;
    }    

    /**
     * Normaliza o valor dentro do intervalo fornecido.
     * 
     * @param value O valor.
     * @param start O valor inicial.
     * @param end O valor final.
     * @return O valor normalizado entre o valor inicial e final.
     */
    public static double normalize(double value, double start, double end ) {
        return ( value - start ) / ( end - start );
    }

    /**
     * Remapeia um valor entre um intervalo de entrada e um intervalo de saída.
     * 
     * @param value O valor.
     * @param inputStart O valor inicial do intervalo de entrada.
     * @param inputEnd O valor final do intervalo de entrada.
     * @param outputStart O valor inicial do intervalo de saída.
     * @param outputEnd O valor final do intervalo de saída.
     * @return O valor remapeado.
     */
    public static double remap( double value, double inputStart, double inputEnd, double outputStart, double outputEnd ) {
        return ( value - inputStart ) / ( inputEnd - inputStart ) * ( outputEnd - outputStart ) + outputStart;
    }

    /**
     * Coloca um valor entre um valor mínimo e máximo.
     * 
     * @param value O valor.
     * @param min O valor mínimo.
     * @param max O valor máximo.
     * @return O valor ajustado.
     */
    public static double wrap( double value, double min, double max ) {
        return value - ( max - min ) * Math.floor( ( value - min ) / ( max - min ) );
    }

    /**
     * Realiza a interpolação linear entre dois pontos.
     * 
     * @param start ponto inicial.
     * @param end ponto final.
     * @param amount quantidade (0 a 1)
     * @return Um ponto que representa a interpolação linear entre dois pontos.
     */
    public static Point lerp( Point start, Point end, double amount ) {
        double x = start.x + ( end.x - start.x ) * amount;
        double y = start.y + ( end.y - start.y ) * amount;
        return new Point( x, y );
    }

    /**
     * Cria um vetor 2D com ambos os componentes iguais a 0.0.
     * 
     * @return Um vetor 2D com ambos os componentes iguais a 0.0.
     */
    public static Vector vector2DZero() {
        return new Vector( 0.0, 0.0 );
    }

    /**
     * Cria um vetor 2D com ambos os componentes iguais a 1.0.
     * 
     * @return Um vetor 2D com ambos os componentes iguais a 1.0.
     */
    public static Vector vector2DOne() {
        return new Vector( 1.0, 1.0 );
    }

    /**
     * Soma dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor 2D com a soma dos vetores passados.
     */
    public static Vector add( final Vector v1, final Vector v2 ) {
        return new Vector( v1.x + v2.x, v1.y + v2.y );
    }

    /**
     * Soma um valor a um vetor 2D.
     * 
     * @param v Um vetor.
     * @param value O valor a somar.
     * @return Um novo vetor 2D com os componentes somados ao valor passado.
     */
    public static Vector addValue( final Vector v, double value ) {
        return new Vector( v.x + value, v.y + value );
    }

    /**
     * Subtrai dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor 2D com a subtração dos vetores passados.
     */
    public static Vector subtract( final Vector v1, final Vector v2 ) {
        return new Vector( v1.x - v2.x, v1.y - v2.y );
    }

    /**
     * Subtrai um valor de um vetor 2D.
     * 
     * @param v Um vetor.
     * @param value O valor a subtrair.
     * @return Um novo vetor 2D com os componentes subtraídos do valor passado.
     */
    public static Vector subtractValue( final Vector v, double value ) {
        return new Vector( v.x - value, v.y - value );
    }

    /**
     * Calcula o comprimento de um vetor 2D.
     * 
     * @param v Um vetor.
     * @return O comprimento do vetor passado.
     */
    public static double length( final Vector v ) {
        return Math.sqrt( v.x * v.x + v.y * v.y );
    }

    /**
     * Calcula o produto escalar entre dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return O produto escalar entre os vetores passados.
     */
    public static double dotProduct( final Vector v1, final Vector v2 ) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    /**
     * Calcula a distância entre dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return A distância entre os vetores passados.
     */
    public static double distance( final Vector v1, final Vector v2 ) {
        return Math.sqrt( ( v1.x - v2.x ) * ( v1.x - v2.x ) + ( v1.y - v2.y ) * ( v1.y - v2.y ) );
    }

    /**
     * Calcula o ângulo entre dois vetores 2D, sendo que esse ângulo
     * é calculado a partir do ponto de origem (0, 0).
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return O ângulo entre os dois vetores passados.
     */
    public static double angle( final Vector v1, final Vector v2 ) {

        double dot = v1.x * v2.x + v1.y * v2.y;
        double det = v1.x * v2.y - v1.y * v2.x;

        return Math.atan2( det, dot );

    }

    /**
     * Escalona um vetor 2D, análogo à multiplicação por escalar.
     * 
     * @param v O vetor.
     * @param scale A escala.
     * @return Um novo vetor 2D escalonado.
     */
    public static Vector scale( final Vector v, double scale ) {
        return new Vector( v.x * scale, v.y * scale );
    }

    /**
     * Multiplica dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor com o resultado da multiplicação dos vetores passados.
     */
    public static Vector multiply( final Vector v1, final Vector v2 ) {
        return new Vector( v1.x * v2.x, v1.y * v2.y );
    }

    /**
     * Nega um vetor 2D.
     * 
     * @param v Um vetor.
     * @return Um novo vetor com a negação do vetor passado.
     */
    public static Vector negate( final Vector v ) {
        return new Vector( -v.x, -v.y );
    }

    /**
     * Divide dois vetores.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor com o resultado da divisão dos vetores passados.
     */
    public static Vector divide( final Vector v1, final Vector v2 ) {
        return new Vector( v1.x / v2.x, v1.y / v2.y );
    }

    /**
     * Normaliza um vetor 2D.
     * 
     * @param v Um vetor.
     * @return Um novo vetor 2D normalizado.
     */
    public static Vector normalize( final Vector v ) {

        Vector result = vector2DZero();
        double length = Math.sqrt( v.x * v.x + v.y * v.y );

        if ( length > 0 ) {
            double ilength = 1.0 / length;
            result.x = v.x * ilength;
            result.y = v.y * ilength;
        }

        return result;

    }

    /**
     * Realiza a interpolação linear entre dois vetores.
     * 
     * @param start vetor inicial.
     * @param end vetor final.
     * @param amount quantidade (0 a 1)
     * @return Um vetor que representa a interpolação linear entre dois vetores.
     */
    public static Vector lerp( final Vector start, final Vector end, double amount ) {
        double x = start.x + ( end.x - start.x ) * amount;
        double y = start.y + ( end.y - start.y ) * amount;
        return new Vector( x, y );
    }

    /**
     * Calcula um vetor 2D refletido pela normal.
     * 
     * @param v Um vetor.
     * @param normal Vetor normal.
     * @return Um novo vetor refletido.
     */
    public static Vector reflect( final Vector v, final Vector normal ) {

        Vector result = vector2DZero();

        double dotProduct = ( v.x * normal.x + v.y * normal.y ); // produto escalar

        result.x = v.x - ( 2.0 * normal.x ) * dotProduct;
        result.y = v.y - ( 2.0 * normal.y ) * dotProduct;

        return result;

    }

    /**
     * Obtem um novo vetor 2D com o mínimo dos componentes.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor com o mínimo dos componentes dos vetores passados.
     */
    public static Vector min( final Vector v1, final Vector v2 ) {

        Vector result = vector2DZero();

        result.x = Math.min( v1.x, v2.x );
        result.y = Math.min( v1.y, v2.y );

        return result;

    }

    /**
     * Obtem um novo vetor 2D com o máximo dos componentes.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor com o máximo dos componentes dos vetores passados.
     */
    public static Vector max( final Vector v1, final Vector v2 ) {

        Vector result = vector2DZero();

        result.x = Math.max( v1.x, v2.x );
        result.y = Math.max( v1.y, v2.y );

        return result;

    }

    /**
     * Rotaciona um vetor 2D usando um ângulo (em radianos).
     * 
     * @param v Um vetor.
     * @param angle O ângulo.
     * @return Um novo vetor rotacionado.
     */
    public static Vector rotate( final Vector v, double angle ) {

        Vector result = vector2DZero();

        double cos = Math.cos( angle );
        double sin = Math.sin( angle );

        result.x = v.x * cos - v.y * sin;
        result.y = v.x * sin + v.y * cos;

        return result;

    }

    /**
     * Cria um novo vetor movido em direção a um alvo.
     * 
     * @param v Um vetor.
     * @param target O alvo.
     * @param maxDistance A distância máxima.
     * @return Um novo vetor movido em direção ao alvo.
     */
    public static Vector moveTowards( final Vector v, final Vector target, double maxDistance ) {

        Vector result = vector2DZero();

        double dx = target.x - v.x;
        double dy = target.y - v.y;
        double value = dx * dx + dy * dy;

        if ( ( value == 0 ) || ( ( maxDistance >= 0 ) && ( value <= maxDistance * maxDistance ) ) ) {
            return target;
        }

        double dist = Math.sqrt( value );

        result.x = v.x + dx / dist * maxDistance;
        result.y = v.y + dy / dist * maxDistance;

        return result;

    }

    /**
     * Cria um vetor 2D invertido.
     * 
     * @param v Um vetor.
     * @return Um novo vetor invertido.
     */
    public static Vector invert( final Vector v ) {
        return new Vector( 1.0 / v.x, 1.0 / v.y );
    }

    /**
     * Limita um vetor entre dois vetores 2D.
     * 
     * @param v O vetor.
     * @param min O vetor mínimo.
     * @param max O vetor máximo.
     * @return Um novo vetor fixado entre o vetor mínimo e o vetor máximo.
     */
    public static Vector clamp( final Vector v, final Vector min, final Vector max ) {

        Vector result = vector2DZero();

        result.x = Math.min( max.x, Math.max( min.x, v.x ) );
        result.y = Math.min( max.y, Math.max( min.y, v.y ) );

        return result;

    }

    /**
     * Limita a magnitude de um vetor entre mínimo e máximo.
     * 
     * @param v O vetor.
     * @param min O valor mínimo.
     * @param max O valor máximo.
     * @return o valor fixado da magnitude entre os valores mínimo e máximo.
     */
    public static Vector clampValue( final Vector v, double min, double max ) {

        Vector result = new Vector( v.x, v.y );

        double length = v.x * v.x + v.y * v.y;

        if ( length > 0.0 ) {

            length = Math.sqrt( length );

            double scale = 1;    // By default, 1 as the neutral element.
            if ( length < min ) {
                scale = min / length;
            } else if ( length > max ) {
                scale = max / length;
            }

            result.x = v.x * scale;
            result.y = v.y * scale;

        }

        return result;

    }

    /**
     * Cria um ponto 2D usando um vetor 2D.
     * 
     * @param v Um vetor 2D.
     * @return Um ponto 2D.
     */
    public static Point vectorToPoint( Vector v ) {
        return new Point( v.x, v.y );
    }

    /**
     * Cria um vetor 2D usando um ponto 2D.
     * 
     * @param p Um ponto 2D.
     * @return Um vetor 2D.
     */
    public static Vector pointToVector( Point p ) {
        return new Vector( p.x, p.y );
    }

    /**
     * Realiza a interpolação linear entre duas cores.
     * 
     * @param start cor inicial.
     * @param end cor final.
     * @param amount quantidade (0 a 1)
     * @return Uma cor que representa a interpolação linear entre duas cores pontos.
     */
    public static Color lerp( final Color start, final Color end, double amount ) {
        int r = (int) clamp( lerp( start.getRed(), end.getRed(), amount ), 0, 255 );
        int g = (int) clamp( lerp( start.getGreen(), end.getGreen(), amount ), 0, 255 );
        int b = (int) clamp( lerp( start.getBlue(), end.getBlue(), amount ), 0, 255 );
        int a = (int) clamp( lerp( start.getAlpha(), end.getAlpha(), amount ), 0, 255 );
        return new Color( r, g, b, a );
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( double p1x, double p1y, double p2x, double p2y, double t ) {

        double x = p1x * ( 1.0f - t ) + p2x * t;
        double y = p1y * ( 1.0f - t ) + p2y * t;

        return new Point( x, y );
        
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param p1 ponto final.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( Vector p1, Vector p2, double t ) {
        return getPointAtLine( p1.x, p1.y, p2.x, p2.y, t )        ;
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param p1 ponto final.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( Point p1, Point p2, double t ) {
        return getPointAtLine( p1.x, p1.y, p2.x, p2.y, t )        ;
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param line uma linha.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( Line line, double t ) {
        return getPointAtLine( line.x1, line.y1, line.x2, line.y2, t );
    }

    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param cx coordenada x do ponto de controle.
     * @param cy coordenada y do ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, double t ) {

        double a = Math.pow( 1.0 - t, 2 );
        double b = 2.0 * ( 1.0 - t ) * t;
        double c = Math.pow( t, 2 );

        double x = a * p1x + b * cx + c * p2x;
        double y = a * p1y + b * cy + c * p2y;

        return new Point( x, y );

    }

    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( Vector p1, Vector c, Vector p2, double t ) {
        return getPointAtQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, t );
    }

    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( Point p1, Point c, Point p2, double t ) {
        return getPointAtQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, t );
    }
    
    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve uma curva Bézier quadrática.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( QuadCurve quadCurve, double t ) {
        return getPointAtQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, t );
    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param c1x coordenada x do primeiro ponto de controle.
     * @param c1y coordenada y do primeiro ponto de controle.
     * @param c2x coordenada x do segundo ponto de controle.
     * @param c2y coordenada y do segundo ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, double t ) {

        double a = Math.pow( 1.0 - t, 3 );
        double b = 3.0 * Math.pow( 1.0 - t, 2 ) * t;
        double c = 3.0 * ( 1.0 - t ) * Math.pow( t, 2 );
        double d = Math.pow( t, 3 );

        double x = a * p1x + b * c1x + c * c2x + d * p2x;
        double y = a * p1y + b * c1y + c * c2y + d * p2y;

        return new Point( x, y );

    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( Vector p1, Vector c1, Vector c2, Vector p2, double t ) {
        return getPointAtCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, t );
    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( Point p1, Point c1, Point c2, Point p2, double t ) {
        return getPointAtCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, t );
    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve uma curva Bézier cúbica.
     * @param t Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( CubicCurve cubicCurve, double t ) {
        return getPointAtCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, t );
    }

    public static boolean checkCollisionRectangles( Rectangle rec1, Rectangle rec2 ) {
        return ( 
            ( rec1.x < ( rec2.x + rec2.width ) && 
            ( rec1.x + rec1.width ) > rec2.x ) &&
            ( rec1.y < ( rec2.y + rec2.height ) &&
            ( rec1.y + rec1.height ) > rec2.y )
        );
    }

    public static boolean checkCollisionCircles( Vector center1, double radius1, Vector center2, double radius2 ) {
        
        double dx = center2.x - center1.x;
        double dy = center2.y - center1.y;
    
        double distanceSquared = dx * dx + dy * dy;
        double radiusSum = radius1 + radius2;
    
        return distanceSquared <= radiusSum * radiusSum;

    }

    public static boolean checkCollisionCircles( Circle circle1, Circle circle2 ) {
        return checkCollisionCircles( 
            new Vector( circle1.x, circle1.y ), circle1.radius,
            new Vector( circle2.x, circle2.y ), circle2.radius
        );
    }
    
    public static boolean checkCollisionCircleLine( Vector center, double radius, Vector p1, Vector p2 ) {

        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;

        if ( ( Math.abs( dx ) + Math.abs( dy ) ) <= FLT_EPSILON ) {
            return checkCollisionCircles( p1, 0, center, radius );
        }

        double lengthSQ = ( ( dx * dx ) + ( dy * dy ) );
        double dotProduct = ( ( ( center.x - p1.x ) * ( p2.x - p1.x ) ) + 
                              ( ( center.y - p1.y ) * ( p2.y - p1.y ) ) ) / ( lengthSQ );

        if ( dotProduct > 1.0 ) {
            dotProduct = 1.0;
        } else if ( dotProduct < 0.0f ) {
            dotProduct = 0.0f;
        }

        double dx2 = ( p1.x - ( dotProduct * ( dx ) ) ) - center.x;
        double dy2 = ( p1.y - ( dotProduct * ( dy ) ) ) - center.y;
        double distanceSQ = ( dx2 * dx2 ) + ( dy2 * dy2 );

        return distanceSQ <= radius * radius;

    }

    public static boolean checkCollisionCircleLine( Circle circle, Vector p1, Vector p2 ) {
        return checkCollisionCircleLine( new Vector( circle.x, circle.y ), circle.radius, p1, p2 );
    }

    public static boolean checkCollisionCircleLine( Circle circle, Line line ) {
        return checkCollisionCircleLine( 
            new Vector( circle.x, circle.y ), circle.radius,
            new Vector( line.x1, line.y1 ), 
            new Vector( line.x2, line.y2 )
        );
    }

    public static boolean checkCollisionCircleRectangle( Vector center, double radius, Rectangle rec ) {

        double recCenterX = rec.x + rec.width / 2.0;
        double recCenterY = rec.y + rec.height / 2.0;

        double dx = Math.abs( center.x - recCenterX );
        double dy = Math.abs( center.y - recCenterY );

        if ( dx > ( rec.width / 2.0 + radius ) ) {
            return false;
        }
        if ( dy > ( rec.height / 2.0 + radius ) ) { 
            return false;
        }

        if ( dx <= ( rec.width / 2.0 ) ) { 
            return true;
        }
        if ( dy <= ( rec.height / 2.0 ) ) { 
            return true;
        }

        double cornerDistanceSq = ( dx - rec.width / 2.0 ) * ( dx - rec.width / 2.0 ) +
                                  ( dy - rec.height / 2.0 ) * ( dy - rec.height / 2.0 );

        return cornerDistanceSq <= ( radius * radius );

    }

    public static boolean checkCollisionCircleRectangle( Circle circle, Rectangle rec ) {
        return checkCollisionCircleRectangle( new Vector( circle.x, circle.y ), circle.radius, rec );
    }

    public static boolean checkCollisionPointRectangle( Point point, Rectangle rec ) {
        return ( 
            ( point.x >= rec.x ) && 
            ( point.x < ( rec.x + rec.width ) ) && 
            ( point.y >= rec.y ) && 
            ( point.y < ( rec.y + rec.height ) )
        );
    }

    public static boolean checkCollisionVectorRectangle( Vector vector, Rectangle rec ) {
        return ( 
            ( vector.x >= rec.x ) && 
            ( vector.x < ( rec.x + rec.width ) ) && 
            ( vector.y >= rec.y ) && 
            ( vector.y < ( rec.y + rec.height ) )
        );
    }

    public static boolean checkCollisionPointCircle( Point point, Point center, double radius ) {
        double distanceSquared = ( point.x - center.x ) * ( point.x - center.x ) + 
                                 ( point.y - center.y ) * ( point.y - center.y );
        return distanceSquared <= radius * radius;
    }

    public static boolean checkCollisionPointCircle( Point point, Circle circle ) {
        double distanceSquared = ( point.x - circle.x ) * ( point.x - circle.x ) + 
                                 ( point.y - circle.y ) * ( point.y - circle.y );
        return distanceSquared <= circle.radius * circle.radius;
    }

    public static boolean checkCollisionVectorCircle( Vector vector, Vector center, double radius ) {
        double distanceSquared = ( vector.x - center.x ) * ( vector.x - center.x ) + 
                                 ( vector.y - center.y ) * ( vector.y - center.y );
        return distanceSquared <= radius * radius;
    }

    public static boolean checkCollisionVectorCircle( Vector vector, Circle circle ) {
        double distanceSquared = ( vector.x - circle.x ) * ( vector.x - circle.x ) + 
                                 ( vector.y - circle.y ) * ( vector.y - circle.y );
        return distanceSquared <= circle.radius * circle.radius;
    }

    public static boolean checkCollisionPointTriangle( Point point, Vector p1, Vector p2, Vector p3 ) {

        double alpha = ((p2.y - p3.y)*(point.x - p3.x) + (p3.x - p2.x)*(point.y - p3.y)) /
                       ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));

        double beta = ((p3.y - p1.y)*(point.x - p3.x) + (p1.x - p3.x)*(point.y - p3.y)) /
                      ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));

        double gamma = 1.0 - alpha - beta;

        return (alpha > 0 ) && ( beta > 0 ) && ( gamma > 0 );

    }

    public static boolean checkCollisionVectorTriangle( Vector vector, Vector p1, Vector p2, Vector p3 ) {
        return checkCollisionPointTriangle( vectorToPoint( vector ), p1, p2, p3 );
    }

    public static boolean checkCollisionPointTriangle( Point point, Triangle triangle ) {

        double alpha = ((triangle.y2 - triangle.y3)*(point.x - triangle.x3) + (triangle.x3 - triangle.x2)*(point.y - triangle.y3)) /
                       ((triangle.y2 - triangle.y3)*(triangle.x1 - triangle.x3) + (triangle.x3 - triangle.x2)*(triangle.y1 - triangle.y3));

        double beta = ((triangle.y3 - triangle.y1)*(point.x - triangle.x3) + (triangle.x1 - triangle.x3)*(point.y - triangle.y3)) /
                      ((triangle.y2 - triangle.y3)*(triangle.x1 - triangle.x3) + (triangle.x3 - triangle.x2)*(triangle.y1 - triangle.y3));

        double gamma = 1.0 - alpha - beta;

        return ( alpha > 0 ) && ( beta > 0 ) && ( gamma > 0 );

    }

    public static boolean checkCollisionPointTriangle( Vector point, Triangle triangle ) {
        return checkCollisionPointTriangle( vectorToPoint( point ), triangle );
    }

    public static boolean checkCollisionPointPolygon( Vector point, Polygon polygon ) {

        boolean inside = false;

        if ( polygon.sides > 2 ) {

            Point[] points = new Point[polygon.sides];
            double angle = 360.0 / polygon.sides;

            for ( int i = 0; i < polygon.sides; i++ ) {
                points[i].x = polygon.x + Math.cos( Math.toRadians( polygon.rotation + angle * i ) ) * polygon.radius;
                points[i].y = polygon.y + Math.sin( Math.toRadians( polygon.rotation + angle * i ) ) * polygon.radius;
            }

            for ( int i = 0, j = polygon.sides - 1; i < polygon.sides; j = i++ ) {
                if ( ( points[i].y > point.y ) != ( points[j].y > point.y ) &&
                     ( point.x < ( points[j].x - points[i].x ) * ( point.y - points[i].y ) / ( points[j].y - points[i].y ) + points[i].x ) ) {
                    inside = !inside;
                }
            }

        }

        return inside;

    }

    public static Vector checkCollisionLines( Vector startPos1, Vector endPos1, Vector startPos2, Vector endPos2 ) {
        
        boolean collision = false;

        double div = (endPos2.y - startPos2.y)*(endPos1.x - startPos1.x) - (endPos2.x - startPos2.x)*(endPos1.y - startPos1.y);
        double xi = 0;
        double yi = 0;

        if (Math.abs(div) >= FLT_EPSILON)
        {
            collision = true;

            xi = ((startPos2.x - endPos2.x)*(startPos1.x*endPos1.y - startPos1.y*endPos1.x) - (startPos1.x - endPos1.x)*(startPos2.x*endPos2.y - startPos2.y*endPos2.x))/div;
            yi = ((startPos2.y - endPos2.y)*(startPos1.x*endPos1.y - startPos1.y*endPos1.x) - (startPos1.y - endPos1.y)*(startPos2.x*endPos2.y - startPos2.y*endPos2.x))/div;

            if (((Math.abs(startPos1.x - endPos1.x) > FLT_EPSILON) && (xi < Math.min(startPos1.x, endPos1.x) || (xi > Math.max(startPos1.x, endPos1.x)))) ||
                ((Math.abs(startPos2.x - endPos2.x) > FLT_EPSILON) && (xi < Math.min(startPos2.x, endPos2.x) || (xi > Math.max(startPos2.x, endPos2.x)))) ||
                ((Math.abs(startPos1.y - endPos1.y) > FLT_EPSILON) && (yi < Math.min(startPos1.y, endPos1.y) || (yi > Math.max(startPos1.y, endPos1.y)))) ||
                ((Math.abs(startPos2.y - endPos2.y) > FLT_EPSILON) && (yi < Math.min(startPos2.y, endPos2.y) || (yi > Math.max(startPos2.y, endPos2.y))))) collision = false;

        }

        if ( collision ) {
            return new Vector( xi, yi );
        }

        return null;

    }

    public static boolean checkCollisionPointLine( Vector point, Vector p1, Vector p2, int threshold ) {

        double dxc = point.x - p1.x;
        double dyc = point.y - p1.y;
        double dxl = p2.x - p1.x;
        double dyl = p2.y - p1.y;
        double cross = dxc * dyl - dyc * dxl;

        if ( Math.abs(cross) < ( threshold * Math.max( Math.abs(dxl), Math.abs(dyl) ) ) ) {
            if ( Math.abs( dxl ) >= Math.abs( dyl ) ) {
                return ( dxl > 0 ) ? ( ( p1.x <= point.x ) && ( point.x <= p2.x ) ) : ( ( p2.x <= point.x ) && ( point.x <= p1.x ) );
            }
            return ( dyl > 0 ) ? ( ( p1.y <= point.y ) && ( point.y <= p2.y ) ) : ( ( p2.y <= point.y ) && ( point.y <= p1.y ) );
        }

        return false;

    }

    public static Rectangle getCollisionRectangle( Rectangle rec1, Rectangle rec2 ) {
        
        Rectangle overlap = new Rectangle();

        double left = ( rec1.x > rec2.x ) ? rec1.x : rec2.x;
        double right1 = rec1.x + rec1.width;
        double right2 = rec2.x + rec2.width;
        double right = ( right1 < right2 ) ? right1 : right2;
        double top = ( rec1.y > rec2.y ) ? rec1.y : rec2.y;
        double bottom1 = rec1.y + rec1.height;
        double bottom2 = rec2.y + rec2.height;
        double bottom = ( bottom1 < bottom2 ) ? bottom1 : bottom2;

        if ( ( left < right ) && ( top < bottom ) ) {
            overlap.x = left;
            overlap.y = top;
            overlap.width = right - left;
            overlap.height = bottom - top;
        }

        return overlap;

    }

}
