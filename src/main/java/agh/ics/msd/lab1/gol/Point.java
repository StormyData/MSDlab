package agh.ics.msd.lab1.gol;

import java.util.ArrayList;

public class Point {

	//conways
	//private static final int[] ALIVE= new int[]{2,3};
	//private static final int[] DEAD = new int[]{3};

	//cities
	private static final int[] ALIVE= new int[]{2,3,4,5};
	private static final int[] DEAD = new int[]{4,5,6,7,8};

	//coral
	//private static final int[] ALIVE= new int[]{4,5,6,7,8};
	//private static final int[] DEAD = new int[]{3};


	private static boolean[] ALIVE_BIN = new boolean[9];
	private static boolean[] DEAD_BIN = new boolean[9];

	public static void update_rules(int[] alive,int[] dead){
		for (int i = 0; i < 9; i++) {
			ALIVE_BIN[i]= false;
			DEAD_BIN[i]= false;
		}
		for(int n : alive)
		{
			if(n<0 || n>8)
				throw new IllegalArgumentException("rule has to be between 0 and 8");
			ALIVE_BIN[n]= true;
		}
		for(int n : dead)
		{
			if(n<0 || n>8)
				throw new IllegalArgumentException("rule has to be between 0 and 8");
			DEAD_BIN[n]= true;
		}
	}


	static {
		for (int i = 0; i < 9; i++) {
			ALIVE_BIN[i]= false;
			DEAD_BIN[i]= false;
		}
		update_rules(ALIVE,DEAD);
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
		int n = nofAliveNeighbors();
		if(currentState == 0)
			nextState = DEAD_BIN[n] ? 1:0;
		else if(currentState == 1)
			nextState = ALIVE_BIN[n] ? 1:0;

		//T/ODO: insert logic which updates according to currentState and
		//number of active neighbors
	}

	public void changeState() {
		currentState = nextState;
	}
	
	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}
	
	//T/ODO: write method counting all active neighbors of THIS point
	private int nofAliveNeighbors()
	{
		return (int)neighbors.stream().filter(p -> p.currentState == 1).count();
	}
}
