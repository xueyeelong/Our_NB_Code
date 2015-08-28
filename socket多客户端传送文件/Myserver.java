import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 接收文件服务
 * @author Xue_yl
 *
 */
public class Myserver {
	
	/**
	 * 工程main方法
	 * @param args
	 */
	private static Logger logger = Logger.getLogger(Myserver.class);
	static {
		PropertyConfigurator.configure(System.getProperty("user.dir")
				+ "/server-log4j.properties");
	}
	public static void main(String[] args) {
		logger.info(" Server Started  ...");
		try {
			final ServerSocket server = new ServerSocket(48123);
			Thread th = new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {  
							logger.info(" Waiting for connect  ...");
							Socket socket = server.accept();
							logger.info(socket.getInetAddress()+" client connected");
							receiveFile(socket);
						} catch (Exception e) {
							logger.info( " Server exception "
									+ e.getMessage());
						}
					}
				}
			});
			th.run(); //启动线程运行
		} catch (Exception e) {
			e.printStackTrace();
		}     
	}

	public void run() {
	}

	/**
	 * 接收文件方法
	 * @param socket
	 * @throws IOException
	 */
	public static void receiveFile(Socket socket) throws IOException {
		byte[] inputByte = null;
		int length = 0;
		DataInputStream dis = null;
		FileOutputStream fos = null;
		try {
			try {
				dis = new DataInputStream(socket.getInputStream());
				Long l = dis.readLong();
				String filePath = dis.readUTF();		
				inputByte = new byte[1024];   
				logger.info("Start receive...");
				Long reclength = new Long(0); 
				while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
					reclength+=length;
				}
				if(reclength.equals(l))
					logger.info( " File: " + filePath
							+ " reception is complete!");
				else 
					logger.info("unfinished receive ："+filePath);
			} finally {
				if (fos != null)
					fos.close();
				if (dis != null)
					dis.close();
				if (socket != null)
					socket.close(); 
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}
}
