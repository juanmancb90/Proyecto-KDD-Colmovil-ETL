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
 * Clase que controlora las operacion sobre la dimension Pais
 * @author User
 */
public class dimensionPais {
    
     generadorSql consultas;
    
    //se guardan arreglos con la información para cada una de los campos de la dimensión cliente
    private String[] nombre_pais;
    private String[] continente;
    private String[] regin_pais;
    
    private String[][] tablaoperador;
    private int contador;

  public dimensionPais(){
      
      consultas = new generadorSql();
      contador = 0;
  
  }
  
  public void obtenertablaClienteBDR() throws SQLException{
      
      String query = "SELECT * FROM operador";
     tablaoperador=consultas.generarConsulta(query);
  
  
  }
  
  public String[][] gettablacliente(){
  
  return this.tablaoperador;
  
  }
  
  public int getContador(){
  
  return this.contador;
  
  }
  
  public void transformarCaracteresEspeciales(){
      
      
      
      nombre_pais = new String[consultas.getFilas()];
      continente = new String[consultas.getFilas()];
      regin_pais = new String[consultas.getFilas()];
      
      
      
      
  
  for(int i = 0; i < consultas.getFilas(); i++){  
        //aqui se realiza el procedimiento por cadena para transformar
            
            nombre_pais[i]= tablaoperador[i][1];
            continente[i]= tablaoperador[i][2];
            regin_pais[i] = reemplazarCaracter(tablaoperador[i][3]);
          
           
       
        }
  
  }
  
  
  public void insertarRegistroClienteDHW(){
      
      String query;
      
  
  //Aqui hay que hacer un ciclo for reocciendo los arrays ya procesados y transformados para relalziar las coonsultas y llamar al metodo insertarRegistro de la clase generadorSql
  
     for(int i = 0; i < consultas.getFilas(); i++){
           
       query = "INSERT INTO `dimension_cliente`(`tipo_identificacion`, `numero_identificacion`, `nombre`, `apellido`, `email`, `fecha_nacimiento`, `genero`) VALUES ('"+nombre_pais[i]+"',"+continente[i]+",'"+regin_pais[i]+"')";     
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
