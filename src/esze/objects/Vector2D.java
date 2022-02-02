package esze.objects;

public class Vector2D {

	
	public double getX() {
		return x;
	}



	public void setX(double x) {
		this.x = x;
	}



	public double getY() {
		return y;
	}



	public void setY(double y) {
		this.y = y;
	}



	double x;
	double y;
	
	public Vector2D(double x,double y) {
		this.x = x;
		this.y = y;
	}
	
	
	
	public Vector2D subtract(Vector2D other) {
		
		return new Vector2D(x-other.x,y-other.y);
	}
}
