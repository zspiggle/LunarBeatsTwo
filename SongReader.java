

public class SongReader extends CSVReader{
  

  public SongReader(String filename){
    super(filename);

    createSongs();

  }

  public void createSongs(){
    for(int i = 0; i < data.length; i++){

      String songName = data[i][0].toString();
      String artist = data[i][1].toString();
      // boolean liked;
      // if(Integer.parseInt(data[i][2].toString()) == 1){

      //   liked = true;

      // } else {
      //   liked = false;
      // }

      // int songId = Integer.parseInt(data[i][2].toString());
      String fileName = data[i][5].toString();

      // for(int j = 0; j < data[i].length; j++){

      // }

      App.app.newSong(fileName, songName, artist);//, songId); liked, songId);
      
      //System.out.println(fileName);
    }
  }

}
