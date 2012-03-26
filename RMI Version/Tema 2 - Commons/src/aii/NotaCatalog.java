/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii;

import java.io.Serializable;
import java.sql.Date;

/**
 * The Class NotaCatalog that describes a grade in the students sheet.
 */
public class NotaCatalog implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2501749959405280500L;

	/** The cnp student. */
	public String cnpStudent;
	
	/** The cod disciplina. */
	public int codDisciplina;
	
	/** The data. */
	public Date data;
	
	/** The nota. */
	public int nota;
	
	//****************** METHODS ********************/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NotaCatalog [cnpStudent=" + cnpStudent + ", codDisciplina=" + codDisciplina
				+ ", data=" + data + ", nota=" + nota + "]";
	}

	//****************** GETTERS / SETTERS ********************/
	/**
	 * Gets the cnp student.
	 *
	 * @return the cnp student
	 */
	public String getCnpStudent() {
		return cnpStudent;
	}
	
	/**
	 * Sets the cnp student.
	 *
	 * @param cnpStudent the new cnp student
	 */
	public void setCnpStudent(String cnpStudent) {
		this.cnpStudent = cnpStudent;
	}
	
	/**
	 * Gets the cod disciplina.
	 *
	 * @return the cod disciplina
	 */
	public int getCodDisciplina() {
		return codDisciplina;
	}
	
	/**
	 * Sets the cod disciplina.
	 *
	 * @param codDisciplina the new cod disciplina
	 */
	public void setCodDisciplina(int codDisciplina) {
		this.codDisciplina = codDisciplina;
	}
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Date getData() {
		return data;
	}
	
	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(Date data) {
		this.data = data;
	}
	
	/**
	 * Gets the nota.
	 *
	 * @return the nota
	 */
	public int getNota() {
		return nota;
	}
	
	/**
	 * Sets the nota.
	 *
	 * @param nota the new nota
	 */
	public void setNota(int nota) {
		this.nota = nota;
	}
	

}
