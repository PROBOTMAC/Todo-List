package com.example.todo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("Todo")
public class TodoController {
    private final TodoRepo repo;
    private final TitleRepo Trepo;

    @Autowired
    public TodoController(TodoRepo repo,TitleRepo Trepo) {
        this.repo = repo;
        this.Trepo =Trepo;
    }

    @PostMapping("/{id}")
    public Todo addTodo(@PathVariable int id,@RequestBody Todo todo){
        Title title = Trepo.findById(id)
                        .orElseThrow(()->new RuntimeException("This Title id Not Found"));
        todo.setTitle(title);
        return repo.save(todo);
    }
    @PostMapping("/title")
    public Title addTitle(@RequestBody Title title){
        for(Todo todo:title.getTodo()){
            todo.setTitle(title);
        }
        return Trepo.save(title);
    }
    @GetMapping
    public List<Todo> getAllTodo(){
        return repo.findAll();
    }
    @PutMapping
    public Todo updateTodo(@RequestParam int id,@RequestBody String task){
        Todo todo = repo.findById(id)
                .orElseThrow(()->new RuntimeException("List not Found"));
        todo.setTask(task);
        repo.save(todo);
        return repo.save(todo);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteTodo(@RequestParam int id){
        repo.deleteById(id);
        return ResponseEntity.ok("Delete success");
    }
    @DeleteMapping("/Title")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> deleteTitle(@RequestParam int id){
        Trepo.deleteById(id);
        return ResponseEntity.ok("Delete success");
    }
    @PutMapping("/completed")
    public Todo markAsCompleted(@RequestParam int id){
        Todo todo = repo.findById(id)
                .orElseThrow(()->new RuntimeException("list not Found"));
        todo.setCompleted(true);
        return repo.save(todo);
    }
    @PutMapping("/notCompleted")
    public Todo markAsNotCompleted(@RequestParam int id){
        Todo todo = repo.findById(id)
                .orElseThrow(()->new RuntimeException("List not Found"));
        todo.setCompleted(false);
        return repo.save(todo);
    }
    @PutMapping("/title/{id}")
    public Title updateTitleCard(@PathVariable int id, @RequestBody Title updatedTitle) {
        Title existing = Trepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Title not found"));

        existing.setTitle(updatedTitle.getTitle());

        // Update or add todos
        List<Todo> newTodos = new ArrayList<>();
        for (Todo t : updatedTitle.getTodo()) {
            if (t.getId() != 0) {
                Todo existingTodo = repo.findById(t.getId())
                        .orElseThrow(() -> new RuntimeException("Todo not found"));
                existingTodo.setTask(t.getTask());
                existingTodo.setCompleted(t.isCompleted());
                newTodos.add(existingTodo);
            } else {
                t.setTitle(existing);
                newTodos.add(t);
            }
        }

        existing.setTodo(newTodos);
        return Trepo.save(existing);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTask(@PathVariable int id, @RequestBody Todo todo) {
        Todo existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo with id " + id + " not found"));

        // Only update the task name (assuming that's the intent)
        existing.setTask(todo.getTask());

        Todo updated = repo.save(existing);
        return ResponseEntity.ok(updated);
    }



}
