package refactor;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class VentanaDepart extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField numDep = new JTextField(10);
	private JTextField nomDep = new JTextField(25);
	private JTextField location = new JTextField(25);

	private JLabel mensaje = new JLabel(" ----------------------------- ");
	private JLabel titulo = new JLabel("GESTI�N DE DEPARTAMENTOS.");

	private JLabel lblNumDep = new JLabel("NUMERO DEPARTAMENTO:");
	private JLabel lblNomDep = new JLabel("NOMBRE:");
	private JLabel lblLocation = new JLabel("LOCALIDAD:");

	private JButton btnAlta = new JButton("Insertar Depar.t");
	private JButton btnConsult = new JButton("Consultar Depart.");
	private JButton btnDeleteDep = new JButton("Borrar Depart.");
	private JButton btnReset = new JButton("Limpiar datos.");
	private JButton btnModifyDep = new JButton("Modificar Departamento.");
	private JButton btnConsolePrint = new JButton("Ver por consola.");
	private JButton btnClose = new JButton("CERRAR");
	
	private Color colorChange;

	public VentanaDepart(JFrame workingFrame) {
		setTitle("GESTI�N DE DEPARTAMENTOS.");

		JPanel p0 = new JPanel();
		colorChange = Color.CYAN;
		p0.add(titulo);
		p0.setBackground(colorChange);

		JPanel p1 = new JPanel();
		colorChange = Color.GREEN;
		p1.setLayout(new FlowLayout());
		p1.add(lblNumDep);
		p1.add(numDep);
		p1.add(btnConsult);
		p1.setBackground(colorChange);

		JPanel p2 = new JPanel();
		colorChange = Color.BLUE;
		p2.setLayout(new FlowLayout());
		p2.add(lblNomDep);
		p2.add(nomDep);
		p2.setBackground(colorChange);

		JPanel p3 = new JPanel();
		colorChange = Color.MAGENTA;
		p3.setLayout(new FlowLayout());
		p3.add(lblLocation);
		p3.add(location);
		p3.setBackground(colorChange);

		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout());
		colorChange = Color.YELLOW;
		p4.add(btnAlta);
		p4.add(btnDeleteDep);
		p4.add(btnModifyDep);
		p4.setBackground(colorChange);

		JPanel p5 = new JPanel();
		p4.setLayout(new FlowLayout());
		colorChange = Color.PINK;
		p5.add(btnReset);
		p5.add(btnConsolePrint);
		p5.add(btnClose);
		p5.setBackground(colorChange);

		JPanel p7 = new JPanel();
		p7.setLayout(new FlowLayout());
		p7.add(mensaje);

		// para ver la ventana y colocar los controles verticalmente
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		// a�adir los panel al frame
		add(p0);
		add(p1);
		add(p2);
		add(p3);
		add(p4);
		add(p5);
		add(p7);
		pack(); // hace que se coloquen alineados los elementos de cada JPanel

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		btnAlta.addActionListener(this);
		btnReset.addActionListener(this);
		btnClose.addActionListener(this);
		btnConsult.addActionListener(this);
		btnDeleteDep.addActionListener(this);
		btnModifyDep.addActionListener(this);
		btnConsolePrint.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		int dep, confirm;
		
		// 11111111
		// Pulsado bot�n CONSULTAR
		if (e.getSource() == btnAlta) {
			mensaje.setText(" has pulsado el boton alta");
			try {
				dep = Integer.parseInt(numDep.getText());
				if (dep > 0)
					if (consultar(dep))
						mensaje.setText("DEPARTAMENTO EXISTE.");
					else {
						mensaje.setText("NUEVO DEPARTAMENTO.");
						grabar(dep, nomDep.getText(), location.getText());
						mensaje.setText("NUEVO DEPARTAMENTO GRABADO.");
					}
				else
					mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");

			} catch (java.lang.NumberFormatException ex) {
				mensaje.setText("DEPARTAMENTO ERR�NEO.");
			} catch (IOException ex2) {
				mensaje.setText("ERROR EN EL FICHERO. Fichero no existe. (ALTA)");
			}
		}

		
		//22222222222222
		// Pulsado bot�n CONSULTAR
		if (e.getSource() == btnConsult) {
			mensaje.setText(" has pulsado el boton alta");
			try {
				dep = Integer.parseInt(numDep.getText());
				if (dep > 0)
					if (consultar(dep)) {
						mensaje.setText("DEPARTAMENTO EXISTE.");
						visualiza(dep);
					} else {
						mensaje.setText("DEPARTAMENTO NO EXISTE.");
						nomDep.setText(" ");
						location.setText(" ");
					}
				else
					mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");

			} catch (java.lang.NumberFormatException ex) // controlar el error del Integer.parseInt
			{
				mensaje.setText("DEPARTAMENTO ERR�NEO");
			} catch (IOException ex2) {
				mensaje.setText("ERROR EN EL FICHERO. Fichero no existe. (ALTA)");
			}

		}

		
		//33333333333333333333
		// Pulsado bot�n BORRAR
		if (e.getSource() == btnDeleteDep) {
			mensaje.setText(" has pulsado el boton Borrar");
			try {
				dep = Integer.parseInt(numDep.getText());
				if (dep > 0)
					if (consultar(dep)) {
						mensaje.setText("DEPARTAMENTO EXISTE.");
						visualiza(dep);
						confirm = JOptionPane.showConfirmDialog(this, "ESTAS SEGURO DE BORRAR...", "AVISO BORRADO.",
								JOptionPane.OK_CANCEL_OPTION);
						// si devuelve 0 es OK
						// mensaje.setText(" has pulsado el boton Borrar "+ confirm);
						if (confirm == 0) {
							borrar(dep);
							mensaje.setText(" REGISTRO BORRADO: " + dep);
							nomDep.setText(" ");
							location.setText(" ");
						}
					} else {
						mensaje.setText("DEPARTAMENTO NO EXISTE.");
						nomDep.setText(" ");
						location.setText(" ");
					}
				else
					mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");

			} catch (java.lang.NumberFormatException ex){
				mensaje.setText("DEPARTAMENTO ERR�NEO");
			} catch (IOException ex2) {
				mensaje.setText("ERROR EN EL FICHERO. Fichero no existe. (BORRAR)");
			}
		}
		
		
		//4444444444444444444
		// Pulsado bot�n MODIFICAR
		if (e.getSource() == btnModifyDep) {
			mensaje.setText(" has pulsado el boton Modificar.");
			try {
				dep = Integer.parseInt(numDep.getText());
				if (dep > 0)
					if (consultar(dep)) {
						mensaje.setText("DEPARTAMENTO EXISTE.");
						confirm = JOptionPane.showConfirmDialog(this, "ESTAS SEGURO DE MODIFICAR...",
								"AVISO MODIFICACI�N.", JOptionPane.OK_CANCEL_OPTION);
						// si devuelve 0 es OK
						if (confirm == 0) {
							modificar(dep);
							mensaje.setText(" REGISTRO MODIFICADO: " + dep);
						}
					} else {
						mensaje.setText("DEPARTAMENTO NO EXISTE.");
						nomDep.setText(" ");
						location.setText(" ");
					}
				else
					mensaje.setText("DEPARTAMENTO DEBE SER MAYOR QUE 0");

			} catch (java.lang.NumberFormatException ex){
				mensaje.setText("DEPARTAMENTO ERR�NEO");
			} catch (IOException ex2) {
				mensaje.setText(" ERROR EN EL FICHERO. Fichero no existe. (MODIFICAR)");
			}
		}
	
		
		//5555555555555555555555
		// Pulsado bot�n SALIR
		if (e.getSource() == btnClose) { 
			System.exit(0);
		}
		
		
		//66666666666666666
		// Pulsado bot�n VER POR CONSOLA
		if (e.getSource() == btnConsolePrint) { 
			try {
				mensaje.setText("Visualizando el fichero por la consolaa.....");
				verporconsola();
			} catch (IOException e1) {
				System.out.println("ERROR AL LEER AleatorioDep.dat");
			}
		}
	
		
		//777777777777777
		// Pulsado bot�n LIMPIAR
		if (e.getSource() == btnReset) {
			mensaje.setText(" has pulsado el boton limpiar..");
			numDep.setText(" ");
			nomDep.setText(" ");
			location.setText(" ");
		}
	}

	public void verporconsola() throws IOException, FileNotFoundException {
		String nom = "", loc = "";
		int dep = 0;
		long pos;
		File fichero = new File("AleatorioDep.dat");
		RandomAccessFile file = new RandomAccessFile(fichero, "r");
		char cad[] = new char[10], aux;
		
		if (file.length() > 0) {
			pos = 0; // para situarnos al principio
			System.out.println(" ------------------------------------------");
			System.out.println(" - - - VISUALIZO POR CONSOLA ");
			for (;;) { // recorro el fichero, visualiza tambi�n las posiciones vac�as
				file.seek(pos);
				dep = file.readInt(); // obtengo el dep
				for (int i = 0; i < cad.length; i++) {
					aux = file.readChar();// recorro uno a uno los caracteres del apellido
					cad[i] = aux; // los voy guardando en el array
				}
				nom = new String(cad);// convierto a String el array
				for (int i = 0; i < cad.length; i++) {
					aux = file.readChar();
					cad[i] = aux;
				}
				loc = new String(cad);// convierto a String el array
				System.out.println("DEP: " + dep + ", Nombre: " + nom + ", Localidad: " + loc);
				pos = pos + 44;
				// Si he recorrido todos los bytes salgo del for
				if (file.getFilePointer() == file.length())
					break;
			}
			
			file.close(); // cerrar fichero
			System.out.println(" ------------------------------------------");
		} else {// esto s�lo sale la primera vez
			System.out.println(" ---------FICHERO VAC�O --------------------");
			file.close(); // cerrar fichero
			System.out.println(" ------------------------------------------");
		}
	}// fin verporconsola

	boolean consultar(int dep) throws IOException {
		long pos;
		int depa;
		File fichero = new File("AleatorioDep.dat");
		RandomAccessFile file = new RandomAccessFile(fichero, "r");
		// Calculo del reg a leer
		try {
			pos = 44 * (dep - 1);
			if (file.length() == 0)
				return false; // si est� vac�o
			file.seek(pos);
			depa = file.readInt();
			file.close();
			System.out.println("Depart leido:" + depa);
			if (depa > 0)
				return true;
			else
				return false;
		} catch (IOException ex2) {
			System.out.println(" ERROR al leer..");
			return false;
		}
	} // fin consultar

	void visualiza(int dep) {
		String nom = "", loca = "";
		long pos;
		int depa;
		File fichero = new File("AleatorioDep.dat");
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "r");
			// Calculo del reg a leer
			pos = 44 * (dep - 1);
			file.seek(pos);
			depa = file.readInt();
			System.out.println("Depart leido:" + depa);
			char nom1[] = new char[10], aux, loc1[] = new char[10];
			for (int i = 0; i < 10; i++) {
				aux = file.readChar();
				nom1[i] = aux;
			}
			for (int i = 0; i < 10; i++) {
				aux = file.readChar();
				loc1[i] = aux;
			}
			nom = new String(nom1);
			loca = new String(loc1);
			System.out.println("DEP: " + dep + ", Nombre: " + nom + ", Localidad: " + loca);
			nomDep.setText(nom);
			location.setText(loca);
			file.close();
		} catch (IOException e1) {
			System.out.println("ERROR AL LEER AleatorioDep.dat");
			e1.printStackTrace();
		}
	} // fin visualiza

	void borrar(int dep) { // con borrar ponemos a 0 el dep que se quiere borrar
							// y a blancos el nomDep y la localidad
		String nom = "", loca = "";
		StringBuffer buffer = null;
		long pos;
		File fichero = new File("AleatorioDep.dat");
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			// Calculo del reg a leer
			pos = 44 * (dep - 1);
			file.seek(pos);
			int depp = 0;
			file.writeInt(depp);
			buffer = new StringBuffer(nom);
			buffer.setLength(10);
			file.writeChars(buffer.toString());

			buffer = new StringBuffer(loca);
			buffer.setLength(10);
			file.writeChars(buffer.toString());
			System.out.println("----REGISTRO BORRADO--------");

			file.close();
		} catch (IOException e1) {
			System.out.println("ERRROR AL BORRAR AleatorioDep.dat");
			e1.printStackTrace();
		}
	} // fin borrar

	void modificar(int dep) { // con modificar asignamos los datos tecleados
		String nom = "", loca = "";
		StringBuffer buffer = null;
		long pos;
		File fichero = new File("AleatorioDep.dat");
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			// Calculo del reg a leer
			pos = 44 * (dep - 1);
			file.seek(pos);
			file.writeInt(dep);
			nom = nomDep.getText();
			loca = location.getText();
			buffer = new StringBuffer(nom);
			buffer.setLength(10);
			file.writeChars(buffer.toString());
			buffer = new StringBuffer(loca);
			buffer.setLength(10);
			file.writeChars(buffer.toString());
			System.out.println("----REGISTRO MODIFICADO--------");

			file.close();
		} catch (IOException e1) {
			System.out.println("ERRROR AL MODIFICAR AleatorioDep.dat");
			e1.printStackTrace();
		}
	} // fin modificar

	void grabar(int dep, String nom, String loc) {
		long pos;
		StringBuffer buffer = null;
		File fichero = new File("AleatorioDep.dat");
		try {
			RandomAccessFile file = new RandomAccessFile(fichero, "rw");
			// Calculo del reg a leer
			pos = 44 * (dep - 1);
			// if (file.length()==0) return false; // si est� vac�o

			file.seek(pos);
			file.writeInt(dep);
			buffer = new StringBuffer(nom);
			buffer.setLength(10);
			file.writeChars(buffer.toString());// insertar nomDep
			buffer = new StringBuffer(loc);
			buffer.setLength(10);
			file.writeChars(buffer.toString());// insertar location
			file.close();
			System.out.println(" GRABADO el " + dep);
		} catch (IOException e1) {
			System.out.println("ERRROR AL grabar AleatorioDep.dat");
			e1.printStackTrace();
		}
	} 
}
