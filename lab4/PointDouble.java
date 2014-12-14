package popup.lab4;

public class PointDouble implements Point{
	private double x, y;
	public PointDouble(double x, double y){
		this.x = x;
		this.y = y;
	}

	@Override
	public Point add(Point other) {
		this.x += other.getX().doubleValue();
		this.y += other.getY().doubleValue();
		return this;
	}

	@Override
	public Point sub(Point other) {
		this.x -= other.getX().doubleValue();
		this.y -= other.getY().doubleValue();
		return this;
	}

	@Override
	public Point mul(Point other) {
		this.x *= other.getX().doubleValue();
		this.y *= other.getY().doubleValue();
		return this;
	}

	@Override
	public Point div(Point other) {
		this.x /= other.getX().doubleValue();
		this.y /= other.getY().doubleValue();
		return this;
	}

	@Override
	public Number dot(Point other) {
		return x * other.getX().doubleValue() + y * other.getY().doubleValue();
	}

	@Override
	public Number cross(Point other) {
		return x * other.getY().doubleValue() - other.getX().doubleValue() * y;
	}

	@Override
	public double distanse(Point other) {
		return Math.sqrt(
				(x - other.getX().doubleValue()) * (x - other.getX().doubleValue()) 
			   +(y - other.getY().doubleValue()) * (y - other.getY().doubleValue())
			   );
	}

	@Override
	public double angle(Point other) {
		return Math.atan2(this.cross(other).doubleValue(), this.dot(other).doubleValue());
	}
	
	
	public static Point pointsToVector(Point p1, Point p2){
		return new PointDouble(p2.getX().doubleValue() - p1.getX().doubleValue(), p2.getY().doubleValue() - p1.getY().doubleValue());
	}

	@Override
	public Number getX() {
		return x;
	}

	@Override
	public Number getY() {
		return y;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		sb.append(this.x);
		sb.append(", ");
		sb.append(this.y);
		sb.append(')');
		return sb.toString();
	}

	@Override
	public double distanceSquared(Point other) {
		//distance between points squared
		return (this.x - other.getX().doubleValue()) * (this.x - other.getX().doubleValue()) 
			  +(this.y - other.getY().doubleValue()) * (this.y - other.getY().doubleValue());
	}

}
