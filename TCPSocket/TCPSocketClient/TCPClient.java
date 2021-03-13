import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * Write a description of class TCPClient here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TCPClient
{
    public static void main(String argv[]) throws Exception
    {

        String fileName = "Test1 - large.txt"; 

        System.out.println("Looking for " + fileName + " file....");
        System.out.println("\n-----------------------\n");

        File clientFile = new File(fileName); //creating the file variable

        long totalTime = 0; //for the total time

        //this loops sends the file 100 times
        for (int i=0; i<100; i++)
        {
            long fileTime = 0;

            long startTime = System.currentTimeMillis(); //for the start of the transmission

            Scanner clientFileReader = new Scanner(clientFile); //creates a scanner for the file being sent
            //creates the client socket to connect to the server socket
            Socket clientSocket = new Socket("10.67.65.95", 5789); //the desired server socket and IP
            
           
            System.out.println("I am connecting to the Server side: " + clientSocket.getLocalAddress());

            System.out.println("\nI am sending " + clientFile + " file for the " + (i+1) + "th time");
            
            

            

            //the output stream that will be used to send data to the server
            DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());

            //reads in the file and replaces the "\n" dropped by the nextLine() function.
            while(clientFileReader.hasNextLine())
            {
                toServer.writeBytes(clientFileReader.nextLine() + "\n");
            }
            
            clientFileReader.close(); 

            clientSocket.close(); 

            long endTime = System.currentTimeMillis(); //end of the file transmission variable

            
            System.out.println("\nI am finishing sending file " + clientFile +"for the " + (i+1) + "th time");
            fileTime = endTime - startTime; //the time to send the file

            System.out.println("The time used in miliseconds to send " + clientFile +" for the " + (i+1) + "th time is"+ fileTime);

            totalTime += fileTime; //the total time to send all of the files

            System.out.println("The " + clientFile + " file sent " + (i+1) + " times");

            System.out.println("\n-----------------------\n");
        }
        System.out.println("The average time to send the file: " + totalTime/100 + "." + totalTime%100 + " milliseconds"); //displays the average
        System.out.println("I am done");
    }
}
