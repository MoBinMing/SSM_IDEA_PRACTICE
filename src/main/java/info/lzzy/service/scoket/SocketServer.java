package info.lzzy.service.scoket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import info.lzzy.connstants.ApiConstants;

public class SocketServer {
	private static boolean run=false;
	private static List<Socket> list = new ArrayList<Socket>();
	private static List<Service> listService = new ArrayList<Service>();
	private static ExecutorService executorService;
	private static BufferedReader br;
	private static ServerSocket serverSocket;
	private static final int PORT = 54321;
	private static final int POOL_SIZE = 5;

	public SocketServer() throws IOException {
		run=true;
		
		executorService = Executors.newFixedThreadPool(POOL_SIZE);
		if (serverSocket==null) {
			serverSocket = new ServerSocket(PORT);
		}
		System.out.println(serverSocket.getInetAddress().getHostAddress() + ":服务端就绪。");
		Socket client = null;
		while (run) {// 为每一个连接到服务器的客户端分配一个线程进行消息的接收和发送
			client = serverSocket.accept();
			client.setKeepAlive(true);
			if (run) {
				list.add(client);
				Service service=new Service(client);
				listService.add(service);
				executorService.execute(service);
			}
			
		}
	}
	public static boolean isRuning() {
		return run;
	}
	class Service implements Runnable {
		boolean runs=true;
		Socket client;
		BufferedReader br;
		String msg = "";
	

		public Service(Socket client) {
			this.client = client;
			try {
				br = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
				msg = "用户：" + client.getInetAddress() + "加入了聊天室，当前人数：" + list.size();
				sendMsg();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void run() {
			try {
				while (runs) {
					if ((msg = br.readLine()) != null) {
						//客户端发送bye
						if (msg.equals("bye")) {
							list.remove(this.client);
							br.close();
							msg = "用户：" + client.getInetAddress() + "离开了聊天室，当前人数：" + list.size();
							sendMsg();
							client.close();
							break;
						} else {
							msg = client.getInetAddress() + "说：" + msg;
							sendMsg();
						}
					}
					if(client.getKeepAlive()){
						System.out.println("Client connect to Server is OK");
					}else{
						//client=client.c
						client=new ServerSocket(PORT).accept();//client("127.0.0.1",54321);
					}
					 
					 
					try {
					Thread.sleep(60000);
					} catch (InterruptedException e) {
					e.printStackTrace();
					}
				}
				
			} catch (SocketException e) {
				//stop();
				//e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		}

		public void sendMsg() {// 为每一个用户发送这个消息：msg
			PrintWriter pw;
			System.out.println(msg);
			for (Socket client : list) {
				try {
					pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8")));
					pw.println(msg);
					pw.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		public void stop() {
			runs=false;
			msg = "服务器被管理员停止";
			sendMsg();
			for (Socket socket : list) {
				try {
					socket.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			list.clear();
		}
	}

	
	public static Integer getSockets() {
		return list.size();
	}
	
	public static void stopSocketServer() {
		System.out.println("停止前服务数："+listService.size());
		System.out.println("停止前客户端数："+list.size());
		run=false;
		System.out.println("开始删除服务，数量剩余"+listService.size()+"个");
		Integer i=0;
		Integer ii=0;
		for (Service service : listService) {
			i++;
			service.stop();
			listService.remove(service);
			ii++;
		}
		System.out.println("进入删除循环"+i+"次");
		System.out.println("共删除"+ii+"个服务，剩余"+listService.size()+"个");
		
		try {
			serverSocket.close();
			serverSocket=null;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (!executorService.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
				//把超时时间改成1000，将会执行超时处理
				// 超时的时候向线程池中所有的线程发出中断(interrupted)。
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow();
		}
		br=null;
		executorService=null;
		//Pool.clear(pool);
		//释放内存
		//Pool.destroy(POOL_SIZE);
		
		
		ApiConstants.socketServer=null;
		list.clear();
		listService.clear();
		System.out.println("停止后客户端数："+list.size());
	}
	
	
	
//	public static void main(String[] args) {
//		try {
//			new SocketServer();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
