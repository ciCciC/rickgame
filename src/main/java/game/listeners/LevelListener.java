package game.listeners;

import game.StartScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelListener implements ActionListener {

    private StartScreen screen;

    public LevelListener(StartScreen screen){
        this.screen = screen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        int selectedIndex = cb.getSelectedIndex();
        System.out.println("Selected: " + cb.getName());
        System.out.println("Selected index: " + selectedIndex);
        this.screen.setLevel(selectedIndex);
    }
}
