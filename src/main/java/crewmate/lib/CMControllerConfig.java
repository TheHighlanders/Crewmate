package crewmate.lib;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class CMControllerConfig {
    private final Map<Trigger, Map<ActionType, Command>> buttonActions;

    public CMControllerConfig() {
        this.buttonActions = new HashMap<>();
    }

    public void bindButtonAction(Trigger trigger, ActionType actionType, Command supplier) {
        buttonActions.computeIfAbsent(trigger, k -> new HashMap<>()).put(actionType, supplier);
    }

    public Map<Trigger, Map<ActionType, Command>> getButtonActions() {
        return buttonActions;
        
    }

    public enum ActionType {
        WHILE_TRUE,
        WHILE_FALSE,
        ON_TRUE,
        ON_FALSE,
        TOGGLE_ON_TRUE,
        TOGGLE_ON_FALSE,
    }
}