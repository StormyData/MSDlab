package agh.ics.msd.lab5;
import javax.xml.stream.FactoryConfigurationError;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class Point {

	public ArrayList<Point> neighbors;
	public static Integer []types ={0,1,2,3};
	public int type;
	public int staticField;
	public int wallField;
	public int dynamic_field;
	public boolean isPedestrian;
	public boolean blocked = false;


	public Point() {
		type=0;
		staticField = 100000;
		neighbors= new ArrayList<Point>();
		wallField = 0;
		dynamic_field = 0;
	}
	
	public void clear() {
		staticField = 100000;
		wallField = 100000;
		dynamic_field = 100000;
	}

	public boolean calcStaticField() {
		if(type == 1)
		{
			if(staticField != 100000)
			{
				staticField = 100000;
				return true;
			}
			return false;
		}
		int new_val = neighbors.stream().map(p -> p.staticField).min(Integer::compareTo).orElse(100000) + 1;
		if(new_val < staticField)
		{
			staticField = new_val;
			return true;
		}
		return false;
	}
	public boolean calcWallField() {
		if(type == 1)
		{
			if(wallField != 0)
			{
				wallField = 0;
				return true;
			}
			return false;
		}
		int new_val = neighbors.stream().map(p -> p.wallField).min(Integer::compareTo).orElse(10000) + 1;
		if(new_val < wallField)
		{
			wallField = new_val;
			return true;
		}
		return false;
	}
	public boolean calcDynamicField() {
		if(type == 1)
		{
			if(dynamic_field != 10000)
			{
				dynamic_field = 10000;
				return true;
			}
			return false;
		}
		int new_val = neighbors.stream().map(p -> p.dynamic_field).min(Integer::compareTo).orElse(10000) + 1;
		if(new_val < dynamic_field)
		{
			dynamic_field = new_val;
			return true;
		}
		return false;
	}

	public void move(){
		if(!isPedestrian || blocked)
			return;
		Optional<Point> op = neighbors.stream()
				.filter(p -> !p.isPedestrian && (p.type == 0 || p.type == 2))
				.min(Comparator.comparingDouble(Point::getField));
		if(op.isEmpty())
			return;
		Point p = op.get();
		isPedestrian = false;
		if(p.type != 2)
		{
			p.isPedestrian = true;
			p.blocked = true;
		}

	}
	public float getField()
	{
		return staticField;
//		float a = 1.0f;
//		float b = 10.0f;
//		float c = 1000.0f;
//		return a * staticField + b / wallField + c / dynamic_field;

	}

	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}
}