package popup.lab1;

/**
 * @author ThompZon
 */
public class Fenwick {
	private static boolean DEBUGGING = false;

	private long[] t;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Fenwick();
	}
	public Fenwick(){
		readAndCommand();
	}
	
	public Fenwick(int size){
		t = new long[size + 1];
	}
	
	public void readAndCommand(){
		Fenwick.DEBUGGING = false;
		if(DEBUGGING)System.out.println("Setting up IO...");
		Kattio kio = new Kattio(System.in);
		int n = kio.getInt();
		int ops = kio.getInt();
		
		t = new long[n + 1];
		
		if(DEBUGGING)System.out.println("STARTING OPS:");
		for(int i = 0; i < ops; i++){
			String op = kio.getWord();
			if(DEBUGGING)System.out.println("OP: " + i + " == " + op);
			if(op.equals("+")){
				int index = kio.getInt();
				int delta = kio.getInt();
				update(index, delta);
			}else if(op.equals("?")){
				int index = kio.getInt();
				System.out.println(query(index));
			}
		}
		if(DEBUGGING)System.out.println("Closing IO...");
		kio.close();
	}
	
	public void update(int i, int delta){
		for(; i < t.length; i |= i + 1){
			t[i] += delta;
		}
	}
	
	public long query(int i){
		long sum = 0;
		while(i > 0){
			sum += t[i];
			i = (i & (i + 1)) - 1;
		}
		return sum;
	}
	
//	public void update(int i, int delta){
//		if(DEBUGGING)System.out.println("Updating: " + i + ", with delta: " + delta);
//		while(i <= t.length){
//			t[i] = t[i] + delta;
//			i = i + (i & (-i));
//		}
//		if(DEBUGGING)System.out.println("Update Complete... Result:");
//		if(DEBUGGING)System.out.println(toString());
//
//	}
//
//	public long query(int i){
//		long sum = 0;
//		while(i >= 0){
//			sum += t[i];
//			i = i - (i & (-i));
//		}
//		return sum;
//	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < t.length; i++)
		{
			sb.append(t[i] + " ");
		}
		return sb.toString();
	}
	
	public long getVal(int i){
		return t[i];
	}
}
