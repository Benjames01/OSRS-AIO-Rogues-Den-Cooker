package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import RoguesDen.RoguesDen;
import tasks.Task;

public class GUI extends JFrame {


    private static final long serialVersionUID = 1L;
    private JFrame frmScaffedAioRogues;
    private JTextField textFood;
    private JTextField textLevel;

    private ArrayList<Task> tasks = new ArrayList<Task>();
    private RoguesDen ctx;

    /**
     * Create the application.
     */
    public GUI(RoguesDen main) {
        this.ctx = main;
        initialize();
        tasks = new ArrayList<Task>();
    }

    public JFrame getJFrame() {
        return frmScaffedAioRogues;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmScaffedAioRogues = new JFrame();
        frmScaffedAioRogues.setResizable(false);
        frmScaffedAioRogues.setTitle("Ben's AIO Rogue's Den - V0.1");
        frmScaffedAioRogues.setBounds(100, 100, 535, 329);
        frmScaffedAioRogues.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmScaffedAioRogues.getContentPane().setLayout(null);

        JLabel lblScaffedAioRogues = new JLabel("Ben's AIO ROGUES DEN");
        lblScaffedAioRogues.setBounds(-107, 0, 666, 73);
        lblScaffedAioRogues.setFont(new Font("Golden Plains - Demo", Font.BOLD | Font.ITALIC, 30));
        lblScaffedAioRogues.setHorizontalAlignment(SwingConstants.CENTER);
        frmScaffedAioRogues.getContentPane().add(lblScaffedAioRogues);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 72, 666, 263);
        frmScaffedAioRogues.getContentPane().add(tabbedPane);

        JPanel tasksPanel = new JPanel();
        tabbedPane.addTab("Tasks List", null, tasksPanel, null);
        tasksPanel.setLayout(null);

        JList<Object> list = new JList<Object>();
        list.setVisibleRowCount(10);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setBounds(10, 11, 392, 137);
        tasksPanel.add(list);

        JButton btnDeleteTask = new JButton("Delete Task");
        btnDeleteTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    if (list.getSelectedIndex() > -1) {
                        tasks.remove(list.getSelectedIndex());
                        Object[] taskArray = (Object[]) tasks.toArray();
                        list.setListData(taskArray);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnDeleteTask.setBounds(412, 11, 106, 23);
        tasksPanel.add(btnDeleteTask);

        JButton btnDeleteAll = new JButton("Delete All");
        btnDeleteAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (!tasks.isEmpty()) {
                    tasks.clear();
                }
                Object[] taskArray = (Object[]) tasks.toArray();
                list.setListData(taskArray);
            }
        });
        btnDeleteAll.setBounds(412, 45, 106, 23);
        tasksPanel.add(btnDeleteAll);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 159, 508, 2);
        tasksPanel.add(separator);

        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!tasks.isEmpty()) {
                    ctx.setStartScript(true);
                    ctx.setTasks(tasks);
                }
            }
        });
        btnStart.setBounds(10, 169, 508, 23);
        tasksPanel.add(btnStart);

        JPanel finishPanel = new JPanel();
        tabbedPane.addTab("Create Task", null, finishPanel, null);
        finishPanel.setLayout(null);

        JLabel lblNameOfRaw = new JLabel("Raw Food: ");
        lblNameOfRaw.setBounds(10, 14, 105, 14);
        finishPanel.add(lblNameOfRaw);

        textFood = new JTextField();
        textFood.setBounds(75, 11, 185, 20);
        finishPanel.add(textFood);
        textFood.setColumns(10);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(62, 43, 198, 2);
        finishPanel.add(separator_1);

        JCheckBox chckbxUnlimitedTask = new JCheckBox("Unlimited Task");
        chckbxUnlimitedTask.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                if (chckbxUnlimitedTask.isSelected()) {
                    textLevel.setEnabled(false);
                    textLevel.setEditable(false);
                } else {
                    textLevel.setEnabled(true);
                    textLevel.setEditable(true);
                }

            }
        });
        chckbxUnlimitedTask.setBounds(167, 52, 136, 23);
        finishPanel.add(chckbxUnlimitedTask);

        JLabel lblTaskType = new JLabel("Goal Type");
        lblTaskType.setFont(new Font("Tahoma", Font.PLAIN, 9));
        lblTaskType.setBounds(10, 37, 54, 14);
        finishPanel.add(lblTaskType);

        textLevel = new JTextField();
        textLevel.setBounds(56, 53, 105, 20);
        finishPanel.add(textLevel);
        textLevel.setColumns(10);

        JLabel lblLevel = new JLabel("Level:");
        lblLevel.setBounds(20, 56, 56, 14);
        finishPanel.add(lblLevel);

        JSeparator separator_2 = new JSeparator();
        separator_2.setBounds(10, 82, 250, 2);
        finishPanel.add(separator_2);

        JButton btnEditTask = new JButton("EDIT TASK");
        btnEditTask.setBounds(144, 95, 116, 50);
        finishPanel.add(btnEditTask);

        JButton btnCreateTask = new JButton("CREATE TASK");
        btnCreateTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textFood.getText() != null) {
                    if (chckbxUnlimitedTask.isSelected()) {
                        Task task = new Task(textFood.getText(), 0, true);
                        tasks.add(task);
                        Object[] taskArray = (Object[]) tasks.toArray();
                        list.setListData(taskArray);
                    } else {
                        int goal = 0;
                        try {
                            if (textLevel.getText() != null)
                                goal = Integer.parseInt(textLevel.getText());
                            Task task = new Task(textFood.getText(), goal, false);
                            tasks.add(task);
                            Object[] taskArray = (Object[]) tasks.toArray();
                            list.setListData(taskArray);
                        } catch (NumberFormatException e1) {
                            e1.printStackTrace();
                        }
                    }

                }
            }
        });
        btnCreateTask.setBounds(10, 95, 116, 50);
        finishPanel.add(btnCreateTask);
    }
}
