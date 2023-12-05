package object;
import main.*;

import java.util.*;

public class Deck {
    private ArrayList<Card> cards;
    
    public Deck(MainBoard m, String filePath) {
        cards = new ArrayList<>();

        Scanner fileReader = new Scanner(Deck.class.getResourceAsStream(filePath));
        while(fileReader.hasNextLine()) {
            cards.add(new Card(m, fileReader.nextLine()));
        }
        
        shuffle((int)Math.random());
    }
    
    public void shuffle(int randSeed) {
		Random rand = new Random(randSeed);
		for(int i=0; i<cards.size(); i++) {
			int k = rand.nextInt(cards.size());
			Card temp = cards.get(k);
			cards.set(k,cards.get(i));
			cards.set(i, temp);
		}
	}
}
