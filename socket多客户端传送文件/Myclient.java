import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 文件发送客户端主程序
 * 
 * @author Xue_yl
 * 
 */
public class Myclient {

	/**
	 * 程序main方法
	 * 
	 * @param args
	 * @throws IOException
	 */
	String Clientname = null;
	String Serverip = null;
	int Serverport;
	private static Logger logger = Logger.getLogger(Myclient.class); 
	static{  	
    	PropertyConfigurator.configure(System.getProperty("user.dir")+"/client-log4j.properties");  	
    } 
	public Myclient(String cname,String sip,int sport) {
		Clientname = cname;
		Serverip = sip;
		Serverport = sport;
	}

	public void createclient() {
		logger.info(Clientname+" Login!");
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					File file = null;
					try {
						Date dt = new Date(System.currentTimeMillis());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
						String fileName = Clientname + sdf.format(dt)+ ".xml";
						file = new File(fileName);
						String str = "012345678vasdjhklsadfqwiurewopt";
						PrintWriter pw = new PrintWriter(file);
						int len = str.length();
						Random random = new Random();
						int rand = 20 + random.nextInt() % 10;
						for (int i = 0; i < rand; i++) {
							StringBuilder s = new StringBuilder();
							for (int j = 0; j < 1024; j++) {
								s.append(str.charAt((int) (Math.random() * len)));
							}
							pw.println(s.toString());
						}
						pw.close();
						transfile(file, Clientname);
						Thread.sleep(1200);
					} catch (Exception e) {
						// TODO: handle exception
						logger.info("Server exception "
								+ e.getMessage());
					}
				}
			}
		}).start();
	}

	public void transfile(File file, String Clientname) throws IOException {
		int length = 0;
		double sumL = 0;
		byte[] sendBytes = null;
		Socket socket = null;
		DataOutputStream dos = null;
		FileInputStream fis = null;
		boolean bool = false;
		try {																								
			long l = file.length();
			socket = new Socket();
			socket.connect(new InetSocketAddress(Serverip, Serverport));
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeLong(l);
			dos.writeUTF(file.getName());
			fis = new FileInputStream(file);
			sendBytes = new byte[1024];
			logger.info(file.getName()+" begin tranfer!");
			while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
				sumL += length;
				dos.write(sendBytes, 0, length);
				dos.flush();
			}
			
			if (sumL == l) {
				bool = true;
			}
		} catch (Exception e) {
			logger.info(Clientname+" file "+file.getName()+" transfer exception!"+e.getMessage());
			
			bool = false;
		} finally {
			if (dos != null)
				dos.close();
			if (fis != null)
				fis.close();
			if (socket != null)
				socket.close();
			file.delete();
		}
		logger.info(Clientname+file.getName()+(bool ? " transfer success" : " transfer failed"));
		
	}

	public static void main(String[] args) throws IOException {
		Myclient c1 = new Myclient("c1","127.0.0.1", 48123);
		Myclient c2 = new Myclient("c2","127.0.0.1", 48123);
		c1.createclient();
		c2.createclient();
	}
}