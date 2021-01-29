package game;

import java.awt.*;
import java.util.Random;

public class mapgenerator {
	
	//declare variables
	Random rand = new Random();
	public int map[][]; //2D array for the bricks
	public int brickw;
	public int brickh;
	Color color;
	
	//generate random colors
	int r = 220;
	int h = 80;
	int b = 170+ rand.nextInt(255) /3; 

	/*****************************
	 * Class method
	 * @param row
	 * @param col
	 * Pre: the number of rows and columns
	 * Post: sets the value of all of the bricks to 1
	 ***************************************/
	public mapgenerator(int row, int col) {
		map = new int[row][col];
		
		//set the values of the bricks
		for(int i = 0; i<map.length; i++) {
			for(int j = 0;j< map[0].length; j++) {
				map[i][j] = 1;
			}
		}
		
		//initialize the values
		brickw = 50; //width
		brickh = 20; //height
	}

	/************************
	 * Class method
	 * Pre: the value of the bricks
	 * @param g
	 * Post: draw the bricks
	 ******************************/
	public void draw(Graphics2D g) {
		
		//initialize the colors
		Color col1 = new Color(r, h, b);
		Color col2 = new Color(h, b, r);
		Color col3 = new Color(b, r, h);
		color = col1;
		for(int i = 0; i<map.length; i++) {
			for(int j = 0;j < map[0].length; j++) {
				if (color == col2)
					color = col1;
				else if (color == col1)
					color = col3;
				else if (color == col3)
					color = col2;
				
				if (map[i][j] > 0 ) {//check if the value of the bricks is more than 0
					
					//draw the bricks
					g.setColor(color);
					g.fillRect(j*brickw + brickw, i*brickh + brickh, brickw, brickh);

					//draw the black border of the bricks
					g.setStroke(new BasicStroke(3)); //the border is 3 units wide
					g.setColor(Color.black);
					g.drawRect(j*brickw + 50, i*brickh + 20, brickw, brickh);

				} 
			}
		}
	}
		

	/**************************************
	 * Class Method
	 * Pre: none 
	 * @param value
	 * @param row
	 * @param col
	 * Post: get the values from the game class
	 *************************************/
	public void setBrickValue(int value, int row, int col) {
		map[row][col] = value;//get the value of the bricks from the main game
	}
}


/************************
 * Algorithm
 * declare all of the variables
 * import the number of rows and columns from the main game
 * set the values of all bricks to one
 * if the value of the bricks is not 0
 * alternate colors of the bricks
 * draw the bricks
 * draw the black border of the bricks
 *************************************/

