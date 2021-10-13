import javax.sound.sampled.*;
import java.io.*;

public class Audio implements LineListener{
 
  public static App app;
  private boolean playCompleted;
  private Clip audioClip;

  private String audioFilePath;

  private boolean isPlaying;
  
  public Audio(App a){

    Audio.app = a;

    isPlaying = false;
    //audioFilePath = "Music/STARSET - EARTHRISE.wav";

    //loadSong(audioFilePath);
    //play();
      //audioClip.start();

    //FloatControl volume = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
      //volume.setValue(1f);

    

    
    
  }

  /**
     * Listens to the START and STOP events of the audio line.
     */
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
         
        if (type == LineEvent.Type.START) {
          playCompleted = false;
          System.out.println("Playback started.");
             
        } else if (type == LineEvent.Type.STOP) {
           playCompleted = true;
          System.out.println("Playback completed.");
        }
 
    }



  public void play(){
    App.display.updatePlayButton();
    audioClip.start();
    isPlaying = true;
  }

  public void pause(){
    App.display.updatePlayButton();
    audioClip.stop();
    isPlaying = false;
  }

  public boolean isPlaying(){
    return isPlaying;
  }

  public void loadSong(String path){

    if(audioClip != null){
      pause();
      audioClip.close();
    }
    

    File audioFile = new File(path);
    
    try {
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

      AudioFormat format = audioStream.getFormat();
 
      DataLine.Info info = new DataLine.Info(Clip.class, format);

      audioClip = (Clip) AudioSystem.getLine(info);

    
      audioClip.open(audioStream);



      setVolume(.2f);


    } catch (Exception e) {
      System.out.println("Exception with Audio");
    }

  }


  public float getVolume() {
    FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);        
    return (float) Math.pow(10f, gainControl.getValue() / 20f);
  }
  
  public void setVolume(float volume) {
      if (volume < 0f || volume > 1f)
          throw new IllegalArgumentException("Volume not valid: " + volume);
      FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);        
      gainControl.setValue(20f * (float) Math.log10(volume));
  }

}
