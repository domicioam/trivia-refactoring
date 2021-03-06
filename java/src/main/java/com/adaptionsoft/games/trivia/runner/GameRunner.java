
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Game aGame = new Game();
		
		aGame.addPlayer("Chet");
		aGame.addPlayer("Pat");
		aGame.addPlayer("Sue");
		
		Random rand = new Random();
	
		do {
			aGame.roll(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				aGame.wrongAnswer();
				notAWinner = aGame.isNotAWinner();
				aGame.changeToNextPlayer();
			} else {
				aGame.correctAnswer();
				notAWinner = aGame.isNotAWinner();
				aGame.changeToNextPlayer();
			}
		} while (notAWinner);
	}
}
