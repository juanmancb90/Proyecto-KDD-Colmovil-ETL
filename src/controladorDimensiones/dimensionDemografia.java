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
 *Clase que controlora las operacion sobre la dimension demografia
 * @author User
 */
public class dimensionDemografia {
    
    generadorSql consultas;
    
    //se guardan arreglos con la información para cada una de los campos de la dimensión cliente
    private String[] direccion_residencia;
    private String[] estrato;
    private String[] estado_civil;
    private String[] edad;
    
    private String[][] tablademografia;
    private int contador;

  public dimensionDemografia(){
      
      consultas = new generadorSql();
      contador = 0;
  
  }
  
  public void obtenertablaClienteBDR() throws SQLException{
      
      String query = "SELECT direccion_residencia, estrato, estado_civil, year(curdate())-year(fecha_nacimiento) as edad FROM cliente";
  
     tablademografia=consultas.generarConsulta(query);
  
  
  }
  
  public String[][] gettablacliente(){
  
  return this.tablademografia;
  
  }
  
  public int getContador(){
  
  return this.contador;
  
  }
  
  public void transformarCaracteresEspeciales(){
      
      
      
      direccion_residencia = new String[consultas.getFilas()];
      estrato = new String[consultas.getFilas()];
      estado_civil = new String[consultas.getFilas()];
      edad = new String[consultas.getFilas()];
      
      
  
  for(int i = 0; i < consultas.getFilas(); i++){  
        //aqui se realiza el procedimiento por cadena para transformar
            
            direccion_residencia[i]= reemplazarCaracter(tablademografia[i][0]);
            estrato[i]= reemplazarCaracter(tablademografia[i][1]);
            estado_civil[i] = reemplazarCaracter(tablademografia[i][2]);
            edad[i] = reemplazarCaracter(tablademografia[i][3]);
            
           
       
        }
  
  }
  
  
  public void insertarRegistroDemografiaDHW(){
      
      String query;
      
  
  //Aqui hay que hacer un ciclo for reocciendo los arrays ya procesados y transformados para relalziar las coonsultas y llamar al metodo insertarRegistro de la clase generadorSql
  
     for(int i = 0; i < consultas.getFilas(); i++){
           
       query = "INSERT INTO `dimension_demografia`(`direccion_residencia`, `estrato`, `estado_civil`, `edad`) VALUES ('"+direccion_residencia[i]+"',"+estrato[i]+",'"+estado_civil[i]+"','"+edad[i]+"')";     
       consultas.insertarRegistro(query, "jdbc:mysql://localhost/bodegacolmovil", "root", "");
       contador++;
       System.out.println(contador);
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
