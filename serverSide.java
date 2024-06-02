import javax.sound.sampled.*; 
import java.io.*; 
import java.net.ServerSocket; 
import java.net.Socket; 
public class Server { 
private static final int PORT = 12345; 
public static void main(String[] args) throws IOException, LineUnavailableException { 
ServerSocket serverSocket = new ServerSocket(PORT); 
System.out.println("Server listening on port " + PORT); 
Socket clientSocket = serverSocket.accept(); 
System.out.println("Connection established with client"); 
AudioFormat format = new AudioFormat(44100, 16, 1, true, false); // Audio setup 
DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, format); 
SourceDataLine speaker = (SourceDataLine) AudioSystem.getLine(speakerInfo); 
speaker.open(format); 
speaker.start(); 
Thread receiveThread = new Thread(() -> { // Thread to receive and play audio data 
try { 
InputStream inputStream = clientSocket.getInputStream(); 
byte[] buffer = new byte[1024]; 
int bytesRead; 
while ((bytesRead = inputStream.read(buffer)) != -1) 
speaker.write(buffer, 0, bytesRead); 
} catch (IOException e) 
{ e.printStackTrace(); 
} 
}); 
receiveThread.start(); 
}}
