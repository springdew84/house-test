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
 * 这个例子的关键点：
 *
 *     创建一个ServerSocketChannel，和一个Selector，并且把这个server channel
 *     注册到 selector上，注册的时间指定，这个channel 所感觉兴趣的事件是
 *     SelectionKey.OP_ACCEPT，这个事件代表的是有客户端发起TCP连接请求。
 *     使用 select
 *     方法阻塞住线程，当select 返回的时候，线程被唤醒。
 *     再通过selectedKeys方法得到所有可用channel的集合。
 *
 *     遍历这个集合，如果其中channel 上有连接到达，就接受新的连接，
 *     然后把这个新的连接也注册到selector中去。如果有channel是读，
 *     那就把数据读出来，并且把它感兴趣的事件改成写。
 *
 *     如果是写，就把数据写出去，并且把感兴趣的事件改成读。
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
