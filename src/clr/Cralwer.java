package clr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cralwer {

	@SuppressWarnings("resource")
	public static void testPost() throws IOException {
		List<String> nameList = new ArrayList<String>();
		Map<String,Integer> resultMap = new HashMap<String,Integer>();
        try {
        	 File file = new File("input.txt");
        	 BufferedReader reader = null;

      
        	 reader = new BufferedReader(new FileReader(file));
        	 String text = null;
        	 
        	 
        	   while((text=reader.readLine())!=null){
        		   nameList.add(text);
        	   }
        	   
        	   int index=0;
        	   for(String s:nameList){
        		   double proc = 1.0*index/nameList.size();
        		   ++index;
        		   System.out.println(proc*100+"%");
        		   resultMap.put(s, getInt(s));
        	   }
        	   

        	 FileOutputStream fos = null;
        	 BufferedWriter bw = null;

        	     File fileo = new File("result.txt");
        	     fos = new FileOutputStream(fileo);
        	     bw = new BufferedWriter(new OutputStreamWriter(fos));
        	    

               	 for(String s:nameList){
               		 if(resultMap.containsKey(s)){
               			bw.write(s+"\t"+resultMap.get(s)+"\r\n");
               		 }	 
               	 }
        	    bw.close();
        	    fos.close();
	    } catch (MalformedURLException e) {}
	      catch (IOException e) {} catch (InterruptedException e) {
		}
        finally{
        	FileOutputStream fos = null;
       	 	BufferedWriter bw = null;

       	     File fileo = new File("result.txt");
       	     fos = new FileOutputStream(fileo);
       	     bw = new BufferedWriter(new OutputStreamWriter(fos));
       	    

       	 for(String s:nameList){
       		 if(resultMap.containsKey(s)){
       			bw.write(s+"\t"+resultMap.get(s)+"\r\n");
       		 }	 
       	 }
       	    bw.close();
       	    fos.close();
        }
        
	}
	static int cnt=0;
	public static int getInt(String st) throws InterruptedException, IOException{
		int result = 0;
		try {
			URL url = new URL("http://www.google.com/search?q=\""+st.replace(' ', '+')+"\"+coach");
            URLConnection conn;

			conn = url.openConnection();

			 conn.setRequestProperty("User-Agent",
                     "Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.6) Gecko/20070723 Iceweasel/2.0.0.6 (Debian-2.0.0.6-0etch1)");
           BufferedReader in = new BufferedReader(
                   new InputStreamReader(conn.getInputStream())
           );
           String str;

           while ((str = in.readLine()) != null) {
                   if(str.contains("<div class=\"sd\" id=\"resultStats\">")){
                   	int index = str.indexOf("<div class=\"sd\" id=\"resultStats\">");
                   	String w = str.substring(index+39);
                   	result = parseInt(w.substring(0, w.indexOf("results")-1));
                   	break;
                   }
           }
           if(result==0){
        	   cnt++;
        	   if(cnt==3){
        		   System.out.println("Google Closed Connection");
        		   throw new IOException();
        	   }
        	   
        	   Thread.sleep(5000);
        	   return getInt(st);
           }
           in.close();
		}catch (IOException e) {
	           if(result==0){
	        	   cnt++;
	        	   if(cnt==3){
	        		   System.out.println("Google Closed Connection");
	        		   throw new IOException();
	        	   }
	        	   
	        	   Thread.sleep(5000);
	        	   return getInt(st);
	           }else{
	        	   return result;
	           }
   		}

          

		return result;
	}
	
	public static int parseInt(String str){
		str=str.trim();
		int result = 0;
		for(int i=0;i<str.length();i++){
			if(str.charAt(i)>='0'&&str.charAt(i)<='9'){
				result *= 10;
				result+=str.charAt(i)-'0';
			}
		}
		return result;
	}
	
	public static void main(String[] args) throws IOException {

		testPost();

	}

}