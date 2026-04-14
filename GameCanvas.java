import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class GameCanvas extends JComponent{
    private int width;
    private int height;
    private int playerId;
    private Character playerCharacter;
    private Character opponentCharacter;
    Image backgroundImage = new ImageIcon("hallbackground.png").getImage();

    public GameCanvas(int w, int h, int playerId) {
        width = w;
        height = h;
        this.playerId = playerId;
        playerCharacter = new Character(100, 100, playerId);
        opponentCharacter = new Character(600, 100, playerId == 1 ? 2 : 1);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color backgroundColor = Color.BLACK;
        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        Rectangle2D background = new Rectangle2D.Double(0, 0, width, height);
        g2d.setColor(backgroundColor);
        g2d.fill(background);
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, width, height, null);
        }
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Player " + playerId, 20, 30);
        playerCharacter.render(g2d);
        if (opponentCharacter != null) {
            opponentCharacter.render(g2d);
        }
    }

    public Character getPlayerCharacter() {
        return playerCharacter;
    }

    public Character getOpponentCharacter() {
        return opponentCharacter;
    }

    public void setOpponentCharacter(Character c) {
        this.opponentCharacter = c;
    }

    public void update() {
        playerCharacter.update(width, height);
        if (opponentCharacter != null) {
            opponentCharacter.update(width, height);
        }
        repaint();
    }
}