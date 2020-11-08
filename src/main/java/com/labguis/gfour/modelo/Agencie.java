/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labguis.gfour.modelo;

import javax.persistence.*;


/**
 *
 * @author Nicolas Castillo
 */
@Entity
@Table(name="agencies")
public class Agencie {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;      
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Agencie() {
    }
    
    
}
