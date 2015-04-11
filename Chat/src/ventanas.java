import java.awt.BorderLayout;   
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ventanas extends JFrame  implements Runnable{
	JTextArea caja=new JTextArea();
	JTextField info=new JTextField(20);
	JButton boton=new JButton();
	String infoserver="desconectado";
	JLabel texto=new JLabel("INFO =");
	
	JScrollPane scroll = new JScrollPane();
	
	String recibido;
    OutputStream osalida;
	DataOutputStream dsalida;
	boolean salida=true;

	InputStream ientrada;
	DataInputStream dentrada;

	Socket cliente;
	Thread hilocaja;
	
	
	public ventanas() {
		
		setSize(400,400);
		setLocation(200,200);
		
		servidor();
		
		setVisible(true);	
		
		scroll.setViewportView(caja);
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
			System.out.println("ALGO SALIO MAL CON EL RECIBIDO");
			System.err.println("Error: " + e);
		}
		
		hilocaja=new Thread(this);
		hilocaja.start();
		
	}


	public void servidor() { 
		try {
			
		} catch (Exception e) {
			
		}  
		
		this.setLayout(new GridLayout(1, 1, 1, 1));
		JPanel Pservidor=new JPanel();
		Pservidor.setLayout(new BorderLayout());
				
		JPanel Pcliente=new JPanel();
		Pcliente.setLayout(new BorderLayout());
		JPanel pcentro=new JPanel();
		pcentro.setLayout(new FlowLayout());
		this.info.setText("ORDEN");
		this.boton.setText("ACEPTAR");
		pcentro.add(this.info);
		pcentro.add(this.boton);
		boton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent evento){
					 try {
						dsalida.writeUTF(info.getText()+"\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							
					}	
				}
			);
		
		
		Pcliente.add(new JLabel("Select servidor"), BorderLayout.NORTH);
		Pcliente.add(pcentro, BorderLayout.CENTER);
		Pcliente.add(texto, BorderLayout.SOUTH);
		
		Pservidor.add(new JLabel("Select servidor"), BorderLayout.NORTH);
		Pservidor.add(scroll, BorderLayout.CENTER);
		Pservidor.add(Pcliente, BorderLayout.SOUTH);
		this.add(Pservidor);
		
			}
	public void cliente() {
		this.setLayout(new GridLayout(1, 1, 1, 1));
				
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
	    
	        
	}while(true);
		} catch (Exception e) {
			caja.setText(caja.getText()+"ERROR AL RECIBIR DATO \n ");
		}
	
	}

}		

	public static void main(String[] args) {
		ventanas obj= new ventanas();
	}
	
}
