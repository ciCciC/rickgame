package game;

import audio.MusicPlayer;
import enums.FolderType;
import enums.ImageType;
import game.listeners.LevelListener;
import game.listeners.MusicListener;
import gfx.BackgroundList;
import gfx.sprite.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class StartScreen extends JFrame {

    private static final long serialVersionUID = 1L;
    public static final String TITLE = "Alien Game";

    public static void main(String[] args) {
        StartScreen start = new StartScreen();
    }

    private final int WIDTH = 300;
    private final int HEIGHT = 400;

    public JPanel screen;
    public JButton startButton;

    public Sprite background;

    private MusicPlayer musicStartscreen;

    private JComboBox comboLevel, comboMusic;
    private int updateLevel, updateMusic;
    private LinkedList<File> filePaths;
    private LinkedList<String> musicPaths;

    private MusicPlayer music;
    private Game game;

    public StartScreen(){
        super.setTitle(TITLE);

        try {
            background = new Sprite(FolderType.images, ImageType.gif, BackgroundList.startscreen);
        } catch (IOException ex) {
            System.out.println("Startscreen background doesnt work.");
        }

        super.setSize(background.imageGif.getWidth(null), background.imageGif.getHeight(null));

        this.filePaths = new LinkedList<>();
        this.musicPaths = new LinkedList<>();
        this.music = new MusicPlayer();
        this.game = null;

        this.init();

        super.setResizable(false);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
    }

    private void init(){
        screen = new JPanel(new GridBagLayout()){
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                screen.setSize(background.imageGif.getWidth(null), background.imageGif.getHeight(null));
                g.drawImage(background.imageGif, 0, 0, background.imageGif.getWidth(null), background.imageGif.getHeight(null), this);
            }
        };

        GridBagConstraints c = new GridBagConstraints();

//        header = new JLabel("Alien game");

//        JLabel header = new JLabel("Alien game");

//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 2;
//        c.gridy = 0;
//        screen.add(header, c);

        startButton = new JButton("Start game!");
        startButton.addActionListener((ActionEvent e) -> {

            //Start game
            this.initGame(filePaths.get(updateLevel));
//            Stop music
            if(updateMusic != 0){
                this.music.chooseSong(updateMusic-1);
                this.music.run();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 2;
        c.gridy = 1;
        screen.add(startButton, c);


//        Label level list
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = 1;
        screen.add(new JLabel("Choose level:"), c);

        comboLevel = new JComboBox(this.getLevels().toArray());
        comboLevel.setSelectedIndex(0);
        comboLevel.addActionListener(new LevelListener(this));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 1;
        c.gridy = 1;
        screen.add(comboLevel, c);

//        Label music list
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy = 2;
        screen.add(new JLabel("Choose music:"), c);

        comboMusic = new JComboBox(this.getMusicList().toArray());
        comboMusic.setSelectedIndex(0);
        comboMusic.addActionListener(new MusicListener(this));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 1;
        c.gridy = 2;
        screen.add(comboMusic, c);

        this.add(screen);

        screen.setOpaque(false);
    }

    private LinkedList<String> getMusicList(){
        musicPaths.add("none");
        musicPaths.addAll(this.music.getMusicListFromDir());
        return musicPaths;
    }

    private LinkedList<String> getLevels(){
        String rootLevel = "./assets/level";

        File paths = new File(rootLevel);

        filePaths.addAll(Arrays.asList(paths.listFiles()));

        LinkedList<String> names = new LinkedList<>();

        for (File file : paths.listFiles()) {
            if(file.getName().equals(".DS_Store")){
                filePaths.remove(file);
            }else{
                names.add(file.getName());
            }
        }
        return names;
    }

    public void setLevel(int chosenLevel){
        this.updateLevel = chosenLevel;
    }

    public void setMusic(int chosenMusic){

        if(this.game != null && this.game.isRunning()){
            this.game.stopRunning();
            this.music.stop();
        }

        if(!this.music.clipIsNull()){
            this.music.stop();
        }

        this.updateMusic = chosenMusic;
    }

    private void initGame(File level){
        this.game = new Game(level);
        JFrame frame = new JFrame(TITLE);
        frame.add(game);
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        game.start();
    }
}
