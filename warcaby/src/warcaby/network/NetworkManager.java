package warcaby.network;

import warcaby.model.Move;
import java.io.*;
import java.net.*;
import java.util.function.Consumer;

public class NetworkManager {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean isServer;

    public NetworkManager(boolean isServer, String ip, int port, Runnable onConnected, Consumer<Move> onMoveReceived, Runnable onConnectionLost) {
        this.isServer = isServer;

        new Thread(() -> {
            try {
                if (isServer) {
                    ServerSocket serverSocket = new ServerSocket(port);
                    socket = serverSocket.accept(); 
                } else {
                    socket = new Socket(ip, port); 
                }
                
                if (onConnected != null) {
                    onConnected.run();
                }
                
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    Move move = (Move) in.readObject();
                    if (move != null) {
                        onMoveReceived.accept(move);
                    }
                }
            } catch (Exception e) {
                if (onConnectionLost != null) onConnectionLost.run();
            }
        }).start();
    }

    public void sendMove(Move move) {
        if (out != null) {
            try {
                out.writeObject(move);
                out.flush();
                out.reset(); 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
