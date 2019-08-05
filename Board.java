import java.util.Scanner;
import java.util.ArrayList;
public class Board {
	int[][] board = new int[9][9];
	//smart board exists as a list of all the remaining possible numbers for each square, used to make concrete decisions and calculate how long a full guess calcuation would take.
	ArrayList<Integer>[][] smartboard = new ArrayList[9][9];
	public Board() {
		System.out.println("Manually enter each square, put 0 if it is blank");
		for (int a = 0; a < 9; a++) {
			for(int b = 0; b < 9; b++) {
				smartboard[a][b] = new ArrayList<Integer>();
				board[a][b] = 0;
			}		
		}	
		//sample board for bug testing
		board[0][0] = 0;
		board[0][1] = 0;
		board[0][2] = 8;
		board[0][3] = 0;
		board[0][4] = 3;
		board[0][5] = 9;
		board[0][6] = 0;
		board[0][7] = 0;
		board[0][8] = 1;
		board[1][0] = 0;
		board[1][1] = 0;
		board[1][2] = 0;
		board[1][3] = 0;
		board[1][4] = 8;
		board[1][5] = 0;
		board[1][6] = 7;
		board[1][7] = 0;
		board[1][8] = 0;
		board[2][0] = 0;
		board[2][1] = 1;
		board[2][2] = 0;
		board[2][3] = 0;
		board[2][4] = 0;
		board[2][5] = 0;
		board[2][6] = 3;
		board[2][7] = 2;
		board[2][8] = 0;
		board[3][0] = 0;
		board[3][1] = 0;
		board[3][2] = 5;
		board[3][3] = 9;
		board[3][4] = 0;
		board[3][5] = 0;
		board[3][6] = 0;
		board[3][7] = 7;
		board[3][8] = 0;
		board[4][0] = 0;
		board[4][1] = 2;
		board[4][2] = 0;
		board[4][3] = 0;
		board[4][4] = 0;
		board[4][5] = 0;
		board[4][6] = 0;
		board[4][7] = 9;
		board[4][8] = 0;
		board[5][0] = 0;
		board[5][1] = 3;
		board[5][2] = 0;
		board[5][3] = 0;
		board[5][4] = 0;
		board[5][5] = 6;
		board[5][6] = 4;
		board[5][7] = 0;
		board[5][8] = 0;
		board[6][0] = 0;
		board[6][1] = 6;
		board[6][2] = 4;
		board[6][3] = 0;
		board[6][4] = 0;
		board[6][5] = 0;
		board[6][6] = 0;
		board[6][7] = 3;
		board[6][8] = 0;
		board[7][0] = 0;
		board[7][1] = 0;
		board[7][2] = 3;
		board[7][3] = 0;
		board[7][4] = 1;
		board[7][5] = 0;
		board[7][6] = 0;
		board[7][7] = 0;
		board[7][8] = 0;
		board[8][0] = 7;
		board[8][1] = 0;
		board[8][2] = 0;
		board[8][3] = 5;
		board[8][4] = 6;
		board[8][5] = 0;
		board[8][6] = 8;
		board[8][7] = 0;
		board[8][8] = 0;
		
		//set all the smart board possibilities ahead of time so as not to unintentionally reset any deductive progress
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] == 0) {
					for (int k = 0; k < 9; k++) {
						smartboard[i][j].add(k + 1);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		Board g = new Board();
		g.print();
		
		while (!g.done() ) {
			g.simplecheck();
			g.mediumcheck();
			g.hardcheck();
			g.valid();
		}
		g.print();
	}
	public void print() {
		for (int a = 0; a < 9; a++) {
			for(int b = 0; b < 9;b++) {
				System.out.print(" _ ");
			}
			System.out.println();
			for(int b = 0; b < 9;b++) {
				System.out.print("|" + board[a][b] + "|");
			}
			System.out.println();
		}
		for(int b = 0; b < 9;b++) {
			System.out.print(" _ ");
		}
		System.out.println();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void printsmartboard(int x, int y) {
		//System.out.print("Square " + x + y + " has possible solultions:" );
		for (int i = 0; i < smartboard[x][y].size(); i++) {
			System.out.print(smartboard[x][y].get(i));
		}
		System.out.println();
	}
	public boolean done() {
		boolean check = true;
		for (int a = 0; a < 9; a++) {
			for (int b = 0; b < 9; b++) {
				if (board[a][b] == 0) {
					check = false;
				}
			}
		}
		System.out.println("The puzzle is " + check + " done");
		return check;
	}
	public boolean valid() {
		boolean all_valid = true;
		//All rows
		for (int a = 0; a < 9; a++) {
			boolean valid = true;
			int[] rowcheck = new int[9];
			for (int b = 0; b < 9; b++) {
				if (inside(board[a][b],rowcheck)) {
					valid = false;
					all_valid = false;
				}
				rowcheck[b] = board[a][b];
				
			}
			if (valid) {
				//System.out.println("Row " + (a + 1) + " is valid");
			}
			else {
				//System.out.println("Row " + (a + 1) + " is not valid");
			}
			
		}
		//All columns
		for (int a = 0; a < 9; a++) {
			boolean valid = true;
			int[] rowcheck = new int[9];
			for (int b = 0; b < 9; b++) {
				if (inside(board[b][a],rowcheck)) {
					valid = false;
					all_valid = false;
				}
				rowcheck[a] = board[b][a];
				
			}
			if (valid) {
				//System.out.println("Column " + (a + 1) + " is valid");
			}
			else {
				//System.out.println("Column " + (a + 1) + " is not valid");
			}
			
		}
		//All squares
		for(int a = 0; a < 9; a += 3) {
			for(int b = 0; b < 9; b += 3) {
				boolean valid = true;
				int[] squarecheck = new int[9];
				int count = 0;
				for(int c = a; c < a + 3; c++) {
					for(int d = b; d < b + 3; d++) {
						if(inside(board[c][d],squarecheck)){
							valid = false;
							all_valid= false;
						}
						squarecheck[count] = board[c][d];
						count++;
					}
				}
				if (valid) {
					//System.out.println("Square " + ((b/3) + a + 1) + " is valid");
				}
				else {
					//System.out.println("Square " + ((b/3) + a + 1) + " is not valid");
				}
			}
		}
		System.out.println("The puzzle is " + all_valid + " valid");
		return all_valid;
	}
	public static boolean inside(int target, int[] input) {
		for (int a = 0; a < input.length; a++) {
			if (input[a] == target && target != 0) {
				return true;
			}
		}
		return false;
	}
	
	public void smartupdate() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (smartboard[i][j].size() == 1 && board[i][j] == 0) {
					System.out.println("Changed " + i + j + " to " + smartboard[i][j].get(0));
					board[i][j] = smartboard[i][j].get(0);
					smartboard[i][j].clear();
					simplecheck();
				}
			}
		}
	}
	//check for easy comparisons, guaranteed eliminations where there is only one possible location for a number
	public void simplecheck() {
		//eliminate from the possibilities all the known squares via column and row check
		for (int a = 0; a < 9; a++) {
			for (int b = 0; b < 9; b++) {
				if(board[a][b] != 0) {
					//System.out.println("Number found at " + a + b);
					for (int c = 0; c < 9; c++) {						
						smartboard[c][b].remove(new Integer(board[a][b]));
						smartboard[a][c].remove(new Integer(board[a][b]));
					}
				}
				else {	
						//printsmartboard(a,b);
				}
				
			}
		}
		//remove in squares
		for(int a = 0; a < 9; a += 3) {
			for(int b = 0; b < 9; b += 3) {
				//check each box
				for(int c = a; c < a + 3; c++) {
					for(int d = b; d < b + 3; d++) {
						//check each square in the box 
						if (board[c][d] != 0) {
							for(int e = a; e < a + 3; e++) {
								for(int f = b; f < b + 3; f++) {
									if (board[e][f] == 0) {
										smartboard[e][f].remove(new Integer(board[c][d]));
									}
								}
							}
						}
					}
				}
			}
		}
		print();
	}
	//slightly more complex method to check for rows, columns, boxes, where there is one possibility 
	public void mediumcheck() {
		//check rows for single instance solutions
		for(int a = 0; a < 9; a++) {
			int[] columncount = new int[9];
			for(int i = 0; i < 9; i++) {
				columncount[i] = 0; 
			}
			for(int b = 0; b < 9; b++) {
				for(int c = 0; c < 9; c++) {
					if (smartboard[a][b].contains(c + 1)) {
						columncount[c]++;
					}
				}
			}
			//display possible solutions
			//System.out.print("Row " + a);
			for(int i = 0; i < 9; i++) {
				System.out.print("[" + columncount[i] + "]"); 
			}
			System.out.println();

			for(int b = 0; b < 9; b++) {
				for(int i = 0; i < 9; i++) {
					if ((smartboard[a][b].contains(i + 1)) && (columncount[i] == 1) && (board[a][b] == 0)) {
						//System.out.println("columnChanged " + a + b + " to " + (i + 1) );
						board[a][b] = i + 1;
						smartboard[a][b].clear();
						simplecheck();
					}
				}
			}
		}
		//check the columns
		for(int a = 0; a < 9; a++) {
			int[] rowcount = new int[9];
			for(int i = 0; i < 9; i++) {
				rowcount[i] = 0; 
			}
			for(int b = 0; b < 9; b++) {
				for(int c = 0; c < 9; c++) {
					if (smartboard[b][a].contains(c + 1)) {
						rowcount[c]++;
					}
				}
			}
			//display possible solutions for single instance solutions
			System.out.print("Row " + a);
			for(int i = 0; i < 9; i++) {
				System.out.print("[" + rowcount[i] + "]"); 
			}
			System.out.println();

			for(int b = 0; b < 9; b++) {
				for(int i = 0; i < 9; i++) {
					if ((smartboard[b][a].contains(i + 1)) && (rowcount[i] == 1) && (board[b][a] == 0)) {
						//System.out.println("rowChanged " + b + a + " to " + (i + 1) );
						board[b][a] = i + 1;
						smartboard[b][a].clear();
						simplecheck();
					}
				}
			}
		}
		//check the boxes for single instance solutions
		for (int a = 0; a < 9; a += 3) {
			for (int b = 0; b < 9; b+= 3) {
				//check each square
				int[] boxcount = new int [9];
				for(int i = 0; i < 9; i++) {
					boxcount[i] = 0; 
				}
				for(int c = a; c < a + 3; c++) {
					for(int d = b; d < b + 3; d++) {
						for (int e = 0; e < 9; e++) {
							if(smartboard[c][d].contains(e + 1)) {
								boxcount[e]++;
							}
						}
					}
				}
				for(int c = a; c < a + 3; c++) {
					for(int d = b; d < b + 3; d++) {
						for (int e = 0; e < 9; e++) {
							//smartupdate();
							
							//smartupdate();
							if(smartboard[c][d].contains(e + 1) && (boxcount[e] == 1 && board[c][d] == 0)) {
								//System.out.println("changed box");
								board[c][d] = e + 1;
								smartboard[c][d].clear();
								simplecheck();
							}
						}
					}
				}
			}
		}
		
	}
	public void hardcheck() {
		for (int a = 0; a < 9; a += 3) {
			for (int b = 0; b < 9; b+= 3) {
				//Each Square
				//first check for rows
				for (int c = 1; c < 10; c++) {
					//check if a number can only go in first row
					boolean[] row = new boolean[3];
					for(int d = 0; d < 3; d++){
						row[d] = false;
						if (smartboard[a+d][b].contains(new Integer(c)) || smartboard[a+d][b+1].contains(new Integer(c)) || smartboard[a+d][b+2].contains(new Integer(c))) {
							row[d] = true;
							for(int y = 0; y < 3; y++) {
								for(int z = 0; z < 3; z++) {
									if (smartboard[a+y][b+z].contains(new Integer(c)) && y != d) {
										row[d] = false;
									}
								}
							}
						}
						if(row[d] == true) {
							for (int z = 0; z < 9;z++) {
								if(z != b && z != b + 1 && z != b + 2) {
									smartboard[a+d][z].remove(new Integer(c));
								}
							}
						}
					//check if number can only go in second row
						
					}
					boolean[] column = new boolean[3];
					for(int d = 0; d < 3; d++){
						column[d] = false;
						if (smartboard[a][b+d].contains(new Integer(c)) || smartboard[a+1][b+d].contains(new Integer(c)) || smartboard[a+2][b+d].contains(new Integer(c))) {
							column[d] = true;
							for(int y = 0; y < 3; y++) {
								for(int z = 0; z < 3; z++) {
									if (smartboard[a+y][b+z].contains(new Integer(c)) && z != d) {
										column[d] = false;
									}
								}
							}
						}
						if(column[d] == true) {
							for (int z = 0; z < 9; z++) {
								if(z != a && z != a + 1 && z != a + 2) {
									smartboard[z][b+d].remove(new Integer(c));
								}
							}
						}
					}
				}
			}
		}
	}

}
