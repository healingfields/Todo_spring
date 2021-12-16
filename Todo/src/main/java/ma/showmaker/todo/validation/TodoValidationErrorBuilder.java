package ma.showmaker.todo.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class TodoValidationErrorBuilder {
    public static ToDoValidationError fromBindingErrors(Errors errors){
        ToDoValidationError error = new ToDoValidationError("validation" +
                "failed." + errors.getErrorCount() + " error(s)");
        for(ObjectError objectError : errors.getAllErrors()){
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}
