import java.io.IOException;
import java.util.Scanner;
import java.util.Properties;
import java.io.*;
import java.net.*;


public class Main   {

    private static HttpURLConnection con;


    public static void main(String[] args) throws FileNotFoundException,IOException {
      String name = GetUserName() ;
      String q = Ask("\nWhat do you want to do?\n\nA: Set new mood.\nB: Get your friends moods.") ;

      if (q.equals("A")){
          String mood = Ask("Enter you mood");

          Http("https://web.site/php/post.php?username=" + URLEncoder.encode(name, "UTF-8") + "&mood=" + URLEncoder.encode(mood, "UTF-8"));
      }else if(q.equals("B")){
        Http("https://web.site/php/get.php");
      }else{return;
      }
    }



    public static void Http(String url)throws MalformedURLException,
            ProtocolException, IOException {

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("GET");

            StringBuilder content;

            con.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            System.out.println("\n" + content.toString());

        } finally {

            con.disconnect();
        }
    }


    public static String GetUserName()throws FileNotFoundException,IOException  {


        try {
            Properties loadProps = new Properties();
            loadProps.loadFromXML(new FileInputStream("settings.xml"));
            String name = loadProps.getProperty("name");
            System.out.println("Welcome Back " + name);
            return name ;
        }catch (Exception e){
            // If did't found the user name (FIRST TIME)

            String name = Ask("Enter your name") ;

            Properties saveProps = new Properties();
            saveProps.setProperty("name", name);
            saveProps.storeToXML(new FileOutputStream("settings.xml"), "");
            System.out.println("Welcome  " + name);
            return name ;
        }


    }

    public static String Ask(String question){
  Scanner reader = new Scanner(System.in);
    System.out.println(question + " :");
   String r = reader.next();
    return r;

}

}
