package chatEspai;

import java.io.IOException;
import java.net.*;

public class ServidorThreads {

    private final int PORT = 3203;
    Socket client;

    public void runServer() throws IOException {
        ServerSocket skt = new ServerSocket(PORT);
        System.out.println("Servidor connectat. Esperant connexions...");
        while (true) {
            client = skt.accept();
            new MultiplesClients(client).start();
        }
    }

    public static void main(String[] args) throws IOException {
        new ServidorThreads().runServer();
    }
}
