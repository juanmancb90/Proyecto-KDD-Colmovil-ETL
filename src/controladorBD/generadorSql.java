/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorBD;

import java.sql.*;

/**
 *esta clase va a permitir generar los sql de cargar de dimensiones y hechos
 * @author User
 */
public class generadorSql {
    
    gestorBD obj;
    Connection var;
    ResultSet resultado;
    String[][] Datos;
    int columnas;
    int filas;
    
    /**
     * Funcion que permite consultar una tabla de la base de datos y la información en ella almacenada
     * tiene como parametros de entrada la consulta a realizar y el array matriz donde se guardara la información de la tabla correspondiente
     */
    public String[][] generarConsulta(String consulta) throws SQLException{
        //asignar un objeto de conexion
        obj = new gestorBD();
        var  = obj.abrirConexion();
        
        
        //ejecutar o relizar la consulta
        resultado = obj.ejecutarConsulta(consulta);
       
           
        
        //guardar la tabla consultada
        ResultSetMetaData metadatos = resultado.getMetaData();
        
        
        //se ubica un apuntador en la ultima de las columnas
        resultado.last();
        
        columnas = metadatos.getColumnCount();
        filas = resultado.getRow();
        //System.out.println(columnas);
        //System.out.println(filas);
        
         
        
        //crear la matriz que guardara la tabla consultada 
        Datos = new String[filas][columnas];
        
        resultado.beforeFirst();
        
        //recorrer la matriz consultada para guardar la información
            for(int i=0;i<filas;i++)
            {

                //Va a la siguiente fila

                resultado.next();

                for(int j=0;j<columnas;j++)
                {

		//Obtiene el valor de cada una de las columnas en la fila actual

		 Datos[i][j]=resultado.getString(j+1);
                  
                }

             }
            
        //cerrar conexion
        obj.cerrarConexion(var);
        
        return Datos;
       
   
        
    }
    
    public void insertarRegistro (String consulta, String host, String usuario, String password){
    
        obj = new gestorBD(host, usuario, password);
        var  = obj.abrirConexion();
        
       obj.insertar_registro(consulta);
       
       obj.cerrarConexion(var);
    
    }
    
    public int getColumnas(){
    
    return this.columnas;
    
    }
    
    public int getFilas(){
    
    return this.filas;
    
    }
    
    
    
    
}
