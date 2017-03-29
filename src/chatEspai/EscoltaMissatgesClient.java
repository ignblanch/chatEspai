package chatEspai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

// Thread que escolta el servidor i imprimeix els missatges al client en mode text
public class EscoltaMissatgesClient extends Thread{
    String missatgeEntrant;
    BufferedReader input;
    
    public EscoltaMissatgesClient(Socket sk) throws IOException{
            input = new BufferedReader(new InputStreamReader(sk.getInputStream()));
        }
    
    @Override
    public void run(){
        try {
            while((missatgeEntrant=input.readLine()) != null){
                System.out.println(missatgeEntrant);
            }   
        } catch (IOException ex) {
            Logger.getLogger(EscoltaMissatgesClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}