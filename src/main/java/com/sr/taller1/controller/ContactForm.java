package com.sr.taller1.controller;
import java.util.HashMap;
public class ContactForm {

    private HashMap<String, Long> usuarios = new HashMap<String, Long>();

    public HashMap<String, Long> darUsuarios(){
        return usuarios;
    }

    public void setUsuarios(HashMap<String, Long> pUsuarios){
        usuarios = pUsuarios;
    }


}
