package tcp;
import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        try {
			final ServerSocket server = new ServerSocket(2680);
                    //while (true) {
                        try {
                            System.out.println("开始监听...");
                            Socket socket = server.accept();
                            System.out.println("有链接");
                            BufferedReader inFromClient=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            
                            Thread th=new Thread(new Runnable(){
                            	public void run(){
                            		while(true){
                            		   try{
                                         String fileName=inFromClient.readLine();
                                         if (fileName.equals("esc")) break;
                                         File file = new File(fileName);
                                         FileInputStream fis = null;
                                         fis = new FileInputStream(file);
                                         System.out.println("找到文件");
                                         byte[] sendBytes = null;
                                         sendBytes = new byte[1024];
                                         int length = 0;
                                         DataOutputStream dos = null;
                                         dos = new DataOutputStream(socket.getOutputStream());
                                         while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
                                                dos.write(sendBytes, 0, length);
                                                System.out.println("已开始传输");
                                                //dos.flush();
                                                }
                                         System.out.println("传输成功");
                            		   } catch (Exception e) {
                                       }
                                    }
                                }
                            });
                            
                            th.run();
                            //receiveFile(socket);
                        } catch (Exception e) {
                        }
                    //}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}