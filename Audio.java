import javax.sound.sampled.*;
import java.io.*;

public class Audio implements LineListener{
 
  public static App app;
  private boolean playCompleted;
  private Clip audioClip;

  private String audioFilePath;

  private boolean isPlaying;
  private int volumeSetting;
  private boolean isMute;
  
  public Audio(App a){

    Audio.app = a;

    isPlaying = false;
    volumeSetting = 2; //0 mute, 1 low, 2 med, 3 loud
    isMute = false;
    
    
    
  }

  /**
   * Listens to the START and STOP events of the audio line.
   */
  @Override
  public void update(LineEvent event) {
      LineEvent.Type type = event.getType();
      
      if (type == LineEvent.Type.START) {
        playCompleted = false;
        //System.out.println("Playback started.");
            
      } else if (type == LineEvent.Type.STOP) {
        //checks if just paused
        if(audioClip.getFramePosition() >= audioClip.getFrameLength()){
          playCompleted = true;
          System.out.println("Playback completed.");


          App.app.nextSong();

        }
        
        //

      }

  }



  public void play(){
    App.display.updatePlayButton();
    audioClip.start();
    isPlaying = true;
    playCompleted = false;
  }

  public void pause(){
    App.display.updatePlayButton();
    audioClip.stop();
    isPlaying = false;
  }

  public boolean isPlaying(){
    return isPlaying;
  }

  public boolean isMute(){
    return isMute;
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

      audioClip.addLineListener(this);
    
      audioClip.open(audioStream);



      setVolumeSetting(volumeSetting);
      if(isMute == true){
        FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);        
        gainControl.setValue(gainControl.getMinimum()); 
      }


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

  public int getVolumeSetting(){
      return volumeSetting;
  }

  public void mute(){
    if(isMute == false ){
      FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);        
      gainControl.setValue(gainControl.getMinimum()); 
      isMute = true;
    } else {
      setVolumeSetting(volumeSetting);
      isMute = false;
    }


    App.display.updateMuteButton();

  }

  public void setVolumeSetting(int setting){
      volumeSetting = setting;

      switch(volumeSetting){
          case 1:setVolume(0.1f); break;
          case 2: setVolume(0.3f); break;
          case 3: setVolume(0.7f); break;
          default: break;
      }

      App.display.updateVolumeButton();
  }

public Clip getAudioClip(){
  return audioClip;
}


}
