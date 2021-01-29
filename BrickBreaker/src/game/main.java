package game;

import java.awt.*;
import javax.swing.*;

public class main {

	public static void main (String[] args) {
		new main().run();
	}

	/************************
	 * Class Method
	 * Pre: none
	 * Post: runs the game
	 ************************/
	private void run(){
		JFrame f = new JFrame();
		game Game = new game();
		f.setSize(600,400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBackground(Color.BLACK);
		f.getContentPane().add(Game);
		f.setVisible(true);
		f.setResizable(false);


	}
}

/***********************************
 * Algorithm
 * set up the window size to (600, 400)
 * set the window size to be fixed
 * set the background to black
 * close the program when the user closes the window
 * add the main game
 **************************************/
