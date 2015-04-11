import java.io.*;  
import java.net.*;
import javax.swing.JTextArea;

public class SocketClient implements Runnable{
	String recibido;
    OutputStream osalida;
	DataOutputStream dsalida;
	boolean salida=true;

	InputStream ientrada;
	DataInputStream dentrada;

	Socket cliente;
	Thread hilocaja;
	JTextArea caja;
	public SocketClient(JTextArea caja){
		this.caja=caja;
		hilocaja=new Thread(this);
		hilocaja.start();
		try {	
			cliente = new Socket("127.0.0.1", 3000);  
			osalida = cliente.getOutputStream();
			dsalida = new DataOutputStream(osalida);

			ientrada = cliente.getInputStream();
			dentrada = new DataInputStream(ientrada);
 
			recibido = dentrada.readUTF();
	        caja.setText(caja.getText()+recibido);
		}
		catch (Exception e) {
			System.err.println("Error: " + e);
		}
	}

	public void mensaje(String mmensaje){
		
		try {	
			dsalida.writeUTF(mmensaje+"\n");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
		
	public void cerrarsesion(){
		
		try {
			dsalida.close();
			dentrada.close();
		    cliente.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void run() {
		Thread ct= Thread.currentThread();
		if(ct==hilocaja){
			try {
		do{	
				recibido = dentrada.readUTF();
				caja.setText(caja.getText()+recibido);
		        System.out.println("cleinte recivio esto --->>>"+recibido);
			
		}while(true);
		
       } catch (Exception e) {
				
			}
		}
	
	}
	
 
	
}