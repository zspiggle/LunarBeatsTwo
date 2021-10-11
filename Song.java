import java.io.*;
/**
 * Stores the basic information needed for a song
 *
 * @author Zachary Spiggle
 * @version 1.0
 */
public class Song //implements Comparable<Song>
{
    //The comparable was never used, songs are organized by files and automatically load in an
    //alphabetical order
    
    private String fileLoc;
    
    private String artist;
    private String name;
    
    private boolean liked;
    
    /**
     *  Default constructer for a song. Requires 4 values
     *  
     *  @param  String f  The file associated with the song, is used in the audio class to play music
     *  @param  String a  The name of the artist who made the song
     *  @param  String n  The name of the song
     *  @param  boolean l  This value is whether the song is liked on not. This feature has been omitted
     */
    public Song(String f, String a, String n, boolean l){
        this.fileLoc = f;
        this.artist = a;
        this.name = n;
        this.liked = l;
        
    }
    
    /**
     *  Getter for the name of the song
     *  
     *  @return  The name of the song
     */
    public String getName(){
        return name;
    }
    
    /**
     *  Getter for the name of the artist of the song
     *  
     *  @return  The name of the artist
     */
    public String getArtist(){
        return artist;
    }
    
    /**
     *  Getter for the file of the song
     *  
     *  @return  The file of the song
     */
    public String getFileLoc(){
        return fileLoc;
    }
    
    //Omitted functions
    
    public String to_csv(){
        return artist + "," + name + "," + fileLoc + "," + liked;
    }
    
    public boolean getLike(){
        return liked;
    }
    
    public void setLike(boolean value){
        this.liked = value;
    }
    
    /*
    @Override
    public int compareTo(Song s){
        if((this.getName().equals(s.getName())) && (this.getArtist().equals(s.getArtist()))){
            return 0;
            
        } else { 
            //return -1;
        }
        
        return 0;
        
    }*/
    
    /**
     * Debugging function for the song
     * 
     * @param  returns a string version of the song
     */
    public String toString(){
        return artist + ":" + name;
    }
}
