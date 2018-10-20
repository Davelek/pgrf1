package drawable;

import utils.Renderer;

import java.util.ArrayList;
import java.util.List;

public class Nuhelnik implements Drawable{

    List<Point> points;
    private int index = -1;


    public Nuhelnik() {
        points = new ArrayList<>();
    }

    public void addPoint(Point p){
        points.add(p);
        index++;
    }

    @Override
    public void draw(Renderer renderer) {
    //TODO  - forcyklus propojení bodů

        if (index >0){
            for (int i = 0; i <=index; i++) {
                if (i == index){
                    renderer.lineDDA(points.get(i).getX(),points.get(i).getY(),points.get(0).getX(),points.get(0).getY() );

                }else{
                    renderer.lineDDA(points.get(i).getX(),points.get(i).getY(),points.get(i+1).getX(),points.get(i+1).getY() );
                }
            }
        }



        }
    }

