/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import com.csvreader.CsvReader;
import java.io.*;
import java.util.*;
/**
 *
 * @author User
 */
public class gestorCSV {
    private ArrayList<ArrayList<String>> arrListData;
    CsvReader cvsReader = null;
    
    /**
     * contructor de la clase
     * @param archivo recibe la ruta del archivo csv a cargar
     * @throws FileNotFoundException 
     */
    public gestorCSV(String archivo) throws FileNotFoundException{
        char delimitador = ';';
        cvsReader = new CsvReader(archivo, delimitador );//archivos.csv"
    }
    
    /**
     * funcion que permite leer un arcivo csv
     * @throws IOException 
     */
    public void  leerCsv() throws IOException{
    
        try
        {
            while(cvsReader.readRecord()) 
            {
                arrListData.add(new ArrayList<String>());
           }
        } 
        catch(Exception e) 
        {
            throw e;
        }  
        finally 
        {
            if(cvsReader!=null) 
            {
            cvsReader.close();
            }
        }
    }
    
}
