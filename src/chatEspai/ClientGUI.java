package chatEspai;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

// Client versió GUI
public class ClientGUI extends JFrame{
    // Variables per construir GUI
    private JFrame frame;
    private JTextField texto;
    private JButton enviar;
    public static JTextArea area;
    
    // Variables per a les connexions
    boolean run = true;
    Socket skt;
    String nomUsuari;
    PrintWriter output;
    BufferedReader input;
    
    
    // Monta la GUI
    public void startGUI(){
        frame = new JFrame ("ChatEspai");
        frame.setSize(560, 350);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        texto = new JTextField(150);
        texto.setBounds(10, 10, 425, 25);
        texto.setFocusable(true);
        
        enviar = new JButton("Enviar");
        enviar.setBounds(445, 10, 75, 25);
        enviar.addActionListener(new EscuchaBoton());
        frame.getRootPane().setDefaultButton(enviar);
        area = new JTextArea();
        area.setEditable(false);
        area.setBounds(10, 40, 520, 260);
        
        frame.add(texto);
        frame.add(enviar);
        frame.add(area);
        frame.setVisible(true);
        nomUsuari= JOptionPane.showInputDialog("Entra el teu nom d'usuari: "); // Pop-up per demanar el nom d'usuari
        area.append(nomUsuari + " s'ha connectat al servidor...");
    }
    
    // Mètode que escolta quan tanquem la finestra i tanca les connexions i el programa
    @Override
    protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
        if(e.getID() == WindowEvent.WINDOW_CLOSING) {
        try {
            output.close();
            input.close();
            skt.close();
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    // Recull les dades del textField i les envia i rep la resposta del servidor
    class EscuchaBoton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            String readerInput;
            readerInput = texto.getText();
            texto.setText("");
            texto.setFocusable(true);
            
            if(readerInput.compareToIgnoreCase("FIN")==0){
                area.append("Programa Finalizado");
                output.close();
                System.exit(0);
            }
            
            output.println(nomUsuari + ": " + readerInput);
            output.flush();
           
        }    
    }
    
    // Constructor. Inicia les connexions i streams
    public ClientGUI() throws IOException, UnknownHostException {
        //Iniciem el socket per la connexio
        skt = new Socket("localhost", 3203);
        
        //Iniciem el thread que escoltara els missatges del servidor
        new EscoltaMissatgesGUI(skt).start();
        
        //Creem els Streams per l'entrada i sortida de dades
        output = new PrintWriter(skt.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(skt.getInputStream()));
    }
    
    
    
    public static void main(String[] args) throws IOException {
        ClientGUI app = new ClientGUI();
        app.startGUI();
    }
    
}