import java.text.SimpleDateFormat;
import java.util.Date;

public class Job {
    private Client client;
    private String name;
    private Date dueDate;
    private Worker assignedWorker;

    public Job(Client client, String name, Date dueDate) {
        this.client = client;
        this.name = name;
        this.dueDate = dueDate;
        this.assignedWorker = null; // Initially unassigned
    }

    public String getName() {
        return name;
    }

    public void assignWorker(Worker worker) {
        this.assignedWorker = worker;
    }

    public Worker getAssignedWorker() {
        return assignedWorker;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String workerInfo = (assignedWorker != null) ? assignedWorker.getName() : "Unassigned";
        return "Job: " + name + ", Client: " + client.getName() + ", Due: " + sdf.format(dueDate) + ", Worker: " + workerInfo;
    }
}

