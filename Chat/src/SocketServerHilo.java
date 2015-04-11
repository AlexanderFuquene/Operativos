import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class SocketServerHilo implements Runnable {

    String recibido;
    OutputStream osalida;
	DataOutputStream dsalida;

	InputStream ientrada;
	DataInputStream dentrada;

	Socket socket;
	int numerodelhilo;
	
	ArrayList<SocketServerHilo> hermanos;

	public SocketServerHilo(Socket lsocket,int numerodelhilo,ArrayList<SocketServerHilo> hermanos){
		try{
			this.numerodelhilo=numerodelhilo;
			this.hermanos=hermanos;
			socket = lsocket;			
		}
		catch (Exception excepcion) {
			System.out.println(excepcion);
		}		
	}

	public void run() {	

		try{			
			osalida = socket.getOutputStream();
			dsalida = new DataOutputStream(osalida);

			ientrada = socket.getInputStream();
			dentrada = new DataInputStream(ientrada);

			dsalida.writeUTF("Bienvenido al server\n");

			do{
				recibido = dentrada.readUTF();	
				
				int tama=hermanos.size();
				for (int i = 0; i < tama; i++) {
					hermanos.get(i).dsalida.writeUTF("Cliente "+numerodelhilo+": "+recibido);
					 
				}
				
			}while(!recibido.equals("bye"));
		}
		catch (IOException excepcion) {
			System.out.println("SE SALIO EL SERVIDOR DEL CLIENTE "+numerodelhilo);
		//	System.out.println(excepcion.getMessage());
		}		
		
		try{
			dsalida.close();
			dentrada.close();
			socket.close();			
		}
		catch (IOException excepcion) {
			System.out.println(excepcion);
		}			
	}
}