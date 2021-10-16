import java.io.*;

public class CSVWriter {
  
  //static String path = "Files/";

  private String fileLoc;
  private FileWriter fw;


  public CSVWriter(String filename){
    fileLoc = filename;

    File file = new File(filename);
    

    try{


      file.createNewFile();


      fw = new FileWriter(file);

    } catch (Exception e){
      System.out.println(e.getMessage());
      System.out.println("There was an issue loading a file");
    }
  }


  public void writeElement(String element){
    try{
      fw.write(element + ',');
    } catch (Exception e){
      System.out.println("There was an issue writing to a filewriter");
    }
    
  }

  public void writeLine(){
    try{
      fw.write("\n");
    } catch (Exception e){
      System.out.println("There was an issue writing to a filewriter");
    }
  }

  public void close(){
    try{
      fw.close();
    } catch (Exception e) {
      System.out.println("There was an issue closing a filewriter");
    }
  }

}
