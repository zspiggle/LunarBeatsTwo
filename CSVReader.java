import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.FileNotFoundException;

/**
 * Parses information from a CSV file and loads it into the program
 */
public class CSVReader{


    private Object[] columnNames;

    private Object[][] data;
    
    private String filename;

    
    /**
     * Constructor for the file loader
     * Starts the process of loading the file
     * @param filename  The file you want to load into the program
     */
    public CSVReader (String filename){

      //Initalize Variables
      this.filename = filename;


      parseFile(); //gets raw data


    }

    /**
     * The parsing loop of the file. Uses a bufferedReader to read the file line by line.
     *  Will print to the console if it catches an exception. 
     * Stores all data gathered from the file into the data double array
     */
    public void parseFile() {

      ArrayList<Object[]> rows = new ArrayList<Object[]>();

      try{
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        String line;
        int row = 0;

        while((line = reader.readLine()) != null){
          if (row == 0){
            this.columnNames = parseLine(line);
          } else {
            rows.add(parseLine(line));
          }
          row++;
        }
        reader.close();


      } catch (Exception e){
        System.out.println("There was an error parsing the file.");
      }
      
      data = new Object[columnNames.length][rows.size()-1]; //-1 for column names
      data = rows.toArray(data);
      


    }


    /**
     * Parses an individual line into multiple points of data that were seperated by commas
     * 
     * @param line  The line you want data parsed from
     * @return  An array of objects that contain the row of data from the line
     */
    public Object[] parseLine(String line){
      ArrayList<Object> cells = new ArrayList<Object>();

      //int cellStart = 0;
      char c;
      StringBuilder sb = new StringBuilder();
      for(int pos = 0; pos < line.length(); pos++){
        
        
        c = line.charAt(pos);
        if(c != ','){
          sb.append(c);
          //System.out.println(c);
        } else {
          cells.add(parseCell(sb.toString()));
          sb.setLength(0);
        }

      }
      cells.add(parseCell(sb.toString())); //Run once more after not finding last comma
      sb.setLength(0);

      return cells.toArray();
    }

    /**
     * Parses a single point of data into its proper type. If it is a string, it will stay a string, if it is a double, it will return double, if int then int.
     * @param cell  The data point to be parsed
     * @return  A parsed data value in string, double, or int
     */
    public Object parseCell(String cell){
      Object value = cell;

      try{
        value = Double.parseDouble(cell);
      } catch (Exception e){
        //nothing
      }
      //Int after double because a int can always be a double, but a double cant always be an int
      try{
        value = Integer.parseInt(cell); //valueOf returns an Integer Object
      } catch (Exception e){
        //nothing
      }
      

      return value;
    }


    //Getters
    /**
     * Get method for raw data
     * @return array of raw data
     */
    public Object[][] getRawData(){
      return data;
    }

    /**
     * Get method for the column names
     * @return The column names in an array
     */
    public Object[] getHeaders(){
      return columnNames;
    }

    /**
     * Prints out an "organized" set of data that was parsed from the cvs file
     */
    public void printData(){
      //Could be formatted better
      System.out.print("\t");
      for(int row = 0; row < data.length; row++){
        for(int col = 0; col < columnNames.length; col++){
          if(row==0){
            System.out.print(columnNames[col]);
            if(col != columnNames.length - 1){
              System.out.print("\t\t | \t\t");
            }
          } else {
            System.out.print(data[row][col]);
            //This is horrible code
            if(data[row][col].toString().length() < 16){
              System.out.print("\t\t\t");
            } else {
              System.out.print("\t\t");
            }
           
          }
          
        }
        System.out.println();
      }

    }


    /**
     * Gives a string version of the double array. 
     */
    @Override
    public String toString(){
      StringBuilder sb = new StringBuilder();


      sb.append("[");


      for(int row = 0; row < data.length; row++){
        sb.append("[");
        for(int col = 0; col < columnNames.length; col++){
          sb.append(data[row][col]);
          sb.append(",");
          
        }
        sb.deleteCharAt(sb.length()-1); //removes last comma
        sb.append("]");
      }
      sb.append("]");
      return sb.toString();
    }

}