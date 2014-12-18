package backend;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server implements Runnable {
   Socket csocket;
   server(Socket csocket) {
      this.csocket = csocket;
   }

   public static void main(String args[]) 
   throws Exception {
      ServerSocket ssock = new ServerSocket(49150);
      System.out.println("Listening");
      while (true) {
         Socket sock = ssock.accept();
         System.out.println("Connected");
         BufferedReader input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    		String answer = input.readLine();
    		System.out.println(answer);
         new Thread(new server(sock)).start();
      }
   }
   public void run() {
      try {
         PrintStream pstream = new PrintStream
         (csocket.getOutputStream());
         for (int i = 100; i >= 0; i--) {
            pstream.println(i + 
            " bottles of beer on the wall");
         }
         pstream.close();
         csocket.close();
      }
      catch (IOException e) {
         System.out.println(e);
      }
   }
}
