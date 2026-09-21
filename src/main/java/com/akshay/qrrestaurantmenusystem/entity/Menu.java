package com.akshay.qrrestaurantmenusystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "menus")
public class Menu {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank(message = "Menu name is required")
	    @Column(nullable = false, length = 100)
	    private String name;

	    @Size(max = 300, message = "Description can contain maximum 300 characters")
	    @Column(length = 300)
	    private String description;

	    @NotNull(message = "Price is required")
	    @DecimalMin(value = "1.0", message = "Price must be greater than 0")
	    @Column(nullable = false)
	    private Double price;

	    @Column(name = "image_name")
	    private String imageName;

	    @NotNull(message = "Select Veg or Non Veg")
	    @Column(nullable = false)
	    private Boolean veg = true;

	    @NotNull(message = "Select Availability")
	    @Column(nullable = false)
	    private Boolean available = true;

	    @NotNull(message = "Category is required")
	    @ManyToOne
	    @JoinColumn(name = "category_id")
	    private Category category;

    public Menu() {
    }

    public Menu(Long id, String name, String description, Double price, String imageName, Boolean veg,
            Boolean available, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageName = imageName;
        this.veg = veg;
        this.available = available;
        this.category = category;
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

    public Double getPrice() {
        return price;
    }

    public String getImageName() {
        return imageName;
    }

    public Boolean getVeg() {
        return veg;
    }

    public Boolean getAvailable() {
        return available;
    }

    public Category getCategory() {
        return category;
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setVeg(Boolean veg) {
        this.veg = veg;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Menu [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
                + ", imageName=" + imageName + ", veg=" + veg + ", available=" + available + "]";
    }

}