package br.com.davidbuzatto.mysimplegameengine.utils;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;

import br.com.davidbuzatto.mysimplegameengine.geom.Circle;
import br.com.davidbuzatto.mysimplegameengine.geom.CubicCurve;
import br.com.davidbuzatto.mysimplegameengine.geom.Line;
import br.com.davidbuzatto.mysimplegameengine.geom.Point;
import br.com.davidbuzatto.mysimplegameengine.geom.Polygon;
import br.com.davidbuzatto.mysimplegameengine.geom.QuadCurve;
import br.com.davidbuzatto.mysimplegameengine.geom.Rectangle;
import br.com.davidbuzatto.mysimplegameengine.geom.Triangle;
import br.com.davidbuzatto.mysimplegameengine.geom.Vector2;

/**
 * Classe com métodos estáticos utilitários.
 * Várias implementações são baseadas na raylib e em seus módulos
 * (www.raylib.com).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Utils {
    
    private static final double FLT_EPSILON = 2.2204460492503131e-16;
    private static final Random random = new Random();

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
    public static int clamp( int value, int min, int max ) {
        int result = value < min ? min : value;
        if ( result > max ) result = max;
        return result;
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
     * Gera um número pseudo-aleatório entre min (inclusive) e max (inclusive).
     * 
     * @param min Início do intervalo.
     * @param max Fim do intervalo.
     * @return Um número pseudo-aleatório entre min (inclusive) e max (inclusive).
     */
    public static int getRandomValue( int min, int max ) {
        return random.nextInt( min, max + 1 );
    }

    /**
     * Configura a semente aleatória do gerador de números aleatórios.
     * 
     * @param seed A semente a ser utilizada.
     */
    public static void setRandomSeed( long seed ) {
        random.setSeed( seed );
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
     * Cria um vetor 2D com ambos os componentes iguais a 1.0.
     * 
     * @return Um vetor 2D com ambos os componentes iguais a 1.0.
     */
    public static Vector2 vectorOne() {
        return new Vector2( 1.0, 1.0 );
    }

    /**
     * Soma dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor 2D com a soma dos vetores passados.
     */
    public static Vector2 add( final Vector2 v1, final Vector2 v2 ) {
        return new Vector2( v1.x + v2.x, v1.y + v2.y );
    }

    /**
     * Soma um valor a um vetor 2D.
     * 
     * @param v Um vetor.
     * @param value O valor a somar.
     * @return Um novo vetor 2D com os componentes somados ao valor passado.
     */
    public static Vector2 addValue( final Vector2 v, double value ) {
        return new Vector2( v.x + value, v.y + value );
    }

    /**
     * Subtrai dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor 2D com a subtração dos vetores passados.
     */
    public static Vector2 subtract( final Vector2 v1, final Vector2 v2 ) {
        return new Vector2( v1.x - v2.x, v1.y - v2.y );
    }

    /**
     * Subtrai um valor de um vetor 2D.
     * 
     * @param v Um vetor.
     * @param value O valor a subtrair.
     * @return Um novo vetor 2D com os componentes subtraídos do valor passado.
     */
    public static Vector2 subtractValue( final Vector2 v, double value ) {
        return new Vector2( v.x - value, v.y - value );
    }

    /**
     * Calcula o comprimento de um vetor 2D.
     * 
     * @param v Um vetor.
     * @return O comprimento do vetor passado.
     */
    public static double length( final Vector2 v ) {
        return Math.sqrt( v.x * v.x + v.y * v.y );
    }

    /**
     * Calcula o produto escalar entre dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return O produto escalar entre os vetores passados.
     */
    public static double dotProduct( final Vector2 v1, final Vector2 v2 ) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    /**
     * Calcula a distância entre dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return A distância entre os vetores passados.
     */
    public static double distance( final Vector2 v1, final Vector2 v2 ) {
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
    public static double angle( final Vector2 v1, final Vector2 v2 ) {

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
    public static Vector2 scale( final Vector2 v, double scale ) {
        return new Vector2( v.x * scale, v.y * scale );
    }

    /**
     * Multiplica dois vetores 2D.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor com o resultado da multiplicação dos vetores passados.
     */
    public static Vector2 multiply( final Vector2 v1, final Vector2 v2 ) {
        return new Vector2( v1.x * v2.x, v1.y * v2.y );
    }

    /**
     * Nega um vetor 2D.
     * 
     * @param v Um vetor.
     * @return Um novo vetor com a negação do vetor passado.
     */
    public static Vector2 negate( final Vector2 v ) {
        return new Vector2( -v.x, -v.y );
    }

    /**
     * Divide dois vetores.
     * 
     * @param v1 Um vetor.
     * @param v2 Outro vetor.
     * @return Um novo vetor com o resultado da divisão dos vetores passados.
     */
    public static Vector2 divide( final Vector2 v1, final Vector2 v2 ) {
        return new Vector2( v1.x / v2.x, v1.y / v2.y );
    }

    /**
     * Normaliza um vetor 2D.
     * 
     * @param v Um vetor.
     * @return Um novo vetor 2D normalizado.
     */
    public static Vector2 normalize( final Vector2 v ) {

        Vector2 result = new Vector2();
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
    public static Vector2 lerp( final Vector2 start, final Vector2 end, double amount ) {
        double x = start.x + ( end.x - start.x ) * amount;
        double y = start.y + ( end.y - start.y ) * amount;
        return new Vector2( x, y );
    }

    /**
     * Calcula um vetor 2D refletido pela normal.
     * 
     * @param v Um vetor.
     * @param normal Vetor normal.
     * @return Um novo vetor refletido.
     */
    public static Vector2 reflect( final Vector2 v, final Vector2 normal ) {

        Vector2 result = new Vector2();

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
    public static Vector2 min( final Vector2 v1, final Vector2 v2 ) {

        Vector2 result = new Vector2();

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
    public static Vector2 max( final Vector2 v1, final Vector2 v2 ) {

        Vector2 result = new Vector2();

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
    public static Vector2 rotate( final Vector2 v, double angle ) {

        Vector2 result = new Vector2();

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
    public static Vector2 moveTowards( final Vector2 v, final Vector2 target, double maxDistance ) {

        Vector2 result = new Vector2();

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
    public static Vector2 invert( final Vector2 v ) {
        return new Vector2( 1.0 / v.x, 1.0 / v.y );
    }

    /**
     * Limita um vetor entre dois vetores 2D.
     * 
     * @param v O vetor.
     * @param min O vetor mínimo.
     * @param max O vetor máximo.
     * @return Um novo vetor fixado entre o vetor mínimo e o vetor máximo.
     */
    public static Vector2 clamp( final Vector2 v, final Vector2 min, final Vector2 max ) {

        Vector2 result = new Vector2();

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
    public static Vector2 clampValue( final Vector2 v, double min, double max ) {

        Vector2 result = new Vector2( v.x, v.y );

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
    public static Point vectorToPoint( Vector2 v ) {
        return new Point( v.x, v.y );
    }

    /**
     * Cria um vetor 2D usando um ponto 2D.
     * 
     * @param p Um ponto 2D.
     * @return Um vetor 2D.
     */
    public static Vector2 pointToVector( Point p ) {
        return new Vector2( p.x, p.y );
    }

    /**
     * Ontém a coordenada do mouse de um evento do mouse e gera um ponto.
     * 
     * @param e O evento do mouse.
     * @return O ponto onde ocorreu o evento do mouse.
     */
    public static Point mouseEventPositionToPoint( MouseEvent e ) {
        return new Point( e.getX(), e.getY() );
    }

    /**
     * Ontém a coordenada do mouse de um evento do mouse e gera um vetor.
     * 
     * @param e O evento do mouse.
     * @return O vetor onde ocorreu o evento do mouse.
     */
    public static Vector2 mouseEventPositionToVector( MouseEvent e ) {
        return new Vector2( e.getX(), e.getY() );
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( double p1x, double p1y, double p2x, double p2y, double amount ) {

        double x = p1x * ( 1.0 - amount ) + p2x * amount;
        double y = p1y * ( 1.0 - amount ) + p2y * amount;

        return new Point( x, y );
        
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param p1 ponto inicial da linha.
     * @param p2 ponto final da linha.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( Point p1, Point p2, double amount ) {
        return getPointAtLine( p1.x, p1.y, p2.x, p2.y, amount )        ;
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param v1 vetor do ponto inicial da linha.
     * @param v2 vetor do ponto final da linha.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( Vector2 v1, Vector2 v2, double amount ) {
        return getPointAtLine( v1.x, v1.y, v2.x, v2.y, amount )        ;
    }

    /**
     * Obtém um ponto dentro de uma linha.
     * 
     * @param line uma linha.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da linha.
     */
    public static Point getPointAtLine( Line line, double amount ) {
        return getPointAtLine( line.x1, line.y1, line.x2, line.y2, amount );
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
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, double amount ) {

        double a = Math.pow( 1.0 - amount, 2 );
        double b = 2.0 * ( 1.0 - amount ) * amount;
        double c = Math.pow( amount, 2 );

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
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( Point p1, Point c, Point p2, double amount ) {
        return getPointAtQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, amount );
    }

    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param v1 vetor do ponto inicial da curva.
     * @param c vetor do ponto de controle da curva.
     * @param v2 vetor do ponto final da curva.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( Vector2 v1, Vector2 c, Vector2 v2, double amount ) {
        return getPointAtQuadCurve( v1.x, v1.y, c.x, c.y, v2.x, v2.y, amount );
    }
    
    /**
     * Obtém um ponto dentro de uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve uma curva Bézier quadrática.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtQuadCurve( QuadCurve quadCurve, double amount ) {
        return getPointAtQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, amount );
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
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, double amount ) {

        double a = Math.pow( 1.0 - amount, 3 );
        double b = 3.0 * Math.pow( 1.0 - amount, 2 ) * amount;
        double c = 3.0 * ( 1.0 - amount ) * Math.pow( amount, 2 );
        double d = Math.pow( amount, 3 );

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
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( Point p1, Point c1, Point c2, Point p2, double amount ) {
        return getPointAtCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, amount );
    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param v1 vetor do ponto inicial da curva.
     * @param c1 vetor do primeiro ponto de controle da curva.
     * @param c2 vetor do segundo ponto de controle da curva.
     * @param v2 vetor do ponto final da curva.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( Vector2 v1, Vector2 c1, Vector2 c2, Vector2 v2, double amount ) {
        return getPointAtCubicCurve( v1.x, v1.y, c1.x, c1.y, c2.x, c2.y, v2.x, v2.y, amount );
    }

    /**
     * Obtém um ponto dentro de uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve uma curva Bézier cúbica.
     * @param amount Um valor de 0 a 1 que representa a posição, em porcentagem, do ponto desejado.
     * @return O ponto dentro da curva.
     */
    public static Point getPointAtCubicCurve( CubicCurve cubicCurve, double amount ) {
        return getPointAtCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, amount );
    }

    /**
     * Verifica se dois retângulos colidiram.
     * 
     * @param rec1 Um retângulo.
     * @param rec2 Outro retângulo.
     * @return Verdadeiro caso os retângulos tenham colidido, falso caso contrário.
     */
    public static boolean checkCollisionRectangles( Rectangle rec1, Rectangle rec2 ) {
        return ( 
            ( rec1.x < ( rec2.x + rec2.width ) &&  ( rec1.x + rec1.width ) > rec2.x ) &&
            ( rec1.y < ( rec2.y + rec2.height ) && ( rec1.y + rec1.height ) > rec2.y )
        );
    }

    /**
     * Verifica se dois círculos definidos por pontos e raios colidiram.
     * 
     * @param center1 Ponto do centro do primeiro círculo.
     * @param radius1 Raio do primeiro círculo.
     * @param center2 Ponto do centro do segundo círculo.
     * @param radius2 Raio do segundo círculo.
     * @return Verdadeiro caso os círculos tenham colidido, falso caso contrário.
     */
    public static boolean checkCollisionCircles( Point center1, double radius1, Point center2, double radius2 ) {
        
        double dx = center2.x - center1.x;
        double dy = center2.y - center1.y;
    
        double distanceSquared = dx * dx + dy * dy;
        double radiusSum = radius1 + radius2;
    
        return distanceSquared <= radiusSum * radiusSum;

    }

    /**
     * Verifica se dois círculos definidos por vetores e raios colidiram.
     * 
     * @param center1 Vetor do centro do primeiro círculo.
     * @param radius1 Raio do primeiro círculo.
     * @param center2 Vetor do centro do segundo círculo.
     * @param radius2 Raio do segundo círculo.
     * @return Verdadeiro caso os círculos tenham colidido, falso caso contrário.
     */
    public static boolean checkCollisionCircles( Vector2 center1, double radius1, Vector2 center2, double radius2 ) {
        return checkCollisionCircles( vectorToPoint( center1 ), radius1, vectorToPoint( center2 ), radius2 );

    }

    /**
     * Verifica se dois círculos colidiram.
     * 
     * @param circle1 Um círculo.
     * @param circle2 Outro círculo.
     * @return Verdadeiro caso os círculos tenham colidido, falso caso contrário.
     */
    public static boolean checkCollisionCircles( Circle circle1, Circle circle2 ) {
        return checkCollisionCircles( 
            new Point( circle1.x, circle1.y ), circle1.radius,
            new Point( circle2.x, circle2.y ), circle2.radius
        );
    }
    
    /**
     * Verifica se um círculo, definido por um ponto e pelo raio, colidiu
     * com uma linha definida por dois pontos.
     * 
     * @param center Ponto do centro do círculo.
     * @param radius Raio do círculo.
     * @param p1 Ponto inicial da linha.
     * @param p2 Ponto final da linha.
     * @return Verdadeiro caso o círculo tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionCircleLine( Point center, double radius, Point p1, Point p2 ) {

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
        } else if ( dotProduct < 0.0 ) {
            dotProduct = 0.0;
        }

        double dx2 = ( p1.x - ( dotProduct * ( dx ) ) ) - center.x;
        double dy2 = ( p1.y - ( dotProduct * ( dy ) ) ) - center.y;
        double distanceSQ = ( dx2 * dx2 ) + ( dy2 * dy2 );

        return distanceSQ <= radius * radius;

    }

    /**
     * Verifica se um círculo, definido por um ponto e pelo raio, colidiu
     * com uma linha definida por dois vetores.
     * 
     * @param center Ponto do centro do círculo.
     * @param radius Raio do círculo.
     * @param p1 Vetor inicial da linha.
     * @param p2 Vetor final da linha.
     * @return Verdadeiro caso o círculo tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionCircleLine( Point center, double radius, Vector2 p1, Vector2 p2 ) {
        return checkCollisionCircleLine( center, radius, vectorToPoint( p1 ), vectorToPoint( p2 ) );
    }

    /**
     * Verifica se um círculo, definido por um vetor e pelo raio, colidiu
     * com uma linha definida por dois pontos.
     * 
     * @param center Vetor do centro do círculo.
     * @param radius Raio do círculo.
     * @param p1 Ponto inicial da linha.
     * @param p2 Ponto final da linha.
     * @return Verdadeiro caso o círculo tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionCircleLine( Vector2 center, double radius, Point p1, Point p2 ) {
        return checkCollisionCircleLine( vectorToPoint( center ), radius, p1, p2 );
    }

    /**
     * Verifica se um círculo, definido por um vetor e pelo raio, colidiu
     * com uma linha definida por dois vetores.
     * 
     * @param center Vetor do centro do círculo.
     * @param radius Raio do círculo.
     * @param p1 Vetor inicial da linha.
     * @param p2 Vetor final da linha.
     * @return Verdadeiro caso o círculo tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionCircleLine( Vector2 center, double radius, Vector2 p1, Vector2 p2 ) {
        return checkCollisionCircleLine( vectorToPoint( center ), radius, vectorToPoint( p1 ), vectorToPoint( p2 ) );
    }

    /**
     * Verifica se um círculo colidiu com uma linha definida por dois pontos.
     * 
     * @param circle O círculo.
     * @param p1 Ponto inicial da linha.
     * @param p2 Ponto final da linha.
     * @return Verdadeiro caso o círculo tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionCircleLine( Circle circle, Point p1, Point p2 ) {
        return checkCollisionCircleLine( new Point( circle.x, circle.y ), circle.radius, p1, p2 );
    }

    /**
     * Verifica se um círculo colidiu com uma linha definida por dois vetores.
     * 
     * @param circle O círculo.
     * @param p1 Vetor inicial da linha.
     * @param p2 Vetor final da linha.
     * @return Verdadeiro caso o círculo tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionCircleLine( Circle circle, Vector2 p1, Vector2 p2 ) {
        return checkCollisionCircleLine( new Point( circle.x, circle.y ), circle.radius, vectorToPoint( p1 ), vectorToPoint( p2 ) );
    }

    /**
     * Verifica se um círculo colidiu com uma linha.
     * 
     * @param circle O círculo.
     * @param line A linha.
     * @return Verdadeiro caso o círculo tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionCircleLine( Circle circle, Line line ) {
        return checkCollisionCircleLine( 
            new Point( circle.x, circle.y ), circle.radius,
            new Point( line.x1, line.y1 ), 
            new Point( line.x2, line.y2 )
        );
    }

    /**
     * Verifica se um círculo, definido por um ponto e pelo raio, colidiu
     * com um retângulo.
     * 
     * @param center Ponto do centro do círculo.
     * @param radius Raio do círculo.
     * @param rec Um retângulo.
     * @return Verdadeiro caso o círculo tenha colidido com o retângulo, falso caso contrário.
     */
    public static boolean checkCollisionCircleRectangle( Point center, double radius, Rectangle rec ) {

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
    
    /**
     * Verifica se um círculo, definido por um vetor e pelo raio, colidiu
     * com um retângulo.
     * 
     * @param center Vetor do centro do círculo.
     * @param radius Raio do círculo.
     * @param rec Um retângulo.
     * @return Verdadeiro caso o círculo tenha colidido com o retângulo, falso caso contrário.
     */
    public static boolean checkCollisionCircleRectangle( Vector2 center, double radius, Rectangle rec ) {
        return checkCollisionCircleRectangle( vectorToPoint( center ), radius, rec );
    }

    /**
     * Verifica se um círculo colidiu com um retângulo.
     * 
     * @param circle O círculo.
     * @param rec O retângulo.
     * @return Verdadeiro caso o círculo tenha colidido com o retângulo, falso caso contrário.
     */
    public static boolean checkCollisionCircleRectangle( Circle circle, Rectangle rec ) {
        return checkCollisionCircleRectangle( new Point( circle.x, circle.y ), circle.radius, rec );
    }

    /**
     * Verifica se um ponto, definido pela coordenada inicial, colidiu com um retângulo.
     * 
     * @param x Coordenada x do ponto.
     * @param y Coordenada y do ponto.
     * @param rec O retângulo.
     * @return Verdadeiro caso o ponto tenha colidido com o retângulo, falso caso contrário.
     */
    public static boolean checkCollisionPointRectangle( double x, double y, Rectangle rec ) {
        return ( 
            ( x >= rec.x ) && 
            ( x < ( rec.x + rec.width ) ) && 
            ( y >= rec.y ) && 
            ( y < ( rec.y + rec.height ) )
        );
    }

    /**
     * Verifica se um ponto colidiu com um retângulo.
     * 
     * @param point O ponto.
     * @param rec O retângulo.
     * @return Verdadeiro caso o ponto tenha colidido com o retângulo, falso caso contrário.
     */
    public static boolean checkCollisionPointRectangle( Point point, Rectangle rec ) {
        return checkCollisionPointRectangle( point.x, point.y, rec );
    }

    /**
     * Verifica se um vetor colidiu com um retângulo.
     * 
     * @param vector O vetor.
     * @param rec O retângulo.
     * @return Verdadeiro caso o ponto tenha colidido com o retângulo, falso caso contrário.
     */
    public static boolean checkCollisionVectorRectangle( Vector2 vector, Rectangle rec ) {
        return checkCollisionPointRectangle( vector.x, vector.y, rec );
    }

    /**
     * Verifica se um ponto colidiu com um círculo.
     * 
     * @param x Coordenada x do ponto.
     * @param y Coordenada y do ponto.
     * @param center Ponto do centro do círculo.
     * @param radius Raio do círculo.
     * @return Verdadeiro caso o ponto tenha colidido com o círculo, falso caso contrário.
     */
    public static boolean checkCollisionPointCircle( double x, double y, Point center, double radius ) {
        double distanceSquared = ( x - center.x ) * ( x - center.x ) + 
                                 ( y - center.y ) * ( y - center.y );
        return distanceSquared <= radius * radius;
    }

    /**
     * Verifica se um ponto colidiu com um círculo.
     * 
     * @param point O ponto.
     * @param center Ponto do centro do círculo.
     * @param radius Raio do círculo.
     * @return Verdadeiro caso o ponto tenha colidido com o círculo, falso caso contrário.
     */
    public static boolean checkCollisionPointCircle( Point point, Point center, double radius ) {
        return checkCollisionPointCircle( point.x, point.y, center, radius );
    }

    /**
     * Verifica se um ponto colidiu com um círculo.
     * 
     * @param point O ponto.
     * @param center Vetor do centro do círculo.
     * @param radius Raio do círculo.
     * @return Verdadeiro caso o ponto tenha colidido com o círculo, falso caso contrário.
     */
    public static boolean checkCollisionPointCircle( Point point, Vector2 center, double radius ) {
        return checkCollisionPointCircle( point.x, point.y, vectorToPoint( center ), radius );
    }

    /**
     * Verifica se um ponto colidiu com um círculo.
     * 
     * @param point O ponto.
     * @param circle O círculo.
     * @return Verdadeiro caso o ponto tenha colidido com o círculo, falso caso contrário.
     */
    public static boolean checkCollisionPointCircle( Point point, Circle circle ) {
        return checkCollisionPointCircle( point.x, point.y, new Point( circle.x, circle.y ), circle.radius );
    }

    /**
     * Verifica se um vetor colidiu com um círculo.
     * 
     * @param vector O vetor.
     * @param center Ponto do centro do círculo.
     * @param radius Raio do círculo.
     * @return Verdadeiro caso o ponto tenha colidido com o círculo, falso caso contrário.
     */
    public static boolean checkCollisionVectorCircle( Vector2 vector, Point center, double radius ) {
        return checkCollisionPointCircle( vector.x, vector.y, center, radius );
    }
    
    /**
     * Verifica se um vetor colidiu com um círculo.
     * 
     * @param vector O vetor.
     * @param center Vetor do centro do círculo.
     * @param radius Raio do círculo.
     * @return Verdadeiro caso o ponto tenha colidido com o círculo, falso caso contrário.
     */
    public static boolean checkCollisionVectorCircle( Vector2 vector, Vector2 center, double radius ) {
        return checkCollisionPointCircle( vector.x, vector.y, vectorToPoint( center ), radius );
    }

    /**
     * Verifica se um vetor colidiu com um círculo.
     * 
     * @param vector O vetor.
     * @param circle O círculo.
     * @return Verdadeiro caso o ponto tenha colidido com o círculo, falso caso contrário.
     */
    public static boolean checkCollisionVectorCircle( Vector2 vector, Circle circle ) {
        return checkCollisionPointCircle( vector.x, vector.y, new Point( circle.x, circle.y ), circle.radius );
    }

    /**
     * Verifica se um ponto colidiu com um triângulo.
     * 
     * @param point O ponto.
     * @param p1 Ponto do primeiro vértice do triângulo.
     * @param p2 Ponto do segundo vértice do triângulo.
     * @param p3 Ponto do terceiro vértice do triângulo.
     * @return Verdadeiro caso o ponto tenha colidido com o triângulo, falso caso contrário.
     */
    public static boolean checkCollisionPointTriangle( Point point, Point p1, Point p2, Point p3 ) {

        double alpha = ((p2.y - p3.y)*(point.x - p3.x) + (p3.x - p2.x)*(point.y - p3.y)) /
                       ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));

        double beta = ((p3.y - p1.y)*(point.x - p3.x) + (p1.x - p3.x)*(point.y - p3.y)) /
                      ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));

        double gamma = 1.0 - alpha - beta;

        return (alpha > 0 ) && ( beta > 0 ) && ( gamma > 0 );

    }

    /**
     * Verifica se um ponto colidiu com um triângulo.
     * 
     * @param point O ponto.
     * @param p1 Vetor do primeiro vértice do triângulo.
     * @param p2 Vetor do segundo vértice do triângulo.
     * @param p3 Vetor do terceiro vértice do triângulo.
     * @return Verdadeiro caso o ponto tenha colidido com o triângulo, falso caso contrário.
     */
    public static boolean checkCollisionPointTriangle( Point point, Vector2 p1, Vector2 p2, Vector2 p3 ) {
        return checkCollisionPointTriangle( point, vectorToPoint( p1 ), vectorToPoint( p2 ), vectorToPoint( p3 ) );
    }

    /**
     * Verifica se um ponto colidiu com um triângulo.
     * 
     * @param point O ponto.
     * @param triangle O triângulo.
     * @return Verdadeiro caso o vetor tenha colidido com o triângulo, falso caso contrário.
     */
    public static boolean checkCollisionPointTriangle( Point point, Triangle triangle ) {
        return checkCollisionPointTriangle( 
            point, 
            new Point( triangle.x1, triangle.y1 ),
            new Point( triangle.x2, triangle.y2 ),
            new Point( triangle.x3, triangle.y3 )
        );
    }

    /**
     * Verifica se um vetor colidiu com um triângulo.
     * 
     * @param vector O vetor.
     * @param p1 Ponto do primeiro vértice do triângulo.
     * @param p2 Ponto do segundo vértice do triângulo.
     * @param p3 Ponto do terceiro vértice do triângulo.
     * @return Verdadeiro caso o vetor tenha colidido com o triângulo, falso caso contrário.
     */
    public static boolean checkCollisionVectorTriangle( Vector2 vector, Point p1, Point p2, Point p3 ) {
        return checkCollisionPointTriangle( vectorToPoint( vector ), p1, p2, p3 );
    }

    /**
     * Verifica se um vetor colidiu com um triângulo.
     * 
     * @param vector O vetor.
     * @param p1 Vetor do primeiro vértice do triângulo.
     * @param p2 Vetor do segundo vértice do triângulo.
     * @param p3 Vetor do terceiro vértice do triângulo.
     * @return Verdadeiro caso o vetor tenha colidido com o triângulo, falso caso contrário.
     */
    public static boolean checkCollisionVectorTriangle( Vector2 vector, Vector2 p1, Vector2 p2, Vector2 p3 ) {
        return checkCollisionPointTriangle( vectorToPoint( vector ), vectorToPoint( p1 ), vectorToPoint( p2 ), vectorToPoint( p3 ) );
    }

    /**
     * Verifica se um vetor colidiu com um triângulo.
     * 
     * @param vector O vetor.
     * @param triangle O triângulo.
     * @return Verdadeiro caso o vetor tenha colidido com o triângulo, falso caso contrário.
     */
    public static boolean checkCollisionPointTriangle( Vector2 vector, Triangle triangle ) {
        return checkCollisionPointTriangle( 
            vectorToPoint( vector ), 
            new Point( triangle.x1, triangle.y1 ),
            new Point( triangle.x2, triangle.y2 ),
            new Point( triangle.x3, triangle.y3 )
        );
    }

    /**
     * Verifica se um ponto colidiu com um polígono regular.
     * 
     * @param point O ponto.
     * @param polygon O polígono.
     * @return Verdadeiro caso o ponto tenha colidido com o polígono, falso caso contrário.
     */
    public static boolean checkCollisionPointPolygon( Point point, Polygon polygon ) {

        boolean inside = false;

        if ( polygon.sides > 2 ) {

            Point[] points = new Point[polygon.sides];
            double angle = 360.0 / polygon.sides;

            for ( int i = 0; i < polygon.sides; i++ ) {
                points[i] = new Point(
                    polygon.x + Math.cos( Math.toRadians( polygon.rotation + angle * i ) ) * polygon.radius,
                    polygon.y + Math.sin( Math.toRadians( polygon.rotation + angle * i ) ) * polygon.radius
                );
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

    /**
     * Verifica se um vetor colidiu com um polígono regular.
     * 
     * @param vector O vetor.
     * @param polygon O polígono.
     * @return Verdadeiro caso o ponto tenha colidido com o polígono, falso caso contrário.
     */
    public static boolean checkCollisionVectorPolygon( Vector2 vector, Polygon polygon ) {
        return checkCollisionPointPolygon( vectorToPoint( vector ), polygon );
    }

    /**
     * Verifica a colisão entre duas linhas.
     * 
     * @param startPos1 Ponto inicial da primeira linha.
     * @param endPos1 Ponto final da segunda linha.
     * @param startPos2 Ponto inicial da segunda linha.
     * @param endPos2 Ponto final da segunda linha.
     * @return Retorna o ponto de colisão caso as linhas tenham se interceptado ou nulo caso contrário.
     */
    public static Point checkCollisionLines( Point startPos1, Point endPos1, Point startPos2, Point endPos2 ) {
        
        boolean collision = false;

        double div = (endPos2.y - startPos2.y)*(endPos1.x - startPos1.x) - (endPos2.x - startPos2.x)*(endPos1.y - startPos1.y);
        double xi = 0;
        double yi = 0;

        if ( Math.abs(div) >= FLT_EPSILON ) {

            collision = true;

            xi = ((startPos2.x - endPos2.x)*(startPos1.x*endPos1.y - startPos1.y*endPos1.x) - (startPos1.x - endPos1.x)*(startPos2.x*endPos2.y - startPos2.y*endPos2.x))/div;
            yi = ((startPos2.y - endPos2.y)*(startPos1.x*endPos1.y - startPos1.y*endPos1.x) - (startPos1.y - endPos1.y)*(startPos2.x*endPos2.y - startPos2.y*endPos2.x))/div;

            if (((Math.abs(startPos1.x - endPos1.x) > FLT_EPSILON) && (xi < Math.min(startPos1.x, endPos1.x) || (xi > Math.max(startPos1.x, endPos1.x)))) ||
                ((Math.abs(startPos2.x - endPos2.x) > FLT_EPSILON) && (xi < Math.min(startPos2.x, endPos2.x) || (xi > Math.max(startPos2.x, endPos2.x)))) ||
                ((Math.abs(startPos1.y - endPos1.y) > FLT_EPSILON) && (yi < Math.min(startPos1.y, endPos1.y) || (yi > Math.max(startPos1.y, endPos1.y)))) ||
                ((Math.abs(startPos2.y - endPos2.y) > FLT_EPSILON) && (yi < Math.min(startPos2.y, endPos2.y) || (yi > Math.max(startPos2.y, endPos2.y))))) {
                collision = false;
            }

        }

        if ( collision ) {
            return new Point( xi, yi );
        }

        return null;

    }

    /**
     * Verifica a colisão entre duas linhas.
     * 
     * @param startPos1 Vetor inicial da primeira linha.
     * @param endPos1 Vetor final da segunda linha.
     * @param startPos2 Vetor inicial da segunda linha.
     * @param endPos2 Vetor final da segunda linha.
     * @return Retorna o ponto de colisão caso as linhas tenham se interceptado ou nulo caso contrário.
     */
    public static Point checkCollisionLines( Vector2 startPos1, Vector2 endPos1, Vector2 startPos2, Vector2 endPos2 ) {
        return checkCollisionLines( vectorToPoint( startPos1 ), vectorToPoint( endPos1 ), vectorToPoint( startPos2 ), vectorToPoint( endPos2 ) );
    }

    /**
     * Verifica a colisão entre duas linhas.
     * 
     * @param line1 Uma linha.
     * @param line2 Outra linha.
     * @return Retorna o ponto de colisão caso as linhas tenham se interceptado ou nulo caso contrário.
     */
    public static Point checkCollisionLines( Line line1, Line line2 ) {
        return checkCollisionLines(
            new Point( line1.x1, line1.y1 ),
            new Point( line1.x2, line1.y2 ),
            new Point( line2.x1, line2.y1 ),
            new Point( line2.x2, line2.y2 )
        );
    }

    /**
     * Verifica a colisão de um ponto com uma linha.
     * 
     * @param point O ponto.
     * @param p1 O ponto inicial da linha.
     * @param p2 O ponto final da linha.
     * @param threshold O limite de proximidade entre o ponto e a linha.
     * @return Verdadeiro caso o ponto tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionPointLine( Point point, Point p1, Point p2, int threshold ) {

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

    /**
     * Verifica a colisão de um ponto com uma linha.
     * 
     * @param point O ponto.
     * @param v1 O vetor inicial da linha.
     * @param v2 O vetor final da linha.
     * @param threshold O limite de proximidade entre o ponto e a linha.
     * @return Verdadeiro caso o ponto tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionPointLine( Point point, Vector2 v1, Vector2 v2, int threshold ) {
        return checkCollisionPointLine( point, vectorToPoint( v1 ), vectorToPoint( v2 ), threshold );
    }

    /**
     * Verifica a colisão de um ponto com uma linha.
     * 
     * @param point O ponto.
     * @param line A linha.
     * @param threshold O limite de proximidade entre o ponto e a linha.
     * @return Verdadeiro caso o ponto tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionPointLine( Point point, Line line, int threshold ) {
        return checkCollisionPointLine( point, new Point( line.x1, line.y1 ), new Point( line.x2, line.y2 ), threshold );
    }

    /**
     * Verifica a colisão de um vetor com uma linha.
     * 
     * @param vector O vetor.
     * @param p1 O ponto inicial da linha.
     * @param p2 O ponto final da linha.
     * @param threshold O limite de proximidade entre o vetor e a linha.
     * @return Verdadeiro caso o vetor tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionVectorLine( Vector2 vector, Point p1, Point p2, int threshold ) {
        return checkCollisionPointLine( vectorToPoint( vector ), p1, p2, threshold );
    }

    /**
     * Verifica a colisão de um vetor com uma linha.
     * 
     * @param vector O vetor.
     * @param v1 O vetor inicial da linha.
     * @param v2 O vetor final da linha.
     * @param threshold O limite de proximidade entre o vetor e a linha.
     * @return Verdadeiro caso o vetor tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionVectorLine( Vector2 vector, Vector2 v1, Vector2 v2, int threshold ) {
        return checkCollisionPointLine( vectorToPoint( vector ), vectorToPoint( v1 ), vectorToPoint( v2 ), threshold );
    }

    /**
     * Verifica a colisão de um vetor com uma linha.
     * 
     * @param vector O vetor.
     * @param linha A linha.
     * @param threshold O limite de proximidade entre o vetor e a linha.
     * @return Verdadeiro caso o vetor tenha colidido com a linha, falso caso contrário.
     */
    public static boolean checkCollisionVectorLine( Vector2 vector, Line line, int threshold ) {
        return checkCollisionPointLine( vectorToPoint( vector ), new Point( line.x1, line.y1 ), new Point( line.x2, line.y2 ), threshold );
    }

    /**
     * Calcula o retângulo da sobreposição entre dois retângulos.
     * 
     * @param rec1 Um retângulo.
     * @param rec2 Outro retângulo.
     * @return O retângulo da sobreposição entre os dois retângulos.
     */
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

    /**
     * Aplica transparência (alpha) na cor.
     * 
     * @param color A cor base.
     * @param alpha A quantidade de transparência entre 0.0 e 1.0
     * @return A nova cor.
     */
    public static Color fade( Color color, double alpha ) {
        return new Color( 
            color.getRed(), color.getGreen(), color.getBlue(), 
            clamp( (int) ( 255 * alpha ), 0, 255 ) );
    }

    /**
     * Obtém os valores HSV (hue/saturation/value - matiz/saturação/valor) de uma
     * cor. Os intervalos são h: [0..360], s: [0..1] e v: [0..1].
     * 
     * @param color Uma cor.
     * @return Os valores HSV na forma de uma array de três elementos. Os valores
     * seguem na ordem.
     */
    public static double[] colorToHSV( Color color ) {
        float[] hsb = Color.RGBtoHSB( color.getRed(), color.getGreen(), color.getBlue(), null );
        return new double[] { hsb[0] * 360, hsb[1], hsb[2] };
    }

    /**
     * Obtém uma cor a par dos valores HSV (hue/saturation/value - matiz/saturação/valor).
     * Os intervalos são h: [0..360], s: [0..1] e v: [0..1].
     * 
     * @param hue matiz [0..360]
     * @param saturation [0..1]
     * @param value [0..1]
     * @return Uma cor com tais parâmetros.
     */
    public static Color colorFromHSV( double hue, double saturation, double value ) {
        return new Color( Color.HSBtoRGB( (float) ( hue / 360.0 ), (float) saturation, (float) value ) );
    }

    /**
     * Multiplica uma cor por uma tonalidade.
     * 
     * @param color A cor base.
     * @param tint A tonalidade.
     * @return Uma nova cor multiplicada pela tonalidade.
     */
    public static Color colorTint( Color color, Color tint ) {
        return new Color( 
            color.getRed() * tint.getRed() / 255,
            color.getGreen() * tint.getGreen() / 255,
            color.getBlue() * tint.getBlue() / 255,
            color.getAlpha() * tint.getAlpha() / 255
        );
    }

    /**
     * Obtém uma cor com correção em relação ao brilho. O fator de brilho vai de 
     * -1.0 a 1.0.
     * 
     * @param color Uma cor.
     * @param factor O fato de brilho de -1.0 a 1.0.
     * @return Uma nova cor corrigida.
     */
    public static Color colorBrightness( Color color, double factor ) {

        if ( factor > 1.0 ) factor = 1.0;
        else if ( factor < -1.0 ) factor = -1.0 ;

        double red = color.getRed();
        double green = color.getGreen();
        double blue = color.getBlue();

        if ( factor < 0.0 ) {
            factor = 1.0 + factor;
            red *= factor;
            green *= factor;
            blue *= factor;
        } else {
            red = ( 255 - red ) * factor + red;
            green = ( 255 - green ) * factor + green;
            blue = ( 255 - blue ) * factor + blue;
        }

        return new Color( (int) red, (int) green, (int) blue, color.getAlpha() );

    }

    /**
     * Obtém uma cor com correção em relação ao contraste. O fator de contraste vai de 
     * -1.0 a 1.0.
     * 
     * @param color Uma cor.
     * @param factor O fato de contraste de -1.0 a 1.0.
     * @return Uma nova cor corrigida.
     */
    public static Color colorContrast( Color color, double contrast ) {

        if ( contrast < -1.0 ) contrast = -1.0;
        else if ( contrast > 1.0 ) contrast = 1.0;

        contrast = ( 1.0 + contrast );
        contrast *= contrast;

        double pR = color.getRed() / 255.0;
        pR -= 0.5;
        pR *= contrast;
        pR += 0.5;
        pR *= 255;
        if ( pR < 0 ) pR = 0;
        else if ( pR > 255 ) pR = 255;

        double pG = color.getGreen() / 255.0;
        pG -= 0.5;
        pG *= contrast;
        pG += 0.5;
        pG *= 255;
        if ( pG < 0 ) pG = 0;
        else if ( pG > 255 ) pG = 255;

        double pB = color.getBlue() / 255.0f;
        pB -= 0.5;
        pB *= contrast;
        pB += 0.5;
        pB *= 255;
        if ( pB < 0 ) pB = 0;
        else if ( pB > 255 ) pB = 255;

        return new Color( (int) pR, (int) pG, (int) pB, color.getAlpha() );

    }

    /**
     * Aplica transparência (alpha) na cor.
     * 
     * @param color A cor base.
     * @param alpha A quantidade de transparência entre 0.0 e 1.0
     * @return A nova cor.
     */
    public static Color colorAlpha( Color color, double alpha ) {
        return fade( color, alpha );
    }

    // Get Color structure from hexadecimal value
    // 0xAARRGGBB
    /**
     * Obtém uma cor a partir de um inteido em hexadecimal na forma 0xAARRGGBB, onde:
     *     AA: canal alfa de 00 a FF;
     *     RR: canal vermelho de 00 a FF;
     *     GG: canal verde de 00 a FF;
     *     BB: canal azul de 00 a FF;
     * 
     * Exemplo:
     *     0xFF00FF00: verde com alfa máximo (sem transparência)
     * 
     * @param hexValue Um valor inteiro em hexadecimal.
     * @return A cor relativa ao inteiro passado.
     */
    public static Color getColor( int hexValue ) {
        return new Color( hexValue, true );
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

}
