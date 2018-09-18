package nodes;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import RoguesDen.RoguesDen;
import tasks.Task;

public class CookNode implements Node {

    private RoguesDen script;
    private Task task;

    public CookNode(RoguesDen script, Task task) {
        this.script = script;
        this.task = task;
    }

    @Override
    public boolean validate() {

        if (!isGoalMet()) {
            return true;
        }
        script.removeNode(this);
        return false;
    }

    @Override
    public void execute() {
        while (!isGoalMet()) {

            if (script.getTabs().isOpen(Tab.INVENTORY))
                script.getTabs().open(Tab.INVENTORY);

            if (!script.getInventory().isFull() || !script.getInventory().contains(task.getFish())) {
                if (bank() == false)
                    break;
            }

            GameObject fire = script.getGameObjects().closest(fireObj -> fireObj != null && fireObj.getName().equals("Fire"));
            if (fire == null)
                return;

            while (script.getInventory().contains(task.getFish())) {

                if (script.getDialogues().inDialogue()) {
                    script.getDialogues().spaceToContinue();
                }

                if (!script.getLocalPlayer().isAnimating()) {
                    useOnFireAndWait(fire);
                }
            }
        }
        script.setStatus("GOAL MET");
        MethodProvider.sleep(5000);
        script.removeNode(this);
    }

    private void useOnFireAndWait(GameObject fire) {
        script.setStatus("cook is set");
        script.getInventory().get(task.getFish()).useOn(fire);
        MethodProvider.sleep(Calculations.random(500, 1000));
        getCookChildren().interact();
        MethodProvider.sleep(1000);
        script.setStatus("Cooking");
        MethodProvider.sleepUntil(() -> !script.getInventory().contains(task.getFish()), 80000);
    }

    private boolean bank() {
        script.setStatus("Banking");
        NPC npc = script.getNpcs().closest(banker -> banker != null && banker.getName().equals("Emerald Benedict"));
        if (npc == null)
            return false;
        npc.interact("Bank");
        MethodProvider.sleep(Calculations.random(500, 1000));
        if (script.getBank() != null) {
            MethodProvider.sleep(Calculations.random(500, 1000));
            script.getBank().depositAllItems();
            MethodProvider.sleep(Calculations.random(500, 1000));
            script.getBank().withdrawAll(task.getFish());
            MethodProvider.sleep(Calculations.random(1000, 1250));
            script.getBank().close();
            return true;
        }
        return false;

    }

    public boolean isGoalMet() {
        if (task.isUnlimitedTask())
            return false;
        else if (script.getSkills().getRealLevel(Skill.COOKING) < task.getGoal())
            return false;
        return true;
    }

    public WidgetChild getCookChildren() {
        return script.getWidgets().getWidget(270).getChild(14);
    }

}
