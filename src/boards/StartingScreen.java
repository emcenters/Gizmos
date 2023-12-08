package boards;
import main.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class StartingScreen extends JPanel{
    private MainBoard m;
    private int numPlayers;

    private JLabel titleText;
    private JComboBox<Integer> totalPlayerList;
    private JButton start, instructions;
    private Font buttonFont, titleFont;

    public StartingScreen(MainBoard main) {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout(FlowLayout.CENTER, 2000, 40));
        setPreferredSize(new Dimension(1400, 800));
        m = main;
        numPlayers = 0;

        titleFont = new Font(Font.SANS_SERIF, Font.PLAIN, 60);
        buttonFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

        setComponents();
        //add space between title and top
        for(int i = 0; i < 4; i++) {
            add(new JLabel(" "));
        }
        add(titleText);

        for(int i = 0; i < 2; i++) {
            add(new JLabel(" "));
        }
        add(start);
        add(instructions);
    }

    public void setComponents() {
        start = new JButton("START");
        start.setPreferredSize(new Dimension(300, 40));
        start.setFont(buttonFont);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.setGameScreen();
                // titleText.setText("Choose Amount of Players");
                // add(totalPlayerList);
            }
        });

        instructions = new JButton("INSTRUCTIONS");
        instructions.setPreferredSize(new Dimension(300, 40));
        instructions.setFont(buttonFont);
        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "<html>To Pick: click on any marble at the bottom of the screen at the beginnning of your turn."
                    +"<br/>To File or Build: click on any shown card on the game board at the beginning of your turn."
                    +"<br/>To Research: click on the tier card for each deck at the beginning of your turn."
                    +"<br/>To Activate a Card's Effects: after one of the basic actions, click on the card that you want to activate.", "INSTRUCTIONS", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        titleText = new JLabel("GIZMOS");
        titleText.setFont(titleFont);

        // totalPlayerList = new JComboBox<>(new Integer[] {2, 3, 4});
        // totalPlayerList.setPreferredSize(new Dimension(300, 100));
        // totalPlayerList.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         numPlayers = totalPlayerList.getSelectedIndex()+2;
        //         m.setGameScreen();
        //     }
        // });
    }
    public int getNumPlayers() {
        return 4;
    }
}
