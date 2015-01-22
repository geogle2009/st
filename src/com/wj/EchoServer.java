package com.wj;

import com.wj.SocketUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class EchoServer {

	private int port = 8000;
	private ServerSocket serverSocket;
	private ExecutorService executorService ; // 连接池
	private final int POOL_SIZE = 4;// 连接池大小
	
	public EchoServer() throws Exception{
		serverSocket = new ServerSocket(port);
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
		System.out.println("waitting connet...");
	}
	
	/**
	 * 接受连接
	 * 
	 * @author henushang
	 */
	public void service() {
		Socket socket = null;
		while (true) {
			try {
				socket = serverSocket.accept();
				executorService.execute(new Handler(socket));// 使用连接池
//				new Thread(new Handler(socket)).start();// 不使用连接池
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) throws Exception {
		new EchoServer().service();
	}
	
	/**
	 * 线程类，负责维持与一个客户端的通信 
	 *
	 * @author henushang
	 */
	class Handler implements Runnable{
		private Socket socket = null;
		
		public Handler(Socket socket) {
			this.socket = socket;
		}
		@Override
		public void run() {
			System.out.println("new connection accepted:" + socket.getInetAddress() + ":" + socket.getPort());
			try {
				BufferedReader reader = SocketUtils.getReader(this.socket);
				PrintWriter writer = SocketUtils.getWriter(this.socket);
				String msg = null;
				while ((msg = reader.readLine()) != null) {
					System.out.println(msg);
					// 因为客户端接收消息的时候使用的是readline()方法，如果消息没有以\r\n 结尾的话，客户端则接收不到消息
//					writer.write(SocketUtils.echo(msg) + "\r\n");
					writer.println(SocketUtils.echo(msg));
					writer.flush();
					if ("bye".equals(msg)) {
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				SocketUtils.close(socket);
			}
		}
	}
}
