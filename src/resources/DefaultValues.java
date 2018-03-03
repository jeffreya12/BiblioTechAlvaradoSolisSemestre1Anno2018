/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author jefal
 */
public class DefaultValues {
    private DefaultValues(){}
    
    // Valores
    public static String PASSWORD = "1234";
    public static int FEE_PER_DAY = 100; // Colones
    public static int LOAN_TIME = 5; // Dias
    public static int STUDENT_REG_SIZE = 140; // Tamanno de registro Student
    public static int MEDIA_PLAYER_REG_SIZE = 500; // Tamanno de registro MediaPlayer
    public static int MEDIA_REG_SIZE = 500; // Tamanno de registro Media
    public static int BOOK_REG_SIZE = 1000; // Tamanno de registro Media
    public static String[] MATERIAL_TYPE = {"Libro",
                                            "Multimedia",
                                            "Reproductor multimedia"
                                           };
    
    // Paths
    public static String STUDENT_FILE_PATH = "./Student.dat";
    public static String MEDIA_PLAYER_FILE_PATH = "./MediaPlayer.dat";
    public static String MEDIA_FILE_PATH = "./Media.dat";
    public static String BOOK_FILE_PATH = "./Book.dat";
    public static String CHECK_ICON_PATH = "./src/resources/images/CHECK_ICON.png";
    public static String WRONG_ICON_PATH = "./src/resources/images/WRONG_ICON.png";
    
    // Identificador de elemento borrado
    public static String DELETE_NAME_ON_RECORD = "delete";
    
    // Mensajes
    public static String LOGIN_ERROR = "La contraseña es incorrecta";
    public static String STUDENT_ID_ERROR = "El número de carné es inválido";
    public static String BOOK_ID_ERROR = "El número de ISBN es inválido";
    public static String MEDIA_PLAYER_ID_ERROR = "El número de identificación es inválido";
    public static String MEDIA_ID_ERROR = "El número de identificación es inválido";
    public static String FAILED_INSERT = "Error guardando sus datos";
    public static String STUDENT_NOT_FOUND_ERROR = "El estudiante no se encuentra registrado en el sistema";
    public static String FAILED_SEARCH = "No se puede realizar la búsqueda";
    
    // Funciones
    public static boolean checkId(String id){
        boolean isCorrect;
        if(id.length() >= 5){
            try{
                Integer.parseInt(id);
                isCorrect = true;
            }
            catch(Exception e){
                isCorrect = false;
            }
        }
        else{
            isCorrect = false;
        }
        return isCorrect;
    }
}
