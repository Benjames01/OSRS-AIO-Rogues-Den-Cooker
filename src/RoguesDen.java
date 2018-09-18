package RoguesDen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.widgets.message.Message;

import gui.GUI;
import nodes.CookNode;
import nodes.Node;
import tasks.Task;

@ScriptManifest(author = "Ben", description = "Cooks many things at Rogue's Den in Burthope.", category = Category.COOKING, name = "Ben's AIO Rogue's Den", version = 0.1)
public class RoguesDen extends AbstractScript {

    private final Image bg = getImage("https://i.imgur.com/7h788wA.png");
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private boolean startScript;
    private GUI gui;
    private Timer timer;
    private int cooked = 0, burned = 0;
    private String status = "";
    private int startLvl;
    private int startXP;

    @Override
    public int onLoop() {
        if (startScript) {
            if (nodes.isEmpty()) {
                for (Task task : tasks) {
                    Node n = new CookNode(this, task);
                    nodes.add(n);
                }
            }

            for (Node node : nodes) {
                final Node n = node;
                if (n.validate())
                    n.execute();
                break;
            }
        }
        return Calculations.random(450, 600);
    }

    private Image getImage(String url) {

        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
        }

        return null;
    }

    @Override
    public void onExit() {
        gui.getJFrame().dispose();
        stop();
    }

    @Override
    public void onStart() {
        gui = new GUI(this);
        gui.getJFrame().setVisible(true);
    }

    @Override
    public void onMessage(Message message) {
        if (message.getMessage() != null && (message.getMessage().toLowerCase().contains("you roast") || message.getMessage().toLowerCase().contains("you successfully"))) {
            cooked++;
        } else if (message.getMessage() != null && message.getMessage().toLowerCase().contains("burn")) {
            burned++;
        }
    }

    public void onPaint(Graphics g) {

        if (startScript == true) {
            if (gui.getJFrame().isVisible())
                gui.getJFrame().dispose();
            if (timer == null) {
                timer = new Timer();
            }
            if (startLvl == 0) {
                getSkillTracker().start(Skill.COOKING);
                startLvl = getSkills().getRealLevel(Skill.COOKING);
                startXP = getSkills().getExperience(Skill.COOKING);
            }

            int currentLvl = getSkills().getRealLevel(Skill.COOKING);
            int gainedLvl = currentLvl - startLvl;
            int gainedXP = getSkills().getExperience(Skill.COOKING) - startXP;
            long xpPerHour = (long) (gainedXP * 3600000d / timer.elapsed());

            g.drawImage(bg, 7, 345, null);
            g.setColor(Color.black);
            g.drawString("Runtime: " + Timer.formatTime(timer.elapsed()), 110, 400);
            g.drawString("XP: " + gainedXP + " [" + gainedLvl + "]", 110, 430);
            g.drawString("XP/H: " + xpPerHour, 325, 400);
            g.drawString("Cooked: " + cooked, 240, 400);
            g.drawString("Burnt: " + burned, 240, 430);
            g.drawString("Status: " + status, 10, 460);


        }

    }


    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartScript(boolean startScript) {
        this.startScript = startScript;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }

}
