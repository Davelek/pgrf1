package utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer {
    BufferedImage img;
    private int color;


    public Renderer(BufferedImage img) {
        this.img = img;
        color = Color.RED.getRGB();
    }


    private void drawPixel(int x, int y) {
        img.setRGB(x, y, color);
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
        for (int i = 0; i <= max; i++) {
            drawPixel(Math.round(x), Math.round(y));
            x = x + G;
            y = y + H;
        }


    }
}


