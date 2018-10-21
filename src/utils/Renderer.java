package utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer {
    BufferedImage img;
    private int color;
    private boolean dashed = false;
    private int count = 0;

    public Renderer(BufferedImage img) {
        this.img = img;
        color = Color.BLACK.getRGB();
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setColorByString(String s){
        switch (s){
            case "Černá":
                color =(Color.BLACK.getRGB());
                break;
            case "Červená":
                color =(Color.RED.getRGB());
                break;
            case "Modrá":
                color =(Color.BLUE.getRGB());
                break;
            case "Zelená":
                color =(Color.GREEN.getRGB());
                break;

            case "Růžová":
                color =(Color.PINK.getRGB());
                break;
        }
    }

    private void drawPixel(int x, int y) {
        if (x<0||x>=800)return;;
        if (y<0||y>=600)return;;
        img.setRGB(x, y, color);
    }

    private void drawPixelDashed(int x, int y , int d) {
        if (x<0||x>=800)return;;
        if (y<0||y>=600)return;;
        if (d % 4 < 0){

        }else{
            dashed = !dashed;
        }
        if (dashed) {
            img.setRGB(x, y, color);
        }




    }

    public void drawDashedLine(int x1, int y1, int x2, int y2){

        float[] floats = doDDA(x1,y1, x2, y2);

        int max = (int)floats[0];
        float x = floats[1];
        float y = floats[2];
        float G = floats[3];
        float H = floats[4];
        for (int i = 0; i <= max; i++) {
            drawPixelDashed(Math.round(x), Math.round(y) , i);
            x = x + G;
            y = y + H;
        }


    }

    public void lineTrivial(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;

        if (Math.abs(dx) > Math.abs(dy)) {
            //řídící osa X
            //otáčení souřadnic todo


            if (x1 > x2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;

                temp = y1;
                y1 = y2;
                y2 = temp;

            }


            double k, q;
            k = dy / (double) dx;


            for (int x = x1; x < x2; x++) {
                int y = y1 + (int) (k * (x - x1));
                drawPixel(x, y);
            }


        } else {
            //řídící osa y
            if (y1 > y2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;

                temp = y1;
                y1 = y2;
                y2 = temp;

            }


            double k, q;
            k = dx / (double) dy;


            for (int y = y1; y < y2; y++) {
                int x = x1 + (int) (k * (y - y1));
                drawPixel(x, y);
            }

        }
    }


    public void lineDDA(int x1, int y1, int x2, int y2) {
       float[] floats = doDDA(x1,y1, x2, y2);

        int max = (int)floats[0];
        float x = floats[1];
        float y = floats[2];
        float G = floats[3];
        float H = floats[4];
        for (int i = 0; i <= max; i++) {
            drawPixel(Math.round(x), Math.round(y));
            x = x + G;
            y = y + H;
        }



    }

    private float[] doDDA(int x1, int y1, int x2, int y2 ){
        float[] helfer = new float[10];
        int dx, dy;
        float x, y, k, G, H;
        dx = x2 - x1;
        dy = y2 - y1;

        k = dy / (float) dx;

        if (Math.abs(dx) > Math.abs(dy)) {
            G = 1;
            H = k;
            if (x1 > x2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        } else {
            G = 1 / k;
            H = 1;

            if (y1 > y2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        }

        x = x1;
        y = y1;

        int max = Math.max(Math.abs(dx), Math.abs(dy));
        helfer[0] = max;
        helfer[1] = x;
        helfer[2] = y;
        helfer[3] = G;
        helfer[4] = H;


        return helfer;
    }

    public void polygon(int x1, int y1, int x2, int y2, int count){

        double x0 = x2-x1;
        double y0 = y2-y1;
        double circleRadius = 2*Math.PI;

        double step = circleRadius/(double) count;
        for (double i = 0; i < circleRadius; i+=step){
            //dle rotační matice
            double x = x0*Math.cos(step) + y0 *Math.sin(step);
            double y = y0*Math.cos(step) - x0 *Math.sin(step);

            lineDDA((int) x0+x1, (int) y0+y1, (int) x+x1, (int) y+y1);
            //potřeba změnit x0,y0
            x0 = (int)x;
            y0 = (int)y;

        }
    }
}


