import java.util.ArrayList;

public class PlaylistReader extends CSVReader{
  
  private ArrayList<Song> songs;
  private String name;

  public PlaylistReader(String filename){
    super(filename);
    //System.out.println(filename);



    
    name = filename;

    String newName = name.substring(16, name.length()-4);


    songs = new ArrayList<>();


    getSongs();

    //System.out.println(songs);

    App.app.createPlaylist(newName, songs);

  }

  public void getSongs(){




    //I dont know why this was a bug but data.length returned 4 for no reason
    //System.out.println(data.length);
    for(int i = 0; i < data.length; i++){

      Song s = App.app.findSong(data[i][0].toString(), data[i][1].toString());

      //System.out.println(s);

      if(s != null){
        songs.add(s);
      }

    }


  }
  

}
