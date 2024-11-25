import java.text.SimpleDateFormat;
import java.util.Date;

public class Job {
    private Client client;
    private String name;
    private Date dueDate;

    public Job(Client client, String name, Date dueDate) {
        this.client = client;
        this.name = name;
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Job: " + name + ", Client: " + client.getName() + ", Due: " + sdf.format(dueDate);
    }
}

