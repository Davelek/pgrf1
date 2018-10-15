package ui;

import drawable.Drawable;
import drawable.DrawableType;
import drawable.Line;
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
Boolean firstClickLine=true;
private DrawableType type = DrawableType.LINE;

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
            public void mouseClicked(MouseEvent e) {
                if(type ==DrawableType.LINE) {
                    if (firstClickLine) {

                        clickX = e.getX();
                        clickY = e.getY();

                    } else {
                        drawables.add(new Line(clickX, clickY, e.getX(), e.getY()));
                        clickX = e.getX();
                        clickY = e.getY();


                    }
                    firstClickLine = !firstClickLine;
                }
                if (type == DrawableType.N_OBJECT){
                    //TODO váš n-úhelník
                }
                if (type == DrawableType.POLYGON){
                    //TODO polygon
                }
             //   drawables.add(new Line(e.getX(),e.getY()));
            super.mouseClicked(e);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    //šipka nahoru

                    count++;
                }


                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    //plus na numerické klávesnici
                    if (count == 3)return;
                    count--;
                }

                if(e.getKeyCode() == KeyEvent.VK_ADD){
                    //plus na numerické klávesnici
                }

                if(e.getKeyCode() == KeyEvent.VK_SUBTRACT){
                    //minus na numerické klávesnici
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
        }, 100, FPS);

        //  draw();
    }

    private void draw() {
        img.getGraphics().fillRect(0, 0, img.getWidth(), img.getHeight());
//        renderer.lineDDA(clickX, clickY, coorX, coorY);
//        renderer.polygon(clickX, clickY, coorX, coorY,count);
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

