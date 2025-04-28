package com.article.hub.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;



@NamedQuery(name = "Category.updateCategory", query = "update category set name=:name where id=:id")
// name=:name (here "name" before '=' is from the Category class (ie. Entity class) and
// "name" after ':' is from CategoryRepository class (ie. Repository class) and same goes for id.

@Entity(name = "category")
@Table(name = "category")
@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;


    // Default constructor
    public  Category(){

    }

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
