import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class Makefile implements Runnable {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Makefile mf = new Makefile();
		Thread demo = new Thread(mf);
		demo.start();		
	}

	public void run() {
		while (true) {
			Date dt = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String fileName ="/home/tianxuanling/send/"+sdf.format(dt)+".xml";
			String str = "012345678vasdjhklsadfqwiurewopt"; 
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(fileName));
				int len = str.length();
			    Random random = new Random();
			    int  rand = 20+random.nextInt()%10;

				for (int i = 0; i < rand; i++)
				{
					StringBuilder s = new StringBuilder();
					for (int j = 0; j < 1024; j++)
					{
						s.append(str.charAt((int)(Math.random()*len)));
					}
					pw.println(s.toString());
				}
				pw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				Thread.sleep(1200);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
