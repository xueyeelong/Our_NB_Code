import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

public class GzipFileCompression{

	public String compression(String fileDir) throws Exception {
		// TODO Auto-generated method stub
		File file = new File(fileDir);
		String desc = file.getAbsolutePath();
		if (file.exists()) {
			desc = file.getParent() + File.separator + file.getName() + ".gz";
			FileInputStream fin = new FileInputStream(fileDir);
			FileOutputStream out = new FileOutputStream(desc);
			GZIPOutputStream gos = new GZIPOutputStream(out);
			int count;
			byte data[] = new byte[1024];
			while ((count = fin.read(data, 0, 1024)) != -1) {
				gos.write(data, 0, count);
			}
			gos.finish();
			gos.flush();
			gos.close();
			fin.close();
		}
		return desc;
	}

	public void decompression(String fileDir) throws Exception {
		// TODO Auto-generated method stub
		File gzipFile = new File(fileDir); 
        File parentDir = gzipFile.getParentFile();
        String filename = gzipFile.getName();
        String desc = parentDir + File.separator +filename.substring(0, filename.lastIndexOf("."));
        System.out.print(desc);
		FileInputStream fin = new FileInputStream(fileDir);
		BufferedInputStream in = new BufferedInputStream(fin);
		FileOutputStream out = new FileOutputStream(desc);
		GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
		final byte[] buffer = new byte[1024];
		int n = 0;
		while (-1 != (n = gzIn.read(buffer))) {
			out.write(buffer, 0, n);
		}
		out.close();
		gzIn.close();

	}


}