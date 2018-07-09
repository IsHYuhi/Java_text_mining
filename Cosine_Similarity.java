//ネットから読み込んでファイルに書き込むプログラム。
//get_htmlの変更後複数のURLを指定しファイルに書き込む
//Extract.javaを用いてparseする。
//javac -cp .:jsoup-1.8.2.jar Extract.java
//javac -cp .:jsoup-1.8.2.jar Get_html_and_Write.java
//java -cp .:jsoup-1.8.2.jar Get_html_and_Write
//必要ファイル TFIDF.java Extract.java
//https://www.bbc.com/news/world-us-canada-44669009 https://www.bbc.com/news/live/business-44657989 https://www.bbc.com/news/business-44493414
//https://www.bbc.com/sport/live/football/44071127 https://www.bbc.com/sport/football/44679819 https://www.bbc.com/news/entertainment-arts-44677975 https://www.bbc.com/news/newsbeat-44679514 https://www.bbc.com/sport/football/44419842 https://www.bbc.com/news/business-44664834 https://www.bbc.com/news/uk-northern-ireland-44651240
import java.io.*;
import java.net.*;

class Csine_Similarity{
  public static void main(String args[])throws Exception{
    String aHTML = new String();
    System.out.println("比較したい文書のURLを入力をしてください(複数可)");
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String buf = null;
    buf = br.readLine();
    String  [] file_name = buf.split("\\s");
    System.out.println("次に出力するファイル名をそれぞれ対応する順番で入力してください");
    buf = null;
    buf = br.readLine();
    String  [] write_file_name = buf.split("\\s");

    if(file_name.length != write_file_name.length) {
      System.out.println("errorwritefile");// java ReadHTMLWriteFile の後にurlと書き込み先のファイルを並べて書く。
      System.exit(0);
    }
    br.close();

    for(int j = 0; j<file_name.length; j++){
      aHTML = new String();
      try {
        // Obtain URL
        URL url = new URL(file_name[j]);

        // Obtain input stream
        InputStream is = url.openStream();

        // Read and display data from URL
        byte buffer[] = new byte[1024];
        int i;
        while((i = is.read(buffer)) != -1) {
          aHTML = aHTML + new String(buffer);
        }

      }
      catch (Exception e) {
        e.printStackTrace();
      }
      // End of Read from network, and now write to a file.
      BufferedWriter writer = null;
      try
      {
        writer = new BufferedWriter( new FileWriter( write_file_name[j]));
        writer.write( aHTML);
      } catch ( IOException e) {

      }finally {
        try  {
          if ( writer != null)
          writer.close( );
        } catch ( IOException e) {
        }
      } // finally close
    }
    for(int i = 0; i<file_name.length;i++){
      Extract ex = new Extract();
      ex.parse(write_file_name[i]);
    }
    TFIDF tfidf = new TFIDF();
    tfidf.Tfidf(write_file_name);
  }
}

