package crewmate.lib;

import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.util.function.BooleanSupplier;

public class DynamicTriggerAccess extends Trigger {

    String name = "DynamicTriggerAccessDefaultName";
    
    public DynamicTriggerAccess(BooleanSupplier condition) {
        super(condition);
    }

    public DynamicTriggerAccess(EventLoop loop, BooleanSupplier condition) {
        super(loop, condition);
    }

    public DynamicTriggerAccess(EventLoop loop, BooleanSupplier condition, String name) {
        super(loop, condition);
        this.name = name;
    }

    public DynamicTriggerAccess(BooleanSupplier condition, String name) {
        super(condition);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}