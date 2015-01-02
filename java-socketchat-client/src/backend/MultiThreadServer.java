package backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer implements Runnable {
   Socket csocket;
   MultiThreadServer(Socket csocket) {
      this.csocket = csocket;
   }

   public static void main(String args[]) 
   throws Exception {
      ServerSocket ssock = new ServerSocket(49149);
      System.out.println("Listening");
      while (true) {
    	  System.out.println("Will accept");
         Socket sock = ssock.accept();
         System.out.println("Connected");
         new Thread(new MultiThreadServer(sock)).start();
         BufferedReader input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
         String answer = input.readLine();
         System.out.println(answer);
      }
   }
   @Override
   public void run() {
      try {
    	 System.out.println("for looped started");
    	 PrintWriter out = new PrintWriter(csocket.getOutputStream(), true);
         for (int i = 100; i >= 0; i--) {
            out.println(i + " bottles of beer on the wall");
         }
         System.out.println("Closing connections");
         out.close();
         csocket.close();
      }
      catch (IOException e) {
         System.out.println(e);
      }
   }
}
