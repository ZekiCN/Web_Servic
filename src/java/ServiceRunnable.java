import java.io.*;
import java.net.Socket;

public class ServiceRunnable implements Runnable{
    private Socket socket;
    private String path ;
    private String errorFile ;
    private String welFile;

    public ServiceRunnable(Socket socket) {
        this.socket = socket;
        new getConf();
        this.path = getConf.getValue("path");
        this.errorFile = getConf.getValue("errorFile");
        this.welFile = getConf.getValue("welFile");

    }

    @Override
    public void run() {
        String url = null;
        try {
            url = getUrl();
        System.out.println("getUrl: "+url);

        this.sendResponse(url);
        this.socket.close();
        } catch (IOException e) {
        e.printStackTrace();
    }
    }

    private String getUrl() throws IOException {
        InputStream in = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);

        String line = null;
        String str = br.readLine();
        System.out.println(str);
        if(str == null)
            return null;
        String[] array = str.split(" ");
        if(array.length != 3) {
            System.out.println("Request-line is Empty!");
            return null;
        }

        return array[1];
    }

    private void sendResponse(String url) throws IOException {
        PrintStream ps = new PrintStream(this.socket.getOutputStream());
        File file = new File(this.path, url);
        if (file.exists()) {
            if ("/".equals(url)) {
                file = new File(this.path, this.welFile);
            }

            ps.println("HTTP/1.1 200 OK");
        } else {
            file = new File(this.path, this.errorFile);
            ps.println("HTTP/1.1 404 NotFound");
        }

        ps.println();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] array = new byte[1024];
        boolean var6 = false;

        int size;
        while((size = bis.read(array)) != -1) {
            ps.write(array, 0, size);
        }

        bis.close();
        ps.close();
    }

}
