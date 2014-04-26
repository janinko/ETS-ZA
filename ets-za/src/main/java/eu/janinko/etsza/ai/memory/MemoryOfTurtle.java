package eu.janinko.etsza.ai.memory;

import eu.janinko.etsza.wrapper.Turtle;

/**
 * @author Honza Brázdil <janinko.g@gmail.com>
 * @author Jakub Senko <jsenko@mail.muni.cz>
 */
public abstract class MemoryOfTurtle {

    protected long id;
    protected double posX;
    protected double posY;
    protected long date;


    protected MemoryOfTurtle(Turtle turtle, long date) {
        update(turtle, date);
    }


    public long getId() {
        return id;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public long getDate() {
        return date;
    }


    public void update(Turtle turtle, long date) {
        this.id = turtle.getId();
        this.posX = turtle.getPosX();
        this.posY = turtle.getPosY();
        this.date = date;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MemoryOfHuman other = (MemoryOfHuman) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
