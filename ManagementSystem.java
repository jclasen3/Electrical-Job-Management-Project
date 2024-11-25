import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManagementSystem {
    private List<Worker> workers;
    private List<Client> clients;
    private List<Job> jobs;

    public ManagementSystem() {
        workers = new ArrayList<>();
        clients = new ArrayList<>();
        jobs = new ArrayList<>();
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void createJob(Client client, String jobName, Date dueDate) {
        Job newJob = new Job(client, jobName, dueDate);
        jobs.add(newJob);
    }
}


