package ma.showmaker.todoclient;

import ma.showmaker.todoclient.domain.Todo;
import ma.showmaker.todoclient.service.TodoRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoClientApplication {

    public static void main(String[] args) {
        SpringApplication app = new
                SpringApplication(TodoClientApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
    private Logger log = LoggerFactory.getLogger(TodoClientApplication.class);

    @Bean
    public CommandLineRunner process(TodoRestClient client){
        return args -> {
            Iterable<Todo> todos = client.findAll();
            assert  todos != null;
            todos.forEach(todo -> log.info(todo.toString()));
            Todo newTodo = client.upsert(new Todo("drink tea"));
            assert newTodo != null;
            log.info(newTodo.toString());

            Todo todo = client.setCompleted(newTodo.getId());
            assert todos != null;
            log.info(todo.toString());

            Todo completed = client.setCompleted(newTodo.getId());
            assert completed.isCompleted();
            log.info(completed.toString());

            client.delete(newTodo.getId());
            assert client.findById(newTodo.getId()) == null;
        };
    }




}
