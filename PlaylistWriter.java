

public class PlaylistWriter extends CSVWriter {
  

  private Playlist playlist;


  public PlaylistWriter(String filename, Playlist p){
    super(filename);

    playlist = p;


    writeElement("Song");
    writeElement("Artist");
    writeElement("File Location");
    writeLine();

    writePlaylist();
  }

  public void writeSong(Song s){
    writeElement(s.getName());
    writeElement(s.getArtist());
    writeElement(s.getFileLoc());
    writeLine();

  }

  public void writePlaylist(){
    for(Song s : playlist.getSongs()){
      writeSong(s);
    }
  }


}
