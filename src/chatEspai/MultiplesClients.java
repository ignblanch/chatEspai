package chatEspai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

    public class MultiplesClients extends Thread {

        Socket client;
        static ArrayList<Socket> usuaris = new ArrayList();

        public MultiplesClients(Socket client) {
            this.client = client;
            usuaris.add(client);
        }

        @Override
        public void run() {
            try {
                System.out.println("Petició entrant des de " + client.getInetAddress());
                String message;
                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter output = new PrintWriter(client.getOutputStream(), true);
                while ((message = input.readLine()) != null) {
                    System.out.println("Missatge entrant del client " + message);
                    
                    // Envia el missatge a tots els clients
                    for(int i=1; i<=usuaris.size(); i++){
                        Socket socket_temp = (Socket) usuaris.get(i-1);
                        PrintWriter out_temp = new PrintWriter(socket_temp.getOutputStream());
                        out_temp.println(message);
                        out_temp.flush();
                    }
                }
                input.close();
                output.close();
                client.close();

            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }