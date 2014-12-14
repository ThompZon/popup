package popup.lab4;


public interface Point {
	public Point add(Point other);
	public Point sub(Point other);
	public Point mul(Point other);
	public Point div(Point other);
	
	public Number dot(Point other);
	public Number cross(Point other);
	public double distanse(Point other);
	public double distanceSquared(Point other); //Slightly faster than the above
	public double angle(Point other);
	
	public Number getX();
	public Number getY();
}
