package br.com.davidbuzatto.mysimplegameengine.core;

import br.com.davidbuzatto.mysimplegameengine.event.*;
import br.com.davidbuzatto.mysimplegameengine.geom.*;
import br.com.davidbuzatto.mysimplegameengine.utils.Utils;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Engine simples para criação de jogos ou simulações usando Java 2D.
 * Grande parte da sua API é baseada na engine de jogos Raylib (www.raylib.com).
 * 
 * @author Prof. Dr. David Buzatto
 * @copyright Copyright (c) 2024
 */
public abstract class Engine extends JFrame {

    /*
     * painel de desenho onde todas as operações de desenho e de registro
     * de eventos ocorretá.
     */
    private DrawingPanel drawingPanel;

    // referência ao contexto gráfico do painel de desenho.
    private Graphics2D g2d;

    // fonte padrão
    private Font defaultFont;
    private Font defaultFpsFont;

    // contorno padrão
    private BasicStroke defaultStroke;

    // flag que sinaliza o uso da suavização (antialiasing) para o contexto gráfico
    private boolean antialiasing;

    // tempo antes de iniciar os processos de atualização e desenho
    private long timeBefore;

    // tempo depois de realizar os processos de atualização e desenho
    private long timeAfter;

    // tempo a esperar antes de iniciar o próximo ciclo
    private long waitTime;

    // tempo de um frame
    private long frameTime;

    // quadros por segundo
    private int targetFps;

    // quadros por segundo atual
    private int currentFps;

    // tempo esperado baseado na quantidade de quadros por segundo
    private long waitTimeFps;

    // flag para controle de execução da thread de desenho
    private boolean running;

    /**
     * Processa a entrada inicial fornecida pelo usuário e cria
     * e/ou inicializa os objetos/contextos/variáveis do jogo ou simulação.
     * 
     * É executado apenas UMA VEZ.
     */
    public abstract void create();

    /**
     * Atualiza os objetos/contextos/variáveis do jogo ou simulação.
     * 
     * É executado uma vez a cada frame, sempre antes do método de desenho.
     */
    public abstract void update();

    /**
     * Desenha o estado dos objetos/contextos/variáveis do jogo ou simulação.
     * 
     * É executado uma vez a cada frame, sempre após o método de atualização.
     */
    public abstract void draw();

    /**
     * Trata os eventos do mouse.
     * 
     * @param e Objeto que contém os dados do evento que foi capturado.
     * @param met Tipo de evento que foi capturado.
     */
    public void handleMouseEvents( MouseEvent e, MouseEventType met ) {
    }

    /**
     * Trata os eventos relacionaos a roda de rolagem do mouse.
     * 
     * @param e Objeto que contém os dados do evento que foi capturado.
     */
    public void handleMouseWheelEvents( MouseWheelEvent e ) {
    }

    /**
     * Trata os eventos relacionados ao teclado.
     * 
     * @param e Objeto que contém os dados do evento que foi capturado.
     * @param ket Tipo de evento que foi capturado.
     */
    public void handleKeyboardEvents( KeyEvent e, KeyboardEventType ket ) {
    }

    /**
     * Cria uma instância da engine e inicia sua execução.
     * 
     * @param windowWidth Largura da janela.
     * @param windowHeight Altura da janela.
     * @param windowTitle Título de janela.
     * @param antialiasing Flag que indica se deve ou não usar suavização para o desenho no contexto gráfico.
     * @param targetFps A quantidade máxima de frames por segundo que se deseja que o processo de atualização e desenho mantenha.
     */
    public Engine( int windowWidth, int windowHeight, String windowTitle, boolean antialiasing, int targetFps ) {

        if ( windowWidth <= 0 ) {
            throw new IllegalArgumentException( "width must be positive!" );
        }

        if ( windowHeight <= 0 ) {
            throw new IllegalArgumentException( "height must be positive!" );
        }

        if ( targetFps <= 0 ) {
            throw new IllegalArgumentException( "target fps must be positive!" );
        }

        defaultFont = new Font( Font.MONOSPACED, Font.PLAIN, 10 );
        defaultFpsFont = new Font( Font.MONOSPACED, Font.BOLD, 20 );
        defaultStroke = new BasicStroke( 1 );

        this.antialiasing = antialiasing;
        this.targetFps = targetFps;
        waitTimeFps = (long) ( 1000.0 / this.targetFps );   // quanto se espera que cada frame demore

        // cria e configura o painel de desenho
        drawingPanel = new DrawingPanel();
        drawingPanel.setPreferredSize( new Dimension( windowWidth, windowHeight ) );
        drawingPanel.setFocusable( true );
        prepararEventosPainel( drawingPanel );

        // configura a engine
        setTitle( windowTitle );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        add( drawingPanel, BorderLayout.CENTER );
        pack();
        setLocationRelativeTo( null );

        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e ) {
                running = false;
            }
        });

        // inicializa os objetos/contexto/variáveis do jogo atual
        create();

        // inicia o processo de execução do jogo ou simulação
        running = true;
        start();

        setVisible( true );

    }

    private void start() {

        new Thread( () -> {

            while ( running ) {

                timeBefore = System.currentTimeMillis();

                try {
                    SwingUtilities.invokeAndWait( () -> {
                        update();
                        drawingPanel.repaint();
                    });
                } catch ( InterruptedException | InvocationTargetException exc ) {
                    exc.printStackTrace();
                }

                timeAfter = System.currentTimeMillis();

                // quanto um frame demorou?
                frameTime = timeAfter - timeBefore;

                // quanto se deve esperar?
                waitTime = waitTimeFps - frameTime;

                //System.out.printf( "%d %d %d %d\n", timeBefore, timeAfter, frameTime, waitTime );

                // se o tempo a esperar for negativo, quer dizer que não
                // houve tempo suficiente, baseado no tempo esperado
                // para todo o frame ser atualizado e desenhado
                if ( waitTime < 0 ) {
                    waitTime = 0;      // não espera
                }

                // se o tempo do frame é menor que o tempo se que deve esperar,
                // quer dizer que sobrou tempo para executar o frame, ou seja
                // o frame foi atualizado e desenhado em menos tempo do que 
                // é esperado baseado na quantidade de frames por segundo
                if ( frameTime < waitTime ) {
                    frameTime = waitTime;  // o tempo que o frame demorou para executar
                }

                try {
                    Thread.sleep( waitTime );
                } catch ( InterruptedException exc ) {
                    exc.printStackTrace();
                }

            }

        }).start();

    }

    /**
     * Classe inerna que encapsula o processo de desenho.
     */
    private class DrawingPanel extends JPanel {

        @Override
        public void paintComponent( Graphics g ) {

            super.paintComponent( g );
            g2d = (Graphics2D) g.create();

            g2d.setFont( defaultFont );
            g2d.setStroke( defaultStroke );
            
            g2d.clearRect( 0, 0, getWidth(), getHeight() );

            if ( antialiasing ) {
                g2d.setRenderingHint( 
                    RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON );
            }

            draw();
            g2d.dispose();

        }

    }

    /***************************************************************************
     * Métodos de desenho
     **************************************************************************/

    /**
     * Desenha um pixel.
     * 
     * @param posX coordenada x do pixel.
     * @param posY coordenada y do pixel.
     * @param color cor de desenho.
     */
    public void drawPixel( double posX, double posY, Color color ) {
        g2d.setColor( color );
        g2d.draw( new java.awt.geom.Line2D.Double( posX, posY, posX, posY ) );
    }

    /**
     * Desenha um pixel.
     * 
     * @param vector vetor do pixel.
     * @param color cor de desenho.
     */
    public void drawPixel( Vector vector, Color color ) {
        drawPixel( vector.x, vector.y, color );
    }

    /**
     * Desenha um pixel.
     * 
     * @param point ponto do pixel.
     * @param color cor de desenho.
     */
    public void drawPixel( Point point, Color color ) {
        drawPixel( point.x, point.y, color );
    }

    /**
     * Desenha uma linha.
     * 
     * @param startPosX coordenada x do ponto inicial.
     * @param startPosY coordenada y do ponto inicial.
     * @param endPosX coordenada x do ponto final.
     * @param endPosY coordenada y do ponto final.
     * @param color cor de desenho.
     */
    public void drawLine( double startPosX, double startPosY, double endPosX, double endPosY, Color color ) {
        g2d.setColor( color );
        g2d.draw( new java.awt.geom.Line2D.Double( startPosX, startPosY, endPosX, endPosY ) );
    }

    /**
     * Desenha uma linha.
     * 
     * @param startVector vetor inicial.
     * @param endVector vetor final.
     * @param color cor de desenho.
     */
    public void drawLine( Vector startVector, Vector endVector, Color color ) {
        drawLine( startVector.x, startVector.y, endVector.x, endVector.y, color );
    }

    /**
     * Desenha uma linha.
     * 
     * @param startPoint ponto inicial.
     * @param endPoint ponto final.
     * @param color cor de desenho.
     */
    public void drawLine( Point startPoint, Point endPoint, Color color ) {
        drawLine( startPoint.x, startPoint.y, endPoint.x, endPoint.y, color );
    }

    /**
     * Desenha uma linha.
     * 
     * @param line uma linha.
     * @param color cor de desenho.
     */
    public void drawLine( Line line, Color color ) {
        drawLine( line.x1, line.y1, line.x2, line.y2, color );
    }

    /**
     * Desenha um retângulo.
     * 
     * @param posX coordenada x do vértice superior esquerdo do retângulo.
     * @param posY coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param color cor de desenho.
     */
    public void drawRectangle( double posX, double posY, double width, double height, Color color ) {
        g2d.setColor( color );
        g2d.draw( new java.awt.geom.Rectangle2D.Double( posX, posY, width, height ) );
    }

    /**
     * Desenha um retângulo.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color cor de desenho.
     */
    public void drawRectangle( Vector pos, double width, double height, Color color ) {
        drawRectangle( pos.x, pos.y, width, height, color );
    }
    
    /**
     * Desenha um retângulo.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color cor de desenho.
     */
    public void drawRectangle( Point pos, double width, double height, Color color ) {
        drawRectangle( pos.x, pos.y, width, height, color );
    }

    /**
     * Desenha um retângulo.
     * 
     * @param rectangle um retângulo.
     * @param color cor de desenho.
     */
    public void drawRectangle( Rectangle rectangle, Color color ) {
        drawRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, color );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param posX coordenada x do vértice superior esquerdo do retângulo.
     * @param posY coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param color cor de desenho.
     */
    public void fillRectangle( double posX, double posY, double width, double height, Color color ) {
        g2d.setColor( color );
        g2d.fill( new java.awt.geom.Rectangle2D.Double( posX, posY, width, height ) );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color cor de desenho.
     */
    public void fillRectangle( Vector pos, double width, double height, Color color ) {
        fillRectangle( pos.x, pos.y, width, height, color );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color cor de desenho.
     */
    public void fillRectangle( Point pos, double width, double height, Color color ) {
        fillRectangle( pos.x, pos.y, width, height, color );
    }

    /**
     * Pinta um retângulo.
     * 
     * @param rectangle um retângulo.
     * @param color cor de desenho.
     */
    public void fillRectangle( Rectangle rectangle, Color color ) {
        fillRectangle( rectangle.x, rectangle.y, rectangle.width, rectangle.height, color );
    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param posX coordenada x do vértice superior esquerdo do retângulo.
     * @param posY coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param originX coordenada x do pivô da rotação.
     * @param originY coordenada y do pivô da rotação.
     * @param rotation rotação em graus.
     * @param color cor de desenho.
     */
    public void drawRectanglePro( double posX, double posY, double width, double height, double originX, double originY, double rotation, Color color ) {

        Graphics2D gc = (Graphics2D) g2d.create();
        gc.setColor( color );

        gc.rotate( Math.toRadians( -rotation ), originX, originY );
        gc.draw( new java.awt.geom.Rectangle2D.Double( posX, posY, width, height ) );

        gc.dispose();

    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height algura.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus.
     * @param color cor de desenho.
     */
    public void drawRectanglePro( Vector pos, double width, double height, Point origin, double rotation, Color color ) {
        drawRectanglePro( pos.x, pos.y, width, height, origin.x, origin.y, rotation, color );
    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height algura.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus.
     * @param color cor de desenho.
     */
    public void drawRectanglePro( Point pos, double width, double height, Point origin, double rotation, Color color ) {
        drawRectanglePro( pos.x, pos.y, width, height, origin.x, origin.y, rotation, color );
    }

    /**
     * Desenha um retângulo rotacionado.
     * 
     * @param rectangle um retângulo.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus.
     * @param color cor de desenho.
     */
    public void drawRectanglePro( Rectangle rectangle, Point origin, double rotation, Color color ) {
        drawRectanglePro( rectangle.x, rectangle.y, rectangle.width, rectangle.height, origin.x, origin.y, rotation, color );
    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param posX coordenada x do vértice superior esquerdo do retângulo.
     * @param posY coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param originX coordenada x do pivô da rotação.
     * @param originY coordenada y do pivô da rotação.
     * @param rotation rotação em graus.
     * @param color cor de desenho.
     */
    public void fillRectanglePro( double posX, double posY, double width, double height, double originX, double originY, double rotation, Color color ) {

        Graphics2D gc = (Graphics2D) g2d.create();
        gc.setColor( color );

        gc.rotate( Math.toRadians( -rotation ), originX, originY );
        gc.fill( new java.awt.geom.Rectangle2D.Double( posX, posY, width, height ) );

        gc.dispose();

    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height algura.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus.
     * @param color cor de desenho.
     */
    public void fillRectanglePro( Vector pos, double width, double height, Point origin, double rotation, Color color ) {
        fillRectanglePro( pos.x, pos.y, width, height, origin.x, origin.y, rotation, color );
    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height algura.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus.
     * @param color cor de desenho.
     */
    public void fillRectanglePro( Point pos, double width, double height, Point origin, double rotation, Color color ) {
        fillRectanglePro( pos.x, pos.y, width, height, origin.x, origin.y, rotation, color );
    }

    /**
     * Pinta um retângulo rotacionado.
     * 
     * @param rectangle um retângulo.
     * @param origin pivô da rotação.
     * @param rotation rotação em graus.
     * @param color cor de desenho.
     */
    public void fillRectanglePro( Rectangle rectangle, Point origin, double rotation, Color color ) {
        fillRectanglePro( rectangle.x, rectangle.y, rectangle.width, rectangle.height, origin.x, origin.y, rotation, color );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param posX coordenada x do vértice superior esquerdo do retângulo.
     * @param posY coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void drawRoundRectangle( double posX, double posY, double width, double height, double roundness, Color color ) {
        g2d.setColor( color );
        g2d.draw( new java.awt.geom.RoundRectangle2D.Double( posX, posY, width, height, roundness, roundness ) );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param pos ponto superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void drawRoundRectangle( Vector pos, double width, double height, double roundness, Color color ) {
        drawRoundRectangle( pos.x, pos.y, width, height, roundness, color );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param pos ponto superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void drawRoundRectangle( Point pos, double width, double height, double roundness, Color color ) {
        drawRoundRectangle( pos.x, pos.y, width, height, roundness, color );
    }

    /**
     * Desenha um retângulo com cantos arredondados.
     * 
     * @param roundRectangle um retângulo com os cantos arredondados.
     * @param color cor de desenho.
     */
    public void drawRoundRectangle( RoundRectangle roundRectangle, Color color ) {
        drawRoundRectangle( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, color );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param posX coordenada x do vértice superior esquerdo do retângulo.
     * @param posY coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void fillRoundRectangle( double posX, double posY, double width, double height, double roundness, Color color ) {
        g2d.setColor( color );
        g2d.fill( new java.awt.geom.RoundRectangle2D.Double( posX, posY, width, height, roundness, roundness ) );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param pos ponto superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void fillRoundRectangle( Vector pos, double width, double height, double roundness, Color color ) {
        fillRoundRectangle( pos.x, pos.y, width, height, roundness, color );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param pos ponto superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param roundness arredondamento dos cantos.
     * @param color cor de desenho.
     */
    public void fillRoundRectangle( Point pos, double width, double height, double roundness, Color color ) {
        fillRoundRectangle( pos.x, pos.y, width, height, roundness, color );
    }

    /**
     * Pinta um retângulo com cantos arredondados.
     * 
     * @param roundRectangle um retângulo com os cantos arredondados.
     * @param color cor de desenho.
     */
    public void fillRoundRectangle( RoundRectangle roundRectangle, Color color ) {
        fillRoundRectangle( roundRectangle.x, roundRectangle.y, roundRectangle.width, roundRectangle.height, roundRectangle.roundness, color );
    }

    /**
     * Pinta um retângulo com um gradiente horizontal.
     * 
     * @param posX coordenada x do vértice superior esquerdo do retângulo.
     * @param posY coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientH( double posX, double posY, double width, double height, Color color1, Color color2 ) {
        g2d.setPaint( new GradientPaint( (int) posX, (int) (posY + height / 2), color1, (int) (posX + width), (int) (posY + height / 2), color2 ) );
        g2d.fill( new java.awt.geom.Rectangle2D.Double( posX, posY, width, height ) );
    }

    /**
     * Pinta um retângulo com um gradiente horizontal.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientH( Vector pos, double width, double height, Color color1, Color color2 ) {
        fillRectangleGradientH( pos.x, pos.y, width, height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente horizontal.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientH( Point pos, double width, double height, Color color1, Color color2 ) {
        fillRectangleGradientH( pos.x, pos.y, width, height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente horizontal.
     * 
     * @param rectangle um retângulo.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientH( Rectangle rectangle, Color color1, Color color2 ) {
        fillRectangleGradientH( rectangle.x, rectangle.y, rectangle.width, rectangle.height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente vertical.
     * 
     * @param posX coordenada x do vértice superior esquerdo do retângulo.
     * @param posY coordenada y do vértice superior esquerdo do retângulo.
     * @param width largura.
     * @param height algura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientV( double posX, double posY, double width, double height, Color color1, Color color2 ) {
        g2d.setPaint( new GradientPaint( (int) (posX + width / 2), (int) posY, color1, (int) (posX + width / 2), (int) (posY + height), color2 ) );
        g2d.fill( new java.awt.geom.Rectangle2D.Double( posX, posY, width, height ) );
    }

    /**
     * Pinta um retângulo com um gradiente vertical.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientV( Point pos, double width, double height, Color color1, Color color2 ) {
        fillRectangleGradientV( pos.x, pos.y, width, height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente vertical.
     * 
     * @param pos vértice superior esquerdo.
     * @param width largura.
     * @param height altura.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientV( Vector pos, double width, double height, Color color1, Color color2 ) {
        fillRectangleGradientV( pos.x, pos.y, width, height, color1, color2 );
    }

    /**
     * Pinta um retângulo com um gradiente vertical.
     * 
     * @param rectangle um retângulo.
     * @param color1 cor inicial do gradiente.
     * @param color2 cor final do gradiente.
     */
    public void fillRectangleGradientV( Rectangle rectangle, Color color1, Color color2 ) {
        fillRectangleGradientV( rectangle.x, rectangle.y, rectangle.width, rectangle.height, color1, color2 );
    }

    /**
     * Desenha um círculo.
     * 
     * @param centerX coordenada x do centro do círculo.
     * @param centerY coordenada y do centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void drawCircle( double centerX, double centerY, double radius, Color color ) {
        g2d.setColor( color );
        g2d.draw( new java.awt.geom.Ellipse2D.Double( centerX - radius, centerY - radius, radius * 2, radius * 2 ) );
    }

    /**
     * Desenha um círculo.
     * 
     * @param center centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void drawCircle( Vector center, double radius, Color color ) {
        drawCircle( center.x, center.y, radius, color );
    }

    /**
     * Desenha um círculo.
     * 
     * @param center centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void drawCircle( Point center, double radius, Color color ) {
        drawCircle( center.x, center.y, radius, color );
    }

    /**
     * Desenha um círculo.
     * 
     * @param circle um círculo.
     * @param color cor de desenho.
     */
    public void drawCircle( Circle circle, Color color ) {
        drawCircle( circle.x, circle.y, circle.radius, color );
    }

    /**
     * Pinta um círculo.
     * 
     * @param centerX coordenada x do centro do círculo.
     * @param centerY coordenada y do centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void fillCircle( double centerX, double centerY, double radius, Color color ) {
        g2d.setColor( color );
        g2d.fill( new java.awt.geom.Ellipse2D.Double( centerX - radius, centerY - radius, radius * 2, radius * 2 ) );
    }

    /**
     * Pinta um círculo.
     * 
     * @param center centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void fillCircle( Vector center, double radius, Color color ) {
        fillCircle( center.x, center.y, radius, color );
    }

    /**
     * Pinta um círculo.
     * 
     * @param center centro do círculo.
     * @param radius raio.
     * @param color cor de desenho.
     */
    public void fillCircle( Point center, double radius, Color color ) {
        fillCircle( center.x, center.y, radius, color );
    }

    /**
     * Pinta um círculo.
     * 
     * @param circle um círculo.
     * @param color cor de desenho.
     */
    public void fillCircle( Circle circle, Color color ) {
        fillCircle( circle.x, circle.y, circle.radius, color );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param centerX coordenada x do centro da elipse.
     * @param centerY coordenada y do centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void drawEllipse( double centerX, double centerY, double radiusH, double radiusV, Color color ) {
        g2d.setColor( color );
        g2d.draw( new java.awt.geom.Ellipse2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2 ) );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param center centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void drawEllipse( Vector center, double radiusH, double radiusV, Color color ) {
        drawEllipse( center.x, center.y, radiusH, radiusV, color );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param center centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void drawEllipse( Point center, double radiusH, double radiusV, Color color ) {
        drawEllipse( center.x, center.y, radiusH, radiusV, color );
    }

    /**
     * Desenha uma elipse.
     * 
     * @param ellipse uma elipse.
     * @param color cor de desenho.
     */
    public void drawEllipse( Ellipse ellipse, Color color ) {
        drawEllipse( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, color );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param centerX coordenada x do centro da elipse.
     * @param centerY coordenada y do centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void fillEllipse( double centerX, double centerY, double radiusH, double radiusV, Color color ) {
        g2d.setColor( color );
        g2d.fill( new java.awt.geom.Ellipse2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2 ) );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param center centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void fillEllipse( Vector center, double radiusH, double radiusV, Color color ) {
        fillEllipse( center.x, center.y, radiusH, radiusV, color );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param center centro da elipse.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param color cor de desenho.
     */
    public void fillEllipse( Point center, double radiusH, double radiusV, Color color ) {
        fillEllipse( center.x, center.y, radiusH, radiusV, color );
    }

    /**
     * Pinta uma elipse.
     * 
     * @param ellipse uma elipse.
     * @param color cor de desenho.
     */
    public void fillEllipse( Ellipse ellipse, Color color ) {
        fillEllipse( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, color );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void drawCircleSector( double centerX, double centerY, double radius, double startAngle, double endAngle, Color color ) {
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.draw( new java.awt.geom.Arc2D.Double( centerX - radius, centerY - radius, radius * 2, radius * 2, startAngle, extent, java.awt.geom.Arc2D.PIE ) );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param center ponto do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void drawCircleSector( Vector center, double radius, double startAngle, double endAngle, Color color ) {
        drawCircleSector( center.x, center.y, radius, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param center ponto do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void drawCircleSector( Point center, double radius, double startAngle, double endAngle, Color color ) {
        drawCircleSector( center.x, center.y, radius, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param circle um círculo.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void drawCircleSector( Circle circle, double startAngle, double endAngle, Color color ) {
        drawCircleSector( circle.x, circle.y, circle.radius, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor circular.
     * 
     * @param circleSector um setor circular.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void drawCircleSector( CircleSector circleSector, Color color ) {
        drawCircleSector( circleSector.x, circleSector.y, circleSector.radius, circleSector.startAngle, circleSector.endAngle, color );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void fillCircleSector( double centerX, double centerY, double radius, double startAngle, double endAngle, Color color ) {
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.fill( new java.awt.geom.Arc2D.Double( centerX - radius, centerY - radius, radius * 2, radius * 2, startAngle, extent, java.awt.geom.Arc2D.PIE ) );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param center ponto do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void fillCircleSector( Vector center, double radius, double startAngle, double endAngle, Color color ) {
        fillCircleSector( center.x, center.y, radius, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param center ponto do centro.
     * @param radius raio.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void fillCircleSector( Point center, double radius, double startAngle, double endAngle, Color color ) {
        fillCircleSector( center.x, center.y, radius, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param circle um círculo.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void fillCircleSector( Circle circle, double startAngle, double endAngle, Color color ) {
        fillCircleSector( circle.x, circle.y, circle.radius, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor circular.
     * 
     * @param circleSector um setor circular.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void fillCircleSector( CircleSector circleSector, Color color ) {
        fillCircleSector( circleSector.x, circleSector.y, circleSector.radius, circleSector.startAngle, circleSector.endAngle, color );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void drawEllipseSector( double centerX, double centerY, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.draw( new java.awt.geom.Arc2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2, startAngle, extent, java.awt.geom.Arc2D.PIE ) );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void drawEllipseSector( Vector center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        drawEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void drawEllipseSector( Point center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        drawEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param ellipse uma elipse.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void drawEllipseSector( Ellipse ellipse, double startAngle, double endAngle, Color color ) {
        drawEllipseSector( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um setor de uma elipse.
     * 
     * @param ellipseSector um setor de uma elipse.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void drawEllipseSector( EllipseSector ellipseSector, Color color ) {
        drawEllipseSector( ellipseSector.x, ellipseSector.y, ellipseSector.radiusH, ellipseSector.radiusV, ellipseSector.startAngle, ellipseSector.endAngle, color );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void fillEllipseSector( double centerX, double centerY, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.fill( new java.awt.geom.Arc2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2, startAngle, extent, java.awt.geom.Arc2D.PIE ) );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void fillEllipseSector( Vector center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        fillEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void fillEllipseSector( Point center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        fillEllipseSector( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param ellipse uma elipse.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void fillEllipseSector( Ellipse ellipse, double startAngle, double endAngle, Color color ) {
        fillEllipseSector( ellipse.x, ellipse.y, ellipse.radiusH, ellipse.radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um setor de uma elipse.
     * 
     * @param ellipseSector um setor de uma elipse.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void fillEllipseSector( EllipseSector ellipseSector, Color color ) {
        fillEllipseSector( ellipseSector.x, ellipseSector.y, ellipseSector.radiusH, ellipseSector.radiusV, ellipseSector.startAngle, ellipseSector.endAngle, color );
    }

    /**
     * Desenha um arco.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void drawArc( double centerX, double centerY, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.draw( new java.awt.geom.Arc2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2, startAngle, extent, java.awt.geom.Arc2D.OPEN ) );
    }

    /**
     * Desenha um arco.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void drawArc( Vector center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        drawArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um arco.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void drawArc( Point center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        drawArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Desenha um arco
     * 
     * @param arc um arco.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void drawArc( Arc arc, Color color ) {
        drawArc( arc.x, arc.y, arc.radiusH, arc.radiusV, arc.startAngle, arc.endAngle, color );
    }

    /**
     * Pinta um arco.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void fillArc( double centerX, double centerY, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        g2d.setColor( color );
        double extent = endAngle - startAngle;
        g2d.fill( new java.awt.geom.Arc2D.Double( centerX - radiusH, centerY - radiusV, radiusH * 2, radiusV * 2, startAngle, extent, java.awt.geom.Arc2D.CHORD ) );
    }

    /**
     * Pinta um arco.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void fillArc( Vector center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        fillArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um arco.
     * 
     * @param center ponto do centro.
     * @param radiusH raio horizontal.
     * @param radiusV raio vertical.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void fillArc( Point center, double radiusH, double radiusV, double startAngle, double endAngle, Color color ) {
        fillArc( center.x, center.y, radiusH, radiusV, startAngle, endAngle, color );
    }

    /**
     * Pinta um arco
     * 
     * @param arc um arco.
     * @param startAngle ângulo inicial.
     * @param endAngle ângulo final.
     * @param color cor de desenho.
     */
    public void fillArc( Arc arc, Color color ) {
        fillArc( arc.x, arc.y, arc.radiusH, arc.radiusV, arc.startAngle, arc.endAngle, color );
    }

    /**
     * Desenha um anel.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus.
     * @param endAngle ângulo final em graus.
     * @param segments quantidade de segmentos.
     * @param color cor de desenho.
     */
    public void drawRing( double centerX, double centerY, double innerRadius, double outerRadius, double startAngle, double endAngle, int segments, Color color ) {
        processRing( centerX, centerY, innerRadius, outerRadius, startAngle, endAngle, segments, color, true );
    }

    /**
     * Desenha um anel.
     * 
     * @param center centro do anel.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus.
     * @param endAngle ângulo final em graus.
     * @param segments quantidade de segmentos.
     * @param color cor de desenho.
     */
    public void drawRing( Vector center, double innerRadius, double outerRadius, double startAngle, double endAngle, int segments, Color color ) {
        drawRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, segments, color );
    }

    /**
     * Desenha um anel.
     * 
     * @param center centro do anel.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus.
     * @param endAngle ângulo final em graus.
     * @param segments quantidade de segmentos.
     * @param color cor de desenho.
     */
    public void drawRing( Point center, double innerRadius, double outerRadius, double startAngle, double endAngle, int segments, Color color ) {
        drawRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, segments, color );
    }

    /**
     * Desenha um anel.
     * 
     * @param ring um anel.
     * @param color cor de desenho.
     */
    public void drawRing( Ring ring, Color color ) {
        drawRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle, ring.segments, color );
    }

    /**
     * Pinta um anel.
     * 
     * @param centerX coordenada x do centro.
     * @param centerY coordenada y do centro.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus.
     * @param endAngle ângulo final em graus.
     * @param segments quantidade de segmentos.
     * @param color cor de desenho.
     */
    public void fillRing( double centerX, double centerY, double innerRadius, double outerRadius, double startAngle, double endAngle, int segments, Color color ) {
        processRing( centerX, centerY, innerRadius, outerRadius, startAngle, endAngle, segments, color, false );
    }

    /**
     * Pinta um anel.
     * 
     * @param center centro do anel.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus.
     * @param endAngle ângulo final em graus.
     * @param segments quantidade de segmentos.
     * @param color cor de desenho.
     */
    public void fillRing( Vector center, double innerRadius, double outerRadius, double startAngle, double endAngle, int segments, Color color ) {
        fillRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, segments, color );
    }

    /**
     * Pinta um anel.
     * 
     * @param center centro do anel.
     * @param innerRadius raio interno.
     * @param outerRadius raio externo.
     * @param startAngle ângulo inicial em graus.
     * @param endAngle ângulo final em graus.
     * @param segments quantidade de segmentos.
     * @param color cor de desenho.
     */
    public void fillRing( Point center, double innerRadius, double outerRadius, double startAngle, double endAngle, int segments, Color color ) {
        fillRing( center.x, center.y, innerRadius, outerRadius, startAngle, endAngle, segments, color );
    }

    /**
     * Pinta um anel.
     * 
     * @param ring um anel.
     * @param color cor de desenho.
     */
    public void fillRing( Ring ring, Color color ) {
        fillRing( ring.x, ring.y, ring.innerRadius, ring.outerRadius, ring.startAngle, ring.endAngle, ring.segments, color );
    }

    /*
     * Gera o desenho do anel.
     */
    private void processRing( double centerX, double centerY, double innerRadius, double outerRadius, double startAngle, double endAngle, int segments, Color color, boolean draw ) {

        g2d.setColor( color );

        java.awt.geom.Path2D path = new java.awt.geom.Path2D.Double();
        double currentAngle = -startAngle;
        double angleIncrement = Math.abs( endAngle - startAngle ) / segments;

        double rad = Math.toRadians( currentAngle );
        double x = centerX + innerRadius * Math.cos( rad );
        double y = centerY + innerRadius * Math.sin( rad );
        path.moveTo( x, y );

        for ( int i = 0; i < segments; i++ ) {

            currentAngle -= angleIncrement;

            rad = Math.toRadians( currentAngle );
            x = centerX + innerRadius * Math.cos( rad );
            y = centerY + innerRadius * Math.sin( rad );

            path.lineTo( x, y );

        }

        rad = Math.toRadians( currentAngle );
        x = centerX + outerRadius * Math.cos( rad );
        y = centerY + outerRadius * Math.sin( rad );
        path.lineTo( x, y );

        for ( int i = 0; i < segments; i++ ) {

            currentAngle += angleIncrement;

            rad = Math.toRadians( currentAngle );
            x = centerX + outerRadius * Math.cos( rad );
            y = centerY + outerRadius * Math.sin( rad );

            path.lineTo( x, y );

        }

        path.closePath();

        if ( draw ) {
            g2d.draw( path );
        } else {
            g2d.fill( path );
        }

    }

    /**
     * Desenha um triângulo.
     * 
     * @param v1x coordenada x do primeiro vértice.
     * @param v1y coordenada y do primeiro vértice.
     * @param v2x coordenada x do segundo vértice.
     * @param v2y coordenada y do segundo vértice.
     * @param v3x coordenada x do terceiro vértice.
     * @param v3y coordenada y do terceiro vértice.
     * @param color cor de desenho.
     */
    public void drawTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Color color ) {
        processTriangle( v1x, v1y, v2x, v2y, v3x, v3y, color, true );
    }

    /**
     * Desenha um triângulo.
     * 
     * @param v1 primeiro vértice.
     * @param v2 segundo vértice.
     * @param v3 terceiro vértice.
     * @param color cor de desenho.
     */
    public void drawTriangle( Vector v1, Vector v2, Vector v3, Color color ) {
        drawTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, color );
    }

    /**
     * Desenha um triângulo.
     * 
     * @param v1 primeiro vértice.
     * @param v2 segundo vértice.
     * @param v3 terceiro vértice.
     * @param color cor de desenho.
     */
    public void drawTriangle( Point v1, Point v2, Point v3, Color color ) {
        drawTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, color );
    }

    /**
     * Desenha um triângulo.
     * 
     * @param triangle um triângulo.
     * @param color cor de desenho.
     */
    public void drawTriangle( Triangle triangle, Color color ) {
        drawTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3, color );
    }

    /**
     * Pinta um triângulo.
     * 
     * @param v1x coordenada x do primeiro vértice.
     * @param v1y coordenada y do primeiro vértice.
     * @param v2x coordenada x do segundo vértice.
     * @param v2y coordenada y do segundo vértice.
     * @param v3x coordenada x do terceiro vértice.
     * @param v3y coordenada y do terceiro vértice.
     * @param color cor de desenho.
     */
    public void fillTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Color color ) {
        processTriangle( v1x, v1y, v2x, v2y, v3x, v3y, color, false );
    }

    /**
     * Pinta um triângulo.
     * 
     * @param v1 primeiro vértice.
     * @param v2 segundo vértice.
     * @param v3 terceiro vértice.
     * @param color cor de desenho.
     */
    public void fillTriangle( Vector v1, Vector v2, Vector v3, Color color ) {
        fillTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, color );
    }

    /**
     * Pinta um triângulo.
     * 
     * @param v1 primeiro vértice.
     * @param v2 segundo vértice.
     * @param v3 terceiro vértice.
     * @param color cor de desenho.
     */
    public void fillTriangle( Point v1, Point v2, Point v3, Color color ) {
        fillTriangle( v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, color );
    }

    /**
     * Pinta um triângulo.
     * 
     * @param triangle um triângulo.
     * @param color cor de desenho.
     */
    public void fillTriangle( Triangle triangle, Color color ) {
        fillTriangle( triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3, color );
    }

    /*
     * Gera o desenho do triângulo.
     */
    private void processTriangle( double v1x, double v1y, double v2x, double v2y, double v3x, double v3y, Color color, boolean draw ) {

        g2d.setColor( color );

        java.awt.geom.Path2D path = new java.awt.geom.Path2D.Double();
        path.moveTo( v1x, v1y );
        path.lineTo( v2x, v2y );
        path.lineTo( v3x, v3y );
        path.closePath();

        if ( draw ) {
            g2d.draw( path );
        } else {
            g2d.fill( path );
        }

    }

    /**
     * Desenha um polígono regular.
     * 
     * @param centerX coordenada x do centro do polígono.
     * @param centerY coordenada y do centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus.
     * @param color cor de desenho.
     */
    public void drawPolygon( double centerX, double centerY, double sides, double radius, double rotation, Color color ) {
        processPolygon( centerX, centerY, sides, radius, rotation, color, true );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param center centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus.
     * @param color cor de desenho.
     */
    public void drawPolygon( Vector center, double sides, double radius, double rotation, Color color ) {
        drawPolygon( center.x, center.y, sides, radius, rotation, color );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param center centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus.
     * @param color cor de desenho.
     */
    public void drawPolygon( Point center, double sides, double radius, double rotation, Color color ) {
        drawPolygon( center.x, center.y, sides, radius, rotation, color );
    }

    /**
     * Desenha um polígono regular.
     * 
     * @param plygon um polígono regular.
     * @param color cor de desenho.
     */
    public void drawPolygon( Polygon polygon, Color color ) {
        drawPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation, color );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param centerX coordenada x do centro do polígono.
     * @param centerY coordenada y do centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus.
     * @param color cor de desenho.
     */
    public void fillPolygon( double centerX, double centerY, double sides, double radius, double rotation, Color color ) {
        processPolygon( centerX, centerY, sides, radius, rotation, color, false );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param center centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus.
     * @param color cor de desenho.
     */
    public void fillPolygon( Vector center, double sides, double radius, double rotation, Color color ) {
        fillPolygon( center.x, center.y, sides, radius, rotation, color );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param center centro do polígono.
     * @param sides quantidade de lados.
     * @param radius raio.
     * @param rotation rotação em graus.
     * @param color cor de desenho.
     */
    public void fillPolygon( Point center, double sides, double radius, double rotation, Color color ) {
        fillPolygon( center.x, center.y, sides, radius, rotation, color );
    }

    /**
     * Pinta um polígono regular.
     * 
     * @param polygon um polígono regular.
     * @param color cor de desenho.
     */
    public void fillPolygon( Polygon polygon, Color color ) {
        fillPolygon( polygon.x, polygon.y, polygon.sides, polygon.radius, polygon.rotation, color );
    }

    /*
     * Gera o desenho do polígono regular.
     */
    private void processPolygon( double centerX, double centerY, double sides, double radius, double rotation, Color color, boolean draw ) {

        g2d.setColor( color );

        java.awt.geom.Path2D path = new java.awt.geom.Path2D.Double();
        double currentAngle = -rotation;
        double angleIncrement = 360.0 / sides;

        for ( int i = 0; i < sides; i++ ) {

            double rad = Math.toRadians( currentAngle );
            double x = centerX + radius * Math.cos( rad );
            double y = centerY + radius * Math.sin( rad );

            if ( i == 0 ) {
                path.moveTo( x, y );
            } else {
                path.lineTo( x, y );
            }

            currentAngle -= angleIncrement;

        }

        path.closePath();

        if ( draw ) {
            g2d.draw( path );
        } else {
            g2d.fill( path );
        }

    }

    /**
     * Desenha um caminho.
     * 
     * @param path caminho a ser desenhado.
     * @param color cor de desenho.
     */
    public void drawPath( Path path, Color color ) {
        g2d.setColor( color );
        g2d.draw( path.path );
    }

    /**
     * Ponta um caminho.
     * 
     * @param path caminho a ser desenhado.
     * @param color cor de desenho.
     */
    public void fillPath( Path path, Color color ) {
        g2d.setColor( color );
        g2d.fill( path.path );
    }

    /***************************************************************************
     * Métodos de desenhos de curvas.
     **************************************************************************/

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param cx coordenada x do ponto de controle.
     * @param cy coordenada y do ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param color cor de desenhho.
     */
    public void drawQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, Color color ) {
        this.g2d.setColor( color );
        g2d.draw( new java.awt.geom.QuadCurve2D.Double( p1x, p1y, cx, cy, p2x, p2y ) );
    }

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void drawQuadCurve( Vector p1, Vector c, Vector p2, Color color ) {
        drawQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, color );
    }

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void drawQuadCurve( Point p1, Point c, Point p2, Color color ) {
        drawQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, color );
    }

    /**
     * Desenha uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve uma curva Bézier quadrática.
     * @param color cor de desenhho.
     */
    public void drawQuadCurve( QuadCurve quadCurve, Color color ) {
        drawQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, color );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param cx coordenada x do ponto de controle.
     * @param cy coordenada y do ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param color cor de desenhho.
     */
    public void fillQuadCurve( double p1x, double p1y, double cx, double cy, double p2x, double p2y, Color color ) {
        this.g2d.setColor( color );
        g2d.fill( new java.awt.geom.QuadCurve2D.Double( p1x, p1y, cx, cy, p2x, p2y ) );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void fillQuadCurve( Vector p1, Vector c, Vector p2, Color color ) {
        fillQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, color );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param p1 ponto inicial.
     * @param c ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void fillQuadCurve( Point p1, Point c, Point p2, Color color ) {
        fillQuadCurve( p1.x, p1.y, c.x, c.y, p2.x, p2.y, color );
    }

    /**
     * Pinta uma curva quadrática (curva Bézier quadrática).
     * 
     * @param quadCurve uma curva Bézier quadrática.
     * @param color cor de desenhho.
     */
    public void fillQuadCurve( QuadCurve quadCurve, Color color ) {
        fillQuadCurve( quadCurve.x1, quadCurve.y1, quadCurve.cx, quadCurve.cy, quadCurve.x2, quadCurve.y2, color );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param c1x coordenada x do primeiro ponto de controle.
     * @param c1y coordenada y do primeiro ponto de controle.
     * @param c2x coordenada x do segundo ponto de controle.
     * @param c2y coordenada y do segundo ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param color cor de desenhho.
     */
    public void drawCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, Color color ) {
        this.g2d.setColor( color );
        g2d.draw( new java.awt.geom.CubicCurve2D.Double( p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y ) );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void drawCubicCurve( Vector p1, Vector c1, Vector c2, Vector p2, Color color ) {
        drawCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, color );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void drawCubicCurve( Point p1, Point c1, Point c2, Point p2, Color color ) {
        drawCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, color );
    }

    /**
     * Desenha uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve uma curva Bézier cúbica.
     * @param color cor de desenhho.
     */
    public void drawCubicCurve( CubicCurve cubicCurve, Color color ) {
        drawCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, color );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1x coordenada x do ponto inicial.
     * @param p1y coordenada y do ponto inicial.
     * @param c1x coordenada x do primeiro ponto de controle.
     * @param c1y coordenada y do primeiro ponto de controle.
     * @param c2x coordenada x do segundo ponto de controle.
     * @param c2y coordenada y do segundo ponto de controle.
     * @param p2x coordenada x do ponto final.
     * @param p2y coordenada y do ponto final.
     * @param color cor de desenhho.
     */
    public void fillCubicCurve( double p1x, double p1y, double c1x, double c1y, double c2x, double c2y, double p2x, double p2y, Color color ) {
        this.g2d.setColor( color );
        g2d.fill( new java.awt.geom.CubicCurve2D.Double( p1x, p1y, c1x, c1y, c2x, c2y, p2x, p2y ) );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void fillCubicCurve( Vector p1, Vector c1, Vector c2, Vector p2, Color color ) {
        fillCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, color );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param p1 ponto inicial.
     * @param c1 primeiro ponto de controle.
     * @param c2 segundo ponto de controle.
     * @param p2 ponto final.
     * @param color cor de desenhho.
     */
    public void fillCubicCurve( Point p1, Point c1, Point c2, Point p2, Color color ) {
        fillCubicCurve( p1.x, p1.y, c1.x, c1.y, c2.x, c2.y, p2.x, p2.y, color );
    }

    /**
     * Pinta uma curva cúbica (curva Bézier cúbica).
     * 
     * @param cubicCurve uma curva Bézier cúbica.
     * @param color cor de desenhho.
     */
    public void fillCubicCurve( CubicCurve cubicCurve, Color color ) {
        fillCubicCurve( cubicCurve.x1, cubicCurve.y1, cubicCurve.c1x, cubicCurve.c1y, cubicCurve.c2x, cubicCurve.c2y, cubicCurve.x2, cubicCurve.y2, color );
    }

    /***************************************************************************
     * Métodos de desenho de texto.
     **************************************************************************/

    /**
     * Desenha um texto.
     * 
     * @param text o texto a ser desenhado.
     * @param posX coordenada x do início do desenho do texto.
     * @param posY coordenada y do início do desenho do texto.
     * @param color cor de desenho.
     */
    public void drawText( String text, double posX, double posY, Color color ) {
        g2d.setColor( color );
        g2d.drawString( text, (int) posX, (int) posY );
    }

    /**
     * Desenha um texto.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param color cor de desenho.
     */
    public void drawText( String text, Vector point, Color color ) {
        drawText( text, point.x, point.y, color );
    }

    /**
     * Desenha um texto.
     * 
     * @param text o texto a ser desenhado.
     * @param point ponto do inicio do desenho do texto.
     * @param color cor de desenho.
     */
    public void drawText( String text, Point point, Color color ) {
        drawText( text, point.x, point.y, color );
    }

    /**
     * Mede a largura de um texto.
     * 
     * @param text o texto a ser medido.
     * @return a largura de um texto.
     */
    public int measureText( String text ) {
        return g2d.getFontMetrics().stringWidth( text );
    }
    
    

    /***************************************************************************
     * Métodos utilitários variados.
     **************************************************************************/

    /**
     * Limpa o fundo da tela de desenho.
     * 
     * @param color cor a ser usada.
     */
    public void clearBackground( Color color ) {
        fillRectangle( 0, 0, getScreenWidth(), getScreenHeight(), color );
    }

    /***************************************************************************
     * Métodos para obtenção de dados relativos à execução.
     **************************************************************************/

    /**
     * Obtém o tempo que um frame demorou para ser atualizado e desenhado.
     * @return
     */
    public double getFrameTime() {
        return frameTime / 1000.0;
    }

    /**
     * Obtém a largura da tela.
     * 
     * @return largura da tela.
     */
    public int getScreenWidth() {
        return drawingPanel.getWidth();
    }

    /**
     * Obtém a altura da tela.
     * 
     * @return altura da tela.
     */
    public int getScreenHeight() {
        return drawingPanel.getHeight();
    }

    /**
     * Retorna o contexto gráfico atual.
     * 
     * Observação: Utilize apenas no método draw!
     */
    public Graphics2D getGraphics2D() {
        return g2d;
    }

    public void drawFps( double x, double y ) {

        Font t = g2d.getFont();
        g2d.setFont( defaultFpsFont );

        int localFps = (int) ( Math.round( 1000.0 / frameTime / 10.0 ) ) * 10;
        if ( localFps >= 0 ) {
            currentFps = localFps;
        }

        drawText( 
            String.format( "%d FPS", currentFps ), 
            x, y, Utils.lerp( RED, LIME, currentFps / (double) targetFps ) );

        g2d.setFont( t );

    }



    /***************************************************************************
     * Métodos para configuração da fonte e do contorno.
     **************************************************************************/

    /**
     * Altera a fonte padrão do contexto gráfico.
     * Utilize no método create();
     * 
     * @param font Fonte a ser usada.
     */
    public void setDefaultFont( Font font ) {
        this.defaultFont = font;
    }

    /**
     * Altera o nome da fonte padrão do contexto gráfico.
     * Utilize no método create();
     * 
     * @param name Nome da fonte padrão.
     */
    public void setDefaultFontName( String name ) {
        defaultFont = new Font( defaultFont.getName(), defaultFont.getStyle(), defaultFont.getSize() );
    }

    /**
     * Altera o estilo da fonte padrão do contexto gráfico.
     * Utilize no método create();
     * 
     * @param style O estilo da fonte padrão.
     */
    public void setDefaultFontStyle( int style ) {
        defaultFont = defaultFont.deriveFont( style );
    }

    /**
     * Altera o tamanho da fonte padrão do contexto gráfico.
     * Utilize no método create();
     * 
     * @param size O tamanho da fonte padrão.
     */
    public void setDefaultFontSize( int size ) {
        defaultFont = defaultFont.deriveFont( (float) size );
    }

    /**
     * Altera o nome da fonte corrente do contexto gráfico.
     * Cuidado, essa operação alterará a fonte toda vez que for executada.
     * 
     * @param name Nome da fonte.
     */
    public void setFontName( String name ) {
        g2d.setFont( new Font( name, defaultFont.getStyle(), defaultFont.getSize() ) );
    }

    /**
     * Altera o estilo da fonte corrente do contexto gráfico.
     * Cuidado, essa operação alterará a fonte toda vez que for executada.
     * 
     * @param style O estilo da fonte corrente.
     */
    public void setFontStyle( int style ) {
        g2d.setFont( new Font( defaultFont.getName(), style, defaultFont.getSize() ) );
    }

    /**
     * Altera o tamanho da fonte corrente do contexto gráfico.
     * Cuidado, essa operação alterará a fonte toda vez que for executada.
     * 
     * @param size O tamanho da fonte corrente.
     */
    public void setFontSize( int size ) {
        g2d.setFont( new Font( defaultFont.getName(), defaultFont.getStyle(), size ) );
    }

    /**
     * Altera o contorno padrão do contexto gráfico.
     * Utilize no método create();
     * 
     * @param stroke Contorno a ser usado.
     */
    public void setDefaultStroke( BasicStroke stroke ) {
        this.defaultStroke = stroke;
    }

    /**
     * Altera a largura do contorno padrão do contexto gráfico.
     * Utilize no método create();
     * 
     * @param width A largura do contorno padrão.
     */
    public void setDefaultStrokeWidth( float width ) {
        defaultStroke = new BasicStroke( width, defaultStroke.getEndCap(), defaultStroke.getLineJoin() );
    }

    /**
     * Altera o modelo desenho do fim das linhas do contorno padrão do contexto
     * gráfico.
     * Utilize no método create();
     * 
     * @param endCap O novo modelo de desenho.
     */
    public void setDefaultStrokeEndCap( int endCap ) {
        defaultStroke = new BasicStroke( defaultStroke.getLineWidth(), endCap, defaultStroke.getLineJoin() );
    }

    /**
     * Altera o modelo de junção de linhas do contorno padrão do contexto
     * gráfico.
     * Utilize no método create();
     * 
     * @param lineJoin O novo modelo de junção de linhas.
     */
    public void setDefaultStrokeLineJoin( int lineJoin ) {
        defaultStroke = new BasicStroke( defaultStroke.getLineWidth(), defaultStroke.getEndCap(), lineJoin );
    }

    /**
     * Altera a largura do contorno corrente do contexto gráfico.
     * Cuidado, essa operação alterará o contorno toda vez que for executada.
     * 
     * @param width A largura do contorno padrão.
     */
    public void setStrokeWidth( float width ) {
        g2d.setStroke( new BasicStroke( width, defaultStroke.getEndCap(), defaultStroke.getLineJoin() ) );
    }

    /**
     * Altera o modelo desenho do fim das linhas do contorno corrente do contexto
     * gráfico.
     * Cuidado, essa operação alterará o contorno toda vez que for executada.
     * 
     * @param endCap O novo modelo de desenho.
     */
    public void setStrokeEndCap( int endCap ) {
        g2d.setStroke( new BasicStroke( defaultStroke.getLineWidth(), endCap, defaultStroke.getLineJoin() ) );
    }

    /**
     * Altera o modelo de junção de linhas do contorno corrente do contexto
     * gráfico.
     * Cuidado, essa operação alterará o contorno toda vez que for executada.
     * 
     * @param lineJoin O novo modelo de junção de linhas.
     */
    public void setStrokeLineJoin( int lineJoin ) {
        g2d.setStroke( new BasicStroke( defaultStroke.getLineWidth(), defaultStroke.getEndCap(), lineJoin ) );
    }



    /***************************************************************************
     * Controle interno dos eventos.
     **************************************************************************/
    private void prepararEventosPainel( DrawingPanel painelDesenho ) {

        painelDesenho.addMouseListener( new MouseListener() {

            @Override
            public void mouseClicked( MouseEvent e ) {
                handleMouseEvents( e, MouseEventType.CLICKED );
            }

            @Override
            public void mousePressed( MouseEvent e ) {
                handleMouseEvents( e, MouseEventType.PRESSED );
            }

            @Override
            public void mouseReleased( MouseEvent e ) {
                handleMouseEvents( e, MouseEventType.RELEASED );
            }

            @Override
            public void mouseEntered( MouseEvent e ) {
                handleMouseEvents( e, MouseEventType.ENTERED );
            }

            @Override
            public void mouseExited( MouseEvent e ) {
                handleMouseEvents( e, MouseEventType.EXITED );
            }
            
        });

        painelDesenho.addMouseMotionListener( new MouseMotionListener() {

            @Override
            public void mouseDragged( MouseEvent e ) {
                handleMouseEvents( e, MouseEventType.DRAGGED );
            }

            @Override
            public void mouseMoved( MouseEvent e ) {
                handleMouseEvents( e, MouseEventType.MOVED );
            }
            
        });

        painelDesenho.addMouseWheelListener( new MouseWheelListener() {

            @Override
            public void mouseWheelMoved( MouseWheelEvent e ) {
                handleMouseWheelEvents( e );
            }
            
        });

        painelDesenho.addKeyListener( new KeyAdapter() {

            @Override
            public void keyPressed( KeyEvent e ) {
                handleKeyboardEvents( e, KeyboardEventType.PRESSED );
            }

            @Override
            public void keyReleased( KeyEvent e ) {
                handleKeyboardEvents( e, KeyboardEventType.RELEASED );
            }
            
        });

    }

    /*
     * Cores padrão.
     */
    protected static final Color LIGHTGRAY  = new Color( 200, 200, 200 );
    protected static final Color GRAY       = new Color( 130, 130, 130 );
    protected static final Color DARKGRAY   = new Color( 80, 80, 80 );
    protected static final Color YELLOW     = new Color( 253, 249, 0 );
    protected static final Color GOLD       = new Color( 255, 203, 0 );
    protected static final Color ORANGE     = new Color( 255, 161, 0 );
    protected static final Color PINK       = new Color( 255, 109, 194 );
    protected static final Color RED        = new Color( 230, 41, 55 );
    protected static final Color MAROON     = new Color( 190, 33, 55 );
    protected static final Color GREEN      = new Color( 0, 228, 48 );
    protected static final Color LIME       = new Color( 0, 158, 47 );
    protected static final Color DARKGREEN  = new Color( 0, 117, 44 );
    protected static final Color SKYBLUE    = new Color( 102, 191, 255 );
    protected static final Color BLUE       = new Color( 0, 121, 241 );
    protected static final Color DARKBLUE   = new Color( 0, 82, 172 );
    protected static final Color PURPLE     = new Color( 200, 122, 255 );
    protected static final Color VIOLET     = new Color( 135, 60, 190 );
    protected static final Color DARKPURPLE = new Color( 112, 31, 126 );
    protected static final Color BEIGE      = new Color( 211, 176, 131 );
    protected static final Color BROWN      = new Color( 127, 106, 79 );
    protected static final Color DARKBROWN  = new Color( 76, 63, 47 );
    protected static final Color WHITE      = new Color( 255, 255, 255 );
    protected static final Color BLACK      = new Color( 0, 0, 0 );
    protected static final Color BLANK      = new Color( 0, 0, 0, 0 );
    protected static final Color MAGENTA    = new Color( 255, 0, 255 );
    protected static final Color RAYWHITE   = new Color( 245, 245, 245 );

}
