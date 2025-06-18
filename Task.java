import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private static int idCounter =1;
    private int id;
    private String title;
    private String desc;
    private LocalDate date;
    private boolean isComplete;

    //constructor for user Input
    public Task(String title,String desc,LocalDate date){
        this.id = idCounter++;
        this.title =title;
        this.desc=desc;
        this.date=date;
        this.isComplete = false;

    }

    //constructor for File input
    public Task(int id,String title,String desc,LocalDate date,boolean iscomplete){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.isComplete = iscomplete;
        idCounter = Math.max(idCounter,id+1);
    }



    //getter functions
    public int getId(){return id;}
    public String getDesc(){return desc;}
    public LocalDate getDate(){return date;}
    public boolean getComplete(){return isComplete;}

    //setter functions
    public void setId(int id){this.id =id;}
    public void setDate(LocalDate date){this.date=date;}
    public void setComplete(boolean iscomplete){this.isComplete = iscomplete;}

    public void display(){
        System.out.println(id+"."+title+"      "+date+"\n"+"    "+desc+"\n");
    }

    //display override
    @Override
    public String toString(){
        return id+","+title+","+desc+","+date+","+isComplete;
    }

    //existing Tasks from file when load
    public static Task fromString(String line){
        String[] parts = line.split(",",5);
        int id = Integer.parseInt(parts[0]);
        String title = parts[1];
        String desc = parts[2];
        LocalDate date = LocalDate.parse(parts[3]);
        boolean iscomplete = Boolean.parseBoolean(parts[4]);
        return new Task(id,title,desc,date,iscomplete);

    }


}
