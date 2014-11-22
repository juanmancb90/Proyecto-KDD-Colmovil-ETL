/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorBD;

/**
 *
 * @author User
 */

//clase que realiza la conexion y operaciones sobre la base de datos de mysql
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class gestorBD {
    
    String usuario,password,host;
    Connection con;
    Statement statement;
    ResultSet tabla;
    
    /**
     * metodo contructor de la clse
     */
    public gestorBD(){
        
        host = "jdbc:mysql://localhost/colmovil";
        usuario = "root";
        password = "";  
    }
    
    public gestorBD(String hos, String usu, String pass){
        
        host = hos ;
        usuario = usu;
        password = pass;  
    }
    
    /**
     * funcion que permite crear una conexion a la base de datos
     * @return Connection con: Devuelve el objeto de conexion 
     */
    public Connection abrirConexion(){   
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(host, usuario, password);
            //System.out.print("Estamos conectados");
            return con;
        }
        catch (Exception e)  {
            System.out.println("No se pudo abrir la conexion a la base de datos");
        }
        return null;
    }
    
    /**
     * funcion que permite cerrar la conexion a la base de datos
     * @param c hace referencia al objeto de conexion
     */
    public void cerrarConexion(Connection c){
        try {
            c.close();
        } catch (Exception e) {
            System.out.println("No se pudo cerrar la conexion a la base de datos");
        }
    }
    
    /**
     * funcion que permite ejecutar query de consultas 
     * @param consulta hace referencia al string de consulta o query ->("select * from table_name;s");
     * @return ResultSet objeto de respuesta con la consulta a la base de datos;
     */
    public ResultSet ejecutarConsulta(String consulta){
        
        ResultSet resultado = null;
        //System.out.println(consulta);
        try {
            statement = con.createStatement();
            resultado = statement.executeQuery(consulta);
            //cerrarConexion(con);
        }
        catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta");
        }
        catch (Exception e) {
            System.out.println("Error al ejecutar la consulta");
        }
        return resultado;
    }
    
    /**
     * Funcion que permite ejecutar query sql de insert, delete o update
     * @param sql hace referencia al query sql que se desea ejecutar sobre la base de datos
     */
    
    
    
    public void insertar_registro(String sql)
    {
        try{
            Statement sentencia = con.createStatement();
            sentencia.executeUpdate(sql);
            con.close();
            }
            catch(SQLException e){ System.out.println(e); }
            catch(Exception e){ System.out.println(e); }
    }
    
    /**
     * funcion que permite retornar un valor int de la consulta
     * @param consulta hace referencia a la consulta query de la cual deseamos obtener el valor entero
     * @return devuelve el valor entero 1 sino devolvera -1 si se ejecuta la excepcion 
     */
    public int retornarEntero(String consulta)
    {
        System.out.println(consulta);
        ResultSet rs=ejecutarConsulta(consulta);
        try {
            while(rs.next()){
               return rs.getInt(1); 
            } 
        } catch (SQLException ex) {
            Logger.getLogger(gestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;  
    }
    
    /**
     * funcion que permite obtener un valor float 
     * @param consulta hace referencia a la consulta deseada
     * @return devuelve el valor entero 1 sino devolvera -1 si se ejecuta la excepcion 
     */
    public float retornarFloat(String consulta)
    {
        ResultSet rs=ejecutarConsulta(consulta);
        try {
            while(rs.next()){
               return rs.getFloat(1); 
            }            
        } catch (SQLException ex) {
            Logger.getLogger(gestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
        
    }
    
    /**
     * funcion que permite retornar un string o cadena de texto
     * @param consulta hace referencia a la consulta que deseamos hace cast
     * @return una cadena string 
     */
    public String retornarString(String consulta)
    {
        ResultSet rs=ejecutarConsulta(consulta);
        try {
            while(rs.next()){
               return rs.getString(1); 
            } 
        } catch (SQLException ex) {
            Logger.getLogger(gestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";        
    }
    
    /**
     * Funcion que permite retornar los registro de una tabla al hacer una consulta
     * @param consulta hace referencia a la consulta sql
     * @return arraylist con los registro de la consulta
     */
    public ArrayList retornarRegistro(String consulta)
    {
        ArrayList respuesta=new ArrayList();
        ResultSet rs=ejecutarConsulta(consulta);
        try {
            while(rs.next())
            {
                respuesta.add(rs.getObject(1));
            }
            return respuesta;
        } catch (SQLException ex) {
            Logger.getLogger(gestorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;        
    }
    
    /**
     * funcion que permite retornar una tabla con los registros de  una consulta
     * @param consulta hace referencia a la consulta ingresada al metodo
     * @return ArrayList con la tabla de registros resultado de la consulta a la base de datos
     * @throws SQLException Exception de la clase sql para indicar un error en la ejecucion del query SQL
     */
    public ArrayList<ArrayList> retornarTabla(String consulta) throws SQLException
    {
        ArrayList<ArrayList> respuesta=new ArrayList();
        ResultSet rs=ejecutarConsulta(consulta);
        if(!(rs==null)){
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            try {
                while(rs.next())
                {
                    ArrayList fila=new ArrayList();
                    for (int i = 0; i < colCount; i++) {
                       fila.add(rs.getString(i + 1));
                      }
                    respuesta.add(fila);
                }
                return respuesta;
            } catch (SQLException ex) {
                Logger.getLogger(gestorBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        return respuesta;        
    }
    
    /**
     * funcion que permite hacer consulta sql personalizadas 
     * @param query hace referencia a los parametros de consulta ->(count(*), nombre_cliente, max, min, average)
     * @param from hace referncia a la tabla de la base de datos a consultar ->cliente, plan,
     * @param where hace referencia a las restricciones de consulta -> login = login, 
     * @param limite hace referencia al limite de registros;
     * @return retorna una sentencia sql o query de consulta;
     */
    public String consultaPersonalizada(String query,ArrayList<String> from,ArrayList<String> where,int limite)
    {
       /*cada registro del where estara compuesto por el atributo, el operador de comparacion y el elemento con el cual se compara*/
        /*En el caso de operadores binarios del tipo BETWEEN se transforma a 2 condiciones unidas por un AND*/
        //falta dar soporte a los join Denilson mira esta parte 
        String consulta="SELECT "+query+",count(*)";
        
        consulta +=" FROM "+from.get(0)+",";
        for (int i = 0; i < from.size(); i++) {
            consulta+=","+from.get(i);  
        }
        if(where.size()>0){
           consulta+=" WHERE "+where.get(0)+"";
            for (int i = 0; i < where.size(); i++) {
                consulta += " AND "+where.get(i);
            } 
        }
        
        consulta+=" GROUP BY "+query+" limit "+limite+" ;";
        return consulta;
    }
    
}
    
    
   