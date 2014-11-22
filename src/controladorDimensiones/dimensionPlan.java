/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorDimensiones;

import controladorBD.generadorSql;
import static controladorDimensiones.dimensionCliente.reemplazarCaracter;
import java.sql.SQLException;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que controlora las operacion sobre la dimension Plan
 * @author User
 */
public class dimensionPlan {
    
     generadorSql consultas;
    
    //se guardan arreglos con la información para cada una de los campos de la dimensión cliente
    private String[] tipo_plan_voz; //tabla plan_voz relacion voz
    private String[] tipo_plan_datos; //tabla plan_voz relacion datos
    private String[] fecha_inicio_plan; //tabla plan_voz
    private String[] fecha_retiro; //tabla plan_voz relacion retiro
    private String[] causa_retiro; //tabla plan_voz relacion retiro
    private String[] flag_roaming; //esta en veremos lo voy a dejar como N/A
    private String[] tipo_plan; // tabla plan_voz
    private String[] costo_otros_operadores; // tabla plan_voz voz
   private String[] costo_minuto_adicional; //tabla plan_voz voz
   private String[] tarifa_datos; //tabla plan_voz datos
   private String[] tarifa_datos_adicional; //tabla plan_voz datos
   private String[] total_minutos; //tabla plan_voz voz
   private String[] flag_corporativo;//tabla plan_voz plan voz y datos
   private String[] id_contrato;
    
    private String[][] plan_voz;
    private String[][] plan_datos;
    private String[][] retiros;
    
    private int contador;

  public dimensionPlan(){
      
      consultas = new generadorSql();
      contador = 0;
  
  }
  
  public void obtenerAtributosContratoVozBDR() throws SQLException{
      
      String query = "SELECT id_contrato, tipo_plan, fecha_contrato, nombre,costo_otros_operadores, costo_minuto_adicional, total_minutos, c.id_plan_voz FROM contrato c, plan_voz p WHERE c.id_plan_voz = p.id_plan_voz order by id_contrato";
      
  
     this.plan_voz=consultas.generarConsulta(query);
  
  
  }
  
   public void obtenerAtributosContratoDatosBDR() throws SQLException{
      
      String query = "SELECT nombre,tipo_tarifa,valor_tarifa, valor_kb_adicional, c.id_plan_datos FROM contrato c, plan_datos d WHERE c.id_plan_datos = d.id_plan_datos order by id_contrato";
      
  
     this.plan_datos=consultas.generarConsulta(query);
  
  
  }
   
    public void obtenerAtributosContratoRetiroBDR() throws SQLException{
      
      String query = "SELECT * FROM retiro order by id_contrato";
      
     this.retiros=consultas.generarConsulta(query);
    // System.out.print(this.retiros.length);
  
  
  }
    
  public Stack<String> obtenerPila(String[][] retiro, int index){
  
  Stack<String> pila=new Stack<String>();

  //System.out.print(vector.size()+"---"+retiro.length);

  for(int i = 0; i < retiro.length; i++){
  
      pila.add(retiro[i][index]);
  
  }
  
  return pila;
  
  
  }  
  
  public String[][] gettablaVoz(){
  
  return this.retiros;
  
  }
  
  public int getContador(){
  
  return this.contador;
  
  }
  
  
  
  public void transformarCaracteresEspeciales() throws SQLException{
      
    String cadena;
    Stack<String> id;
    Stack<String> fecha;
    Stack<String> motivo;
    id = obtenerPila(this.retiros,0);
    fecha = obtenerPila(this.retiros, 1);
    motivo = obtenerPila(this.retiros, 2);
      
      tipo_plan_voz = new String[plan_voz.length];
      tipo_plan_datos = new String[plan_voz.length];
      fecha_inicio_plan = new String[plan_voz.length];
      fecha_retiro = new String[plan_voz.length];
      causa_retiro = new String[plan_voz.length];
      tipo_plan = new String[plan_voz.length];
      costo_otros_operadores = new String[plan_voz.length];
      costo_minuto_adicional = new String[plan_voz.length];
      tarifa_datos = new String[plan_voz.length];
      tarifa_datos_adicional = new String[plan_voz.length];
      total_minutos = new String[plan_voz.length];
      flag_corporativo = new String[plan_voz.length];
      id_contrato = new String[plan_voz.length];
     
      
  for(int i = 0; i < plan_voz.length; i++){  
        //aqui se realiza el procedimiento por cadena para transformar 
            tipo_plan_voz[i]= plan_voz[i][3];
            tipo_plan_datos[i]= plan_datos[i][0];
            fecha_inicio_plan[i] = plan_voz[i][2];
    
            int id_contrato = Integer.parseInt(plan_voz[i][0]);
            
            if(id_contrato == Integer.parseInt(id.get(0))){
                System.out.print("entre");
            fecha_retiro[i] = fecha.get(0) ; 
            causa_retiro[i] = motivo.get(0);
            id.remove(0);
            fecha.remove(0);
            motivo.remove(0);
            }else{
            fecha_retiro[i] = "0000/00/00"; 
            causa_retiro[i] = "Contrato Vigente";
            }
            
            tipo_plan[i] = plan_voz[i][1];
            costo_otros_operadores[i] = plan_voz[i][4];
            costo_minuto_adicional[i] = plan_voz[i][5];
            tarifa_datos[i] = plan_datos[i][2]; 
            tarifa_datos_adicional[i] = plan_datos[i][3];
            total_minutos[i] = plan_voz[i][6];
            if("16".equals(plan_voz[i][7]) || "17".equals(plan_voz[i][7]) || "18".equals(plan_voz[i][7]))
            {
            flag_corporativo[i] = "1"; 
            }else
            {
            flag_corporativo[i] = "0"; 
            }
            
           
       
        }
  
  }
  
  
  public void insertarRegistroPlanDHW(){
      
      String query;
      
  
  //Aqui hay que hacer un ciclo for reocciendo los arrays ya procesados y transformados para relalziar las coonsultas y llamar al metodo insertarRegistro de la clase generadorSql
  
     for(int i = 0; i < plan_voz.length; i++){
           
       query = "INSERT INTO `dimension_plan`(`tipo_plan_voz`, `tipo_plan_datos`, `fecha_inicio_plan`, `fecha_retiro`, `causa_retiro`, `flag_corporativo`, `tipo_plan`, `costo_otros_operadores`, `costo_minuto_adicional`, `tarifa_datos`, `tarifa_datos_adicional`, `total_minutos`) VALUES ('"+tipo_plan_voz[i]+"','"+tipo_plan_datos[i]+"','"+fecha_inicio_plan[i]+"','"+fecha_retiro[i]+"','"+causa_retiro[i]+"','"+flag_corporativo[i]+"','"+tipo_plan[i]+"','"+costo_otros_operadores[i]+"','"+costo_minuto_adicional[i]+"','"+tarifa_datos[i]+"','"+tarifa_datos_adicional[i]+"','"+total_minutos[i]+"')";     
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
