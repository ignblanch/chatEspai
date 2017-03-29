package chatEspai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

// Thread que escolta les respostes del servidor i les imprimeix al GUI
public class EscoltaMissatgesGUI extends Thread{
    String missatgeEntrant;
    BufferedReader input;
    
    public EscoltaMissatgesGUI(Socket sk) throws IOException{
            input = new BufferedReader(new InputStreamReader(sk.getInputStream()));
        }
    
    @Override
    public void run(){
        try {
            while((missatgeEntrant=input.readLine()) != null){
                ClientGUI.area.append("\n" + missatgeEntrant);
            }   
        } catch (IOException ex) {
            Logger.getLogger(EscoltaMissatgesClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}