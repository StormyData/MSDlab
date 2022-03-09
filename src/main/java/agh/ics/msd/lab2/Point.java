package agh.ics.msd.lab2;


public class Point {
	public static Integer[] types = {0,1,2};
	private final static float C_SQUARED = 0.5f;

	public float sinInput;
	public int type;
	public Point nNeighbor;
	public Point wNeighbor;
	public Point eNeighbor;
	public Point sNeighbor;
	public float nVel;
	public float eVel;
	public float wVel;
	public float sVel;
	public float pressure;

	public Point() {
		clear();
	}

	public void clicked() {
		pressure = 1;
	}
	
	public void clear() {
		nVel = 0;
		eVel = 0;
		wVel = 0;
		sVel = 0;
		pressure = 0;
		sinInput = 0;
		type = 0;
	}

	public void updateVelocity() {
		if(type != 0)
			return;
		nVel -= nNeighbor.pressure - pressure;
		eVel -= eNeighbor.pressure - pressure;
		sVel -= sNeighbor.pressure - pressure;
		wVel -= wNeighbor.pressure - pressure;
	}

	public void updatePresure() {
		if(type == 1)
			return;
		if(type == 0)
			pressure -= C_SQUARED * (nVel + eVel + wVel + sVel);
		if(type == 2)
		{
			double radians = Math.toRadians(sinInput);
			pressure = (float) (Math.sin(radians));
		}
	}

	public float getPressure() {
		return pressure;
	}
}