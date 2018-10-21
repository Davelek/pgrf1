package drawable;

import utils.Renderer;

public class Polygon implements Drawable {
    private Point point;
    private Point point2;
    private int count = 5;
    private boolean moznoUdelat = false;

    public Polygon(Point point) {
        this.point = point;
    }

    @Override
    public void addPoint(Point p) {
        point2 = p;
        moznoUdelat = true;

    }

    @Override
    public void count(int count) {
        this.count = count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void draw(Renderer renderer) {
        if (moznoUdelat){
            renderer.polygon(point.getX(),point.getY(), point2.getX(),point2.getX(), count);

        }

    }
}
