//単語に分解し一行に出力
//javac -cp .:jsoup-1.8.2.jar Extract.java
//java -cp .:jsoup-1.8.2.jar Extract
import java.io.*;
import org.jsoup.Jsoup;

public class Extract{
  public Extract() {}

    public static String extractText(Reader reader) throws IOException {
      StringBuilder sb = new StringBuilder();
      BufferedReader br = new BufferedReader(reader);
      String line;
      while ( (line=br.readLine()) != null) {
        sb.append(line);
      }
      String textOnly = Jsoup.parse(sb.toString()).text();
      return textOnly;
    }

    public final static void parse(String filename) throws Exception{
      FileReader reader = new FileReader(filename);
      String s = Extract.extractText(reader);
      System.out.println(s);
      //--------------------------------------------------------------------------------------------------
      BufferedWriter writer = null;
      try
      {
        writer = new BufferedWriter( new FileWriter(filename));
        writer.write(s);
      } catch ( IOException e) {
      }

      finally {
        try  {
          if ( writer != null)
          writer.close( );
        } catch ( IOException e) {
        }
      }
      //------------------------------------------------------------------------------------------------
    }
  }
