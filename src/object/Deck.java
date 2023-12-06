package object;
import main.*;

import java.util.*;

import boards.GameBoard;

public class Deck {
    private ArrayList<Card> cards;
    private Card[] shown;
    
    public Deck(MainBoard m, String filePath, int showLength, int startX, int startY) {
        cards = new ArrayList<>();
        shown = new Card[showLength];

        Scanner fileReader = new Scanner(Deck.class.getResourceAsStream(filePath));
        int cardCount = 1;
        while(fileReader.hasNextLine()) {
            cards.add(new Card(m, fileReader.nextLine(), 0, 0, cardCount++));
        }
        
        shuffle();

        for(int i = 0; i < shown.length; i++) {
            shown[i] = cards.remove(cards.size()-1);
            shown[i].setLocation(startX, startY);
            shown[i].setImage();
            startX += Card.CARDSIZE+GameBoard.OFFSET;
            m.gameBoard.add(shown[i]);
        }
    }
    
    public void shuffle() {
		Random rand = new Random();
		for(int i=0; i<cards.size(); i++) {
			int k = rand.nextInt(cards.size());
			Card temp = cards.get(k);
			cards.set(k,cards.get(i));
			cards.set(i, temp);
		}
	}

    public void update() {
        for(Card c: shown) {
            c.update();
        }
    }
}
