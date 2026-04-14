import java.util.HashMap;
import java.util.Map;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class InputHandler implements KeyListener {
    private final Map<Integer, Runnable> keyBindings;

    public InputHandler() {
        keyBindings = new HashMap<>();
    }

    public void bindKey(int keyCode, Runnable action) {
        keyBindings.put(keyCode, action);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Runnable action = keyBindings.get(e.getKeyCode());
        if (action != null) {
            action.run();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Optionally handle key release events
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Optionally handle key typed events
    }

    // Helper method to attach the input handler to a JFrame
    public void attachTo(JFrame frame) {
        frame.addKeyListener(this);
    }
}