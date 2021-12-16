package ma.showmaker.todo.controller;

import ma.showmaker.todo.domain.ToDo;
import ma.showmaker.todo.repository.CommonRepository;
import ma.showmaker.todo.validation.TodoValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class TodoController {
    private CommonRepository<ToDo> repo;

    @Autowired
    public TodoController(CommonRepository<ToDo> repo){
        this.repo = repo;
    }

    @GetMapping("/todo")
    public ResponseEntity<Iterable<ToDo>> getTodos(){
        return  ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<ToDo> getTodoById(@PathVariable String id){
        ResponseEntity<ToDo> ok = ResponseEntity.ok(repo.findById(id));
        return ok;
    }

    @PatchMapping("todo/{id}")
    public ResponseEntity<ToDo> setCompleted(@PathVariable String id){
        ToDo result = repo.findById(id);
        result.setCompleted(true);
        repo.save(result);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.ok().header("Location",location.toString()).build();
    }

    @RequestMapping(value = "/todo", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> createTodo(@Valid @RequestBody ToDo todo, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(
                    TodoValidationErrorBuilder.fromBindingErrors(errors));
        }
        ToDo result = repo.save(todo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
        path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
