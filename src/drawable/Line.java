package drawable;

import utils.Renderer;


    public class Line implements Drawable{

        int x1,y1,x2,y2;

        public Line(int x1, int y1, int x2, int y2) {
            this.y1 = y1;
            this.y2 = y2;
            this.x1 = x1;
            this.x2 = x2;
        }

        public int getX1() {
            return x1;
        }

        public int getY1() {
            return y1;
        }

        public int getX2() {
            return x2;
        }

        public int getY2() {
            return y2;
        }

        @Override
        public void draw(Renderer renderer) {
            // todo

            renderer.lineDDA(x1,y1,x2,y2);

        }
    }

