package com.sr.taller1.controller;
import com.sr.taller1.data.DataRecommendationModels;
import com.sr.taller1.model.Recommendation;
import com.sr.taller1.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ContactForm {

    private HashMap<String, Long> usuarios = new HashMap<String, Long>();
    private HashMap<Long, String> tracks = new HashMap<Long, String>();

    private DataRecommendationModels models = DataRecommendationModels.instance();


    public ContactForm() throws IOException {
    }

    @RequestMapping("/show_user_list")
    public ModelAndView darUsuarios(@RequestParam Map<String, String> params) {
        usuarios = models.getUsersAllReady();
        tracks = models.getTracksAllReady();

        List<User> nuevaLista = listar(usuarios,tracks);

        Map<String, Object> model = new HashMap<>();
        model.put("recommendations",nuevaLista);


        return new ModelAndView("taller1UsuarioRating", model);
    }




    public void setUsuarios(HashMap<String, Long> pUsuarios) {
        usuarios = pUsuarios;
    }

    public List<User> listar(HashMap<String, Long> pUsuarios, HashMap<Long, String> pTracks) {
        List<String> result2 = new ArrayList(pUsuarios.keySet());
        List<Long> result3 = new ArrayList(pTracks.keySet());


        ArrayList<User> lista = new ArrayList<>();

        ArrayList<Recommendation> recommendationResult = new ArrayList<>();


        for(String value : result2){

            String userId = value;

            User usuario = new User();
            usuario.setUserId(userId);


            lista.add(usuario);
        }
        for(int i = 0; i < result2.size(); i++){

            Long trackId = result3.get(i);
            User usuario = lista.get(i);
            usuario.setTrackId(trackId);
        }

        //Corregir, si hay más items que usuarios
        return lista;
    }

}