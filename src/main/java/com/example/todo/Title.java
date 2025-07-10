package com.example.todo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;

    @OneToMany(mappedBy = "title",cascade = CascadeType.ALL)
    private List<Todo> todo = new ArrayList<>();

    public Title(String title, List<Todo> todo) {
        this.title = (title!=null)?title:"";
        this.todo = Objects.requireNonNullElse(todo,new ArrayList<>());
        this.todo.forEach(t->t.setTitle(this));

    }
}
