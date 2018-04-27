import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class App {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		//		long x=(long) (Math.random());
		//
		//		int max=10;
		//        int min=1;
		//        for(int i=min;i<max;i++){
		//
		//        	long sss=Math.round(( Math.random()*8+1)*100);//每个线程随机等待n毫秒
		//
		//        	System.out.println(sss);
		//        }

		//		String msg="dddd,root,aaaa";
		//
		//		int x=msg.indexOf("rootq");
		//
		//		System.out.println(x);

		File file1 = new File("C:\\Users\\liukun\\Desktop\\sys_department1.sql");
		BufferedReader reader1 = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader1 = new BufferedReader(new FileReader(file1));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader1.readLine()) != null) {
				tempString = tempString.substring(tempString.indexOf("(") + 1, tempString.lastIndexOf(")"));
				String[] splits = tempString.split(",");
				// 显示行号
				System.out.println("INSERT INTO `sys_department` (orgcode, orgname, uporg, perorgcode, entorgcode, startus, orgtype, areacode, remark, creator, creattime)"
						+ " VALUES (" + splits[0] + ","+ splits[3] + "," + splits[9] + ","+ splits[1] + ","
						+ splits[2] + ", '0'," + splits[7] + "," + splits[14] + ","
						+ splits[3] + ", '79100010002', TIMESTAMP '2018-04-25 15:21:49');");
			}
			reader1.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader1 != null) {
				try {
					reader1.close();
				} catch (IOException e1) {
				}
			}
		}
		/*StringBuffer msg=new StringBuffer();

		System.out.println(msg.length());

		msg.append("dsfds");
		System.out.println(msg.length());*/

		//		<DataBody>
		//		  <UPORGCODE>
		//		  <QUERYORGNO>
		//		  <QUERYORGNAME>
		//		  <QUERYUSERSYSNAME>
		//		  <QUERYUSERNAME>
		//		  <QUERYTIME>
		//		  <QUERIEDUSERNAME>
		//		  <CERTTYPE>
		//		  <CERTNO>
		//		  <QUERYREASON>
		//		  <QUERYFORMATNAME>
		//		  <ISQUERIED>
		//		  <QUERYAUTHTIME>
		//		  <QUERYCOMPUTERIP>
		//		</DataBody>

		/*		<DataBody>
		  <UPORGCODE>
		  <QUERYORGNO>
		  <QUERYORGNAME>
		  <QUERYUSERSYSNAME>
		  <QUERYUSERNAME>
		  <DEPTNAME>
		  <QUERYTIME>
		  <COMPANYNAME>
		  <ZZCODE>
		  <ISQUERIED>
		  <QUERYAUTHTIME>
		  <QUERYCOMPUTERIP>
		</DataBody>*/


	}

}
