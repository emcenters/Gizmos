package boards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import main.MainBoard;

public class ActionBoard extends JLayeredPane{
    public static boolean buildClicked = false, pickClicked  = false, fileClicked  = false;
    public JButton[] actionButtons;
    // 0: build, 1: pick, 2: file, 3: research, 4: end turn;

    private MainBoard main;

    public ActionBoard(MainBoard m) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 75, 0));
        setPreferredSize(new Dimension(1400, 100));
        setBorder(BorderFactory.createTitledBorder("CHOOSE ACTION"));

        main = m;
        setButtons();
    }

    public void setButtons() {
        actionButtons = new JButton[]
            {new JButton("BUILD"), new JButton("PICK"), new JButton("FILE"), new JButton("RESEARCH"), new JButton("END TURN")};

        Font buttonFont = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
        int buttonWidth = 200;
        int buttonHeight = 60;

        for(JButton b: actionButtons) {
            b.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            b.setFont(buttonFont);
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for(JButton b: actionButtons) {
                        b.setEnabled(false);
                    }
                    switch(b.getText()) {
                        case "BUILD": buildClicked = true;
                            break;
                        case "PICK": pickClicked = true;
                            break;
                        case "FILE": fileClicked = true;
                            break;
                        case "RESEARCH": main.turnManager.researchPopup();;
                            break;
                        case "END TURN": main.turnManager.nextPlayer();
                            reset();
                            break;
                    }
                }
            });
            add(b);
        }

        reset();
    }

    public void reset() {
        for(JButton b: actionButtons) {
            b.setEnabled(true);
        }
        actionButtons[4].setEnabled(false);

        if(main.player.getTotalMarbles() == 0) {
            actionButtons[0].setEnabled(false);
        }
        if(!main.player.notAtMarbleLimit()) {
            actionButtons[1].setEnabled(false);
        }
        if(!main.player.notAtFileLimit()) {
            actionButtons[2].setEnabled(false);
        }
    }
}
