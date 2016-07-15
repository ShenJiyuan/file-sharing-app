package tcp;
import java.io.*;
import java.net.*;

public class TCPClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int length = 0;
        //byte[] sendBytes = null;
        Socket socket = null;
        DataOutputStream dos = null;
        //FileInputStream fis = null;
        boolean flag=true;
        try {
            try {
            	BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
         	    //输入ip,如59.78.29.78
                String hostname=inFromUser.readLine();
                socket = new Socket();
                socket.connect(new InetSocketAddress(hostname, 2680),
                               10 * 1000);
                dos = new DataOutputStream(socket.getOutputStream());
                while(true){
                String filename=inFromUser.readLine();
                dos.writeBytes(filename+"\n");
                if (filename.equals("esc")) break;
                System.out.println("进入接收阶段");
                receiveFile(socket,filename);
                }
            } finally {
                //if (dos != null)
                //    dos.close();
                //if (fis != null)
                //    fis.close();
                //if (socket != null)
                //    socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void receiveFile(Socket socket,String filename) {
        byte[] inputByte = null;
        int length = 0;
        DataInputStream dis = null;
        FileOutputStream fos = null;
        try {
            try {
                dis = new DataInputStream(socket.getInputStream());
                String arr[]=filename.split("\\\\");
                int len=arr.length;
                String copy="D:\\save\\";//savePath
                copy+=arr[len-1];
                fos = new FileOutputStream(new File(copy));
                inputByte = new byte[1024];
                System.out.println("开始接收数据...");
                if ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                    System.out.println(length);
                    fos.write(inputByte, 0, length);
                    //fos.flush();
                }
                System.out.println("完成接收");
            } finally {
                //if (fos != null)
                //    fos.close();	
                //if (dis != null)
                //    dis.close();
                //if (socket != null)
                //    socket.close();
            }
        } catch (Exception e) {
        }
    }

}
