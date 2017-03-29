package chatEspai;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


//Client en mode text
public class Client {
    String missatgeEntrant;
    
    public Client() throws IOException, UnknownHostException {
        boolean run = true;
        
        // Creem el socket de connexio amb el mateix port del servidor
        Socket skt = new Socket("localhost", 3203);
        // Demanem el nom a l'usuari
        System.out.println("Entra el teu nom d'usuari");
        
        // Creem els streams d'entrada i sortida de dades
        PrintWriter output = new PrintWriter(skt.getOutputStream(), true);
        // Creem dues entrades, una per la consola i l'altra pel servidor
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        // Agafem de la consola el nom d'usuari
        String nom = bfr.readLine();
        // Imprimim un missatge de confirmació
        System.out.println("Usuari " + nom + " connectat...");
        BufferedReader input = new BufferedReader(new InputStreamReader(skt.getInputStream()));
        
        // Creem un while infinit perquè el programa funcioni indefinidament fins que el tanquem
        while(run){
            //Iniciem el fil d'escolta de missatges del servidor que correrà en paral.lel
            
            new EscoltaMissatgesClient(skt).start();
            
            // Emmagatzemem el que entrem per la consola
            String readerInput = bfr.readLine();
            
            //Si introduim FIN acabem el programa
            if(readerInput.compareToIgnoreCase("FIN")==0){
                run = false;
            }
            
            //Enviem al servidor el missatge que hem escrit
            output.println(nom + ": " + readerInput);
            output.flush();
            
            //System.out.println(input.readLine());
        }
        output.close();
    }
    
    public static void main(String[] args) throws IOException {
        new Client();
    }

}
