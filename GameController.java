import java.io.*;
import java.awt.*;

public class GameController extends Thread {
    private GameFrame frame;
    private PrintWriter networkOut;
    private BufferedReader networkIn;
    private int playerId;
    private boolean running = true;

    public GameController(GameFrame frame, PrintWriter out, BufferedReader in, int playerId) {
        this.frame = frame;
        this.networkOut = out;
        this.networkIn = in;
        this.playerId = playerId;
    }

    public void setNetworkStreams(PrintWriter out, BufferedReader in) {
        this.networkOut = out;
        this.networkIn = in;
        System.out.println("Network streams set for Player " + playerId);
    }

    @Override
    public void run() {
        startGame();
    }

    public void startGame() {
        System.out.println("Player " + playerId + " game started");
        while (running) {
            updateGame();
            renderGame();
            if (gameShouldEnd()) {
                running = false;
            }
        }
        System.out.println("Game over");
    }

    private void updateGame() {
        GameCanvas canvas = frame.getCanvas();
        if (canvas != null) {
            canvas.update();
            if (networkOut != null) {
                Character playerChar = canvas.getPlayerCharacter();
                if (playerChar != null) {
                    networkOut.println(playerChar.serialize());
                }
            }
            if (networkIn != null) {
                try {
                    if (networkIn.ready()) {
                        String data = networkIn.readLine();
                        if (data != null) {
                            canvas.updateOpponentCharacter(data);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void renderGame() {
        GameCanvas canvas = frame.getCanvas();
        if (canvas != null) {
            canvas.repaint();
        }
    }

    private boolean gameShouldEnd() {
        return false;
    }

    public void stopGame() {
        running = false;
    }
}
