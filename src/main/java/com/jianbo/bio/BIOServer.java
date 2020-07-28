package com.jianbo.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class BIOServer {

    public static void main(String[] args) throws Exception{

        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务器启动...");
        while (true){
            Socket accept = serverSocket.accept();
            System.out.println("新客户端连接："+accept.getPort());
            new Thread(() -> {
                try {
                    handler(accept);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static void handler(Socket accept) throws Exception {
        InputStream inputStream = accept.getInputStream();

        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        while (true){
            byte[] b = new byte[1];
            int read = inputStream.read(b);
            byteBuffer.put(b);
            if (read == 0 ) {
                byteBuffer.flip();
                String s = new String(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
                System.out.println("收到客户端数据：" + s);
            }
            if (read == -1){
                System.out.println("客户端失去连接....");
                break;
            }
        }
    }
}
