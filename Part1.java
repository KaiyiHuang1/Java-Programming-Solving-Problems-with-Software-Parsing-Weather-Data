
/**
 * 在这里给出对类 Part1 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class Part1 {
      public CSVRecord coldestHourInFile(CSVParser parser){
          CSVRecord smallestSoFar = null;
          for(CSVRecord currentRow : parser){
              if(smallestSoFar == null){
                  smallestSoFar = currentRow;
                }
              else{
                  double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                  double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
                  if(currentTemp < smallestTemp && currentTemp != -9999){
                      smallestSoFar = currentRow;
                    }
                }
            }
            return smallestSoFar;
        }
      
      public String fileWithColdestTemperature(){
          CSVRecord lowestSoFar = null;
          DirectoryResource dr = new DirectoryResource();
          String Filename = null;
          for (File f : dr.selectedFiles()){
          FileResource fr = new FileResource(f);
          CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
          if(lowestSoFar == null){
              lowestSoFar = currentRow; 
              Filename = f.toString();
            }
            else{
                  double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                  double smallestTemp = Double.parseDouble(lowestSoFar.get("TemperatureF"));
                  if(currentTemp < smallestTemp){
                      lowestSoFar = currentRow;
                      Filename = f.toString();
                    }
            }
         }
         return Filename;
        }
      
      public CSVRecord lowestHumidityInFile(CSVParser parser){
          CSVRecord smallestSoFar = null;
          for(CSVRecord currentRow : parser){
              if(smallestSoFar == null){
                  smallestSoFar = currentRow;
                }
              else{
                  String Humidity = currentRow.get("Humidity");
                  if(Humidity != "N/A"){
                  double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
                  double smallestHumidity = Double.parseDouble(smallestSoFar.get("Humidity"));
                  if(currentHumidity < smallestHumidity){
                      smallestSoFar = currentRow;
                    }
                }
                }
            }
            return smallestSoFar;
        }
       
      public CSVRecord lowestHumidityInManyFiles(){
          CSVRecord smallestSoFar = null;
          DirectoryResource dr = new DirectoryResource();
          String Filename = null;
          for (File f : dr.selectedFiles()){
          FileResource fr = new FileResource(f);
          CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
          if(smallestSoFar == null){
              smallestSoFar = currentRow; 
              Filename = f.toString();
            }
            else{
                 String Humidity = currentRow.get("Humidity");
                 if(Humidity != "N/A"){
                 double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
                 double smallestHumidity = Double.parseDouble(smallestSoFar.get("Humidity"));
                 if(currentHumidity < smallestHumidity){
                      smallestSoFar = currentRow;
                    }
                }
            }
         }
         return smallestSoFar;
        }
       
      public double averageTemperatureInFile(CSVParser parser){
          double totaltemp = 0;
          double rowcount = 0;
          double averageTemp = 0;
          for(CSVRecord currentRow : parser){
              totaltemp = Double.parseDouble(currentRow.get("TemperatureF")) + totaltemp;
              rowcount = rowcount + 1;
            }
            averageTemp = totaltemp/rowcount;
            return averageTemp;
        }
        
      public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
          double totalTemp = 0;
          double rowcount = 0;
          double averageTemp = 0;
          for(CSVRecord currentRow : parser){
              if(Double.parseDouble(currentRow.get("Humidity")) >= value){
                  totalTemp = totalTemp + Double.parseDouble(currentRow.get("TemperatureF"));
                  rowcount = rowcount + 1;
                }
            }
            averageTemp = totalTemp/rowcount;
            return averageTemp;
        }
        
      public void testColdestHourInFile(){
          FileResource fr = new FileResource();
          CSVRecord smallest = coldestHourInFile(fr.getCSVParser());
          System.out.println("Coldest temp was " + smallest.get("TemperatureF") + "at" + smallest.get("TimeEST"));
      }
      
      public void testFileWithColdestTemperature(){
          String FileCold = fileWithColdestTemperature();
          System.out.println("Coldest temp was found in file" + FileCold);
        }
      
      public void testLowestHumidityInFile(){
          FileResource fr = new FileResource();
          CSVParser parser = fr.getCSVParser();
          CSVRecord csv = lowestHumidityInFile(parser);
          System.out.println("Coldest Humidity was " + csv.get("Humidity") + "at" + csv.get("TimeEDT"));
        }
        
      public void testLowestHumidityInManyFiles(){
          CSVRecord smallestSoFar = lowestHumidityInManyFiles();
          System.out.println("Lowest Humidity was " + smallestSoFar.get("Humidity") + " at "+ smallestSoFar.get("DateUTC"));
        }
        
      public void testAverageTemperatureInFile(){
          FileResource fr = new FileResource();
          CSVParser parser = fr.getCSVParser();
          double avgTemp = averageTemperatureInFile(parser);
          System.out.println("Average temperature in file is " + avgTemp);
        }
        
      public void testAverageTemperatureWithHighHumidityInFile(){
          FileResource fr = new FileResource();
          CSVParser parser = fr.getCSVParser();
          double avgTemp = averageTemperatureWithHighHumidityInFile(parser, 80);
          if(avgTemp == 0){
              System.out.println("No temperatures with that humidity");
            }
            else{
              System.out.println("Average Temp when high Humidity is " + avgTemp);
            }
        }
}
