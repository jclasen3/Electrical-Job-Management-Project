// The Job class represents a task that can be assigned to a worker.
import java.text.SimpleDateFormat;
import java.util.Date;

public class Job {
    // Instance variables to store the job's name, the associated client, the due date, and the assigned worker
    private String name;
    private Client client;
    private Date dueDate;
    private Worker assignedWorker;

    // Constructor to initialize the job with a name, associated client, and due date
    public Job(String name, Client client, Date dueDate) {
        this.name = name;
        this.client = client;
        this.dueDate = dueDate;
        this.assignedWorker = null;  // Initially, the job is unassigned
    }

    // Method to assign a worker to this job
    public void assignWorker(Worker worker) {
        this.assignedWorker = worker;  // Set the assigned worker
    }

    // Method to get the assigned worker for this job
    public Worker getAssignedWorker() {
        return assignedWorker;  // Return the assigned worker, or null if unassigned
    }

    // Getter method to retrieve the job's name
    public String getName() {
        return name;
    }

    // Getter method to retrieve the client associated with the job
    public Client getClient() {
        return client;
    }

    // Getter method to retrieve the due date of the job
    public Date getDueDate() {
        return dueDate;
    }

    // Override toString to provide a custom string representation of the job
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // Format the due date to "yyyy-MM-dd"
        String dueDateFormatted = sdf.format(dueDate);
        String workerName = (assignedWorker != null) ? assignedWorker.getName() : "Unassigned";  // Check if worker is assigned
        // Return a formatted string containing the job's details
        return "Job: " + name + ", Client: " + client.getName() + ", Due: " + dueDateFormatted + ", Worker: " + workerName;
    }
}

