package com.akshay.qrrestaurantmenusystem.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    // ===========================
    // One Category -> Many Menu Items
    // ===========================

    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Menu> menus = new ArrayList<>();

 

    public Category() {
    }



    public Category(Long id, String name, String description, List<Menu> menus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.menus = menus;
    }



    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Menu> getMenus() {
        return menus;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

   
    @Override
    public String toString() {
        return "Category [id=" + id
                + ", name=" + name
                + ", description=" + description + "]";
    }

}