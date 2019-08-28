import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Service {
    private int server_port;

    public Service() {
        String portStr =getConf.getValue("server_port");

        server_port = Integer.parseInt(portStr);
    }

    public void receive() throws IOException {

        ServerSocket server = new ServerSocket(server_port);
        System.out.println("服务器启动:port: "+server_port);

        while(true) {
            Socket socket = server.accept();
            System.out.println("客户端连接成功:socket: "+socket);
            ServiceRunnable sr = new ServiceRunnable(socket);
            new Thread(sr).start();
        }
    }

    public static void main(String[] args) {
        Service s=new Service();
        try {  
            s.receive();
        } catch (IOException e) {
            // TODO Auto-ge
            //  nerated catch block
            e.printStackTrace();
        }
    }
}

