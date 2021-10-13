import java.util.ArrayList;


public class Playlist {
  
  private int id;
  public String name;

  public ArrayList<Song> songs;


  public Playlist(){
    songs = new ArrayList<Song>();
  }

  public Playlist(String name, ArrayList<Song> s, int id){

    this.name = name;
    this.id = id;

    if(s != null){
      songs = s;
    }
  }


  public int getID(){
    return id;
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
