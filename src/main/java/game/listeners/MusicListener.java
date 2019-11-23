package game.listeners;

import game.StartScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MusicListener implements ActionListener {
    private StartScreen screen;

    public MusicListener(StartScreen screen){
        this.screen = screen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        int selectedIndex = cb.getSelectedIndex();
        System.out.println("Chosen: " + selectedIndex);
        this.screen.setMusic(selectedIndex);
    }
}
