import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


class pinfo {
	
	public String title;
	public String author;
	public String abstr;
	
	public pinfo(String title, String author,String abstr)
	{
		this.title = title;
		this.author = author;
		this.abstr = abstr;
	}
	
}


public class XlsTransfer {
	
	public static void main(String [] args) throws BiffException, IOException, RowsExceededException, WriteException
	{
		
		File sourcefile = new File("./papers.xls");
		File queryFile = new File("./FullProgram_2014.xls");
		File targetfile = new File("./tmp.xls");
	    InputStream is = new FileInputStream(sourcefile); 
	    jxl.Workbook rwb = Workbook.getWorkbook(is);
	    OutputStream os = new FileOutputStream(targetfile); 
	    jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
	    jxl.write.WritableSheet ws = wwb.createSheet("papers", 0);  
	    Sheet rs1 = rwb.getSheet(0);
	    Sheet rs2 = rwb.getSheet(1);
	    Map<String, String> pinfos = new HashMap<String,String>();
	    File inputFile = new File("./final_result.txt");
	    FileReader fr = new FileReader(inputFile);
	    BufferedReader br = new BufferedReader(fr);
	    String tmp = null;
	    while((tmp = br.readLine())!=null)
	    {
	    	String key = tmp;
	    	String value = br.readLine();
	    	pinfos.put(key, value);
	    }
	    is = new FileInputStream(queryFile);
	    rwb = Workbook.getWorkbook(is);
	    rs1 = rwb.getSheet(1);
	    int count=0;
	    for(int i=62;i<227;i++)
	    {
	    	String title = rs1.getCell(3,i).getContents();
	    	if(!pinfos.containsKey(title))
	    	{
	    		++count;
	    		System.out.println(title);
	    		Label ltitle = new Label(0,i-62,"none");
	    		ws.addCell(ltitle);
	    		
	    	}
	    	else {
	    		Label ltitle = new Label(0,i-62,pinfos.get(title));
	    		ws.addCell(ltitle);
			}
	    }
	    System.out.println(count);
	    /*
	    Map<String,pinfo> pinfos = new HashMap<String, pinfo>();
	    for(int i=1;i<rs1.getRows()-2;i++)
	    {
	    	String title = rs1.getCell(1,i).getContents();
	    	String abstr = rs1.getCell(3,i).getContents();
	    	String author = rs1.getCell(4,i).getContents();
	    	pinfos.put(title, new pinfo(title, author, abstr));
	    }
	    for(int i=1;i<rs2.getRows()-1;i++)
	    {
	    	String title = rs2.getCell(1,i).getContents();
	    	String abstr = rs2.getCell(3,i).getContents();
	    	String author = rs2.getCell(4,i).getContents();
	    	pinfos.put(title, new pinfo(title, author, abstr));
	    }
	    is = new FileInputStream(queryFile);
	    rwb = Workbook.getWorkbook(is);
	    rs1 = rwb.getSheet(1);
	    for(int i=94;i<259;i++)
	    {
	    	String title = rs1.getCell(3,i).getContents();
	    	if(pinfos.containsKey(title))
	    	{
	    		Label ltitle = new Label(0,i-94,pinfos.get(title).title);
		    	Label lauthors = new Label(1,i-94,pinfos.get(title).author);
		    	Label labstr = new Label(2,i-94,pinfos.get(title).abstr);
		    	ws.addCell(ltitle);
		    	ws.addCell(labstr);
		    	ws.addCell(lauthors);
	    	}
	    }
	    */
	    /*
	    for(int i=0;i<rs.getRows();i++)
	    {
	    	
	    	String id = rs.getCell(0, i).getContents();
	    	String title = rs.getCell(1,i).getContents();
	    	String abstr = rs.getCell(2,i).getContents();
	    	String authors = rs.getCell(3,i).getContents();
	    	Label lid = new Label(0, i, id);
	    	Label ltitle = new Label(2,i,title);
	    	Label labstr = new Label(4,i,abstr);
	    	Label llink = new Label(1,i,"none");
	    	Label lauthors = new Label(3,i,authors);
	    	ws.addCell(lid);
	    	ws.addCell(ltitle);
	    	ws.addCell(labstr);
	    	ws.addCell(llink);
	    	ws.addCell(lauthors);
	    	
	    }
	    */
		
	    wwb.write(); 
	    wwb.close();
	    os.close();
	    
	}

}
