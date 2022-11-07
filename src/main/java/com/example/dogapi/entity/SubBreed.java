package com.example.dogapi.entity;

import com.example.dogapi.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
@Data
@Getter
@Setter
@ToString
public class SubBreed extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private Boolean isSync;

    private String Description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "breed_id", referencedColumnName = "id")
    private Breed breed;
}
