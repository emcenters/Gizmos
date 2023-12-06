package control;
import main.*;
import object.*;
import boards.*;

public class TurnManager {
    private MainBoard mb;
    private PlayerBoard player;

    public TurnManager(MainBoard m) {
        mb = m;
        player = m.player;
    }

    public void build() {
        Card pickedCard = (Card)MouseHandler.currentObject;
        if(pickedCard.getCost() > player.marbles[pickedCard.getColor()+1]) {
            mb.actionBoard.reset();
        } 
        else {
            player.build(pickedCard);
        }
    }
    public void pick() {
        Marble pickedMarble = (Marble)MouseHandler.currentObject;
        if(player.notAtMarbleLimit()) {
            player.pick(pickedMarble.getColor());
        }
        else {
            mb.actionBoard.reset();
        }
    }
    public void file() {
        Card filedCard = (Card)MouseHandler.currentObject;
        if(player.notAtFileLimit()) {
            player.file(filedCard);
        }
    }
    public void research() {
        
    }
}
