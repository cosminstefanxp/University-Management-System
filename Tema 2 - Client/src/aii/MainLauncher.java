package aii;

import java.awt.EventQueue;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

import aii.arhiva.Arhiva;
import aii.gui.frames.AuthenticationFrame;
import aii.rad.RegistruActivitatiDidactice;

public class MainLauncher {

	/**
	 * Launch the application.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new AuthenticationFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
