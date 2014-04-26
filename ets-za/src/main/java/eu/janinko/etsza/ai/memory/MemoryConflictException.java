
package eu.janinko.etsza.ai.memory;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class MemoryConflictException extends IllegalStateException{

    public MemoryConflictException() {
        super();
    }

    public MemoryConflictException(String s){
        super(s);
    }

    public MemoryConflictException(Throwable cause){
        super(cause);
    }

    public MemoryConflictException(String s, Throwable cause){
        super(s, cause);
    }
    
}
