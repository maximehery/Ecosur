package com.ecosur.gestiontransports.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "mot_de_passe", nullable = false, length = 255)
    private String motDePasse;

    @Column(name = "telephone_mobile", length = 20)
    private String telephoneMobile;

    @Column(name = "actif", nullable = false)
    private boolean actif = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_role")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "id_adresse")
    private Adresse adresse;

    @ManyToOne
    @JoinColumn(name = "id_site")
    private Site site;

    public Utilisateur() {}

    // getters/setters …
    public Long getId() { return id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String getTelephoneMobile() { return telephoneMobile; }
    public void setTelephoneMobile(String telephoneMobile) { this.telephoneMobile = telephoneMobile; }

    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Adresse getAdresse() { return adresse; }
    public void setAdresse(Adresse adresse) { this.adresse = adresse; }

    public Site getSite() { return site; }
    public void setSite(Site site) { this.site = site; }
}
