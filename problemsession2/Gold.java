package popup.problemsession2;

import java.util.ArrayDeque;

/**
 * 
7 4
#######
#P.GTG#
#..TGG#
#######

8 6
########
#...GTG#
#..PG.G#
#...G#G#
#..TG.G#
########
 */
public class Gold {
	private static final boolean DEBUGGING = true;
	private static final int VISITED = 4, GOLD = 2, WALL = 1;
	
	private class Coord{
		public int X;
		public int Y;
		public Coord(int x, int y){
			this.X = x;
			this.Y = y;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//DEBUGGING = false;
		new Gold();
	}

	int[][] board;
	Coord start;
	
	public Gold(){
		Kattio kio = new Kattio(System.in);
		int w = kio.getInt();
		int h = kio.getInt();
		board = new int[h][w];

		for(int y = 0; y < h; y++){
			String line = kio.getWord();
			for(int x = 0; x < w; x++){
				switch(line.charAt(x)){
				case '#':
					board[y][x] |= WALL;
					break;
				case 'G':
					board[y][x] |= GOLD;
					break;
				case 'T':
					board[y][x] |= WALL;
					board[y+1][x] |= WALL;
					board[y-1][x] |= WALL;
					board[y][x+1] |= WALL;
					board[y][x-1] |= WALL;
					break;
				case 'P':
					start = new Coord(x, y);
					break;
				}
			}
		}
		kio.println(getSolution());
		kio.flush();
		kio.close();
	}

	public int getSolution(){
		int currGold = 0;
		
		ArrayDeque<Coord> q = new ArrayDeque<Coord>();
		q.add(start);
		board[start.Y][start.X] |= VISITED;
		Coord curr;
		while(q.isEmpty() == false){
			curr = q.poll();
			if((board[curr.Y][curr.X] & GOLD) == GOLD){
				currGold += 1;
			}
			if((board[curr.Y][curr.X] & WALL) == 0){
				if((board[curr.Y][curr.X + 1] & VISITED) == 0){
					board[curr.Y][curr.X + 1] |= VISITED;
					q.add(new Coord(curr.X + 1, curr.Y));
				}
				if((board[curr.Y][curr.X - 1] & VISITED) == 0){
					board[curr.Y][curr.X - 1] |= VISITED;
					q.add(new Coord(curr.X - 1, curr.Y));
				}
				if((board[curr.Y + 1][curr.X] & VISITED) == 0){
					board[curr.Y + 1][curr.X] |= VISITED;
					q.add(new Coord(curr.X, curr.Y + 1));
				}
				if((board[curr.Y - 1][curr.X] & VISITED) == 0){
					board[curr.Y - 1][curr.X] |= VISITED;
					q.add(new Coord(curr.X, curr.Y - 1));
				}
			}
		}
		
		return currGold;
	}
}
