package net.andreu.descarrega;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {

	private JFrame frame;
		
	private DefaultListModel<String> llistaIMG = new DefaultListModel<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 396);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel titol = new JLabel("URL:", SwingConstants.CENTER);
		titol.setFont(new Font("Serif", Font.PLAIN, 20));
		GridBagConstraints mida = new GridBagConstraints();
		mida.fill = GridBagConstraints.BOTH;
		mida.weightx = 0.5;
		mida.weighty = 0.1;
		mida.gridwidth = 1;
		mida.gridheight = 1;
		mida.gridx = 0;
		mida.gridy = 0;
		frame.getContentPane().add(titol, mida);
		
		JTextField url = new JTextField();
		url.setHorizontalAlignment(JTextField.CENTER);
		mida.fill = GridBagConstraints.BOTH;
		mida.gridwidth = 2;
		mida.gridheight = 1;
		mida.gridx = 1;
		mida.gridy = 0;
		frame.getContentPane().add(url, mida);
				
		JButton descarrega = new JButton("descarrega");
		mida.fill = GridBagConstraints.BOTH;
		mida.weightx = 0.5;
		mida.weighty = 0.05;
		mida.gridwidth = 1;
		mida.gridheight = 1;
		mida.gridx = 2;
		mida.gridy = 1;
		frame.getContentPane().add(descarrega, mida);
		procesa(descarrega, url);
		
		JLabel text = new JLabel("Llista de imatges");
		text.setFont(new Font("Serif", Font.PLAIN, 20));
		mida.fill = GridBagConstraints.BOTH;
		mida.weightx = 0.5;
		mida.weighty = 0.15;
		mida.gridwidth = 1;
		mida.gridheight = 1;
		mida.gridx = 1;
		mida.gridy = 2;
		frame.getContentPane().add(text, mida);

		JList<String> llista = new JList<String>(llistaIMG);
		mida.fill = GridBagConstraints.BOTH;
		mida.weightx = 1;
		mida.weighty = 1;
		mida.gridwidth = 3;
		mida.gridheight = 2;
		mida.gridx = 0;
		mida.gridy = 3;
		frame.getContentPane().add(llista, mida);
	}

	private void procesa(JButton descarrega, final JTextField url) {
		descarrega.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String direccio=url.getText();
				URL web;
				if(direccio.length()>0){
					try {
						Document doc=Jsoup.connect(direccio).get();
						Elements img=doc.getElementsByTag("img");
						for(Element imatge:img){
							String src=imatge.attr("src");
							int contador=0;
							
							String nomImg=src.substring(src.lastIndexOf('/')+1);
							llistaIMG.addElement(nomImg);
							
							web = new URL(src);
							InputStream entrada = web.openStream();
							OutputStream sortida = new FileOutputStream(nomImg);
							byte dades[]=new byte[1024];
							while ((contador=entrada.read(dades))!=-1) {
								sortida.write(dades, 0, contador);
								
							}
							sortida.flush();
							sortida.close();
							entrada.close();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
	}

}
