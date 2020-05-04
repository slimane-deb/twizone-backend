package com.shellgui.twizone.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shellgui.twizone.model.Profile;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "professions", schema = "public")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class ProfessionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    private String nameEn;
    private String nameAr;
    private String professionImg;
    @OneToMany(mappedBy="professionDTO")
    private Profile profiles;

    public ProfessionDTO() {}

}
