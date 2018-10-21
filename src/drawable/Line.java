package drawable;

import utils.Renderer;


    public class Line implements Drawable{

        int x1,y1,x2,y2;

        @Override
        public void count(int count) {

        }

        public Line(Point p) {
            this.y1 = p.getY();

            this.x1 = p.getX();
            this.x2=0;
            this.y2=0;

        }



        @Override
        public void draw(Renderer renderer) {
            // todo
            if (x2 != 0 && y2 != 0)
            renderer.lineDDA(x1,y1,x2,y2);

        }

        @Override
        public void addPoint(Point p) {
            this.y2 = p.getY();
            this.x2 = p.getX();
        }
    }

