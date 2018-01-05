package datav.util;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class Util {

	public static String ReadFile(String Path) throws Exception {
//		BufferedReader reader = null;
//		String laststr = "";
//		try {
//			FileInputStream fileInputStream = new FileInputStream(Path);
//			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
//			reader = new BufferedReader(inputStreamReader);
//			String tempString = null;
//			while ((tempString = reader.readLine()) != null) {
//				laststr += tempString;
//			}
//			reader.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (reader != null) {
//				try {
//					reader.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return laststr;
		
        //FileInputStream 用于读取诸如图像数据之类的原始字节流。要读取字符流，请考虑使用 FileReader。 
        FileInputStream inStream=new FileInputStream(Path);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int length=-1;
        while( (length = inStream.read(buffer)) != -1)
        {
            bos.write(buffer,0,length);
            // .write方法 SDK 的解释是 Writes count bytes from the byte array buffer starting at offset index to this stream.
            //  当流关闭以后内容依然存在
        }
        bos.close();
        inStream.close();
        return bos.toString();   
        // 为什么不一次性把buffer得大小取出来呢？为什么还要写入到bos中呢？ return new(buffer,"UTF-8") 不更好么?
        // return new String(bos.toByteArray(),"UTF-8");       
	}
	
	

}
