package entity;

public interface Sessions {
    String getTime();
    String getIdentifier();
    Collection getPeople();
    void removePerson();
    boolean getOccured();
    boolean isEmpty();
    Integer getCapacity();
    String getLocation();
    void addPerson();
}