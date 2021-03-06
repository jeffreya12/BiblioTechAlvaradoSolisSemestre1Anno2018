/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import domain.Media;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import resources.DefaultValues;

/**
 *
 * Manejador del RAF de la clase Media
 * 
 */
public class MediaFile {
    
    public RandomAccessFile randomAccessFile;
    private int regsQuantity;//me indica la cantidad de registros
    private int regSize;
    private String myFilePath;
    
    public MediaFile(File file) throws IOException{
        start(file);
    }
    
    private void start(File file) throws IOException{
        //almaceno la ruta
        myFilePath = file.getPath();
        
        //tamanno maximo de los registros dentro de esta 
        //clase
        this.regSize = DefaultValues.MEDIA_REG_SIZE;
        
        //una validacion basica
        if(file.exists() && !file.isFile()){
            throw new IOException(file.getName() 
                    + " is an invalid file");
        }
        else{
            //crear la nueva instancia de randomAccessFile
            randomAccessFile = new RandomAccessFile(file, "rw");
            
            //necesitamos indicar cuantos registros tiene el archivo
            this.regsQuantity = (int)Math.ceil((double)randomAccessFile.length() / (double)regSize);
        }
    }
    
    public void close() throws IOException{
        randomAccessFile.close();
    }
    
    public int fileSize(){
        return regsQuantity;
    }
    
    //Inserta un objeto en la posicion especificada
    public boolean putValue(int position, Media media) throws IOException{
        //una pequenna validacion antes de insertar
        if(position >= 0 && position <= regsQuantity){
            if(media.size() > regSize){
                System.err.print("7001 record size is out of bounds");
                return false;
            }
            else{
                //escribimos en archivo
                randomAccessFile.seek(position * regSize);
                
                randomAccessFile.writeUTF(media.getTitle());
                randomAccessFile.writeUTF(media.getGenre());
                randomAccessFile.writeLong(media.getPublished().getTime());
                randomAccessFile.writeUTF(media.getDescription());
                randomAccessFile.writeUTF(media.getId());
                randomAccessFile.writeInt(media.getQuantity());
                randomAccessFile.writeInt(media.getAvailable());
                
                return true;
            }
        }
        else{
            System.err.print("7002 position is "
                    + "out of bounds of this file");
                return false;
        }
        
    }
    
    //Inserta un objeto al final del RAF    
    public boolean addEndRecord(Media media) throws IOException{
        //insertar al final del archivo
        boolean success = putValue(regsQuantity, media);
        
        if(success){
            ++regsQuantity;
        }
        return success;
    }
    
    //Obtiene un objeto basado en su posicion en el RAF
    public Media getRecord(int position) throws IOException{
        //validacion de la posicion
        if(position >= 0 && position <= regsQuantity){
            //colocamos el puntero en el lugar 
            randomAccessFile.seek(position * regSize);
            
            //instancia de person
            Media myMedia = new Media();
            
            //Variable temporal para guardar la fecha
            Date publishedTemp = new Date();
            
            //llevamos a cabo las lecturas
            myMedia.setTitle(randomAccessFile.readUTF());
            myMedia.setGenre(randomAccessFile.readUTF());
            publishedTemp.setTime(randomAccessFile.readLong());
            myMedia.setDescription(randomAccessFile.readUTF());
            myMedia.setId(randomAccessFile.readUTF());
            myMedia.setQuantity(randomAccessFile.readInt());
            myMedia.setAvailable(randomAccessFile.readInt());
            
            //Asigna la fecha al nuevo objeto
            myMedia.setPublished(publishedTemp);
            
            //si es delete no retorno
            if(myMedia.getId().equalsIgnoreCase(DefaultValues.DELETE_NAME_ON_RECORD)){
                return null;
            }
            else{
                return myMedia;
            }
            
        }
        else{
            System.err.println("6001 position is out of bounds");
            return null;
        }
    }
    
    //Retorna todos los objetos del RAF
    public List<Media> getAllRecords() throws IOException{
        
        //variable a retornar
        List<Media> medias = new ArrayList<Media>();
        
        //recorro todos mis registros y los inserto en la lista
        for(int i = 0; i < regsQuantity; i++){
            Media mediaTemp = this.getRecord(i);
            
            if(mediaTemp != null){
                medias.add(mediaTemp);
            }
        }
        
        return medias;
    }
    
    //Busca un objeto en el RAF con base en su ID
    public int searchRecord(String id) throws IOException{
        Media myMedia = null;
        
        //Recorro el RAF
        for(int i = 0; i < regsQuantity; i++){
            //Obtengo el objeto
            myMedia = this.getRecord(i);
            if(myMedia.getId().equalsIgnoreCase(id)){
                //Si existe, devuelve la posicion en el RAF
                return i;
            }
        }
        //Si no existe, devuelve una posicion invalida
        return -1;
    }
    
    //Borra un objeto del RAF
    public boolean deleteRecord(String id) throws IOException{
        Media myMedia;
        
        //buscar el registro para la eliminacion
        for(int i = 0; i < regsQuantity; i++){
            
            //obtengo a la persona de esa posicion
            myMedia = this.getRecord(i);
                
            //pregunto si es la persona que quiero eliminar
            if(myMedia.getId().equalsIgnoreCase(id)){

                //marcar esta persona como eliminada
                myMedia.setId(DefaultValues.DELETE_NAME_ON_RECORD);

                return this.putValue(i, myMedia);
            }
        }
        
        //si llega a este punto no encontro a la persona
        return false;
    }
}
