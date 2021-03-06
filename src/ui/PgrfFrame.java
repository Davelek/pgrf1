package ui;

import message.Message;
import drawable.Drawable;
import drawable.DrawableType;
import drawable.Line;
import drawable.Nuhelnik;
import drawable.Point;
import drawable.Polygon;
import utils.Renderer;

import javax.swing.*;
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
    Boolean last = false;
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
                    if(e.getButton() == MouseEvent.BUTTON3 && !firstClick) {
                        firstClick = !firstClick;
                    }else
                    if (firstClick) {
                        clickX = e.getX();
                        clickY = e.getY();
                        startX = clickX;
                        startY = clickY;
                        drawables.add(new Line(new Point(clickX, clickY)));
                        index++;
                        drawables.get(index).count(count);
                        firstClick = !firstClick;
                    } else {
                        clickX = e.getX();
                        clickY = e.getY();
                        drawables.get(index).addPoint(new Point(clickX, clickY));
                        firstClick = !firstClick;
                    }
                }
                if (type == DrawableType.N_OBJECT){
                    if(e.getButton() == MouseEvent.BUTTON3) {
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
                            drawables.add(new Nuhelnik());
                            index++;
                            drawables.get(index).addPoint(new Point(clickX, clickY));
                        } else {
                            clickX = e.getX();
                            clickY = e.getY();
                            drawables.get(index).addPoint(new Point(clickX, clickY));
                        }
                    }
                }
                if (type == DrawableType.POLYGON){
                    if(e.getButton() == MouseEvent.BUTTON3 && !firstClick) {
                        firstClick = !firstClick;
                    }else
                    if (firstClick) {
                        clickX = e.getX();
                        clickY = e.getY();
                        index += 1;
                        drawables.add(new Polygon(new Point(clickX, clickY)));
                        drawables.get(index).count(count);
                        firstClick = !firstClick;
                    }else{
                        clickX = e.getX();
                        clickY = e.getY();
                        firstClick = !firstClick;
                        drawables.get(index).addPoint(new Point(clickX,clickY));
                    }
                }
            super.mouseReleased(e);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_ADD)  {
                count++;
                    drawables.get(index).count(count);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_SUBTRACT) {

                    if (count == 3) return;
                    count--;
                    drawables.get(index).count(count);
                }
                if (e.getKeyCode() == KeyEvent.VK_L) {
                    if (type == DrawableType.LINE) {
                        Message message = new Message();
                        message.upozorneni("Linka už je zvolena");
                    } else {
                        Message message = new Message();
                        message.upozorneni("Zvolili jste linku");
                        type = DrawableType.LINE;
                        firstClick = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    if (type == DrawableType.N_OBJECT) {
                        Message message = new Message();
                        message.upozorneni("N-uhelník už je zvolen");
                    } else {
                        Message message = new Message();
                        message.upozorneni("Zvolili jste N-Uhelník");
                        type = DrawableType.N_OBJECT;
                        firstClick = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    if (type == DrawableType.POLYGON) {
                        Message message = new Message();
                        message.upozorneni("Polygon už je zvolen");
                    } else {
                        Message message = new Message();
                        message.upozorneni("Zvolili jste Polygon");
                        type = DrawableType.POLYGON;
                        firstClick = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    Message message = new Message();
                    Object[] possibilities = {"Černá", "Červená", "Modrá", "Zelená", "Růžová"};
                    String s = message.vyber("Vyberte barvu: ", "Výběr barvy",possibilities, "Černá");
                    renderer.setColorByString(s);
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE){
                    Message message = new Message();
                    Object[] possibilities = {"Linie", "N-úhelník", "Polygon"};
                    String s = message.vyber("Vyberte typ: ", "Výběr typu",possibilities, "Linie");
                    zmenitType(s);
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
    }


    private void draw() {
        img.getGraphics().fillRect(0, 0, img.getWidth(), img.getHeight());
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

   private void zmenitType(String s){
       switch (s){
           case "Linie":
               type = DrawableType.LINE;
               break;
           case "N-úhelník":
               type = DrawableType.N_OBJECT;
               break;
           case "Polygon":
               type = DrawableType.POLYGON;
               break;

       }

    }


}

