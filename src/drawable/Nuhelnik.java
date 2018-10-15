package drawable;

import utils.Renderer;

import java.util.ArrayList;
import java.util.List;

public class Nuhelnik implements Drawable{

    List<Point> points;


    public Nuhelnik() {
        points = new ArrayList<>();
    }

    public void addPoint(Point p){
        points.add(p);
    }

    @Override
    public void draw(Renderer renderer) {
    //TODO  - forcyklus propojení bodů   
    }
}
