package popup.lab4;

public class PolygonArea {
	
	public static double getPolygonArea(Point[] pts){
		return 0;
	}
	
	public static void main(String[] args) {
		Kattio kio = new Kattio(System.in);
		int points = kio.getInt();
		while(points != 0){
			Point firstPt = new PointInt(kio.getInt(), kio.getInt());
			Point prevPt = firstPt;
			Point currPt;
			double sum = 0;
			for(int i = 1; i < points; i++){
				currPt = new PointInt(kio.getInt(), kio.getInt());
				sum += prevPt.cross(currPt).doubleValue();
				prevPt = currPt;
			}
			sum += prevPt.cross(firstPt).doubleValue();
			sum /= 2;
			
			kio.print(((sum > 0.0) ? "CCW " : "CW "));
			kio.printf("%.1f\n", Math.abs(sum));
			
			points = kio.getInt();
		}
		kio.flush();
		kio.close();
	}

}
