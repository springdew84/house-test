package com.cassey.house.io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

class IoClient {
    public static void main(String[] args) {
        nioTest();
        //bioTest();
    }

    private static void nioTest() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8088));

            ByteBuffer writeBuffer = ByteBuffer.allocate(64);
            ByteBuffer readBuffer = ByteBuffer.allocate(64);

            writeBuffer.put("hello kitty".getBytes());
            writeBuffer.flip();
            writeBuffer.rewind();
            socketChannel.write(writeBuffer);


//            while (true) {
//                writeBuffer.rewind();
//                socketChannel.write(writeBuffer);
//
//                readBuffer.rewind();
//                socketChannel.read(readBuffer);
//                readBuffer.flip();
//                System.out.println("i received : " + new String(readBuffer.array()));
//
//                Thread.sleep(1000);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void bioTest() {
        int BYTE_LENGTH = 1024;
        // 创建 Socket 客户端并尝试连接服务器端
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8088);

            // 发送的消息内容
            final String message = "Hi,Java.";
            // 使用输出流发送消息
            try (OutputStream outputStream = socket.getOutputStream()) {
                // 将数据组装成定长字节数组
                byte[] bytes = new byte[BYTE_LENGTH];
                int idx = 0;
                for (byte b : message.getBytes()) {
                    bytes[idx] = b;
                    idx++;
                }
                // 给服务器端发送 10 次消息
                for (int i = 0; i < 10; i++) {
                    outputStream.write(bytes, 0, BYTE_LENGTH);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}