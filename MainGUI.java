
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainGUI {
    private JFrame frame;
    private JTextArea textArea;
    private ManagementSystem managementSystem;

    public MainGUI() {
        managementSystem = new ManagementSystem();
        frame = new JFrame("Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton addWorkerButton = new JButton("Add Worker");
        JButton addClientButton = new JButton("Add Client");
        JButton createJobButton = new JButton("Create Job");
        JButton viewJobsButton = new JButton("View All Jobs");
        JButton assignJobButton = new JButton("Assign Job");

        addWorkerButton.addActionListener(new AddWorkerAction());
        addClientButton.addActionListener(new AddClientAction());
        createJobButton.addActionListener(new CreateJobAction());
        viewJobsButton.addActionListener(new ViewJobsAction());
        assignJobButton.addActionListener(new AssignJobAction());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addWorkerButton);
        buttonPanel.add(addClientButton);
        buttonPanel.add(createJobButton);
        buttonPanel.add(viewJobsButton);
        buttonPanel.add(assignJobButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void updateTextArea() {
        StringBuilder sb = new StringBuilder();

        sb.append("Workers:\n");
        for (Worker worker : managementSystem.getWorkers()) {
            sb.append(worker).append("\n");
        }

        sb.append("\nClients:\n");
        for (Client client : managementSystem.getClients()) {
            sb.append(client).append("\n");
        }

        sb.append("\nJobs:\n");
        for (Job job : managementSystem.getJobs()) {
            sb.append(job).append("\n");
        }

        textArea.setText(sb.toString());
    }

    private class AddWorkerAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String workerName = JOptionPane.showInputDialog(frame, "Enter worker's name:");
            if (workerName != null && !workerName.isBlank()) {
                managementSystem.addWorker(new Worker(workerName));
                updateTextArea();
            }
        }
    }

    private class AddClientAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String clientName = JOptionPane.showInputDialog(frame, "Enter client's name:");
            if (clientName != null && !clientName.isBlank()) {
                managementSystem.addClient(new Client(clientName));
                updateTextArea();
            }
        }
    }

    private class CreateJobAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String clientName = JOptionPane.showInputDialog(frame, "Enter client's name for the job:");
            String jobName = JOptionPane.showInputDialog(frame, "Enter the name of the job:");
            String dueDateString = JOptionPane.showInputDialog(frame, "Enter due date (yyyy-MM-dd):");

            try {
                Client client = null;
                for (Client c : managementSystem.getClients()) {
                    if (c.getName().equalsIgnoreCase(clientName)) {
                        client = c;
                        break;
                    }
                }
                if (client == null) {
                    throw new IllegalArgumentException("Client not found.");
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dueDate = sdf.parse(dueDateString);

                managementSystem.createJob(client, jobName, dueDate);
                updateTextArea();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date format. Please use yyyy-MM-dd.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        }
    }

    private class ViewJobsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder sb = new StringBuilder("Available Jobs:\n");
            for (Job job : managementSystem.getJobs()) {
                sb.append(job).append("\n");
            }
            JOptionPane.showMessageDialog(frame, sb.toString(), "Jobs", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class AssignJobAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String jobName = JOptionPane.showInputDialog(frame, "Enter the name of the job:");
            String workerName = JOptionPane.showInputDialog(frame, "Enter the name of the worker:");

            Job selectedJob = null;
            Worker selectedWorker = null;

            for (Job job : managementSystem.getJobs()) {
                if (job.getName().equalsIgnoreCase(jobName)) {
                    selectedJob = job;
                    break;
                }
            }

            for (Worker worker : managementSystem.getWorkers()) {
                if (worker.getName().equalsIgnoreCase(workerName)) {
                    selectedWorker = worker;
                    break;
                }
            }

            if (selectedJob == null) {
                JOptionPane.showMessageDialog(frame, "Job not found.");
            } else if (selectedWorker == null) {
                JOptionPane.showMessageDialog(frame, "Worker not found.");
            } else {
                selectedJob.assignWorker(selectedWorker);
                updateTextArea();
            }
        }
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
