package com.sr.taller1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class Taller1Controller {

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

        System.out.println(tipoRecomendador+" "+algoritmo+" "+user);

        Map<String, Object> model = new HashMap<>();
        return new ModelAndView("taller1", model);
    }



}