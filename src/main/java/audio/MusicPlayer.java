package audio;

import enums.AudioType;

import javax.sound.sampled.*;
import java.util.*;
import java.util.stream.*;
import java.io.File;

public class MusicPlayer implements Runnable {

    private LinkedList<String> musicFiles;

    private int currentSongIndex;
    private int volume;

    private Clip clip;

    private static final String RESOURCE_MUSIC = "./assets/audio/music/";
    private final String RESOURCE_SOUND = "./assets/audio/sound/";

    public MusicPlayer(){
        this.musicFiles = new LinkedList<>();
        this.volume = -10;
    }

    public MusicPlayer(String file, AudioType audioType){
        this.musicFiles = new LinkedList<>();

        String wav = ".wav";

        if(audioType == AudioType.Music){
            this.musicFiles.add(RESOURCE_MUSIC + file + wav);
        } else{
            this.musicFiles.add(this.RESOURCE_SOUND + file + wav);
        }

        this.volume = -10;
        this.currentSongIndex = 0;
    }

    public void addMusic(String file){

        if(!this.musicFiles.contains(file)){
            this.musicFiles.add(RESOURCE_MUSIC + file + ".wav");
        }
    }

    public void addSound(String file){
        this.musicFiles.add(this.RESOURCE_SOUND + file + ".wav");
    }

    public LinkedList<String> getMusicListFromDir(){

        File paths = new File(RESOURCE_MUSIC);

        LinkedList<File> filePaths = new LinkedList<>();
        filePaths.addAll(Arrays.asList(paths.listFiles()));

        this.musicFiles = filePaths.stream()
                .filter((file) -> !file.getName().equals(".DS_Store"))
                .map((file) -> file.getAbsolutePath())
                .collect(Collectors.toCollection(LinkedList::new));

        return filePaths.stream()
                .filter((file) -> !file.getName().equals(".DS_Store"))
                .map((file) -> file.getName().replace(".wav", ""))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private void playSound(String fileName){
        try {
            File soundFile = new File(fileName);
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(this.volume);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVolume(int newVolume){
        this.volume = newVolume;
    }

    public int getVolume(){
        return this.volume;
    }

    public void chooseSong(int number){
        this.currentSongIndex = number;
    }

    @Override
    public void run() {
        this.playSound(musicFiles.get(this.currentSongIndex));
    }

    public boolean isRunning(){
        return this.clip.isRunning();
    }

    public boolean clipIsNull(){
        return this.clip == null;
    }

    public void stop(){

        this.clip.stop();
        this.clip.flush();

    }

    public static void main(String[] args) {
        MusicPlayer ts = new MusicPlayer(Musiclist.mario, AudioType.Music);
        ts.run();
    }
}
