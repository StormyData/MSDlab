package agh.ics.msd.lab4;

import java.util.Arrays;
import java.util.Random;

public class Point {

    private static final Random random = new Random();
    public static final Integer[] types = {0,1,2,3,4,5};
    public static final float p = 0.05f;
    public static final int[] MAX_SPEEDS = {3, 5, 7};
    public static final int MAX_SPEED = Arrays.stream(MAX_SPEEDS).max().orElse(0);
    public static final int INITIAL_SPEED = MAX_SPEEDS[0];
    public int type;

    public Point[] forwardNeighborhoodCurrentLane = new Point[MAX_SPEED + 1];
    public Point[] forwardNeighborhoodLowerLane = new Point[MAX_SPEED + 1];
    public Point[] forwardNeighborhoodUpperLane = new Point[MAX_SPEED + 1];
    public Point[] backwardNeighborhoodCurrentLane = new Point[MAX_SPEED + 1];
    public Point[] backwardNeighborhoodLowerLane = new Point[MAX_SPEED + 1];
    public Point[] backwardNeighborhoodUpperLane = new Point[MAX_SPEED + 1];
    public Point upperNeighborhood;
    public Point lowerNeighborhood;
    private Point next = this;

    public boolean moved;
    public int speed;


    public void doNormalMove() {
        if(!isCar())
            return;
        if(speed < getMaxSpeed())
            speed++;
        if(speed > forwardDistanceCurrent() - 1)
            speed = forwardDistanceCurrent() - 1;
        next = this;
        if(speed > 0)
            next = forwardNeighborhoodCurrentLane[speed - 1];
    }

    public void tryEndOvertake() {
        if(!isCar())
            return;
        if(lowerNeighborhood == null)
            return;
        if(!lowerNeighborhood.free())
            return;
        if(backwardDistanceCurrent() > MAX_SPEED)
            return;
        if(backwardDistanceLower() < MAX_SPEED)
            return;
        if(forwardDistanceLower() < speed)
            return;
        next = lowerNeighborhood;
    }

    public void tryBeginOvertake() {
        if(!isCar())
            return;
        if(upperNeighborhood == null)
            return;
        if(!upperNeighborhood.free())
            return;
        if(speed == getMaxSpeed())
            return;
        if(backwardDistanceCurrent() < MAX_SPEED)
            return;
        if(backwardDistanceUpper() < MAX_SPEED)
            return;
        if(forwardDistanceUpper() < speed)
            return;
        speed++;
        next = upperNeighborhood;
    }

    public void move()
    {
        if(isCar() && !moved && next != this && !next.isCar())
        {
            next.type = type;
            next.moved = true;
            type = 0;
            next.speed=speed;
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
    private int forwardDistanceUpper()
    {
        if(upperNeighborhood == null)
            return -1;
        if(!upperNeighborhood.free())
            return 0;
        for(int i = 0; i<= MAX_SPEED; i++)
            if(!forwardNeighborhoodUpperLane[i].free())
                return i + 1;
        return MAX_SPEED + 2;
    }
    private int forwardDistanceCurrent()
    {
        for(int i = 0; i<= MAX_SPEED; i++)
            if(!forwardNeighborhoodCurrentLane[i].free())
                return i + 1;
        return MAX_SPEED + 2;
    }
    private int forwardDistanceLower()
    {
        if(lowerNeighborhood == null)
            return -1;
        if(!lowerNeighborhood.free())
            return 0;
        for(int i = 0; i<= MAX_SPEED; i++)
            if(!forwardNeighborhoodLowerLane[i].free())
                return i + 1;
        return MAX_SPEED + 2;
    }

    private int backwardDistanceUpper()
    {
        if(upperNeighborhood == null)
            return -1;
        if(!upperNeighborhood.free())
            return 0;
        for(int i = 0; i<= MAX_SPEED; i++)
            if(!backwardNeighborhoodUpperLane[i].free())
                return i + 1;
        return MAX_SPEED + 2;
    }
    private int backwardDistanceCurrent()
    {
        for(int i = 0; i<= MAX_SPEED; i++)
            if(!backwardNeighborhoodCurrentLane[i].free())
                return i + 1;
        return MAX_SPEED + 2;
    }
    private int backwardDistanceLower()
    {
        if(lowerNeighborhood == null)
            return -1;
        if(!lowerNeighborhood.free())
            return 0;
        for(int i = 0; i<= MAX_SPEED; i++)
            if(!backwardNeighborhoodLowerLane[i].free())
                return i + 1;
        return MAX_SPEED + 2;
    }
    private boolean free()
    {
        return type == 0;
    }
    private boolean isCar()
    {
        return type == 1 || type == 2 || type == 3;
    }
    public int getMaxSpeed()
    {
        if(!isCar())
            return 0;
        //noinspection ConstantConditions
        return MAX_SPEEDS[type - 1];
    }
}

