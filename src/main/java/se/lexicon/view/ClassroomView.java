package se.lexicon.view;

import se.lexicon.model.Classroom;
import se.lexicon.model.Equipment;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class ClassroomView {

    /* --- Creates a classroom with the provided info by the user --- */
    public Classroom getClassroomInput(){

        String name = IO.readln("Classroom name: ");
        int capacity = Integer.parseInt(IO.readln("Classroom capacity: "));
        boolean accessible = IO.readln("Classroom accessible (yes/no): ").equalsIgnoreCase("yes");
        Set<Equipment> equipment = EnumSet.noneOf(Equipment.class);
        for(Equipment equip : Equipment.values()){
            if(IO.readln("Has " + equip.name().toLowerCase() + "? (yes/no): ").equalsIgnoreCase("yes")){
                equipment.add(equip);
            }
        }
        return new Classroom(name, capacity, accessible, equipment);
    }

    /* --- Displays all the classrooms --- */
    public void displayClassroom(List<Classroom> classrooms){
        if(classrooms.isEmpty()){
            IO.println("❌ No classrooms found!");
            return;
        }
        classrooms.forEach(IO::println);
    }
}
