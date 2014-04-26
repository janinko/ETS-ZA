
package eu.janinko.etsza.ai.memory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Memories {
    private HashMap<Class<? extends Memory>, HashMap<Long, Memory>> memories = new HashMap<>();
    
    public void addMemoryClass(Class<? extends Memory> clazz){
        if(memories.containsKey(clazz)) return;
        
        memories.put(clazz, new HashMap<Long, Memory>());
    }
    
    public <K extends Memory> K get(Class<K> clazz, Long id){
        HashMap<Long, Memory> map = memories.get(clazz);
        if(map == null) throw new IllegalArgumentException("Untracked memory " + clazz.getName());
        return (K) map.get(id);
    }
    
    public <K extends Memory> Map<Long, K> getAll(Class<K> clazz){
        HashMap<Long, Memory> map = memories.get(clazz);
        if(map == null) throw new IllegalArgumentException("Untracked memory " + clazz.getName());
        Map<Long, K> mmap = (Map<Long, K>) map;
        return Collections.unmodifiableMap(mmap);
    }
    
    public <K extends Memory> boolean contains(Class<K> clazz, Long id){
        HashMap<Long, Memory> map = memories.get(clazz);
        if(map == null) throw new IllegalArgumentException("Untracked memory " + clazz.getName());
        return map.containsKey(id);
    }
    
    public <K extends Memory> boolean contains(K memory, Long id){
        return contains(memory.getClass(), id);
    }
    
    public <K extends Memory> void put(K memory, Long id){
        HashMap<Long, Memory> map = memories.get(memory.getClass());
        if(map == null) throw new IllegalArgumentException("Untracked memory " + memory.getClass().getName());
        map.put(id, memory);
    }

    public <K extends Memory> void forget(Class<K> clazz, long id) {
        HashMap<Long, Memory> map = memories.get(clazz);
        if (map == null) return;
        map.remove(id);
    }
}
