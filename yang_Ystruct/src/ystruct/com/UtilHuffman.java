package ystruct.com;
import java.io.File;
import java.io.IOException;

import com.sun.org.apache.bcel.internal.generic.NEW;

//写一个链表的类(头结点为空)
//同时实现初始化Huffman树的功能
public class UtilHuffman extends Thread{
    public UserLineNode<HuffmanNode> head=null;
    public int length=0;
    public String path;
    public ProgressBar proBar=new ProgressBar("压缩");
    public int fileLen;//input文件长度
    public String fname;
    
    //写一个构造器来传入path
    public UtilHuffman(String path,String fname){
    	this.path=path;
    	this.fname=fname;
    }
    
 //   public HuffmanTreeNode<HuffmanNode> root;
    //定义一个数组来表示各个数据
    public static int a[] =new int[256];//如a[1]表示1出现的次数
    public HuffmanCode b[]=new HuffmanCode[256];
    
    
    //定义一个方法，读取数据并记录各个字节数出现频次
    public void readFile() {
        //System.out.println("a[8]"+a[8]);
        try {
            // 创建文件输入流
            java.io.FileInputStream fis = new java.io.FileInputStream(path);

            // 将文件流包装成一个可以写基本数据类型的输出流
            //	java.io.DataInputStream dis = new java.io.DataInputStream(fis);
            while (fis.available() > 0) {
                int i = fis.read();
                //System.out.println("到了->"+i);
                a[i]++;
            }
            uNode();
            //建立各个的HUFFMAN结点
            for (int i = 0; i < 256; i++) {
                if (a[i] != 0) {
                    HuffmanNode huffmanNode = new HuffmanNode(i, a[i]);
                    shuxuIn(huffmanNode);
                }
            }
            HuffmanNode root = utilTree();
            traverSalHuffman(root, "");


        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }
    public void writeFile(String fileName){
        String[] md5= new String[16];
    	try {
    		// 创建文件输入流
			File file = new File(path);
			java.io.FileInputStream fis = new java.io.FileInputStream(path);
			// 创建文件输出流
			java.io.FileOutputStream fos = new java.io.FileOutputStream(path+".yali");
            //写放文件标识信息
            fos.write((int)'y');
            fos.write((int)'a');
            fos.write((int)'l');
            fos.write((int)'i');
            //写入源文件的md5值;
            String s=IsSameFile.getFileMD5(file);
            System.out.println("md5:"+s+"　"+s.length());
            md5=IsSameFile.changStringToByte(s);
            int less=0;
            for(int i=0;i<16;i++){
                while(md5[i].length()<8){
                    md5[i]=md5[i]+"0";
                    less++;
                }
                int t=changeString(md5[i]);
                fos.write(t);
            }
          /*  for(int i=0;i<16;i++){
                System.out.println(md5[i]);
            }*/
            fos.write(less);
            //将文件名输出
			fos.write(fileName.length());
            for(int i=0;i<fileName.length();i++){
            	fos.write((int)fileName.charAt(i));
            }
            for(int i=0;i<256;i++){
                fos.write((a[i] >>> 24) & 0xFF);
                fos.write((a[i] >>> 16) & 0xFF);
                fos.write((a[i] >>>  8) & 0xFF);
                fos.write((a[i] >>>  0) & 0xFF);
            }
            for (int i=0;i<256;i++){
                if(a[i]!=0){
                    System.out.println(i);
                    fos.write(b[i].n);
                }

            }
            for(int i=0;i<256;i++){
                if(a[i]!=0){
                System.out.println(b[i].n+"　言 "+b[i].node);
            }
             }
			int count=0;
			int i=0;
			String writes = "";
			String writes2="";//中转字符串
			String writess;
			while((i<256)||(count>=8)){
				
				if (count>=8){
					writess="";//清空要转化的的码
					for (int t=0;t<8;t++){
			        	writess=writess+writes.charAt(t);	
				     }
				
				//将writes前八位删掉
				if (writes.length()>8){
				  writes2="";
				  for(int t=8;t<writes.length();t++){
				     	writes2=writes2+writes.charAt(t);
				     }
				  writes="";
				  writes=writes2;
				  }else{
					  writes="";
				  }
				  count=count-8;
				  int intw=changeString(writess);
		          
				  fos.write(intw);
				// System.out.println("写了->b[" + i + "]:" + intw + "(" + writess + ")");
				}else{
                    if(a[i]!=0){
					    count=count+b[i].n;
					    writes=writes+b[i].node;
					    i++;
                    }else {
                        i++;
                    }
                }
			}
            System.out.println("ncvndjvcnj");
			//把count剩下的写入
			if (count>0){
				writess="";//清空要转化的的码
				for (int t=0;t<8;t++){
					if(t<writes.length()){
						writess=writess+writes.charAt(t);	
					}else{
						writess=writess+'0';
					}
				}
				fos.write(changeString(writess));//写入
				//System.out.println("写入了->"+writess);
			}
			//显示进度
            int u=0,m=0;
			proBar.start();
			//开始进行数据的写入
			fileLen=fis.available();//写文件的长
            //写入原文件的大小;
            fos.write((fileLen >>> 24) & 0xFF);
            fos.write((fileLen >>> 16) & 0xFF);
            fos.write((fileLen >>>  8) & 0xFF);
            fos.write((fileLen >>>  0) & 0xFF);
			count=0;
			writes ="";
			writes2="";
			int idata=fis.read();
			int intprogs=0;
			while ((fis.available()>0)||(count>=8)){
			//	System.out.println(fileLen+"  "+fis.available());
				float f=((float)fileLen-(float)fis.available())/fileLen;
			//	System.out.println("哈哈哈哈哈哈哈哈哈");
			//	System.out.println((int)((float)((fileLen-fis.available())/fileLen)*100));
				if ((int)((((float)fileLen-(float)fis.available())/fileLen)*100)>intprogs){
				   intprogs=(int)((((float)fileLen-(float)fis.available())/fileLen)*100);
				   proBar.jPM.setValue(intprogs);
				  // System.out.println(intprogs);
				}
				
				if (count>=8){
					writess="";//清空要转化的的码
					for (int t=0;t<8;t++){
			        	writess=writess+writes.charAt(t);

				     }
				
				   //将writes前八位删掉
				   if (writes.length()>8){
				     writes2="";
				     for (int t=8;t<writes.length();t++){
				     	writes2=writes2+writes.charAt(t);
				       }
				      writes="";
				     writes=writes2;
				    }else{
					  writes="";
				    }
				  count=count-8;
				  int intw=changeString(writess);
		         // System.out.println("编码写了->"+intw+"("+writess+")");
                    u++;
				  fos.write(intw);
				}else{
					
					count=count+b[idata].n;
					writes=writes+b[idata].node;
					idata=fis.read();
                    m++;
                  // System.out.println("idata:"+idata);
				}
			}
            System.out.println("u:"+u+"m:"+m);
			count=count+b[idata].n;
			writes=writes+b[idata].node;
			//把count剩下的写入
			 int endsint=0;
			 if (count>0){
				writess="";//清空要转化的的码
				for (int t=0;t<8;t++){
					if (t<writes.length()){
					writess=writess+writes.charAt(t);	
				}else{
					writess=writess+'0';
					endsint++;
					}
				}
				fos.write(changeString(writess));//写入
				//System.out.println("写入了->"+writess+"int:"+endsint);
			 }
			 //写一个n，表示前一个字节中有n个位是无用的
			fos.write(endsint);
            System.out.println("");
            proBar.jPM.setString("压缩完毕！");
			System.out.println("压缩完毕！");  
    	} catch (Exception ef) {
			ef.printStackTrace();
		}
    }

    public static  String[] changStringToByte(String s){
        String[] str =new String[4];
        int count =8;
        int begin =0;
        for(int i=0;i<4;i++){
            str[i]=s.substring(begin,count);
            begin=count;
            count+=8;
        }
        return str;
    }
    
    /*
     *util the frist node
     */
    public void uNode(){
        head=new UserLineNode<HuffmanNode>();
    //	current=new UserLineNode<HuffmanNode>();
        head.data=null;
    //	current=head;
    }
    /*
     * add the Node
     */
//	public void addNode(HuffmanNode huffmanNode){
//        UserLineNode<HuffmanNode> aNode =new UserLineNode<HuffmanNode>();
//        aNode.data=huffmanNode;
//		current.next=aNode;
//		current=aNode;
//	}
    
    /*
     * 按大小顺序插入
     */
    public void shuxuIn(HuffmanNode huffmanNode){
        UserLineNode<HuffmanNode> aNode =new UserLineNode<HuffmanNode>();
        aNode.data=huffmanNode;
        UserLineNode<HuffmanNode> now=new UserLineNode<HuffmanNode>();
		//当插入的是第一个节点时;
        if (head.next==null){
            head.next=aNode;
            length++;
        }else{
            now=head.next; //先取第一个节点
            UserLineNode<HuffmanNode> now2=new UserLineNode<HuffmanNode>();
            now2=head;
            while((aNode.data.times>now.data.times)&&(now.next!=null)){
               now2=now;
               now=now.next;
            }
			//插在最后一个节点
            if ((now.next==null)&&(now.data.times<aNode.data.times)){
                now.next=aNode;
            }else{
				//插入的节点在中间时;
               now2.next=aNode;
               aNode.next=now;
             }
            length++;
        }
    }
    /*
     * 定义一个遍历的方法
     */
  /*  public void traverSal(){
        UserLineNode<HuffmanNode> now=new UserLineNode<HuffmanNode>();
        if (head.next==null) {
            System.out.println("为空!");
        }else{
            now=head;
            for (int i=0;i<length;i++){
             now=now.next;
             System.out.println("数据为："+now.data.data+"times="+now.data.times);

            }

        }
    }*/
    //将一个八位的字符串转成一个整数
    public int changeString(String s){
    	return ((int)s.charAt(0)-48)*128+((int)s.charAt(1)-48)*64+((int)s.charAt(2)-48)*32
    	        +((int)s.charAt(3)-48)*16+((int)s.charAt(4)-48)*8+((int)s.charAt(5)-48)*4
    	        +((int)s.charAt(6)-48)*2+((int)s.charAt(7)-48);
    	
    }
    
    /*
     *  creat a methods , util a huffman Tree
     */
     public HuffmanNode utilTree(){
    	HuffmanNode root =(HuffmanNode)head.next.data;
        // 如果只有一个节点时
    	if (head.next.next==null){
    		return root;
    	}else
		//当存在大于两个节点时进入循环
        while ((head.next!=null)&&(head.next.next!=null)){
             
           //得到前两个权值最小的节点l;
           if((((HuffmanNode)head.next.data).times >0)&&(((HuffmanNode)head.next.next.data).times>0)) {
                HuffmanNode h1 = (HuffmanNode) head.next.data;
                HuffmanNode h2 = (HuffmanNode) head.next.next.data;
                //创建新的节点
                HuffmanNode hmNode = new HuffmanNode(h1.data + h2.data, h1.times + h2.times);
                hmNode.lChild = h1;
                hmNode.rChild = h2;
                //更新
                head.next = head.next.next.next;
                //重新排序
                shuxuIn(hmNode);
            }else{
               head.next=head.next.next.next;
           }
        }

    	return (HuffmanNode)head.next.data;
        
     }
    //递归给每个b[i](0<=i<256)；
    public void traverSalHuffman(HuffmanNode root,String s){
       if ((root.lChild==null)&&(root.rChild==null)){
    	    HuffmanCode hc=new HuffmanCode();
    	    hc.node=s;
    	    hc.n=s.length();
        	b[root.data]=hc;
		    System.out.println(b[root.data].node+"  "+b[root.data].n+"  "+root.data+" "+root.times);
        }
    	if (root.lChild!=null){
    	   traverSalHuffman(root.lChild,s+'0');
    	}
    	if (root.rChild!=null){
     	   traverSalHuffman(root.rChild,s+'1');
     	}
    	
    }
    /*
     * 写一个run方法
     * @see java.lang.Thread#run()
     */
     public void run(){
    	readFile();
 		writeFile(fname);
     }
}
