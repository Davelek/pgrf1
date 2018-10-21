package ui;

import drawable.Drawable;
import drawable.DrawableType;
import drawable.Line;
import drawable.Nuhelnik;
import drawable.Point;
import drawable.Polygon;
import utils.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PgrfFrame extends JFrame implements MouseMotionListener {
    static int FPS = 1000 / 60;
    private BufferedImage img;
    static int width = 800;
    static int height = 600;
    private JPanel panel;
    private Renderer renderer;
    private int coorX, coorY;
    private int clickX = 300;
    private int clickY = 300;
    private int count = 5; //TODO - nesmí klesnout pod 3!
Boolean firstClick =true;
    private int startX;
    private int startY;
    private int k,l;
    Boolean last = false;
    private List<Point> points;
    private int index = -1;

private DrawableType type = DrawableType.POLYGON;



    private List<Drawable> drawables;


    public static void main(String... args) {
        PgrfFrame pgrfFrame = new PgrfFrame();
        pgrfFrame.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        pgrfFrame.init(width, height);

    }


    private void init(int width, int height) {
        drawables = new ArrayList<>();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, height);
        setTitle("Počítačová grafika");
        panel = new JPanel();
        add(panel);

        panel.addMouseMotionListener(this);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(type ==DrawableType.LINE) {
                    if (firstClick) {

                        clickX = e.getX();
                        clickY = e.getY();
                        startX = clickX;
                        startY = clickY;
                        drawables.add(new Line(new Point(clickX, clickY)));
                        index++;
                        drawables.get(index).count(count);

                    } else {

                        clickX = e.getX();
                        clickY = e.getY();
                        drawables.get(index).addPoint(new Point(clickX, clickY));
                       // drawables.get(index).draw(renderer);



                    }
                    firstClick = !firstClick;
                }
                if (type == DrawableType.N_OBJECT){
                    //TODO váš n-úhelník

                    if(e.getButton() == MouseEvent.BUTTON3) {
                        //  drawables.get(index).draw(renderer);
                        firstClick = !firstClick;
                        last = true;

                    }else {
                        if (firstClick) {
                            last = false;
                            clickX = e.getX();
                            clickY = e.getY();
                            startX = clickX;
                            startY = clickY;
                            firstClick = !firstClick;
                            //    points.add(new Point(clickX, clickY));
                            drawables.add(new Nuhelnik());
                            index++;
                            drawables.get(index).addPoint(new Point(clickX, clickY));

                        } else {
                            clickX = e.getX();
                            clickY = e.getY();
                            // points.add(new Point(clickX, clickY));
                            drawables.get(index).addPoint(new Point(clickX, clickY));


                  /*  drawables.add(new Line(clickX, clickY, e.getX(), e.getY()));
                        clickX = e.getX();
                        clickY = e.getY();*/

                        }
                    }
                }
                if (type == DrawableType.POLYGON){
                    //TODO polygon
                    if (firstClick) {
                        clickX = e.getX();
                        clickY = e.getY();
                        index++;
                        drawables.add(new Polygon(new Point(clickX, clickY)));
                        firstClick = !firstClick;
                    }else{
                        clickX = e.getX();
                        clickY = e.getY();
                        firstClick = !firstClick;
                        drawables.get(index).addPoint(new Point(clickX,clickY));
                    }



                }
             //   drawables.add(new Line(e.getX(),e.getY()));
            super.mouseReleased(e);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    //šipka nahoru
                count++;
                    drawables.get(index).count(count);
                }


                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    //plus na numerické klávesnici
                    if (count == 3) return;
                    count--;
                    drawables.get(index).count(count);
                }

                if (e.getKeyCode() == KeyEvent.VK_ADD) {
                    //plus na numerické klávesnici
                }

                if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
                    //minus na numerické klávesnici
                }

                if (e.getKeyCode() == KeyEvent.VK_L) {
                    if (type == DrawableType.LINE) {

                        JOptionPane frame = new JOptionPane();
                        JOptionPane.showMessageDialog(frame,
                                "Linka už je zvolena");
                    } else {
                        JOptionPane frame = new JOptionPane();
                        JOptionPane.showMessageDialog(frame,
                                "Zvolil jste Linku");
                        type = DrawableType.LINE;
                        firstClick = true;

                    }

                }
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    if (type == DrawableType.N_OBJECT) {
                        JOptionPane frame = new JOptionPane();
                        JOptionPane.showMessageDialog(frame,
                                "N-úhelník už je zvolen");
                    } else {

                        JOptionPane frame = new JOptionPane();
                        JOptionPane.showMessageDialog(frame,
                                "Zvolil jste N-Uhélník");
                        type = DrawableType.N_OBJECT;

                        points = new ArrayList<>();
                        firstClick = true;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_P) {
                    if (type == DrawableType.POLYGON) {
                        JOptionPane frame = new JOptionPane();
                        JOptionPane.showMessageDialog(frame,
                                "Polygon už je zvolen");
                    } else {
                        JOptionPane frame = new JOptionPane();
                        JOptionPane.showMessageDialog(frame,
                                "Zvolil jste Polygon");
                        type = DrawableType.POLYGON;
                        firstClick = true;
                    }
                }


                if (e.getKeyCode() == KeyEvent.VK_C) {
                    JOptionPane frame = new JOptionPane();
                    Object[] possibilities = {"Černá", "Červená", "Modrá", "Zelená", "Růžová"};
                    String s = (String) JOptionPane.showInputDialog(

                            frame,
                            "Vyberte barvu: "
                            ,
                            "Výběr barvy",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            "Černá");

                    switch (s){
                        case "Černá":
                            renderer.setColor(Color.BLACK.getRGB());
                            break;
                        case "Červená":
                            renderer.setColor(Color.RED.getRGB());
                            break;
                        case "Modrá":
                            renderer.setColor(Color.BLUE.getRGB());
                            break;
                        case "Zelená":
                            renderer.setColor(Color.GREEN.getRGB());
                            break;

                        case "Růžová":
                            renderer.setColor(Color.PINK.getRGB());
                            break;
                    }

                }



                super.keyReleased(e);
            }
        });

        setLocationRelativeTo(null);

        renderer = new Renderer(img);


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        }, 50, FPS);

        //  draw();
    }

    private void draw() {
        img.getGraphics().fillRect(0, 0, img.getWidth(), img.getHeight());

       /* if (type == DrawableType.LINE && !firstClick)  {

        }else if(type == DrawableType.N_OBJECT && !firstClick){
            renderer.lineDDA(clickX, clickY, coorX, coorY);
            renderer.lineDDA(startX, startY, coorX, coorY);

        }*/
//        renderer.lineDDA(clickX, clickY, coorX, coorY);
//        renderer.polygon(clickX, clickY, coorX, coorY,count);

        if (!firstClick && type != DrawableType.POLYGON){
            renderer.drawDashedLine(clickX,clickY,coorX,coorY);
           if (type == DrawableType.N_OBJECT)
            renderer.drawDashedLine(startX,startY,coorX,coorY);

        }
        if (type == DrawableType.POLYGON && !firstClick){
            renderer.polygon(clickX,clickY,coorX,coorY,count);
        }


        for (Drawable drawable:drawables
             ) {
            drawable.draw(renderer);

        }

        //vykreslovat všechny objekty


        panel.getGraphics().drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
        panel.paintComponents(getGraphics());

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        coorX = e.getX();
        coorY = e.getY();



    }


}

