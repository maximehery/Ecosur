package com.ecosur.gestiontransports.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true, length = 30)
    private RoleName code;

    @Column(name = "label", nullable = false, length = 100)
    private String label;

    public Role() {}

    public Role(RoleName code, String label) {
        this.code = code;
        this.label = label;
    }

    public Long getId() { return id; }
    public RoleName getCode() { return code; }
    public void setCode(RoleName code) { this.code = code; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}
