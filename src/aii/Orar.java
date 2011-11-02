package aii;

import java.util.Date;


/**
 * The Class Orar.
 */
public class Orar {
	public enum Frecventa { Saptamanal, Pare, Impare};
	
	public Date zi;
	public int ora;
	public int durata;
	public int idActivitate;
	public Frecventa frecventa;
	public String grupa;
}
