/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorDimensiones;

import controladorBD.generadorSql;
import static controladorDimensiones.dimensionCliente.reemplazarCaracter;
import java.sql.SQLException;

/**
 *Clase que controlora las operacion sobre la dimension Equipo
 * @author User
 */
public class dimensionEquipo {
   
    generadorSql consultas;
    
    //se guardan arreglos con la información para cada una de los campos de la dimensión cliente
    private String[] marca;
    private String[] modelo;
    private String[] precio;
    private String[] sistema_operativo;
    private String[] pantalla;
    private String[] procesador;
    private String[] memoria;
    private String[] almacenamiento_interno;
    private String[] RED2G;
    private String[] RED3G;
    private String[] camara;
    private String[] GPS;
    private String[] radio;
    private String[] sim_card;
    
    private String[][] tablasimequipo;
    private String[][] tablasim;
    private String[][] tablaequipo;


    private int contador;

  public dimensionEquipo(){
      
      consultas = new generadorSql();
      contador = 0;
  
  }
  
  public void obtenertablaClienteBDR() throws SQLException{
      
      String query = "SELECT `id_sim_card`,`id_equipo_celular` FROM `contrato`";
  
     tablasimequipo=consultas.generarConsulta(query);
  
  
  }
  
  public String[][] gettablacliente(){
  
  return this.tablasimequipo;
  
  }
  
  public int getContador(){
  
  return this.contador;
  
  }
  
  public void transformarCaracteresEspeciales(){
      
      
      
      marca = new String[consultas.getFilas()];
      modelo = new String[consultas.getFilas()];
      precio = new String[consultas.getFilas()];
      sistema_operativo = new String[consultas.getFilas()];
      pantalla = new String[consultas.getFilas()];
      procesador = new String[consultas.getFilas()];
      memoria = new String[consultas.getFilas()];
      
      
  
  for(int i = 0; i < consultas.getFilas(); i++){  
        //aqui se realiza el procedimiento por cadena para transformar
            
            marca[i]= tablasimequipo[i][1];
            modelo[i]= tablasimequipo[i][2];
            precio[i] = reemplazarCaracter(tablasimequipo[i][3]);
            sistema_operativo[i] = reemplazarCaracter(tablasimequipo[i][4]);
            pantalla[i] = reemplazarCaracter(tablasimequipo[i][7]);
            procesador[i] = tablasimequipo[i][8];
            memoria[i] = tablasimequipo[i][9];
           
       
        }
  
  }
  
  
  public void insertarRegistroClienteDHW(){
      
      String query;
      
  
  //Aqui hay que hacer un ciclo for reocciendo los arrays ya procesados y transformados para relalziar las coonsultas y llamar al metodo insertarRegistro de la clase generadorSql
  
     for(int i = 0; i < consultas.getFilas(); i++){
           
       query = "INSERT INTO `dimension_cliente`(`tipo_identificacion`, `numero_identificacion`, `nombre`, `apellido`, `email`, `fecha_nacimiento`, `genero`) VALUES ('"+marca[i]+"',"+modelo[i]+",'"+precio[i]+"','"+sistema_operativo[i]+"','"+pantalla[i]+"','"+procesador[i]+"','"+memoria[i]+"')";     
       consultas.insertarRegistro(query, "jdbc:mysql://localhost/bodegacolmovil", "root", "");
       contador++;
       //aqui se realiza el procedimiento por cadena para transformar
        
  
         }  
      
      
  }
  
  
  public static String reemplazarCaracter(String entrada) {
    // Cadena de caracteres original a sustituir.
    String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
    // Cadena de caracteres ASCII que reemplazarán los originales.
    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
    String salida = entrada;
    for (int i=0; i<original.length(); i++) {
        // Reemplazamos los caracteres especiales.
        salida = salida.replace(original.charAt(i), ascii.charAt(i));
    }
    return salida;
}
 
    
}
