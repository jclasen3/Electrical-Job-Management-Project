
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Date;

public class ManagementSystem {
    // HashMaps to store workers and clients by their names as keys
    private HashMap<String, Worker> workers;
    private HashMap<String, Client> clients;
    // List to store all jobs created
    private List<Job> jobs;
    // Queue to manage jobs that are unassigned and need to be worked on
    private Queue<Job> jobQueue;

    // Constructor to initialize the data structures
    public ManagementSystem() {
        workers = new HashMap<>(); // Initialize the workers HashMap
        clients = new HashMap<>(); // Initialize the clients HashMap
        jobs = new LinkedList<>(); // Initialize the jobs list
        jobQueue = new LinkedList<>(); // Initialize the job queue
    }
    // Method to add a worker to the management system
    public void addWorker(Worker worker) {
        workers.put(worker.getName(), worker);
    }
    // Method to add a client to the management system
    public void addClient(Client client) {
        clients.put(client.getName(), client);
    }

    // Method to create a new job, assign a client to it, and set a due date
    public void createJob(Client client, String jobName, Date dueDate) {
        Job job = new Job(jobName, client, dueDate);
        jobs.add(job); // Add the job to the jobs list
        jobQueue.add(job); // Add the job to the job queue for unassigned jobs
    }
    // Method to get the list of all jobs
    public List<Job> getJobs() {
        return jobs;
    }
    // Method to get the job queue (for unassigned jobs)
    public Queue<Job> getJobQueue() {
        return jobQueue;
    }
    // Method to get the list of all workers as a LinkedList
    public List<Worker> getWorkers() {
        return new LinkedList<>(workers.values());
    }
    // Method to get the list of all clients as a LinkedList
    public List<Client> getClients() {
        return new LinkedList<>(clients.values());
    }
    // Method to retrieve a worker by their name
    public Worker getWorkerByName(String name) {
        return workers.get(name);
    }
    // Method to retrieve a client by their name
    public Client getClientByName(String name) {
        return clients.get(name);
    }
    // Method to assign a job to a worker
    public void assignJob(String jobName, String workerName) {
        Worker worker = getWorkerByName(workerName);  // Retrieve the worker by name
        if (worker == null) {
            throw new IllegalArgumentException("Worker not found: " + workerName);
        }
        // Iterate through the list of jobs to find the job by name
        for (Job job : jobs) {
            if (job.getName().equals(jobName) && job.getAssignedWorker() == null) {
                job.assignWorker(worker);
                jobQueue.remove(job);
                return;
            }
        }
        throw new IllegalArgumentException("Job not found or already assigned: " + jobName);
    }
}

