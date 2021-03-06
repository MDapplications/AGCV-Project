package com.ritndev.agcv.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author Ritn
 */
@Entity
@Table(name = "CONSO_MOIS")
public class ConsoMois implements Serializable {
    
    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Getter @Setter
    private long id;
    
    //Horodatage
    @UpdateTimestamp
    @Column(name = "horodatage", nullable = false)
    @Getter @Setter
    private Timestamp horodatage;
    
    //Nom du mois de cette consommation
    @Column(name = "nom", nullable = false)
    @Getter @Setter
    private String nom;
    
    //id du prix tube utilises lors de ce mois-ci
    @OneToOne
    @JoinColumn(name = "idPrixTube", nullable = false)
    @Getter @Setter private PrixTube idPrixTube;
    
    //id de la reference à la conse de tube (type de tube utilise)
    @OneToOne
    @JoinColumn(name = "idTypeVolant", nullable = false)
    @Getter @Setter private TypeVolant idTypeVolant;
    
    //Nombre de tubes utilises ce mois-ci
    @Column(name = "nbTubeUtilise", nullable = false)
    @Getter @Setter private int nbTubeUtilise;
    
    //Nombre de tubes commandé ce mois-ci
    @Column(name = "nbTubeCommande", nullable = false)
    @Getter @Setter private int nbTubeCommande;
    
    
    
    //Liste des restockages du conso-mois
    @OneToMany(targetEntity=Restock.class, mappedBy="idConsoMois", cascade=CascadeType.REMOVE)
    @Getter @Setter private List<Restock> restocks = new ArrayList<>();
    
    //Liste des commandes de ce PrixTube
    @OneToMany(targetEntity=Commande.class, mappedBy="idConsoMois")
    @Getter @Setter private List<Commande> commandes = new ArrayList<>();
    
    
    
    
    
    //Constructeur
    public ConsoMois() {}

    public ConsoMois(String nom, PrixTube idPrixTube, TypeVolant idTypeVolant, int nbTubeUtilise, int nbTubeCommande) {
        this.nom = nom;
        this.idPrixTube = idPrixTube;
        this.idTypeVolant = idTypeVolant;
        this.nbTubeUtilise = nbTubeUtilise;
        this.nbTubeCommande = nbTubeCommande;
    }

    @Override
    public String toString() {
        return nom;
    }
    
    
    
    /*
        Methodes
    */
    
    public int showTubesUtilises() {
        int value = 0;
        for (Restock rs : restocks) {
            value = value + rs.getValeur();
        }
        for (Commande c : commandes) {
            value = value + c.getNbTubeCommande();
        }
        return nbTubeUtilise + value;
    }
    
    public double getCoutUtilises() {
        double totalMembre = 0.0;
        
        if (idPrixTube.getIdTypeTube().isCommande()) {
            int value = 0;
            for (Commande c : commandes) {
                value = value + c.getNbTubeCommande();
            }
            totalMembre = value * idPrixTube.getPrixMembre();
        }
               
        return (showTubesUtilises() * idPrixTube.getPrix()) - totalMembre;
    }
    public String getStringCoutUtilises() {
        return String.format("%.2f", getCoutUtilises());
    }
    
    public double getCoutCommandes() {
        return nbTubeCommande * idPrixTube.getPrix();
    }
    public String getStringCoutCommandes() {
        return String.format("%.2f", getCoutCommandes());
    }
    
    
}
