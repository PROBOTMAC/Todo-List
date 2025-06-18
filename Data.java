import java.io.*;
import java.util.*;
public class Data {
    private static final String FILE_NAME = "database.txt";


    

    public static void saveTaskToFile(List<Task> tasks){
        try(BufferedWriter br  = new BufferedWriter(new FileWriter(FILE_NAME))){
            for(Task task:tasks){
                br.write(task.toString());
                br.newLine();
            }
        }catch(Exception e){
            System.out.println("File Writing Error Code :102");
        }
    }

    public static List<Task> loadFromFile(){
        List<Task>tasks = new ArrayList<>();
        File file = new File(FILE_NAME);
        if(!file.exists()) {
            return tasks;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            while((line = br.readLine()) != null){
                tasks.add(Task.fromString(line));
            }
        }catch(IOException e){
            System.out.println("Error Reading File");
        }return tasks;
    }
}
