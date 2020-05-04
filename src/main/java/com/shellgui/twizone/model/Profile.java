package com.shellgui.twizone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shellgui.twizone.dto.ProfessionDTO;
import com.shellgui.twizone.interfaces.RegisteredUser;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "profiles", schema = "public")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Profile extends AbstractModel implements Serializable, RegisteredUser {
    private String email;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer professionId;
    private Boolean isActive;
    // TODO Choose the right solution
    // @Convert(converter = HashMapConverter.class)
    // private Map<String, Object> position;
    private String position;
    /**
     * - 0 -> Available
     * - 1 -> Not available
     */
    private Integer status;
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date availableFrom;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date registrationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProfessionDTO professionDTO;



}
