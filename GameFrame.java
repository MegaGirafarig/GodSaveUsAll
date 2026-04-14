import java.awt.*;
import javax.swing.*;

public class GameFrame {
    private int width;
    private int height;
    private int playerId;
    private JFrame frame;
    private GameCanvas canvas;
    private InputHandler inputHandler;
    private ControlsSettings controlsSettings;

    public GameFrame(int w, int h, int playerId) {
        width = w;
        height = h;
        this.playerId = playerId;
        frame = new JFrame("Multiplayer Game - Player " + playerId);
        canvas = new GameCanvas(width, height, playerId);
        controlsSettings = new ControlsSettings();
        inputHandler = new InputHandler(canvas.getPlayerCharacter(), controlsSettings);
    }

    public void setUpGUI() {
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(canvas, BorderLayout.CENTER);
        JPanel controlPanel = createControlPanel();
        contentPane.add(controlPanel, BorderLayout.SOUTH);
        frame.addKeyListener(inputHandler);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Multiplayer Game - Player " + playerId);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.pack();
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setPreferredSize(new Dimension(width, 100));
        JButton settingsBtn = new JButton("Controls");
        settingsBtn.addActionListener(e -> showControlsDialog());
        panel.add(settingsBtn);
        JLabel instructionsLabel = new JLabel("WASD or Arrows to move");
        instructionsLabel.setForeground(Color.WHITE);
        panel.add(instructionsLabel);
        return panel;
    }

    private void showControlsDialog() {
        new ControlsDialog(frame, controlsSettings, inputHandler);
    }

    public GameCanvas getCanvas() {
        return canvas;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }
}