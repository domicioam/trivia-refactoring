package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Game {
	private ArrayList<Player> players = new ArrayList<Player>();
	private Deck deck;
	private PenaltyBox penaltyBox;
	private Player currentPlayer;
	private PenaltyBoxState penaltyBoxState;
	private Board board;

	public Game() {
		penaltyBox = new PenaltyBox();
		deck = new Deck();
		penaltyBoxState = new PlayerOutsidePenaltyBoxState(this);
		int numberOfPositions = 12;
		board = Board.createBoardBasedOnSize(numberOfPositions, deck.getCategoriesSize());
	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean addPlayer(String playerName) {
		Player player = new Player(playerName, 0);
		players.add(player);
		player.setCoins(0);
		
		if(currentPlayer == null) {
			currentPlayer = player;
		}

		System.out.println(playerName + " was added");
		System.out.println("They are player number " + players.size());
		return true;
	}

	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(currentPlayer.getName() + " is the current player");
		System.out.println("They have rolled a " + roll);

		penaltyBoxState.roll(currentPlayer, roll);
	}

	public void correctAnswer() {
		penaltyBoxState.wasCorrectlyAnswered(currentPlayer, penaltyBox);
	}
	
	public void wrongAnswer() {
		System.out.println("Question was incorrectly answered");
		System.out.println(currentPlayer.getName() + " was sent to the penalty box");
		penaltyBox.insertPlayer(currentPlayer);
	}
	
	public void changeToNextPlayer() {
		int nextPosition = board.getNextPosition(players, currentPlayer);
		currentPlayer = players.get(nextPosition);
		
		if (penaltyBox.contains(currentPlayer)) {
			penaltyBoxState = new PlayerInsidePenaltyBoxState(this);
		} else {
			penaltyBoxState = new PlayerOutsidePenaltyBoxState(this);
		}
	}
	
	public boolean isNotAWinner() {
		return !(currentPlayer.getCoins() == 6);
	}
	
	void updatePlayerPositionAndAskQuestion(int roll, Player player) {
		board.updatePlayerPosition(roll, player);
		deck.askQuestion(player.getPosition(), board.currentCategory(player.getPosition()));
	}
	
	void increasePlayerCoins(Player player) {
		System.out.println("Answer was correct!!!!");
		player.setCoins(player.getCoins() + 1);
		System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getCoins() + " Gold Coins.");
	}
}
