import javax.sound.sampled.*; 
import java.io.IOException; 
import java.io.OutputStream; 
import java.net.Socket; 
public class Client { 
private static final String SERVER_IP = "127.0.0.1"; 
private static final int PORT = 12345; 
public static void main(String[] args) throws IOException, LineUnavailableException { 
Socket socket = new Socket(SERVER_IP, PORT); 
AudioFormat format = new AudioFormat(44100, 16, 1, true, false); // Audio setup 
DataLine.Info micInfo = new DataLine.Info(TargetDataLine.class, format); 
TargetDataLine mic = (TargetDataLine) AudioSystem.getLine(micInfo); 
mic.open(format); 
mic.start(); 
Thread sendThread = new Thread(() -> { // Thread to capture and send audio data 
try { 
OutputStream outputStream = socket.getOutputStream(); 
byte[] buffer = new byte[1024]; 
int bytesRead; 
while (true) { 
bytesRead = mic.read(buffer, 0, buffer.length); 
outputStream.write(buffer, 0, bytesRead); 
} 
} catch (IOException e) 
{ e.printStackTrace(); 
} 
}); 
sendThread.start(); 
} 
} 
