import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ManagementSystemTest {
    private ManagementSystem managementSystem;

    @BeforeEach
    void setUp() {
        managementSystem = new ManagementSystem();
    }

    @Test
    void testAddWorker() {
        Worker worker = new Worker("Alice");
        managementSystem.addWorker(worker);

        assertEquals(1, managementSystem.getWorkers().size());
        assertTrue(managementSystem.getWorkers().contains(worker)); // Verify worker in the HashMap
        assertEquals(worker, managementSystem.getWorkerByName("Alice"));
    }

    @Test
    void testAddClient() {
        Client client = new Client("Bob's Company", "123-456-7890");
        managementSystem.addClient(client);

        assertEquals(1, managementSystem.getClients().size());
        assertTrue(managementSystem.getClients().contains(client)); // Verify client in the HashMap
        assertEquals(client, managementSystem.getClientByName("Bob's Company"));
    }

    @Test
    void testCreateJob() throws ParseException {
        Client client = new Client("Client A", "987-654-3210");
        managementSystem.addClient(client);

        String jobName = "Website Development";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dueDate = sdf.parse("2024-12-31");

        managementSystem.createJob(client, jobName, dueDate);

        assertEquals(1, managementSystem.getJobs().size());
        assertEquals(1, managementSystem.getJobQueue().size()); // Verify job added to the queue

        Job job = managementSystem.getJobs().get(0);
        assertEquals(jobName, job.getName());
        assertEquals(client, job.getClient());
        assertEquals(dueDate, job.getDueDate());
        assertTrue(managementSystem.getJobQueue().contains(job)); // Verify job is in the queue
    }

    @Test
    void testAssignWorkerToJob() throws ParseException {
        Client client = new Client("Client B", "555-123-4567");
        managementSystem.addClient(client);

        Worker worker = new Worker("Charlie");
        managementSystem.addWorker(worker);

        String jobName = "Mobile App";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dueDate = sdf.parse("2025-01-01");
        managementSystem.createJob(client, jobName, dueDate);

        managementSystem.assignJob(jobName, "Charlie");

        Job job = managementSystem.getJobs().get(0);
        assertNotNull(job.getAssignedWorker());
        assertEquals(worker, job.getAssignedWorker());
        assertEquals("Charlie", job.getAssignedWorker().getName());
        assertFalse(managementSystem.getJobQueue().contains(job)); // Verify job removed from queue
    }

    @Test
    void testJobToString() throws ParseException {
        Client client = new Client("Client C", "555-765-4321");
        Worker worker = new Worker("Dave");

        String jobName = "Data Analysis";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dueDate = sdf.parse("2025-06-15");
        Job job = new Job(jobName, client, dueDate);

        job.assignWorker(worker);

        String expected = "Job: Data Analysis, Client: Client C, Due: 2025-06-15, Worker: Dave";
        assertEquals(expected, job.toString());
    }

    @Test
    void testUnassignedJobToString() throws ParseException {
        Client client = new Client("Client D", "555-890-1234");

        String jobName = "System Upgrade";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dueDate = sdf.parse("2024-11-30");
        Job job = new Job(jobName, client, dueDate);

        String expected = "Job: System Upgrade, Client: Client D, Due: 2024-11-30, Worker: Unassigned";
        assertEquals(expected, job.toString());
    }
}

