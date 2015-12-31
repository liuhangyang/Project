package ystruct.com;

import java.io.IOException;

public class DecodeHuffman extends Thread{
	public static String md5="";
    //定义一个数组变量来存放各个字节数据对应的编码
	public HuffmanCode b[]=new HuffmanCode[256];
    public String path;
    public ProgressBar proBar=new ProgressBar("解压缩");
    public static int rowfileLen;
    public static int fileLen;//input文件长度
    public static int chufileLen;
    public static int a[] =new int[256];//如a[1]表示1出现的次数
    /**
     * 定义构造器,传入path
     * @param path
     */
	public DecodeHuffman(String path){
		this.path=path;
	}
	/**
	 * 写run
	 */
	public void run(){
		Decode();
	}

	  //  定义一个将int 转为8位的String 的方法
	public String changeint(int n){
		int on=n;
		String s="";
		for (int i=0;i<8;i++){
			if ((on % 2)==0){
				s='0'+s;
			}else if(on%2==1){
				s='1'+s;
			}
			on=on/2;
		}
		
		return s;
	}

	
	public int match(String ss){
		//System.out.println("杨龙飞");
		for (int i=0;i<256;i++){
            if(a[i]!=0){
			    if ((b[i].n)==ss.length()&&(b[i].node.equals(ss))) {
               // System.out.println("单词你的");
                return i;
                }
            }
		}
		
		return -1;
	}
	
	
	/*
	 * 定义一个找点合适分开点n 的方法
	 */
	public int search(String s){
		int n;
		String ss="";
		int num=-1;
		for (n=0;n<s.length();n++){
		    ss=ss+s.charAt(n);
		    num=match(ss);
		    if (num>=0){	
		    	break;
		    }
		}
		//System.out.println(num);
		if(n<s.length()){
			return num;
		 }else{
			return -1;
		 }
			 
		
	}
	
//	public  int toUnsigned(int s) {   
//		   return s & 0x0FFFF;   
//		}
	
	
	//定义一个译码的方
	public void Decode(){
		try{
			//定义文件输入流
			java.io.FileInputStream fis=new java.io.FileInputStream(path);
            fileLen=fis.available();
			if(fis.read()!='y'){
	             throw   new  IOException("只能解压以yali结尾的文件");
	        }
	       if  (fis.read()!='a')   {
	             throw   new  IOException( "只能解压以yali结尾的文件" );
	        }
	       if  (fis.read()!='l')   {
	             throw   new  IOException( "只能解压以yali结尾的文件" );
	        }
	       if  (fis.read()!='i')   {
	             throw   new  IOException( "只能解压以yali结尾的文件" );
	        }
			//读源文件的md5值
			String stringmd5="";
			for(int i=0;i<16;i++){
				stringmd5+=changeint(fis.read());
			}
			int lessmd5=fis.read();
			for(int i =0;i<stringmd5.length()-lessmd5;i++){
				md5+=stringmd5.charAt(i);
			}
			//System.out.println(md5);
	       int len=fis.read();
	       String fname="";
	       for (int i=0;i<len;i++){
			 //  System.out.println("yanglongfei");
			   fname=fname+(char)fis.read();
	    	   
	       }
	       System.out.println(fname+"  -n:"+len);
	       //找到点‘.’
	       int dian;
	       for (dian=path.length()-1;dian>=0;dian--){
	    	   if (path.charAt(dian)=='.'){
	    		    break;   
	    	   }
	       }
	       
	       String path2="";
	       //复制'.'以前的
	       for (int i=0;i<=dian-1;i++){
	    	   path2=path2+path.charAt(i);
	       }
            UserFrame.Depath=path2;
	       //复制fname
//	       for (int i=0;i<fname.length();i++){
//	    	   path2=path2+fname.charAt(i);
//	      
	       System.out.println(path2);
	       //读b中的n
            String str="";
            for(int i=0;i<256;i++) {
                str="";
                for (int j = 0; j < 4; j++) {
                    str+=changeint(fis.read());
                }
                 String s1 =str.substring(1,str.length());
                 a[i]=Integer.valueOf(str,2);
            }
            for(int i =0;i<256;i++){
                System.out.println(a[i]);
            }
	       for (int i=0;i<256;i++){
               if(a[i]!=0){
                   HuffmanCode hC = new HuffmanCode();
                   hC.n = fis.read();
                   System.out.println("n:"+hC.n);
                   hC.node = "";
                   b[i] = hC;
               }
	       }
	       System.out.println("各点有初值了！");
	       int i=0;
	       int count=0;
	       String coms="";
	       //读b的数据
	       while (i<256){
               if(a[i]!=0){
                  // System.out.println("mnvckfd");
	    	   if(coms.length()>=b[i].n){
	    		   //先把这b[i].n位给b[i].node
	    		  for (int t=0;t<b[i].n;t++){
	    			  b[i].node=b[i].node+coms.charAt(t);
	    			  
	    		  }
				   //System.out.println("把编码读回b[i]数组");
	    		  System.out.println("b["+i+"]:"+b[i].n+" "+b[i].node);
	    		  
	    		  //把coms已经匹配的的字符去掉
	    		  String coms2="";
	    		  for (int t=b[i].n;t<coms.length();t++){
	    			  coms2=coms2+coms.charAt(t);
                  //    System.out.println("coms2 "+coms2);
	    		  }
	    		  coms="";
	    		  coms=coms2;
	    		  i++;
	    		 
	    	   }else{
	    	        coms=coms+changeint(fis.read());
                   //System.out.println("coms "+coms);
	    	   }
               }else{
                   i++;
               }
	       }
	       
	     //显示进度
			proBar.start();
			//得到文件大小
            chufileLen=fis.available();
            //System.out.println("filelen:"+fileLen);
	       String rowstrlen="",temp="";
            for(int t=0;t<4;t++){
                temp+=changeint(fis.read());
                System.out.println(temp);
            }
            System.out.println(temp);
            System.out.println("yanbdch");
            rowstrlen=temp.substring(1,temp.length());
            rowfileLen=Integer.valueOf(rowstrlen,2);
	       //读正式数据
	       
	     //定义文件输出流
		   java.io.FileOutputStream fos=new java.io.FileOutputStream(path2);
	       
		   //定义一个rint 来存读进来的数
//		   int rint;
		   String rsrg;//存转换成的Sting
		   String compString="";//存要比较的字符串
			int intprogs=0;
		   while(fis.available()>1){
			   
			   float f=((float)fileLen-(float)fis.available())/fileLen;
			   if ((int)((((float)fileLen-(float)fis.available())/fileLen)*100)>intprogs){
				   intprogs=(int)((((float)fileLen-(float)fis.available())/fileLen)*100);
				   proBar.jPM.setValue(intprogs);
				  // System.out.println(intprogs);
				}
			   if(search(compString)>=0){
				   int cint=search(compString);
				   //System.out.println("写入了："+"int:"+cint+" "+changeint(cint)+"="+compString);
				   fos.write(cint);
				   //去掉前面已经找到匹配的函数;
				   String compString2="";
		    	   for(int t=b[cint].n;t<compString.length();t++){
		    		  compString2=compString2+compString.charAt(t);
		    	   }
		    	   compString="";
		    	   compString=compString2;
//		    	   System.out.println(compString+" remain:"+fis.available());
				   
			   }else{
				   compString=compString+changeint(fis.read());
//				   System.out.println("读了   remain:"+fis.available());
			   }
   
		   }
//		  //剩下一个字节存储的是最后一个字节的无效位为几个；
		  int cint=fis.read();
		  String compString2="";
//		  System.out.println("re:"+cint+"S:"+compString);
		  for (int t=0;t<compString.length()-cint;t++){
			 compString2=compString2+compString.charAt(t);
		  }
		  compString=compString2;
//		  System.out.println("还差："+compString);
		   //删掉前.n个数据
		 //while(compString.length()>0){
			while((search(compString))>0){
		   int ccint=search(compString);
		   fos.write(ccint);   
		 //  System.out.println("写入了："+compString);
		   compString2="";
			  for (int t=b[ccint].n;t<compString.length();t++){
				  compString2=compString2+compString.charAt(t);
   	       }
   	       compString="";
   	       compString=compString2;
		  }
		 // System.out.println("解码完毕！");
		  proBar.jPM.setString("解压缩完毕！");
		}catch(Exception ef) {
			ef.printStackTrace();
		}
		//System.out.println(md5);

	}
    
}
