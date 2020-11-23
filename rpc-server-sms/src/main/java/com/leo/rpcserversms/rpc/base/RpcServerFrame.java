package com.leo.rpcserversms.rpc.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName: RpcServerFrame
 * @Description: 服务端rpc框架
 * @Create by: LeoWu
 * @Date: 2020/11/21
 */
@Service
public class RpcServerFrame {

    @Autowired
    private RegisterService registerService;

    /**
     * 启动服务
     * @param serviceName 待启动的服务名
     * @param clazz 待启动的服务具体实现类
     * @param host 服务IP地址
     * @param port 端口号
     */
    public void startService(String serviceName, Class clazz, String host, int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        //绑定端口
        serverSocket.bind(new InetSocketAddress(port));
        System.out.println("RPC server on:" + port + ":运行");
        registerService.registerService(serviceName, clazz);

        try {
            //启用线程实现(客户端的)远程方法调用
            //可用线程池、或NIO 进行优化
            while (true) {
                new Thread(new ServerTask(serverSocket.accept(), registerService)).start();
            }
        } catch (Exception e) {
            //异常业务处理...
        } finally {
            serverSocket.close();
        }
    }

    /**
     * 启用线程实现(客户端的)远程方法调用
     */
    public static class ServerTask implements Runnable {
        //socket连接
        private Socket socket;
        //本地服务注册
        private RegisterService registerService;

        public ServerTask(Socket socket, RegisterService registerService) {
            this.socket = socket;
            this.registerService = registerService;
        }

        @Override
        public void run() {
            try (
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
                //调用的服务名
                String serviceName = inputStream.readUTF();
                //调用方法名
                String methodName = inputStream.readUTF();
                //调用方法参数类型
                Class<?>[] paramTypes = (Class<?>[]) inputStream.readObject();
                //调用方法参数值
                Object[] args = (Object[]) inputStream.readObject();

                //通过服务名获取该服务的Class对象
                Class serviceClass = registerService.getService(serviceName);
                if (serviceClass == null) {
                    throw new ClassNotFoundException(serviceName + "not found");
                }

                //通过反射调用方法
                Method method = serviceClass.getMethod(methodName, paramTypes);
                Object result = method.invoke(serviceClass.newInstance(), args);

                //返回调用结果
                outputStream.writeObject(result);
                outputStream.flush();

            } catch (Exception e) {
                //异常业务处理...
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    //异常业务处理...
                }
            }
        }
    }
}
