package agh.ics.msd.lab1.rain;

import java.util.ArrayList;
import java.util.Random;


public class Point {
	private static Random random = new Random();
	private static int[] ALIVE= new int[]{2,3,4,5};
	private static int[] DEAD = new int[]{4,5,6,7,8};
	private static boolean[] ALIVE_BIN = new boolean[9];
	private static boolean[] DEAD_BIN = new boolean[9];
	static {
		for (int i = 0; i < 9; i++) {
			ALIVE_BIN[i]= false;
			DEAD_BIN[i]= false;
		}
		for(int n : ALIVE)
		{
			if(n<0 || n>8)
				throw new IllegalArgumentException("rule has to be between 0 and 8");
			ALIVE_BIN[n]= true;
		}
		for(int n : DEAD)
		{
			if(n<0 || n>8)
				throw new IllegalArgumentException("rule has to be between 0 and 8");
			DEAD_BIN[n]= true;
		}
	}


	private ArrayList<Point> neighbors;
	private int currentState;
	private int nextState;
	private int numStates = 6;
	
	public Point() {
		currentState = 0;
		nextState = 0;
		neighbors = new ArrayList<Point>();
	}

	public void clicked() {
		currentState=(++currentState)%numStates;	
	}
	
	public int getState() {
		return currentState;
	}

	public void setState(int s) {
		currentState = s;
	}

	public void calculateNewState() {
		nextState=currentState;
		if(nextState>0)
			nextState-=1;
		if(neighbors.size()==0)
			return;
		if(currentState == 0 && neighbors.get(0).currentState > 0)
			nextState = 6;


		//T/ODO: insert logic which updates according to currentState and
		//number of active neighbors
	}

	public void changeState() {
		currentState = nextState;
	}
	
	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}
	
	public void drop()
	{
		if(random.nextInt(100)<5)
			nextState = 6;
	}
}
