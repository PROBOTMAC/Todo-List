import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Main {

        private static List<Task> tasks = new ArrayList<>();
        private static Scanner sc = new Scanner(System.in);
        private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String args[]){

        
        //tasks.add(new Task("Study","Complete 5 problemes a day", LocalDate.now()));
        //tasks.add(new Task("Health","Dring 3 litter Water", LocalDate.now()));
        //tasks.add(new Task("Movie","Watch Tourist Family Movie", LocalDate.now()));
        //tasks.add(new Task("Shopping","Buy a Tomato,maggie", LocalDate.now()));
        //tasks.add(new Task("Meeting","Tomorrow 2pm Meeting", LocalDate.now()));
        tasks = Data.loadFromFile();    



        while (true) {
            System.out.println("\n=== Task Manager ===");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int op = Integer.parseInt(sc.nextLine());
            switch (op) {
                case 1 -> addTask();
                case 2 -> viewTasks();
                case 3 -> markTaskCompleted();
                case 4 -> deleteTask();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
    //Add Task
    private static void addTask(){
        System.out.print("Enter the Title  :");
        String title = sc.nextLine();

        System.out.print("Enter the Description :");
        String desc = sc.nextLine();

        LocalDate dueDate;
        while(true){
            try{
                System.out.print("Enter  Due Date (dd-MM-yyyy)");
                String dueDateStr = sc.nextLine();
                dueDate = LocalDate.parse(dueDateStr, formatter);
                break;
            }catch(Exception e){
                System.out.println("Invalid Date Format...Try Again...");
            }
        }
        Task task = new Task(title,desc,dueDate);
        tasks.add(task);
        Data.saveTaskToFile(tasks);
    }
    //View Task
    private static void viewTasks(){

        tasks.sort(Comparator.comparing(t->t.getDate()));

        System.out.println("\n\n----Pending Tasks----");
        for(Task t:tasks){
            if(!t.getComplete()){
                t.display();
            }
        }

        System.out.println("\n\n----Completed Tasks----");
        for(Task t:tasks){
            if(t.getComplete()){
                t.display();
            }
        }
    }
    //Mark as Completed
    private static void markTaskCompleted(){
        System.out.print(" Enter the Task Id to Mark as Completed  :");
        int id = Integer.parseInt(sc.nextLine());

        boolean found = false;
        for(Task t:tasks){
            if(t.getId()==id){
                t.setComplete(true);
                System.out.println(id+" "+t.getDesc()+"--Completed");
                found = true;
                Data.saveTaskToFile(tasks);
                break;
            }
        }
        if(!found){
            System.out.println(id+" Not Found!");
        }
    }

    //Delete Task 1
    private static void deleteTask(){
        System.out.print("Enter the Id to Delete :");
        int id = Integer.parseInt(sc.nextLine());

        boolean found = false;
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i).getId() == id){
                tasks.remove(i);
                System.out.println(id+ " is Deleted");
                found = true;
                Data.saveTaskToFile(tasks);
                break;
            }
        }if(!found){System.out.println(id + " Not Found");}
        //hii this is siva

    }
}
