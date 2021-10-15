

public class SongWriter extends CSVWriter{
  
  public SongWriter(String filename){
    //super(CSVWriter.path + filename);
    super(filename);
    for(Song s : App.app.songs){
      writeSong(s);

    }


  }

  public void writeSong(Song s){
    //Song,Artist,Liked,ID,FileLoc
    writeElement(s.getName());
    writeElement(s.getArtist());
    // if(s.getLike() == true){
    //   writeElement("1");
    // } else {
    //   writeElement("0");
    // }
    writeElement((String.valueOf(s.getID())));
    writeElement(s.getFileLoc());
    writeLine();

  }

}
