package com.ritndev.agcv.pages;

import com.ritndev.agcv.InterfaceService.IMainDataService;
import com.ritndev.agcv.model.enumeration.NomMois;
import com.ritndev.agcv.model.enumeration.NomTypeTube;

import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.ui.Model;
import java.security.Principal;
import java.util.List;
import org.springframework.context.MessageSource;



/**
 *
 * @author Ritn
 */
public class PageIndex extends Page {
  
    
    //Constructeur
    public PageIndex(Model model, Principal principal, MessageSource messageSource) {
        super("index", model, principal, messageSource);
        
        super.setAdminPage(true);
        super.addLinks(returnLink("commandesMembres"));
        super.addLinks(returnLink("competition"));
        super.addLinks(returnLink("histo"));
        
    }
    
    //Renvoie la page
    public String getPage(IMainDataService dataService) {
        
        List<String> classStock = new ArrayList<>();       
        List<String> listVolants = new ArrayList<>();
        
        listVolants.add(NomTypeTube.PLASTIQUE.toString());
        listVolants.add(NomTypeTube.COMPETITION.toString());
        listVolants.add(NomTypeTube.ENTRAINEMENT.toString());
        
        for(String volant : listVolants) {
            String strClass = "table-tv-stock";
            
            //si : Stock Volant <= Seuil Bas
            //si la saison active a bien des type volant
            if (!dataService.returnMainData().getIdSaison().getTypeVolants().isEmpty()) {
                //si le nom correspond bien à un nom de typetube
                if(dataService.returnMainData().getIdSaison().getTypeVolantName(volant)!=null){
                    //si le stock de tube de ce type de volant est inferieur au seuil bas on actualise la 'class' html
                    if(dataService.returnMainData().getIdSaison().getTypeVolantName(volant).getStockTotal()
                        <= dataService.returnMainData().getTypeTubeName(volant).getSeuilBas()) {
                        strClass = strClass + "-bas";
                    }
                }
                classStock.add(strClass);
            }
        }
        
        
        // Add Attribute :
        getPageGenerique();
             
        super.getModel().addAttribute("saison", dataService.returnMainData().getIdSaison());
        super.getModel().addAttribute("classStock", classStock);
        super.getModel().addAttribute("listVolants", listVolants);
        super.getModel().addAttribute("nomMois", new ArrayList<>(Arrays.asList(NomMois.values())));
        
        return returnPage();
        
    }
    
    
    
}
