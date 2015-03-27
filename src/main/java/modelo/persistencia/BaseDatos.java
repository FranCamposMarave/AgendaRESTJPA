package modelo.persistencia;

import modelo.datos.Persona;
//import modelo.datos.utils.ListaPersonas;


public interface BaseDatos {
	Persona ENTRADA_NULL = new Persona();
	Persona[] getListaPersonas();
	Persona buscaPersonaPorNIF(String nif);
	boolean nuevaPersona(Persona persona);
	boolean actualizaPersona(Persona entrada);
	boolean borraPersona(String nif);
}