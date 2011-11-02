package aii;

import java.util.Date;


/**
 * The Class Orar.
 */
public class Orar {
	public enum Frecventa { Saptamanal, Pare, Impare};
	public enum Ziua {Luni, Marti, Miercuri, Joi, Vineri};
	
	public Ziua zi;
	public int ora;
	public String sala;
	public int durata;
	public int idActivitate;
	public Frecventa frecventa;
	public String grupa;
}
