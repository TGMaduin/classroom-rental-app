package se.lexicon.model;

import java.util.Collections;
import java.util.EnumSet;

public class Classroom {

    private int id;
    private String name;
    private int capacity;
    private boolean disabilityAccessible;
    private Set<Equipment> equipment;

    /* --- This constructor is used when creating a new classroom, the DB automatically generates the id --- */
    public Classroom(String name, int capacity, boolean disabilityAccessible, Set<Equipment> equipment) {
        this.name = name;
        this.capacity = capacity;
        this.disabilityAccessible = disabilityAccessible;
        /* if there are no equipment, then equipment == null is true so it creates
           an empty set of Equipment to avoid NullPointerException or else creates
           EnumSet containing equipment values */
        this.equipment = equipment == null ? EnumSet.noneOf(Equipment.class) : EnumSet.copyOf(equipment);
    }

    /* --- This constructor is used when reading an existing classroom from the DB --- */
    public Classroom(int id, String name, int capacity, boolean disabilityAccessible, Set<Equipment> equipment) {
        this(name, capacity, disabilityAccessible, equipment);
        this.id = id;
    }

    /* --- Getters & Setters --- */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isDisabilityAccessible() {
        return disabilityAccessible;
    }

    public void setDisabilityAccessible(boolean disabilityAccessible) {
        this.disabilityAccessible = disabilityAccessible;
    }

    public Set<Equipment> getEquipment() {
        /* prevent other classes from modifying the enum directly */
        return Collections.unmodifiableSet(equipment);
    }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment == null ? EnumSet.noneOf(Equipment.class) : EnumSet.copyOf(equipment);
    }

    /* --- Print info layout --- */
    @Override
    public String toString() {
        String equipmentString = equipment.isEmpty() ? "None"
                : String.join(", ",
                    equipment.stream()
                            .map(Enum::name)
                            .toList());

        return String.format(
                "ID: %d | %s | Capacity: %d | Accessibility: %s | Equipment: %s",
                id,
                name,
                capacity,
                disabilityAccessible ? "Yes" : "No",
                equipmentString
        );
    }
}
