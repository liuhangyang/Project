/*
 *Author:yang
 *
 *
 *Created on:2015年 08月 04日 星期二 11:48:56 CST
 *
 *
 *程序名:Clinent.c
 *
 * 
 */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <pthread.h>
#include <sys/types.h>
#include <fcntl.h>
#include <errno.h>
#include <termios.h>
#include <assert.h>
#define MAXLEN  1024
char recvbuf[MAXLEN];
typedef struct message			//定义消息结构体
{
	char flag[100];
	char name[100];
	char addressname[100];
	int size;
	char msg[MAXLEN];
}Message;
int conn_fd;
int fd=-1;
int savefile_fd=-1;
char filefromname[100];
void my_err(const char *err_string,int line)      //错误处理函数
{
	fprintf(stderr,"line:%d",line);
	perror(err_string);
	exit(1);
}
char l_getc(){ 				//单个字符的输入函数，防止%c输入一个字符回车后，换行符对下一个输入行的影响
	char a;
	scanf("%c",&a);
	while(a=='\n') scanf("%c",&a);
	return a;
}
void cl_stdin(){				//清除缓冲区函数
	char ch;
	while((ch=getchar())!='\n'&&ch!=EOF);
}
int getch(void)						 //输入一个字符不回显；
{
        int c=0;
        struct termios org_opts, new_opts;
        int res=0;
        //-----  store old settings -----------
        res=tcgetattr(STDIN_FILENO, &org_opts);
        assert(res==0);
        //---- set new terminal parms --------
        memcpy(&new_opts, &org_opts, sizeof(new_opts));
        new_opts.c_lflag &= ~(ICANON | ECHO | ECHOE | ECHOK | ECHONL | ECHOPRT | ECHOKE | ICRNL);
        tcsetattr(STDIN_FILENO, TCSANOW, &new_opts);
        c=getchar();
            //------  restore old settings ---------
        res=tcsetattr(STDIN_FILENO, TCSANOW, &org_opts);assert(res==0);
        return c;
}

void getpwd(int maxlen,char *pwd){  			//密码输入函数
	int j=0;
	char c;
	//cl_stdin();
	while(j<maxlen && (c=getch())!='\n')
	{
		if(c!=127)
		{
			    printf("*");
			    pwd[j++]=c;
		}else{
			 if(j>0){
				  j=j-1;
				  printf("\b \b");     //可以删除密码
			        }
		}
	} 
			
	pwd[j]='\0';
	
}
void passwd_lock(char *pwd,int len)    //密码加密函数
{
	int i,j;
	char c;
	for(i=0;i<len+1;i++)
	{
		pwd[i]=pwd[i]+i+8;      //给密码的每个字符依次给aclli码加i++;然后反序生成密码
	}
	for(i=0;i<(len/2);i++)
	{
		c=pwd[i];
		pwd[i]=pwd[len-1-i];
		pwd[len-1-i]=c;
	}
	pwd[len]='\0';
}
/*字符串切割函数，把输入的字符串以c进行分割，把c前面的存入*left所指的字符数组，把c以后的存入*right所指的字符数组中.*/
  		
void cut(char *str,char *left,int n,char *right,int m,char c)   
{
	int i ,k, j;
	for(i=0;i<n;i++)
	{
		if(str[i]==c)
			break;
	}
	if(i==n)
	{
		i=-1;
	}
	else
	{
	    for(k=0;k<i;k++)
	    {
		left[k]=str[k];
	    }
	}
	for(j=i+1;j<m;j++)
	{
		if(str[j]=='\0')
		{
			break;
		}
		right[j-i-1] = str[j];
	}
	left[i]='\0';
	if(j<m)
	{
		right[j-i-1] = '\0';
	}
	else
	{
		right[m] = '\0';
	}
}
/*发送文件,循环从文件中每次读出1000个字节，直到read的返回值=0，说明文件已经全部读完，文件传输完成*/
void sendfile()
{
	Message filedata;
	do
	  {
	  	memset(filedata.msg,0,sizeof(filedata.msg));
		filedata.size=read(savefile_fd,filedata.msg,1000);
		strcpy(filedata.flag,"transf");
		strcpy(filedata.name,filefromname);
		if(filedata.size==0)
		{
			printf("文件传输成功!\n");
		}
		else if (filedata.size > 0)
		{
			
			send(conn_fd,&filedata,sizeof(struct message),0);
			usleep(300000);
		}
		else
     	 	{
			printf("读取文件失败,文件传输中止\n");
			break;
		}
	}while(filedata.size > 0);
	close(savefile_fd);
	savefile_fd=-1;
}
/*处理客户端的消息接收函数 */
void clientrecvmsg(int *connfd)
{
	int conn_fd = *connfd;
	int numread;
	char buf[MAXLEN];
	char str[MAXLEN];
	Message recvmsg;
	time_t nowtime;
	if(( fd = open("chatlog.txt",O_RDWR|O_CREAT|O_APPEND,0666)) < 0)
	{
		my_err("open",__LINE__);
	}
	while(1)
	{
		numread = recv(conn_fd,&recvmsg,sizeof(struct message),0);
		//printf("%s\n",recvmsg.flag);
		//printf("%s\n",recvmsg.addressname);
		if(numread == 0)		//当recv的返回值为0时，说明客户端已经断开
		{
			printf("断开了连接!\n");
			close(fd);
			close(conn_fd);
			exit(0);
		}
		else if(strcmp(recvmsg.flag,"online")==0)     //聊天室好友上线提醒
		{
			time(&nowtime);
			printf("%s系统提示:%s\n",ctime(&nowtime),recvmsg.msg);
		}
			
		else if(strcmp(recvmsg.flag,"all")==0)
		{
			time(&nowtime);
			sprintf(str,"%s%s发给所有人:%s\n",ctime(&nowtime),recvmsg.name,recvmsg.msg);    //群聊消息
		        write(fd,str,strlen(str));
			//close(fd);	\033[35m%d.%s\033[0m
			printf("\033[36m%s\033[0m\n",str);
			
		}
		else if(strcmp(recvmsg.flag,"revice")==0)    //修改密码成功系统提醒
		{
			printf("%s\n",recvmsg.msg);
			strcpy(recvbuf,recvmsg.msg);
		}
				
		else if(strcmp(recvmsg.flag,"sermsg")==0)        //好友下线提醒
		{
			time(&nowtime);
			printf("%s系统提示:%s\n",ctime(&nowtime),recvmsg.msg);
		}
		else if(strcmp(recvmsg.flag,"look")==0)      //查看在线用户
		{
			printf("\033[35m%d.%s\033[0m\n",recvmsg.size,recvmsg.name);
		}
		else if(strcmp(recvmsg.flag,"trans")==0)       //文件传输
		{
			pthread_t thid;
			//printf("你好de\n");
			if(strcmp(recvmsg.msg,"y")==0)	//当接收到对方同意接收的消息后，创建一个线程发文件
			{
				strcpy(filefromname,recvmsg.name);
				pthread_create(&thid,NULL,(void *)sendfile,NULL);
			} 
			else if(strcmp(recvmsg.msg,"n")==0)  //对方拒绝接收文件
			{
				printf("对方拒绝接收文件!\n");
				close(savefile_fd);
				savefile_fd=-1;
			}
			else if(strcmp(recvmsg.msg,"noexist")==0)   //当发文件，发消息，客户端不存在时，会有提醒
			{
				printf("该客户端不存在!\n");
				close(savefile_fd);
				savefile_fd = -1;
			}
			else
			{
				strcpy(filefromname,recvmsg.name);     //当文件传输请求
				printf("接收来自%s,文件名为%s的文件[trans@y/trans@n]\n",recvmsg.name,recvmsg.msg);
				savefile_fd = 0;
			}
		}
		else if(strcmp(recvmsg.flag,"transf")==0)
		{
			int n;
			 n=write(savefile_fd,recvmsg.msg,recvmsg.size);    //接收文件.当write的返回值小于每次发送的最大值时，文件传输结束
			// printf("文件传输成功!\n");
			// printf("cnjdncjnd\n");
			 if(n<1000)
			  {
			  	printf("文件传输成功!\n");
			  	close(savefile_fd);
				savefile_fd = -1;
			  }
			 while(n < recvmsg.size && n > 0)
			 {
				lseek(savefile_fd,n,SEEK_CUR);
				n=write(savefile_fd,recvmsg.msg,recvmsg.size);
				
			  }
			  
			  
		      	continue;
		}
		else//if((recvmsg.size!=0)&&(recvmsg.size!=1))
		{
			//printf("nihao\n");\033[35m%d.%s\033[0m
			char perchatlog[100];
			 int per_fd;
			
			time (&nowtime);
			sprintf(perchatlog,"%s.txt",recvmsg.name);	//聊天记录，文件名为你聊天对象的用户名
			   if(( per_fd = open(perchatlog,O_RDWR|O_CREAT|O_APPEND,0666)) < 0)
		           {	
        		         my_err("open",__LINE__);
         		   }
			   if(recvmsg.size==4)				//处理离线消息，当size==4时，为离线消息标志,
			   { 	
			   	sprintf(str,"%s%s发来的私聊消息:%s\n",recvmsg.addressname,recvmsg.name,recvmsg.msg);
			   	write(per_fd,str,strlen(str));
			   	printf("\033[33m%s\033[0m\n",str);	
			   	
			   }
			   else                    //正常聊天处理
			   {                 
			   sprintf(str,"%s%s发来的私聊消息:%s\n",ctime(&nowtime),recvmsg.name,recvmsg.msg);
			   write(per_fd,str,strlen(str));
			   printf("\033[34m%s\033[0m\n",str);	
			   memset(str,0,strlen(str));
			   }			
		}
	}
}
	
char menu()		//主菜单函数
{
	char choice;
	printf("\t\t\t-----------------------------------------------------------------------------------\n\n");
	printf("\t\t\t*********************************欢迎来到聊天室*************************************\n\n");
	printf("\t\t\t\t\t1.登录聊天室 					2.注册用户   		    \n");
	printf("\t\t\t\t\t3.修改密码					4.退出系统           	    \n");
	printf("\t\t\t请选择:");
	choice=l_getc();
	cl_stdin();
	return choice;
}
/*首先输入要注册的用户名，然后发给服务器，服务器判断用户名是否存在*/
void reg()					//注册函数
{
	int i=1;
	char username[100];
	char passwd1[100];
	char passwd1_t[100];
	char buf[MAXLEN];
	Message  a;
	int len;			
	while(strcmp(buf,"验证通过!")!=0)		//当服务器发来"验证通过"时，说明用户名未注册，可以继续注册
	{
		printf("请输入用户名:");
		scanf("%s",username);
				 
		strcpy(a.name,username);
		strcpy(a.msg,"t");
		strcpy(a.flag,"reg");
		if(send(conn_fd,&a,sizeof(a),0)<0)
		{
			my_err("send",__LINE__);
		}
		if(recv(conn_fd,buf,MAXLEN,0)<0)
		{
			my_err("recv",__LINE__);
		}
		printf("系统提示:%s",buf);
			printf("\n");
				
	}
		memset(buf,0,strlen(buf));
		while(i)
		{
			 cl_stdin();
			printf("请输入密码:");	//注册输入两次密码，两次密码相等时注册成功,密码加密后发送给服务器
			getpwd(20,passwd1);
			printf("\n");
			printf("请再输入密码:");
			getpwd(20,passwd1_t);
			if(strcmp(passwd1,passwd1_t)==0)
			{
				i=0;
				len=strlen(passwd1);
				passwd_lock(passwd1,len);
				memset(a.msg,0,sizeof(a.msg));
				strcpy(a.msg,passwd1);
				a.size=1;
				if(send(conn_fd,&a,sizeof(a),0)<0) 
				{
					my_err("send",__LINE__);
				}
				memset(buf,0,strlen(buf));
				if(recv(conn_fd,buf,MAXLEN,0)<0)
				{
					my_err("recv",__LINE__);
				}
					printf("\n");
					printf("%s\n",buf);
			 }
				
			else
			{
					printf("两次输入的密码不同!\n");
			}
		}
		
}
/*修改密码时，首先输入用户名和密码，发送到服务器判断用户是否存在，若不存在，则返回信息，让客户端重新输入，若存在，则继续，输入两次新密码*/
void midfy()				//修改密码函数
{
	Message  a;	
	int i=3;
	char username[100];
	char passwd[100];
	char passwd1[100];
	int len;
  do
  {
			 		//cl_stdin();
	printf("请输入用户名:");	
	scanf("%s",username);
	cl_stdin();
	printf("请输入原密码:");
	getpwd(20,passwd);
	len=strlen(passwd);
	passwd_lock(passwd,len);
	a.size=1;
	strcpy(a.msg,passwd);
	strcpy(a.name,username);
	strcpy(a.flag,"revice");
	if(send(conn_fd,&a,sizeof(a),0)<0)
	{
		my_err("send",__LINE__);
	}
	if(recv(conn_fd,recvbuf,MAXLEN,0)<0)
	{
		my_err("recv",__LINE__);
	}
		printf("\n");
		printf("%s\n",recvbuf);
		//memset(username,0,strlen(username));
		memset(passwd,0,strlen(passwd));
		memset(passwd1,0,strlen(passwd1));
			 i--;
   }while((i!=0)&&(strcmp(recvbuf,"验证通过!")!=0));
			 	//sleep(3);
	if(strcmp(recvbuf,"验证通过!")==0)
	{
	      do
	       {
		 memset(recvbuf,0,strlen(recvbuf));
		 a.size=0;
		 memset(passwd,0,strlen(passwd));
		 printf("请输入新密码:");
		 getpwd(20,passwd);
		 printf("\n");
		 printf("请再输入新密码:");
		 getpwd(20,passwd1);
		 len=strlen(passwd);
		 passwd_lock(passwd,len);    //密码加密
	         passwd_lock(passwd1,len);
		if(strcmp(passwd,passwd1)!=0)
		{
			printf("\n");
			printf("两次输入的密码不同!\n");
		}
	      }while(strcmp(passwd,passwd1)!=0);
		memset(a.msg,0,sizeof(a.msg));
		strcpy(a.msg,passwd);
		strcpy(a.name,username);
		strcpy(a.flag,"revice");
		memset(recvbuf,0,strlen(recvbuf));
		if(send(conn_fd,&a,sizeof(a),0)<0)
		{
			my_err("send",__LINE__);
		}
		if(recv(conn_fd,recvbuf,MAXLEN,0)<0)
		{
			 my_err("recv",__LINE__);
		}
			printf("\n");
			printf("%s\n",recvbuf);
	   			
					   
					  
	}
}
	
void tip()			//聊天室提示小帮助
{
	printf("聊天室用法提示:\n");
	printf("all@hi----------------------给所有人发送消息:hi\n");
	printf("yang@hi---------------------给yang发送私聊消息:hi\n");
	printf("look@-----------------------查看在线用户\n");
	printf("trans@yang@yang.txt---------请求给yang传送文件yang.txt\n");
	printf("trans@y/n-------------------是否同意接收文件\n");
	printf("exit@-----------------------退出聊天室\n");
	printf("chatlog@--------------------查看私聊聊天记录\n");
	printf("allchatlog@-----------------查看群聊聊天记录\n");
}

void look_perlog()	//查看私聊消息的函数
{
	char log[100];
	char str[MAXLEN];
	int llog;
	int read_size=1;
	char buf[MAXLEN];
	Message a;
	strcpy(buf,"all");
	printf("请输入用户名:");    
	scanf("%s",log);
	sprintf(str,"%s.txt",log);  //打开的是你要查看的用户的聊天记录
	if((llog=open(str,O_RDONLY))<0)
	{
		printf("没有与此用户相关的聊天记录!\n");
		   strcpy(a.flag,buf);
	}
	else
        {
		memset(str,0,sizeof(str));
	      while(read_size>0)
	      {
		read_size=read(llog,str,1000);
		if(read_size<1000)
		{
			str[strlen(str)+1]='\0';
			printf("%s",str);
			break;
		}
			printf("%s",str);
			memset(str,0,sizeof(str));
	     }
			strcpy(a.flag,buf);
			close(llog);
	}

}	
//查看群聊记录，输入命令直接打开文件读出显示在终端上
void  look_alllog()
{
	
	char str[MAXLEN];
	int read_size=1;
	int df;
	Message  a;
	char buf[MAXLEN];
	strcpy(buf,"all");
	if((df=open("chatlog.txt",O_RDONLY))<0)
	{
		printf("没有群聊消息\n");
		strcpy(a.flag,buf);
	}
	else
	{
		memset(str,0,sizeof(str));
								
		while(read_size>0)
		{
			read_size=read(df,str,1000);
			if(read_size<1000)
			{
				str[strlen(str)+1]='\0';
				printf("%s",str);
				break;
			}
				printf("%s",str);
				memset(str,0,sizeof(str));
										
		}
			strcpy(a.flag,buf);
			close(df);
	}
}
void login()
{
	int n = 3;
	char pername[100];
	char passwd[MAXLEN];
	Message a;
	int len;
	char buf[MAXLEN];
	pthread_t thid;
	char str[MAXLEN];
       while(n)
      {
					
	 printf("请输入用户名:");
	 scanf("%s",a.name);
	 memset(pername,0,sizeof(pername));
	 strcpy(pername,a.name);
	 cl_stdin();
	 printf("请输入密码:");
	 getpwd(MAXLEN,passwd);
	 printf("\n");	
	 len=strlen(passwd);				
	 passwd_lock(passwd,len);  //对密码简单加密
	 strcpy(a.msg,passwd);
	 strcpy(a.flag,"login");
	 if(send(conn_fd,&a,sizeof(a),0)<0)
	 {
		 my_err("send",__LINE__);
	 }
	 printf("正在等待服务器应答.....\n");
	 recv(conn_fd,buf,MAXLEN,0);
	 printf("接收到服务器的消息:%s\n",buf);
	 if(strcmp(buf,"登录成功!")==0)
	 {
	     printf("\033[35m输入help@查看帮助信息!\033[0m\n");
	     pthread_create(&thid,NULL,(void *)clientrecvmsg,(void *)&conn_fd);   //创建线程去接收由服务器发来的消息
						//scanf("%s",str);
	     strcpy(a.flag,"all");
	  while(1)
	  {
	  	memset(buf,0,sizeof(buf));    
		 strcpy(buf,a.flag);     //每次都进行模式切换，也就是说，群聊标志只要输入一次如果不切换，模式不变
		 memset(a.msg,0,strlen(a.msg));   
		 memset(str,0,sizeof(str));
	      while(1)
	     {
		scanf("%s",str);
			//gets(str);
							
	        if(strlen(str)>99)	//定义了消息的最大长度为100，当大于100，清除缓冲区，继续输入，直到小于100才会跳出循环
		{
		   printf("消息最大长度为100\n");
		   cl_stdin();
		    memset(str,0,strlen(str));
	        }
		else
		{
			break;
		}
	    }
		  str[strlen(str)+1]='\0';
		  //printf("yang:%s\n",str);
		  cut(str,a.flag,100,a.msg,MAXLEN,'@');
		  //printf("%s\n",a.flag);
		  //printf("%s\n",a.msg);
		if(strcmp(a.flag,"exit")==0)		//退出客户端
		{
			 close(conn_fd);
			 exit(0);
		}
		else if(strcmp(a.flag,"help")==0)      //查看帮助信息
		{
		 	 tip();
			strcpy(a.flag,buf);
		}
		else if(strcmp(a.flag,"chatlog")==0)
		{
			look_perlog();	
			strcpy(a.flag,buf);	//调用查看私聊记录;	
		}
		else if(strcmp(a.flag,"allchatlog")==0)
		{
			look_alllog();
			strcpy(a.flag,buf);
		}	
		else if(strcmp(a.flag,"look")==0)		//查看在线用户
		{
			if(send(conn_fd,&a,sizeof(struct message),0)<0)
		       {
			my_err("send",__LINE__);
		      }
			strcpy(a.flag,buf);
	       }	
	       else if((strcmp(a.flag,"trans") == 0) && (savefile_fd <=0))   //文件传输;
	       {
			//printf("你的人\n");
			if((strcmp(a.msg,"y")==0) && (savefile_fd==0))   //接收方执行
			{	
				char savefilename[100];
				printf("请输入你要保存的文件名:");
				do
				{
					scanf("%s",savefilename);
					if((savefile_fd=open(savefilename,O_RDWR|O_CREAT|O_EXCL,0666))<0)
					{
							printf("打开文件失败!请重新输入!\n");
					}
				}while(savefile_fd==-1);
				strcpy(a.name,filefromname);
				send(conn_fd,&a,sizeof(a),0);
			}	
			else   			//发送方执行
			{
				memset(a.name,0,strlen(a.name));
				memset(str,0,strlen(str));
				cut(a.msg,a.name,100,str,MAXLEN,'@');
				//printf("%s\n",a.name);
				//printf("%s\n",str);
				if((str[0]!='\0')&& (a.name[0]!='\0'))
				{
					if((savefile_fd=open(str,O_RDONLY,0666))<0)
					{
							savefile_fd = -1;
						printf("打开失败,文件不存在!\n");
												
					}
					else
					{
						memset(a.msg,0,strlen(a.msg));	
						strcpy(a.msg,str);
						//printf("眼\n");
						send(conn_fd,&a,sizeof(a),0);
					}
				}
				else  if(strcmp(a.msg,"n")==0)   //当拒绝接收文件时执行
				{
					//printf("输入有误\n");
										
					memset(a.msg,0,sizeof(a.msg));
					strcpy(a.msg,"n");
					memset(a.name,0,sizeof(a.name));
					strcpy(a.name,filefromname);
					send(conn_fd,&a,sizeof(a),0);
				}
				else 
				{
					printf("输入有误\n");
					cl_stdin();
				}
		}
			strcpy(a.flag,buf);
	   }
							     		
								
							 
	 else 
	 {
		time_t nowtime;
		time (&nowtime);
		if(strcmp(a.flag,"all")==0)     //发送群聊消息
		{
			sprintf(str,"%s你对所有人说:%s\n",ctime(&nowtime),a.msg);  //把自己的群聊消息也存进文件
			write(fd,str,strlen(str));
			memset(str,0,strlen(str));
		}
		else
		{
			char person[100];
			int per;
			sprintf(person,"%s.txt",a.flag);
			if((per=open(person,O_RDWR|O_CREAT|O_APPEND,0666))<0)    //发送私聊消息,把自己的私聊消息也写进以对方为文件名的文件
			{
				my_err("open",__LINE__);
			}
			sprintf(str,"%s你对%s说:%s\n",ctime(&nowtime),a.flag,a.msg);
			write(per,str,strlen(str));
		}
							   		
			memset(a.name,0,sizeof(a.name));
			strcpy(a.name,pername);
			//printf("%s\n",pername);
			if(send(conn_fd,&a,sizeof(a),0)<0)
			{
				my_err("send",__LINE__);
			}
								
		}						   
	 	   }																			
      }
     else
     {
 	n--;
	printf("你还有%d次机会!\n",n);
     }
    if(n==0)
     {
	break;
    }
					
  }
close(conn_fd);
}							
int main(int argc,char *argv[])
{
	struct sockaddr_in client_addr;
	char choice;
	
	Message  a;
	//int conn_fd;
	
	int PORT;
	if(argc!=3)
	{
		printf("Usage:<服务器IP> <端口>\n");
		exit(1);
	}
	PORT=atoi(argv[2]);
	do
	{
		choice=menu();	//调用主菜单函数
		
		if(choice=='4')	
		{
			printf("退出系统!\n");
			exit(1);
		}
									//创建socket
		conn_fd = socket(AF_INET,SOCK_STREAM,0);
		if(conn_fd < 0)
		{
			my_err("socket",__LINE__);
		}	
									//初始化
		bzero(&client_addr,sizeof(struct sockaddr_in));
		client_addr.sin_family = AF_INET;
		client_addr.sin_port=htons(PORT);
		if(inet_aton(argv[1],&client_addr.sin_addr)==0)	
		{
			printf("invalid  server ip address\n");
			my_err("inet_aton",__LINE__);
		}
								//连接服务器
		if(connect(conn_fd,(struct sockaddr *)&client_addr,sizeof(struct sockaddr))<0)
		{
			my_err("connect",__LINE__);
		}	
		switch(choice)
		{
			case '1':
				login();   //调用登录函数
				break;
		        case '2':
				reg();  //调用注册函数
			        break;
		        case '3':
			   midfy();     //调用修改密码函数
			   break;
		       default:
		           printf("输入有误!\n");
			break;
		}
		
	}while(1);
}							 								  
							
																		
