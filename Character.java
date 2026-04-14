import java.awt.*;

public class Character {
    private double x;
    private double y;
    private double vx; // velocity x
    private double vy; // velocity y
    private int width = 40;
    private int height = 60;
    private int playerId;
    private Color color;
    private static final double SPEED = 5.0;
    private static final double FRICTION = 0.9;

    public Character(double x, double y, int playerId) {
        this.x = x;
        this.y = y;
        this.playerId = playerId;
        this.color = playerId == 1 ? Color.RED : Color.BLUE;
        this.vx = 0;
        this.vy = 0;
    }

    public void moveLeft() {
        vx = -SPEED;
    }

    public void moveRight() {
        vx = SPEED;
    }

    public void moveUp() {
        vy = -SPEED;
    }

    public void moveDown() {
        vy = SPEED;
    }

    public void stopHorizontal() {
        vx *= FRICTION;
        if (Math.abs(vx) < 0.1) vx = 0;
    }

    public void stopVertical() {
        vy *= FRICTION;
        if (Math.abs(vy) < 0.1) vy = 0;
    }

    public void update(int canvasWidth, int canvasHeight) {
        x += vx;
        y += vy;

        // Boundary collision
        if (x < 0) x = 0;
        if (x + width > canvasWidth) x = canvasWidth - width;
        if (y < 0) y = 0;
        if (y + height > canvasHeight) y = canvasHeight - height;
    }

    public void render(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillRect((int)x, (int)y, width, height);
        
        // Draw border
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect((int)x, (int)y, width, height);
        
        // Draw player ID
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("P" + playerId, (int)x + 10, (int)y + 30);
    }

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getPlayerId() { return playerId; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }

    // Setters for network synchronization
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVelocity(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public String serialize() {
        return String.format("POS:%d:%.2f:%.2f:%.2f:%.2f", playerId, x, y, vx, vy);
    }

    public static Character deserialize(String data, int canvasWidth, int canvasHeight) {
        String[] parts = data.split(":");
        int id = Integer.parseInt(parts[1]);
        double x = Double.parseDouble(parts[2]);
        double y = Double.parseDouble(parts[3]);
        double vx = Double.parseDouble(parts[4]);
        double vy = Double.parseDouble(parts[5]);
        
        Character c = new Character(x, y, id);
        c.setVelocity(vx, vy);
        return c;
    }
}