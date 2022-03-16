package agh.ics.msd.lab3;

import java.util.Random;

public class Point {

    public static final Random random = new Random();
    public static final float p = 0.05f;
    public static final int MAX_SPEED = 5;
    public static final int INITIAL_SPEED = 1;
    public int type;
    public Point next[] = new Point[MAX_SPEED + 1];
    public boolean moved;
    public int speed;

    public void update()
    {
        if(speed<MAX_SPEED)
            speed++;

        int dist=MAX_SPEED;
        for(int i=0;i<=MAX_SPEED;i++)
        {
            if(next[i].type==1)
            {
                dist = i;
                break;
            }
        }
        if(speed>dist)
            speed=dist;
        if(speed>0 && random.nextFloat()<=p)
            speed--;
    }
    public void move()
    {
        if(type==1 && !moved)
        {
            if(speed>0)
            {
                next[speed - 1].type = 1;
                next[speed - 1].moved = true;
                type = 0;
                next[speed -1].speed=speed;
            }
            moved = true;

        }
    }

    public void clicked() {
        type = 1;
        speed = INITIAL_SPEED;
    }

    public void clear() {
        type = 0;
        speed = 0;
    }
}

