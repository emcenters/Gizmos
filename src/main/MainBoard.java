package main;
import boards.*;
import control.TurnManager;

import javax.swing.*;

import java.awt.*;
import java.util.EnumSet;

public class MainBoard extends JLayeredPane implements Runnable{
    public final static int STARTSTATE = 0, GAMESTATE = 1, ENDSTATE = 2;

    private StartingScreen startScreen;
    public GameBoard gameBoard;
    public EnergyBoard energyBoard;

    public PlayerBoard playerBoard;

    // public MouseHandler mouseHandler;
    public TurnManager turnManager;

    private Thread gameThread;

    int gameState;
    public Player winner = null;

    public MainBoard() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(1400, 1000));
        setDefaultValues();
    }
    
    //methods setting the pane + components
    public void setDefaultValues() {
        turnManager = new TurnManager(this);
        // mouseHandler = new MouseHandler(this);

        gameState = 0;

        startScreen = new StartingScreen(this);
        add(startScreen);
    }

    public void setGameScreen() {
        //removes start screen
        startScreen.setVisible(false);

        //sets game board
        gameBoard = new GameBoard(this);
        gameBoard.setDefaultValues();
        energyBoard = new EnergyBoard(this);

        //sets player board
        playerBoard = new PlayerBoard(this);

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(playerBoard);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(gameBoard);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(energyBoard);

        nextGameState();
        JOptionPane.showMessageDialog(null, "<html>To Pick: click on any marble at the bottom of the screen at the beginnning of your turn."
                    +"<br/>To File or Build: click on any shown card on the game board at the beginning of your turn."
                    +"<br/>To Research: click on the tier card for each deck at the beginning of your turn."
                    +"<br/>To Activate a Card's Effects: after one of the basic actions, click on the card that you want to activate.", "INSTRUCTIONS", JOptionPane.INFORMATION_MESSAGE);
    }

    //runnable
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void run() {
		double drawInterval = 1000000000/60;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				delta--;
			}
		}
	}
    public void update() {
        switch(gameState) {
            case GAMESTATE: 
                gameBoard.update();
                energyBoard.update();
                // player.update();
                break;
            case ENDSTATE:
                removeAll();
                break;
        }
    }

    public void nextGameState() {
        gameState += 1;
        if(gameState == ENDSTATE) {
            energyBoard.setVisible(false);
            gameBoard.setVisible(false);
            playerBoard.setVisible(false);
            add(new EndScreen(this));
        }
    }
    public void setWinner(Player win) {
        winner = win;
    }
}
