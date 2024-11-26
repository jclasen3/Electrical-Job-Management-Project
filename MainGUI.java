/**
 * This program creates a GUI for a job management system for an electrical business.
 *
 * @author Jarrett Clasen
 */


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
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Electrical Job Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Set layout for the top panel and add buttons
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        frame.getContentPane().add(panel, BorderLayout.NORTH);

        // Button to add a worker
        JButton addWorkerButton = new JButton("Add Worker");
        addWorkerButton.addActionListener(new AddWorkerAction());
        panel.add(addWorkerButton);

        // Button to add a client
        JButton addClientButton = new JButton("Add Client");
        addClientButton.addActionListener(new AddClientAction());
        panel.add(addClientButton);

        // Button to create a job
        JButton createJobButton = new JButton("Create Job");
        createJobButton.addActionListener(new CreateJobAction());
        panel.add(createJobButton);

        // Button to assign a job
        JButton assignJobButton = new JButton("Assign Job");
        assignJobButton.addActionListener(new AssignJobAction());
        panel.add(assignJobButton);

        // Area to display information
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        updateTextArea();
        frame.setVisible(true);
    }

    // Updates the display area with the current workers, clients, jobs, and unassigned jobs.
    private void updateTextArea() {
        // Create a StringBuilder object to construct the content for the text area efficiently
        StringBuilder sb = new StringBuilder();
        // Append a heading for workers section to the StringBuilder
        sb.append("Workers:\n");
        // Loop through the list of workers from the management system and append each worker's information to the StringBuilder
        for (Worker worker : managementSystem.getWorkers()) {
            sb.append(worker).append("\n");
        }

        // Append a heading for clients section to the StringBuilder
        sb.append("\nClients:\n");
        // Loop through the list of clients and append each client's information to the StringBuilder
        for (Client client : managementSystem.getClients()) {
            // Add client info followed by a new line
            sb.append(client).append("\n");
        }

        sb.append("\nJobs:\n");
        for (Job job : managementSystem.getJobs()) {
            sb.append(job).append("\n");
        }

        sb.append("\nUnassigned Jobs (Queue):\n");
        for (Job job : managementSystem.getJobQueue()) {
            sb.append(job).append("\n");
        }

        textArea.setText(sb.toString());
    }

    // Action to add a worker
    private class AddWorkerAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String workerName = JOptionPane.showInputDialog(frame, "Enter the name of the worker:");
            if (workerName != null && !workerName.trim().isEmpty()) {
                Worker worker = new Worker(workerName.trim());
                managementSystem.addWorker(worker);
                updateTextArea();
            } else {
                JOptionPane.showMessageDialog(frame, "Worker name cannot be empty.");
            }
        }
    }

    // Action to add a client
    private class AddClientAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String clientName = JOptionPane.showInputDialog(frame, "Enter the name of the client:");
            String contactInfo = JOptionPane.showInputDialog(frame, "Enter the contact info of the client:");
            // Check if both the client name and contact info are provided and not empty
            if (clientName != null && contactInfo != null && !clientName.trim().isEmpty() && !contactInfo.trim().isEmpty()) {
                // Create a new Client object with the provided client name and contact info
                Client client = new Client(clientName.trim(), contactInfo.trim());
                // Add the newly created client to the management system
                managementSystem.addClient(client);
                updateTextArea();
            } else {
                JOptionPane.showMessageDialog(frame, "Client name and contact info cannot be empty.");
            }
        }
    }

    // Action to create a new job
    private class CreateJobAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String clientName = JOptionPane.showInputDialog(frame, "Enter the name of the client:");
            String jobName = JOptionPane.showInputDialog(frame, "Enter the name of the job:");
            String dueDateString = JOptionPane.showInputDialog(frame, "Enter the due date (yyyy-MM-dd):");

            try {
                Client client = managementSystem.getClients().stream()
                        .filter(c -> c.getName().equalsIgnoreCase(clientName))
                        .findFirst()
                        .orElse(null);

                if (client == null) {
                    JOptionPane.showMessageDialog(frame, "Client not found.");
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dueDate = sdf.parse(dueDateString);

                managementSystem.createJob(client, jobName, dueDate);
                updateTextArea();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date format. Please use yyyy-MM-dd.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        }
    }

    // Action to assign a job to a worker
    private class AssignJobAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String jobName = JOptionPane.showInputDialog(frame, "Enter the name of the job:");
            String workerName = JOptionPane.showInputDialog(frame, "Enter the name of the worker:");

            try {
                if (jobName == null || workerName == null || jobName.trim().isEmpty() || workerName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Job name and worker name cannot be empty.");
                    return;
                }
                managementSystem.assignJob(jobName, workerName);
                updateTextArea();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Unexpected error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
