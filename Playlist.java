import java.util.ArrayList;


public class Playlist {
  
  public ArrayList<Song> songs;


  public Playlist(){
    songs = new ArrayList<Song>();
  }

  public Playlist(ArrayList<Song> s){
    if(s != null){
      songs = s;
    }
  }


  
  public void addSong(Song newSong){
    if(newSong != null){
      songs.add(newSong);
    }
  }


  public String toCSV(){
    return null;
  }

}
