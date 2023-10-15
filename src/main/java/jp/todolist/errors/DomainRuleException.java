package jp.todolist.errors;

/**
 * DomainException
 * Exception thrown when some domain rule is broken
 */
public class DomainRuleException extends RuntimeException {

    String message;

    public DomainRuleException(String message){
        super(message);
        this.message = message;
    }
    
}
