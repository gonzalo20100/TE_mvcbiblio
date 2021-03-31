
package com.emergentes.modelo;
import java.util.ArrayList;

public class LibroDAO {
    private int correlativo;
    private ArrayList<Libro> libros;
    
    public LibroDAO(){
        libros = new ArrayList<Libro>();
    }

    public int getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(int correlativo) {
        this.correlativo = correlativo;
    }

    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public void setLibros(ArrayList<Libro> libros) {
        this.libros = libros;
    }
    public void insertar(Libro item){
        libros.add(item);
    }
    public void modificar(int id, Libro item){
        int pos = posicion(id);
        libros.set(pos,item);
    }
    public void eliminar(int id){
        int pos = posicion(id);
        libros.remove(pos);
    }
    public int posicion(int id){
        int i = -1;
        if (libros.size()>0) {
            for (Libro item : libros) {
                ++i;
                if (item.getId()==id) {
                    break;
                }
                
            }
        }
        return i;
    }
}
