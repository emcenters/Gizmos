package boards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ActionBoard extends JLayeredPane{
    private JButton[] actionButtons;
    // 0: build, 1: pick, 2: file, 3: research;

    public ActionBoard() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 75, 0));
        setPreferredSize(new Dimension(1400, 100));
        setBorder(BorderFactory.createTitledBorder("CHOOSE ACTION"));

        setButtons();
    }

    public void setButtons() {
        actionButtons = new JButton[]
            {new JButton("BUILD"), new JButton("PICK"), new JButton("FILE"), new JButton("RESEARCH")};

        Font buttonFont = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
        int buttonWidth = 250;
        int buttonHeight = 60;

        for(JButton b: actionButtons) {
            b.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            b.setFont(buttonFont);
            b.setActionCommand(b.getName());

            add(b);
        }

        actionButtons[0].setEnabled(false);
    }
}
