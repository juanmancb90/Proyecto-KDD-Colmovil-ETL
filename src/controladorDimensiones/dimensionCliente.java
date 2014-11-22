/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorDimensiones;

import controladorBD.generadorSql;
import java.sql.SQLException;
/**
 *Clase que controlora las operacion sobre la dimension clientes
 * @author User
 */
public class dimensionCliente {
    
    generadorSql consultas;
    
    //se guardan arreglos con la información para cada una de los campos de la dimensión cliente
    private String[] tipo_identificacion;
    private String[] numero_identificacion;
    private String[] nombre;
    private String[] apellido;
    private String[] email;
    private String[] fecha_nacimiento;
    private String[] genero;
    private String[][] tablacliente;
    private int contador;

  public dimensionCliente(){
      
      consultas = new generadorSql();
      contador = 0;
  
  }
  
  public void obtenertablaClienteBDR() throws SQLException{
      
      String query = "SELECT * FROM cliente";
  
     tablacliente=consultas.generarConsulta(query);
  
  
  }
  
  public String[][] gettablacliente(){
  
  return this.tablacliente;
  
  }
  
  public int getContador(){
  
  return this.contador;
  
  }
  
  public void transformarCaracteresEspeciales(){
      
      
      
      tipo_identificacion = new String[consultas.getFilas()];
      numero_identificacion = new String[consultas.getFilas()];
      nombre = new String[consultas.getFilas()];
      apellido = new String[consultas.getFilas()];
      email = new String[consultas.getFilas()];
      fecha_nacimiento = new String[consultas.getFilas()];
      genero = new String[consultas.getFilas()];
      
      
  
  for(int i = 0; i < consultas.getFilas(); i++){  
        //aqui se realiza el procedimiento por cadena para transformar
            
            tipo_identificacion[i]= tablacliente[i][1];
            numero_identificacion[i]= tablacliente[i][2];
            nombre[i] = reemplazarCaracter(tablacliente[i][3]);
            apellido[i] = reemplazarCaracter(tablacliente[i][4]);
            email[i] = reemplazarCaracter(tablacliente[i][7]);
            fecha_nacimiento[i] = tablacliente[i][8];
            genero[i] = tablacliente[i][9];
           
       
        }
  
  }
  
  
  public void insertarRegistroClienteDHW(){
      
      String query;
      
  
  //Aqui hay que hacer un ciclo for reocciendo los arrays ya procesados y transformados para relalziar las coonsultas y llamar al metodo insertarRegistro de la clase generadorSql
  
     for(int i = 0; i < consultas.getFilas(); i++){
           
       query = "INSERT INTO `dimension_cliente`(`tipo_identificacion`, `numero_identificacion`, `nombre`, `apellido`, `email`, `fecha_nacimiento`, `genero`) VALUES ('"+tipo_identificacion[i]+"',"+numero_identificacion[i]+",'"+nombre[i]+"','"+apellido[i]+"','"+email[i]+"','"+fecha_nacimiento[i]+"','"+genero[i]+"')";     
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
