package com.sr.taller1.controller;

import com.sr.taller1.model.Recommendation;
import com.sr.taller1.recommender.RecommenderManager;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Taller1Controller {

    private RecommenderManager recommenders = RecommenderManager.instance();

    public Taller1Controller() throws IOException, TasteException {
    }

    @RequestMapping("/t1_rc_artistas_canciones")
    public ModelAndView t1RcArtistasCanciones(@RequestParam Map<String, String> params) throws TasteException {

        String tipoRecomendador = params.get("tipoRecomendador");
        String algoritmo = params.get("algoritmo");
        String user = params.get("user");
        String tipo_algoritmo = params.get("tipo_algoritmo");
        String resultados = params.get("resultados");

        List<Recommendation> recomendation = recommenders.recommend(tipoRecomendador,algoritmo,user,tipo_algoritmo,Integer.parseInt(resultados));

        Map<String, Object> model = new HashMap<>();
        model.put("recommendations",recomendation);
        System.out.println("Recommendations: "+recomendation);
        return new ModelAndView("taller1", model);
    }



}