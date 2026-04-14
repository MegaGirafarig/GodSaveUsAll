import java.io.*; 
import java.net.*; 
import javax.swing.*; 

public class GameStarter { 
    private static PrintWriter serverOut; 
    private static BufferedReader serverIn; 
    private static int playerId; 

    public static void main(String[] args) { 
        int choice = JOptionPane.showOptionDialog( 
            null, 
            "What would you like to do?", 
            "Game Startup", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            new String[]{"Host Game", "Join Game"}, 
            "Host Game" 
        ); 

        if (choice == 0) { 
            hostGame(); 
        } else { 
            joinGame(); 
        } 
    } 

    private static void hostGame() { 
        try { 
            ServerSocket hostServer = new ServerSocket(5001); 
            playerId = 1; 
            
            // Start game immediately so host can move
            GameFrame frame = new GameFrame(800, 600, playerId);
            frame.setUpGUI();
            GameController controller = new GameController(frame, null, null, playerId);
            
            JOptionPane.showMessageDialog( 
                null, 
                "Game hosted! Waiting for opponent...", 
                "Host Started", 
                JOptionPane.INFORMATION_MESSAGE 
            ); 
            
            // Accept opponent connection in a separate thread
            new Thread(() -> {
                try {
                    Socket opponentSocket = hostServer.accept();
                    serverOut = new PrintWriter(opponentSocket.getOutputStream(), true);
                    serverIn = new BufferedReader(new InputStreamReader(opponentSocket.getInputStream()));
                    
                    // Now that opponent is connected, set up the communication
                    controller.setNetworkStreams(serverOut, serverIn);
                    JOptionPane.showMessageDialog(null, "Opponent connected!", "Connection", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to accept opponent!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }).start();
            
            controller.start(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(null, "Failed to host game!", "Error", JOptionPane.ERROR_MESSAGE); 
        } 
    } 

    private static void joinGame() { 
        try { 
            String ip = JOptionPane.showInputDialog( 
                null, 
                "Enter Host IP Address:", 
                "Join Game", 
                JOptionPane.QUESTION_MESSAGE 
            ); 
            if (ip == null || ip.trim().isEmpty()) { 
                System.out.println("No IP entered. Exiting."); 
                return; 
            } 
            Socket socket = new Socket(ip.trim(), 5001); 
            serverOut = new PrintWriter(socket.getOutputStream(), true); 
            serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            playerId = 2; 
            startGame(playerId, serverOut, serverIn); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(null, "Failed to connect to host!", "Error", JOptionPane.ERROR_MESSAGE); 
        } 
    } 

    private static void startGame(int pId, PrintWriter out, BufferedReader in) { 
        playerId = pId; 
        System.out.println("Connected as Player " + playerId); 
        GameFrame frame = new GameFrame(800, 600, playerId); 
        frame.setUpGUI(); 
        GameController controller = new GameController(frame, out, in, playerId); 
        controller.start(); 
    } 
}