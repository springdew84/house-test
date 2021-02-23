package com.cassey.house.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 *
 */
public class NioServer {
    public static void main(String[] args) {
        try {
            testSelector();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void testSelector() throws IOException {
        //缓冲区申请空间
        ByteBuffer readBuffer = ByteBuffer.allocate(64);
        ByteBuffer writeBuffer = ByteBuffer.allocate(64);
        writeBuffer.put("hi mike".getBytes());
        writeBuffer.flip();

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8088));
        //注册监听accept事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int nReady = selector.select();
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();

                if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                    ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssChannel.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    readBuffer.clear();
                    int n = sc.read(readBuffer);
                    if (n <= 0) {
                        break;
                    }
                    readBuffer.flip();
                    System.out.println("i received : " + new String(readBuffer.array()));

                    key.interestOps(SelectionKey.OP_WRITE);
                } else if ((key.readyOps() & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    if (key.isWritable()) {
                        writeBuffer.rewind();
                        sc.write(writeBuffer);

                        key.interestOps(SelectionKey.OP_READ);
                    }
                }
            }
        }
    }
}
