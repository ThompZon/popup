package popup.lab4;

public class PointInt implements Point{
	private int x, y;
	public PointInt(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public Point add(Point other) {
		this.x += other.getX().intValue();
		this.y += other.getY().intValue();
		return this;
	}

	@Override
	public Point sub(Point other) {
		this.x -= other.getX().intValue();
		this.y -= other.getY().intValue();
		return this;
	}

	@Override
	public Point mul(Point other) {
		this.x *= other.getX().intValue();
		this.y *= other.getY().intValue();
		return this;
	}

	@Override
	public Point div(Point other) {
		this.x /= other.getX().intValue();
		this.y /= other.getY().intValue();
		return this;
	}

	@Override
	public Number dot(Point other) {
		return x * other.getX().intValue() + y * other.getY().intValue();
	}

	@Override
	public Number cross(Point other) {
		return x * other.getY().intValue() - other.getX().intValue() * y;
	}

	@Override
	public double distanse(Point other) {
		return Math.sqrt((x - other.getX().intValue()) * (x - other.getX().intValue()) 
						+(y - other.getY().intValue()) * (y - other.getY().intValue()));
	}

	@Override
	public double angle(Point other) {
		return Math.atan2(this.cross(other).doubleValue(), this.dot(other).doubleValue());
	}

	@Override
	public Number getX() {
		return x;
	}

	@Override
	public Number getY() {
		return y;
	}

}
