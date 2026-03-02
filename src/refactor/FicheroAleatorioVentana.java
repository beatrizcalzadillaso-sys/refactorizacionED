package refactor;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JFrame;

public class FicheroAleatorioVentana {

	public static void main(String[] args) {

		JFrame depDesk = new JFrame("DEPARTAMENTOS.");
		
		try {
			
			File fichero = new File("AleatorioDep.dat");
	
			RandomAccessFile fileObject;
			fileObject = new RandomAccessFile(fichero, "rw");
			fileObject.close();
	
			VentanaDepart ventanaDepart = new VentanaDepart(depDesk);
			ventanaDepart.setVisible(true);
		} catch(IOException exc) {
			exc.printStackTrace();
		}	
	}
}