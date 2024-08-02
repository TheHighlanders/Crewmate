package crewmate.lib;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public class TM {
    private final XboxButton button;
    private final Trigger trigger;

    public TM(XboxButton button, Trigger trigger) {
        this.button = button;
        this.trigger = trigger;
    }

    public XboxButton getButton() {
        return button;
    }

    public Trigger getTrigger() {
        return trigger;
    }
}