package se.lexicon.view;

public class ConsoleMenu {

    public void displayMenu(){
        IO.println(String.format(
                "=============================",
                "|    CLASSROOM RENTAL APP    |",
                "=============================",
                "1. Register customer",
                "2. Add a booking user",
                "3. Add a classroom",
                "4. Show all customers",
                "5. Show all classrooms",
                "6. Search available classrooms",
                "7. Create a booking",
                "8. View classroom bookings",
                "9. View customer bookings",
                "10. View all upcoming bookings",
                "11. Exit"
        ));
    }

    public int getChoice(){
        String choice = IO.readln("Choose an option: ");
        try {
            return Integer.parseInt(choice);
        }
        catch(NumberFormatException e){
            return -1;
        }
    }
}
