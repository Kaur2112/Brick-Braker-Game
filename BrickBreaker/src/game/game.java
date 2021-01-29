package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

/********************
 * @author Karneet Kaur
 * @title 'Brick Breaker'
 * @date 6/18/19
 ***********************/

public class game extends JPanel implements KeyListener, ActionListener {
	private boolean play = false; //the game is not playing
	private boolean menu = true; //the menu is displayed
	private int score = 0;

	//bricks
	private int bricks = 60, brickw = 50, brickh = 20;

	//ball
	private int ballX = 200, ballY = 325; //ball coordinates
	private int ballvelx = 2, ballvely = -2; //ball movement speed
	
	//paddle
	private int paddlex = 200, paddley = 335; //paddle coordinates
	private int s = 20; 

	private int point =10; //points per brick

	Color color;
	Color col1 = new Color (176,196,222); //light steel blue
	Color col2 = new Color (255,228,225); //misty rose color
	Color col3 = new Color (244,164,96); //sandy brown color

	Timer t = new Timer(10, this);
	mapgenerator map;

	/**********************
	 * Constructor
	 * Pre: none
	 * Post: display screen
	 ************************/
	public game() { 
		t.start();
		play = true;
		map = new mapgenerator(6,10);

		//sets the window
		addKeyListener(this); //allows the program to listen to keys
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setSize(600, 400);
		setVisible(true);
	}

	/***************************
	 * Class Method
	 * Pre: none
	 * Post: outputs the graphics
	 *****************************/
	public void paint(Graphics g){
		super.paint(g);
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 600, 400);
		g.setColor(Color.WHITE);
		g.setFont(new Font ("serif", Font.BOLD, 17));
		g.drawString("ESC", 10, 30);
		if (menu ) {//main menu
			g.setColor(Color.black);
			g.fillRect(1, 1, 600, 400);

			//heading
			g.setColor(new Color(0, 250, 154));
			g.setFont(new Font("courrier", Font.BOLD, 40));
			g.drawString("BRICK BREAKER", 120, 170);
			g.setColor(Color.blue);
			g.drawString("BRICK BREAKER", 122, 172);
			g.setColor(Color.pink);

			// enter space to find the instructions
			g.setFont(new Font("serif", Font.BOLD, 15));
			g.drawString("PRESS SPACE FOR INSTRUCTIONS", 150, 200);

			//press enter to play the game
			g.setFont(new Font("serif", Font.BOLD, 15));
			g.drawString("PRESS ENTER TO PLAY", 150, 215);

			//border
			color = col1;	
			border((Graphics2D)g, 0); //draw the top border line
			border((Graphics2D)g, 310);	//draw the bottom border line
		}

		//instructions: if menu is false playing the game is false
		if (!menu && !play) {
			//heading
			g.setFont(new Font("Times", Font.BOLD, 40));
			g.setColor(Color.red); //red color
			g.drawString("HOW TO PLAY", 57, 103);
			g.setColor(Color.YELLOW); //yellow color
			g.drawString("HOW TO PLAY", 60, 100);

			//instructions for the game
			g.setColor(new Color (255, 204, 204));//pink
			g.setFont(new Font("serif", Font.BOLD, 15));
			g.drawString("PRESS THE RIGHT ARROW KEY OR 'D' TO MOVE THE PADDLE", 80, 140);
			g.drawString("TO THE RIGHT", 80, 160); 
			g.drawString("PRESS THE LEFT ARROW KEY OR 'A' TO MOVE THE PADDLE", 80, 200);
			g.drawString("TO THE LEFT", 80, 220);
			g.drawString("DON'T LET THE BALL FALL DOWN", 80, 260);
			g.drawString("BOUNCE THE BALL WITH THE PADDLE TO DESTROY ALL OF", 80, 300);
			g.drawString("THE BRICKS", 80, 320);
		}

		//play the game: menu is false
		if (!menu && play) {	

			//map
			map.draw((Graphics2D)g);

			//score
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString(""+score, 558, 30);

			//draw the paddle
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color (40, 255, 154));
			g2.fillRect(paddlex, paddley, 100, 7);

			//draw the ball
			g.setColor(Color.yellow);
			g.fillOval(ballX, ballY, 10, 10);

			//check if all of the bricks are destroyed
			if (bricks<=0) {
				ballvelx = 2;
				ballvely = -2;
				paddlex = 200;
				point +=5; //if the last round has 5 points per brick, the next will have 10 points each brick
				//regenerate the bricks
				bricks = 60;
				map = new mapgenerator(6, 10);
				repaint();
			}

			//check if the ball falls down
			if(ballY > 380) {
				//stop the ball
				ballvelx = 0; 
				ballvely = 0;
				s=0;

				//output game over
				g.setColor(new Color (255, 40, 40)); //shade of red
				g.setFont(new Font("serif", Font.BOLD, 40));
				g.drawString("GAME OVER!", 190, 190);
				g.setColor(new Color (255,204, 204)); //light pink
				g.drawString("GAME OVER!", 188, 192);
				g.setColor(new Color (240, 250, 250)); //light blue color
				g.setFont(new Font("serif", Font.BOLD, 15));
				g.drawString("SCORE :" + score, 280, 210); //output the total score
				g.drawString("PRESS ENTER TO RESTART", 215, 230); //ask if the user wants to restart
				g.drawString("PRESS ESCAPE TO GO BACK TO THE MENU", 171, 250); //ask if the user wants to restart
			}

			g.dispose();			

		}
	}

	/*************************
	 * Class method
	 * Pre: the graphics
	 * Post: bounce the ball, move the paddle and hit the bricks
	 *********************************/
	@Override
	public void actionPerformed(ActionEvent e) {//what the game would perform
		t.start();
		repaint();

		//bounce the ball from three sides
		if (ballX < 10 || ballX >575 ) //left and right side
			ballvelx = -ballvelx;
		if (ballY < 10 ) //top
			ballvely = -ballvely;

		//bounce the ball from the paddle
		//check if the ball touches the left side of the paddle, the ball goes left
		if (ballX > (paddlex-5) && ballX <= (paddlex+20) && ballY >= (paddley-5)&& ballY <= (paddley)) {
			ballvelx = -1;
			ballvely = -2;
		}
		//check if the ball touches the right side of the paddle, the ball goes right
		else if (ballX < (paddlex+101) && ballX >= (paddlex+80) && ballY >= (paddley-5) && ballY <= (paddley)) {
			ballvelx = 1;
			ballvely = -2;
		}
		//check if the ball touches the middle, the ball goes on the opposite direction
		else if (ballX < (paddlex+81) && ballX >= (paddlex+19) && ballY >= (paddley-5)&& ballY <= (paddley)){
			ballvely = -ballvely;
		}


		//checks the intersection of the ball and the bricks
		A: for(int i = 0; i<map.map.length; i++) {
			for (int j = 0; j<map.map[0].length; j++) {
				if (map.map[i][j]>0) {
					//initializes the variables from the map generator class
					int brickx = j *map.brickw + map.brickw;
					int bricky = i*map.brickh + map.brickh;
					int brickw = map.brickw;
					int brickh = map.brickh;

					//turns the ball, paddle and the bricks to rectangles to check for intersection
					Rectangle rect = new Rectangle (brickx, bricky, brickw, brickh);
					Rectangle ballrect = new Rectangle(ballX, ballY, 10, 10);
					Rectangle brickRect = rect;

					//checks for intersection
					if (ballrect.intersects(brickRect)) {
						map.setBrickValue(0, i, j); //deletes the brick
						bricks--; //decreases the number of bricks
						score+=point; //adds points to the total

						//bounces the ball to the other direction when the ball touches the bricks
						if (ballX + 15 <= brickRect.x || ballX + 1 >= brickRect.x + brickRect.width) { 
							ballvelx = - ballvelx;
						}
						else {
							ballvely = - ballvely;
						}
						break A;						
					}
				}

			}
		}

		//moves the ball in the game 
		ballX += ballvelx;
		ballY += ballvely;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {} //checks for the key typed on the keyboard

	@Override
	public void keyReleased(KeyEvent e) {} //checks if the user have released the key

	/***************************************
	 * Class Method
	 * Pre: value of the variables are initialized
	 * asks the user for input
	 * Post: operate the game with the keys
	 ***************************************/
	@Override
	public void keyPressed(KeyEvent e){ //checks if a key is pressed  
		int code = e.getKeyCode(); //the code for the key pressed

		//check if the user enters the left key or the A
		if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A){ 
			if (play) {//check the user is playing the game
				if (paddlex <=15)//checks if the paddle is less than 15
					paddlex = 15; //sets the value to 15 and does not let the paddle move out of the boundary
				else
					left(); //move the paddle to the left
			}

		}

		//check if the user enters the right key or D
		if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D){
			if (play) {//check if the user is playing the game
				if (paddlex>=475) //checks if the paddle is going to be more that 475
					paddlex = 475; //sets the value to 475 and does not let the paddle move out of the boundary
				else
					right(); //move the paddle to the right
			}	
		}		

		//check if the user pressed the enter key 
		if (code == KeyEvent.VK_ENTER) {
			//reset the values	
			ballX = 200;
			ballY = 325;
			ballvelx = 2;
			ballvely = -2;
			paddlex = 200;
			s = 20;
			score = 0;
			bricks = 60;
			map = new mapgenerator(6, 10); //generate a new map
			repaint();
			menu = false; //the menu is not displayed
			play = true; //the user can play the game
		}

		//check if the user enters the space key
		if (code == KeyEvent.VK_SPACE){
			//redirect to the instructions page
			menu = false;
			play = false;	
		}

		//check if the user enters the escape key
		if (code == KeyEvent.VK_ESCAPE) {
			//redirect to the menu/main page
			play = false;
			menu = true;
		}
	}

	/**********************
	 * Class Method
	 * Pre: key entered value
	 * Post: moves the paddle to the right
	 *******************************/
	public void right() { //move the paddle to the right
		play = true;
		paddlex += s;
	}

	/*************************
	 * Class Method
	 * Pre: key entered value
	 * Post: moves the paddle to the right
	 ********************************/
	public void left () { //move the paddle to the left
		play = true;
		paddlex -= s;			
	}

	/********************************
	 * Class method
	 * @param g
	 * @param h
	 * Pre: the height of the line made of bricks
	 * Post: draws a lne with bricks
	 ***********************************/
	public void border(Graphics2D g, int h) {
		for(int j = -1;j< 12; j++) {
			//change colors
			if (color == col2)
				color = col1;
			else if (color == col1)
				color = col3;
			else if (color == col3)
				color = col2;

			//draw the bricks
			g.setColor(color);
			g.fillRect(j*brickw + brickw, brickh+h, brickw, brickh);

			//draw the black outline of the bricks
			((Graphics2D) g).setStroke(new BasicStroke(3));
			g.setColor(Color.black);
			g.drawRect(j*brickw + 50,brickh+h, brickw, brickh);
		}			
	}
}

/**********************************************************
 * Algorithm
 * declare variables
 * allow the program to track the keys
 * setup the window size at (600,400)
 * color the background black
 * display the main menu screen
 * write the heading "Brick Breaker" with blue color
 * write the functions to operate the program with white color
 * the user will have to press space to look at the instructions
 * the user will have to press enter to play the game
 * draw a border made of bricks
 * if the user enters space
 * display the instructions screen
 * print How to play with yellow and red colors using the serif font
 * print "press the right arrow key or 'D' to move the paddle to the right" with pink color
 * print "press the left arrow key or 'A'to move the paddle to the left" with pink color
 * print "don't let the ball fall down" with pink color
 * print "bounce the ball with the paddle to destroy all of the brick" with pink color
 * print"ESC" on the top left corner to indicate that to get back to the main page, the user had to press escaape
 * if the user pressed the enter key
 * draw the bricks 
 * draw the paddle
 * draw the ball
 * regenerate bricks if all of them are destroyed
 * while the game is playing
 * display the score on the top right corner
 * bounce the ball off the walls and from the paddle
 * if the ball touches the right side of the paddle, the ball goes right
 * if the ball touches the left side of the paddle, the ball goes to the left
 * if the ball bounces from the middle, the ball goes in the opposite direction 
 * bounce the ball off the bricks in the opposite direction
 * add points to the total if it touches a brick
 * move the paddle left if the user pressed the left key 
 * move the paddle right if the user pressed the right key
 * check if the ball fell down
 * print "Game Over!" with red and pink
 * print the score
 * print the instruction if the user wants to play again or go back to the menu
 *********************************************************************/
