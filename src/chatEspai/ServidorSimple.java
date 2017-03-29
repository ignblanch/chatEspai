package chatEspai;

import java.io.*;
import java.net.*;

public class ServidorSimple{
    private final int PORT = 3203;
    Socket client;
    
    public void runServer() throws IOException{
        ServerSocket skt = new ServerSocket(PORT);
        System.out.println("Servidor connectat. Esperant connexions...");
        while ((client=skt.accept()) != null){
   
            System.out.println("Petició entrant des de " + client.getInetAddress());
            //new ServerThread(sokt).start();
            String message;
            BufferedReader input = new BufferedReader (new InputStreamReader(client.getInputStream()));
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);
            while((message = input.readLine())!=null){
                System.out.println("Missatge entrant del client " + message);
                output.println("CLIENT: " + message);
                output.flush();
            }
            client.close();
        }
    }
    
    public static void main(String[] args) throws IOException {
        new ServidorSimple().runServer();
    }
    
    
}