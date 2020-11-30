package com.leo.rpcclient.client.rpc;

import org.springframework.stereotype.Service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @ClassName: RpcClientFrame
 * @Description: rpc框架客户端代理
 * @Create by: LeoWu
 * @Date: 2020/11/21
 */
@Service
public class RpcClientFrame {
    //服务端IP地址
    private static final String host = "127.0.0.1";
    //端口
    private static final int port = 8888;

    /**
     * 远程服务的代理对象
     * @param serviceInterface 客户端要调用的的服务
     * @param <T>
     * @return
     */
    public static <T> T getRemoteProxyObject( final Class<?> serviceInterface) {
        InetSocketAddress address = new InetSocketAddress(host, port);

        //获取一个代理对象，由这个代理对象通过网络进行实际的服务调用
        return (T)Proxy.newProxyInstance(serviceInterface.getClassLoader(),
                new Class<?>[] {serviceInterface}, new DynProxy(serviceInterface, address));
    }

    /**
     * 动态代理，实现对远程服务的访问
     */
    private static class DynProxy implements InvocationHandler {
        private Class<?> serviceInterface;

        private InetSocketAddress address;

        private DynProxy(Class<?> serviceInterface, InetSocketAddress address) {
            this.serviceInterface = serviceInterface;
            this.address = address;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Socket socket = null;
            ObjectOutputStream outputStream = null;
            ObjectInputStream inputStream = null;

            try {
                socket = new Socket();
                socket.connect(address);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());

                //方法所在的类名接口名
                outputStream.writeUTF(serviceInterface.getName());
                //方法名
                outputStream.writeUTF(method.getName());
                //方法入参类型
                outputStream.writeObject(method.getParameterTypes());
                //方法入参值
                outputStream.writeObject(args);
                outputStream.flush();

                //接收服务器调用返回
                System.out.println(serviceInterface + "远程调用执行成功.");
                return inputStream.readObject();

            } catch (Exception e) {
                //异常业务处理
                e.printStackTrace();
            } finally {
                //关闭资源
                if (socket != null)
                    socket.close();
                if (outputStream != null)
                    outputStream.close();
                if (inputStream != null)
                    inputStream.close();
            }

            return null;
        }
    }
}
