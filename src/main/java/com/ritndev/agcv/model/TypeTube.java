package com.ritndev.agcv.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;


/**
 *
 * @author Ritn
 */
@Entity(name = "type_tube")
@Table(name = "TYPE_TUBE")
public class TypeTube implements Serializable {
    
    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Getter @Setter private long id;
    
    //Horodatage
    @UpdateTimestamp
    @Column(name = "horodatage", nullable = false)
    @Getter @Setter private Timestamp horodatage;
      
    //Nom du type de tube
    @Column(name = "nom", nullable = false)
    @Getter @Setter private String nom;
        
    //Est-ce que ce type de tube est commandable pour les membres ?
    @Column(name = "commande", nullable = false)
    @Getter @Setter private boolean commande;
    
    //Seuil bas pour ce type de tube
    @Column(name = "seuilBas", nullable = false)
    @Getter @Setter private int seuilBas;
    
    
    //Liste des PrixTube lié à ce TypeTube
    @OneToMany(targetEntity=PrixTube.class, mappedBy="idTypeTube")
    @Getter @Setter private List<PrixTube> prixTubes = new ArrayList<>();
    
    
    
    //Constructeur
    public TypeTube() {}

    public TypeTube(String nom, boolean commande) {
        this.nom = nom;
        this.commande = commande;
    }
       
    public TypeTube(long id, String nom, boolean commande) {
        this.id = id;
        this.nom = nom;
        this.commande = commande;
    }

    
    
    @Override
    public String toString() {
        return nom;
    }
   
    
    /*
        Methodes
    */
    
    public PrixTube getPrixTubeActif() {
        for(PrixTube pt : prixTubes){
            if(pt.isActif()){
                return pt;
            }
        }
        return null;
    }
    
    
    
}
