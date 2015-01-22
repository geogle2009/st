package com.wj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class EchoClient {
	private String host = "127.0.0.1";
	private int port = 8000;
	private Socket socket ;
	
	public EchoClient() throws Exception {
		socket = new Socket(host, port);
	}
	
	public void talk() throws IOException {
		try {
			BufferedReader reader = SocketUtils.getReader(socket);
			PrintWriter writer = SocketUtils.getWriter(socket);
			// 读取本地控制台的消息
			BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
			String msg = null;
			while ((msg = localReader.readLine()) != null) {
				writer.println(msg);
				writer.flush();
				System.out.println(reader.readLine());
				if ("bye".equals(msg)) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			SocketUtils.close(socket);
		}
	}
	
	public static void main(String[] args) throws Exception{
		new EchoClient().talk();
	}
}

