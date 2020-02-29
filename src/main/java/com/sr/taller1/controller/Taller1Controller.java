package com.sr.taller1.controller;

import com.sr.taller1.recommender.RecommenderManager;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class Taller1Controller {

    private RecommenderManager recommenders = RecommenderManager.instance();

    public Taller1Controller() throws IOException, TasteException {
    }

    @RequestMapping("/t1_add_user_rating")
    public ModelAndView t1UsuarioRating() {
        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("taller1UsuarioRating", model);
    }

    @RequestMapping("/t1_rc_artistas_canciones")
    public ModelAndView t1RcArtistasCanciones(@RequestParam Map<String, String> params) {

        String tipoRecomendador = params.get("tipoRecomendador");
        String algoritmo = params.get("algoritmo");
        String user = params.get("user");
        String tipo_algoritmo = params.get("tipo_algoritmo");
        String resultados = params.get("resultados");

        System.out.println(tipoRecomendador+" "+algoritmo+" "+user+" "+tipo_algoritmo+" "+resultados);

        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("taller1", model);
    }



}