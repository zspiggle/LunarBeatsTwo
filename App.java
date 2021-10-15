import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class App {

  public static App app;
  public static Display display;

  public ArrayList<Song> songs;
  public ArrayList<Playlist> playlists;

  public ArrayList<Song> tracklist;
  private int trackPosition;

  public Song currentSong;
  public Playlist currentPlaylist;


  public Audio audio;

  private boolean isShuffling;

  private int nextID;
  private int nextPlayID;

  public static void main(String[] args){
    App app = new App();
  }

  public App(){

    if(App.app == null){
      app = this;
    }


    nextID = 0;
    nextPlayID = 3;

    String fileLocation = "Music/";

    songs = new ArrayList<Song>();
    playlists = new ArrayList<Playlist>();

    tracklist = new ArrayList<Song>();

    audio = new Audio(this);

    isShuffling = false;
    trackPosition = 0;


    try{
      goThroughFiles(fileLocation);
    } catch (Exception e) {
      System.out.println("There was an issue loading songs");
    }

    // for(int i = 0; i < songs.size(); i++){
    //   System.out.println(songs.get(i));
    // }
    

    Playlist allSongs = new Playlist("All Songs", songs, 0);
    playlists.add(allSongs);
    currentPlaylist = allSongs;


    //Playlist p = new Playlist("All Songs");
     Playlist p1 = new Playlist("Liked Songs", 1);
     playlists.add(p1);
     Playlist p2 = new Playlist("Test playlist", 2);
     playlists.add(p2);


    if(App.display == null){
      App.display = new Display();
      App.display.setVisible(true);
    }


    generateTracklist();


    // for(Song s : tracklist){
    //   System.out.println(s);
    // }

    
      currentSong = tracklist.get(0);
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
        Song song = new Song(file,name,artist,like, nextID);
        nextID++;

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

  public Song findSong(int songID){
    Song found = null;

    //Linear search
    for(Song s : songs){
        if (s.getID() == songID){
            found = s;
        }
    }

    return found;
  }


  public Song getCurrentSong(){
    return currentSong;
  }

  public void setCurrentSong(Song s){
    currentSong = s;
    trackPosition = tracklist.indexOf(s);
  }

  public Playlist getCurrentPlaylist(){
    return currentPlaylist;
  }

  public void setCurrentPlaylist(Playlist p){
    currentPlaylist = p;
  }

  public void play(){
    audio.loadSong(currentSong.getFileLoc());
	  audio.play();
  }

  public Playlist findPlaylist(int playID){
    Playlist found = null;

    //Linear search
    for(Playlist p : playlists){
        if (p.getID() == playID){
            found = p;
        }
    }

    return found;
  }

  public boolean getIsShuffling(){
    return isShuffling;
  }

  public void setIsShuffling(boolean b){
    isShuffling = b;
    display.updateShuffleButton();
    generateTracklist();
  }


  public void generateTracklist(){
      tracklist = currentPlaylist.getSongs();

      if(isShuffling == true){
        Collections.shuffle(tracklist);
      }

      setTrackPos();

  }

  public void setTrackPos(){
    trackPosition = tracklist.indexOf(currentSong);
  }


  public void nextSong(){
    trackPosition++;
    if(trackPosition >= tracklist.size()){
      trackPosition = 0;
    }

    currentSong = tracklist.get(trackPosition);
    play();

  }

  public void prevSong(){
    trackPosition--;
    if(trackPosition < 0){
      trackPosition = tracklist.size()-1;
    }

    currentSong = tracklist.get(trackPosition);
    play();

  }

  public void createPlaylist(String name, Song firstSong){
    Playlist p = new Playlist(name, nextPlayID);

    p.addSong(firstSong);

    playlists.add(p);

    display.updatePlaylistsDisplay();

    nextPlayID++;
  }


}
