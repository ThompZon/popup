package popup.lab4;

public class PolygonArea {
	/**
	 * Calculates the area of the polygon defined by the points
	 * @param pts the points defining the polygon
	 * @return the area of the polygon, positive if the points is in Counter Clockwise order, negative if Clockwise
	 */
	public static double getPolygonArea(Point[] pts){
		double area = 0;
		for(int i = 1; i < pts.length; i++){
			area += pts[i-1].cross(pts[i]).doubleValue();
		}
		area += pts[pts.length-1].cross(pts[0]).doubleValue();
		area /= 2;
		return area;
	}
	
	public static void main(String[] args) {
		Kattio kio = new Kattio(System.in);
		int points = kio.getInt();
		while(points != 0){
			Point[] pts = new Point[points];
			for(int i = 0; i < points; i++){
				pts[i] = new PointInt(kio.getInt(), kio.getInt());
			}
			double area = getPolygonArea(pts);
			kio.print(((area > 0.0) ? "CCW " : "CW "));
			kio.printf("%.1f\n", Math.abs(area));
			
			points = kio.getInt();
		}
		kio.flush();
		kio.close();
	}

}
