package tcp;
import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        try {
			final ServerSocket server = new ServerSocket(2680);
                    //while (true) {
                        try {
                            System.out.println("��ʼ����...");
                            Socket socket = server.accept();
                            System.out.println("������");
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
                                         System.out.println("�ҵ��ļ�");
                                         byte[] sendBytes = null;
                                         sendBytes = new byte[1024];
                                         int length = 0;
                                         DataOutputStream dos = null;
                                         dos = new DataOutputStream(socket.getOutputStream());
                                         while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
                                                dos.write(sendBytes, 0, length);
                                                System.out.println("�ѿ�ʼ����");
                                                //dos.flush();
                                                }
                                         System.out.println("����ɹ�");
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