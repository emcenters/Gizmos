package control;
import main.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TurnManager implements ActionListener{
    private final static String BUILD = "BUILD";
    private final static String PICK = "PICK";
    private final static String FILE = "FILE";
    private final static String RESEARCH = "RESEARCH";

    private MainBoard mb;

    public TurnManager(MainBoard m) {
        mb = m;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch(action) {
            case BUILD: 
                break;
            case PICK:
                break;
            case FILE:
                break;
            case RESEARCH:
                break;
        }
    }
}
