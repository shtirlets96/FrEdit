package Model;

import java.io.Serializable;

public class Point implements Serializable {
    public double x,y;
    private static final long serialVersionUID = 1;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
}
