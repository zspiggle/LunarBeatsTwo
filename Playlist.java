import java.util.ArrayList;


public class Playlist {
  
  private int id;
  private String name;
  private boolean deletable;

  private ArrayList<Song> songs;


  public Playlist(){
    songs = new ArrayList<Song>();
  }

  public Playlist(String name, int id){

    this.name = name;
    this.id = id;

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

  public String getName(){
    return name;
  }
  
  public void addSong(Song newSong){
    if(newSong != null){
      songs.add(newSong);
    }
  }


  public String toCSV(){
    return null;
  }

  public ArrayList<Song> getSongs(){
    return songs;
  }

  public void setDelete(boolean b){
    deletable = b;
  }

  public boolean getDelete(){
    return deletable;

  }

}
