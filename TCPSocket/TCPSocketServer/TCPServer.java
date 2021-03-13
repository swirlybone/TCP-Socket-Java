import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * Write a description of class TCPServer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TCPServer
{
    public static void main(String argv[]) throws Exception
    {

        String clientSentence; //tracks each sentence in file 

        Socket connectionSocket; //Socket variable

        BufferedReader fromClient; //Buffered Reader variable

        long totalTime = 0; //tracks time it takes to recieve file

        String comparedFile = "Test1 - large.txt"; 

        String outputFileName = "receivedFile";

        ServerSocket serSock = new ServerSocket(5789); //opens up the server socket so the client can connect

        System.out.println("I am ready for any client side request");

        //this loop makes the server listen for 100 files from the client side
        for(int i=0; i<100; i++)
        {
            long fileTime = 0;
            long startTime = System.currentTimeMillis();

            //this makes the server wait till the client tries to connect then accepts the connection
            connectionSocket = serSock.accept();
            System.out.println("I am starting to receive the " + comparedFile + " file for the " + (i+1) + "th time.");

            //this creates the stream that will be used to receive the file.
            fromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            //creates a file that the received file will be written to
            PrintWriter out = new PrintWriter(outputFileName+(i+1)+ ".txt");

            int lineCounter = 0; //variable to keep track of the number of lines.

            //this loop checks to see if the client's file has ended and it prints how many lines have been received.
            while(true)
            {
                clientSentence = fromClient.readLine();

                if(clientSentence == null) break;

                out.println(clientSentence);

            }

            out.close();

            connectionSocket.close();
            long endTime = System.currentTimeMillis();
            fileTime = endTime - startTime;
            System.out.println("I am finishing receiving the " + comparedFile + " file for the " + (i+1) + "th time");
            System.out.println("The time used in milisecond to receive "  + comparedFile + " for the " + (i+1) + "th time is " + Math.abs(fileTime));

            totalTime += fileTime;

            System.out.println("\n-----------------------\n");
        }
        System.out.println("I am done receiving files.");

        serSock.close();
        int failCount = 0;

        File serverFile = new File(comparedFile); //the file in the server side folder.

        //this loop reads the server file and the files received from the client to compare them and find any errors
        for(int i=0; i<100; i++)
        {
            File clientFile = new File(outputFileName + (i+1) + ".txt");
            Scanner serverFileReader = new Scanner(serverFile);
            Scanner clientFileReader = new Scanner(clientFile);

            boolean passedTheTest;
            while(serverFileReader.hasNextLine() && clientFileReader.hasNextLine())
            {
                passedTheTest = serverFileReader.nextLine().equals(clientFileReader.nextLine());

                //prints out any error found.
                if(!(passedTheTest))
                {
                    System.out.println("File " + outputFileName+(i+1)+ ".txt has error");
                    failCount++;
                    break;
                }
            }
            serverFileReader.close();
            clientFileReader.close();
        }
        System.out.println("The failure rate is " + failCount + "/100"); //the failure rate when comparing the files
        System.out.println("The average time to receive the file: " + (totalTime/100) + " milliseconds"); //the average time it took to receive a file.
        System.out.println("I am done");
    }

}
