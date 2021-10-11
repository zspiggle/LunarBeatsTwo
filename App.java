import java.io.*;
import java.util.ArrayList;


public class App {

  public static Display display;

  public ArrayList<Song> songs;

  public static void main(String[] args){
    App app = new App();
  }

  public App(){
    String fileLocation = "Music/";

    songs = new ArrayList<Song>();

    if(App.display == null){
      App.display = new Display();
      App.display.setVisible(true);
    }

    try{
      goThroughFiles(fileLocation);
    } catch (Exception e) {
      System.out.println("There was an issue loading songs");
    }

    for(int i = 0; i < songs.size(); i++){
      System.out.println(songs.get(i));
    }
    

  }

  private void goThroughFiles(String dir) throws IOException{
    File f = new File(dir);
    File[] files = f.listFiles();

    for (int i = 0; i < files.length; i++) {

        File file = files[i];
        if(!file.getName().equals("_UNSORTED")){

            if (file.isDirectory()) {   
                goThroughFiles(file.getPath()); 
            } else { 
                //System.out.println("Music file : " + file.getName());
                loadSong(file);
            }
        }

    }

  }

  /**
     * loads a song from a file
     * 
     * @param  File songFile  the file you want to turn into a song
     */
    public void loadSong(File songFile){
      String line = songFile.getName();

      String art = line.substring(0, line.indexOf('-'));
      line = line.substring(line.indexOf('-') + 1);
      String nam = line.substring(0, line.length() -4);

      // System.out.println(nam);

      if(findSong(nam,art) == null){
          newSong(songFile.getPath(), art, nam, false);
      }

  }



  public void newSong(String file, String name, String artist, boolean like){
    if(findSong(name, artist) == null){
        Song song = new Song(file,name,artist,like);
        songs.add(song);

        //allSongs.addSong(song);
        //if(like == true){
            
            //likedSongs.addSong(song);
        //}
    }
  }

  public Song findSong(String findName, String findArtist){

    Song found = null;

    //Linear search
    for(Song s : songs){
        if (s.getName().equals(findName) && s.getArtist().equals(findArtist)){
            found = s;
        }
    }

    return found;
  }

}
