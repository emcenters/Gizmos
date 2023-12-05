package main;
import boards.*;

import javax.swing.*;

import java.awt.*;

public class MainBoard extends JPanel implements Runnable{
    public final static int STARTSTATE = 0, GAMESTATE = 1, ENDSTATE = 2;

    private StartingScreen startScreen;
    private ActionBoard actionBoard;
    private GameBoard gameBoard;

    public PlayerBoard player;

    public MouseHandler mouseHandler;

    private Thread gameThread;

    int gameState;

    public MainBoard() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setDefaultValues();
    }
    
    //methods setting the pane + components
    public void setDefaultValues() {
        mouseHandler = new MouseHandler();

        gameState = 0;

        startScreen = new StartingScreen(this);
        add(startScreen);
    }

    public void setGameScreen() {
        //removes start screen
        startScreen.setVisible(false);

        //sets action board
        actionBoard = new ActionBoard();

        //sets game board
        gameBoard = new GameBoard(this, mouseHandler);

        //sets player board
        player = new PlayerBoard(mouseHandler, null);
        PlayerBoard first = player;
        for(int i = 0; i < startScreen.getNumPlayers(); i++) {
            player.setNext(new PlayerBoard(mouseHandler, null));
            player = player.getNext();
        }
        player.setNext(first);
        player = player.getNext();

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(actionBoard);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(player);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(gameBoard);
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
                player.update();
                break;
            case ENDSTATE:
                break;
        }
    }

    public void nextGameState() {
        gameState += 1;
    }
}