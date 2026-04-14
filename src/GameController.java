class GameController extends Thread {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public GameController(Socket socket) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            // Game loop logic here
            updateGame();
            try {
                // Slight delay for the game loop
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void updateGame() {
        // Logic to update the game state
        updateOpponentCharacter();
    }

    private void updateOpponentCharacter() {
        // Logic to update the opponent character
    }
}