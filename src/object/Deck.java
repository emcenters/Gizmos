package object;
import main.*;

import java.util.*;

import boards.GameBoard;

public class Deck {
    private ArrayList<Card> cards;
    public Card[] shown;
    public Card deckCard;
    
    public Deck(MainBoard m, String filePath, int showLength, int startX, int startY, int cardCount) {
        cards = new ArrayList<>();
        shown = new Card[showLength];

        Scanner fileReader = new Scanner(Deck.class.getResourceAsStream(filePath));
        while(fileReader.hasNextLine()) {
            cards.add(new Card(fileReader.nextLine(), 0, 0, cardCount++));
        }
        
        shuffle();

        int tier = 0;

        switch(showLength) {
            case 4: tier = 1; break;
            case 3: tier = 2; break;
            case 2: tier = 3; break;
        }
        deckCard = new Card(null, startX, startY, 0);
        deckCard.setImage("/card/deck"+tier+".png", Card.CARDSIZE);
        startX += Card.CARDSIZE+GameBoard.OFFSET;
        m.gameBoard.add(deckCard);

        for(int i = 0; i < shown.length; i++) {
            shown[i] = cards.remove(0);
            shown[i].setLocation(startX, startY);
            shown[i].setImage(Card.CARDSIZE);
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
    
    public Card contains(int x, int y) {
        for(Card c: shown) {
            if(c.getBounds().contains(x, y)) {
                return c;
            }
        }
        return null;
    }
    public int getIndex(int x, int y) {
        for(int i = 0; i < shown.length; i++) {
            if(shown[i].getBounds().contains(x, y)) {
                return i;
            }
        }
        return -1;
    }
    public int getIndex(Card c) {
        for(int i = 0; i < shown.length; i++) {
            if(shown[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Card> getCards(int total) {
        ArrayList<Card> topCards = new ArrayList<>();
        for(int i = 0; i < total; i++) {
            topCards.add(cards.remove(0));
        }
        return topCards;
    }
    public void addToBottom(ArrayList<Card> returnCards) {
        for(Card c: returnCards) {
            cards.add(c);
        }
    }

    public void cutDeck() {
        for(int i = 0; i < 20; i++) {
            cards.remove(0);
        }
    }
}
