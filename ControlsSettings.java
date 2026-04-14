import java.util.HashMap;
import java.util.Map;

public class ControlsSettings {

    // Map to hold keybinds
    private Map<String, String> keybinds;

    // Constructor initializes default keybinds
    public ControlsSettings() {
        keybinds = new HashMap<>();
        setupDefaultKeybinds();
    }

    // Method to set up default keybinds
    private void setupDefaultKeybinds() {
        keybinds.put("moveForward", "W");
        keybinds.put("moveBackward", "S");
        keybinds.put("turnLeft", "A");
        keybinds.put("turnRight", "D");
        keybinds.put("jump", "SPACE");
        keybinds.put("attack", "LEFT_CLICK");
        keybinds.put("sprint", "L_SHIFT");
    }

    // Method to get a keybind
    public String getKeybind(String action) {
        return keybinds.getOrDefault(action, "Not bound");
    }

    // Method to set a keybind
    public void setKeybind(String action, String key) {
        keybinds.put(action, key);
    }

    // Method to display all keybinds
    public void displayKeybinds() {
        System.out.println("Current Keybinds:");
        for (Map.Entry<String, String> entry : keybinds.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}