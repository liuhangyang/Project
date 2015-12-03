/*
 *Author:yang
 *
 *Created on: 2015年 08月 05日 星期三 09:24:01 CST
 *
 *程序名:Service.c
 *
 *
 *程序描述:服务器端的程序
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <errno.h>
#include <netinet/in.h>
#include <pthread.h>
#include <arpa/inet.h>
#define MAXLEN  1024
#define PORT  1995
#define LISTEN  20
int System=-1;
char sbuf[MAXLEN];
int chat=-1;
time_t nowtime;
int t=0;
typedef struct message  //消息结构体
{
	char flag[100];
	char name[100];
	char addressname[100];
	int size;
	char msg[MAXLEN];
}Message;

typedef struct clientinfo         //在线用户结构体
{
	char name[21];
	struct sockaddr_in addr_in;
	int decr;
	pthread_t thid;
}Clientinfo;
typedef Clientinfo  DataType;
typedef struct Node		//在线用户的数据
{
	DataType  data;
	struct Node  *next;
}LNode,*LinkList;	
LinkList clientlink;
LinkList CreateLinkList() 	//初始化链表
{
	LinkList L = (LinkList)malloc(sizeof(LNode));
	L->next = NULL;
	return L;
}


void my_err(const char *err_string,int line)       //错误处理函数
{
	int ft;
	ft=open("error.txt",O_RDWR|O_CREAT|O_APPEND,0666);     //错误日志
	char str[MAXLEN];
	fprintf(stdin,"line:%d",line);
	sprintf(str,"%s",err_string);
	write(ft,str,strlen(str));
	perror(err_string);
	exit(1);
}
char l_getc(){					//单个字符的输入函数，防止%c输入一个字符回车后，换行符对下一个输入行的影响
	char a;
	scanf("%c",&a);
	while(a=='\n') scanf("%c",&a);
	return a;
}
void cl_stdin(){					//清除缓冲区函数
	char ch;
	while((ch=getchar())!='\n'&&ch!=EOF);
}
void deletelist(LinkList L ,DataType e)			//当客户端退出时，删除此客户端的节点
{
	int i = 0;
	LinkList s,p;
	p = L;
	s=p;
	if(p==NULL)
	{
		return;
	}
	while ( (strcmp(p->data.name,e.name) != 0) && p->next != NULL) 
	{
		s=p;
		p = p->next;
	}
	if (p->next == NULL &&   (strcmp(p->data.name,e.name) != 0))
	{
		return;
	}
	else
	{
		s->next = p->next;
		free(p);
	}
}
/*注册检查函数，注册的用户名不能被注册过，也不能是系统的协议*/
int reg_check(Message *recvmsg)		
{
	int fd;
	int read_size,write_size;
	Message comparename;
	if(strcmp(recvmsg->name,"all")==0)
	{
		return -1;
	}
	if(strcmp(recvmsg->name,"look")==0)
	{
		return -1;
	}
	if(strcmp(recvmsg->name,"sermsg")==0)
	{
		return -1;
	}
	if(strcmp(recvmsg->name,"login")==0)
	{
		return -1;
	}
	if(strcmp(recvmsg->name,"trans")==0)
	{
		return -1;
	}
	if(strcmp(recvmsg->name,"online")==0)
	{
		return -1;
	}
	
	if((fd=open("user.txt",O_RDWR | O_CREAT | O_APPEND,0666))<0)    //打开用户文件,每次读一个结构体
	{
			my_err("open",__LINE__);
	}
	do
	{
		if((read_size = read(fd,&comparename,sizeof(comparename)))<0)
		{
				perror("read");
				close(fd);
				return -1;
				
		}
		else
		{
			if(strcmp(recvmsg->name,comparename.name)==0)		//当有相同的用户名时返回-1;
			{
				close(fd);
				return -1;
			}
		}
		if(read_size!=sizeof(struct message) && read_size !=0)	
		 {
				close(fd);
				return -1;
		 }
	 }while(read_size == sizeof(struct message));    //当文件全部读完，仍然没有相同的z则返回0;
	 return 0;
}
int reg_write(Message *recvmsg)				//注册的用户写进用户文件
{
	int fd;
	int read_size,write_size;
	Message MES=*recvmsg;
	memset(&MES.size,0,sizeof(MES.size));
	if((fd=open("user.txt",O_RDWR | O_CREAT | O_APPEND,0666))<0)
	{
			my_err("open",__LINE__);
	}
	  if((write_size = write(fd,recvmsg,sizeof(struct message)))<0)
	   {
			close(fd);
			my_err("write",__LINE__);
	   }
	   while(write_size!=sizeof(struct message))		//当写的长度不等于结构体的长度时，文件指针移动到当前，然后写进去
	   {
			lseek(fd,write_size,SEEK_CUR);
			write_size=write(fd,recvmsg,sizeof(struct message));
	   }
	   printf("写入文件成功!\n");
	   close(fd);
	   return 1;
}
int login_check(Message *recvmsg)		//登录检查函数
{
	int fd;
	Message compare;
	int read_size;
	if((fd = open("user.txt",O_RDONLY))<0)
	{
		my_err("open",__LINE__);
	}
	do
	{
		if((read_size = read(fd,&compare,sizeof(struct message)))<0)	
		{
			perror("read");
			close(fd);
			return -2;;
		}
		if(read_size!=(sizeof(struct message)) && read_size!=0)	//当读到的字节数不是结构体的长度时，返回-2;
		{
			close(fd);
			perror("read");
			return -2;
		}
		if((strcmp(recvmsg->name,compare.name)==0) && (strcmp(recvmsg->msg,compare.msg)==0))     //当用户名和密码都相等时，返回0， 登录成功
		{
			close(fd);
			return 0;
		}
	}while(read_size > 0);
	close(fd);
	return -1;
}
		
		
	

void insertend(LinkList L,DataType e)				//当用户登录时，把其用户信息添加到在线链表中
{
	int i=0;
	LinkList s,p;
	p = L;
	while(p->next != NULL)
	{
		p = p->next;
		i++;
	}
	s = (LinkList)malloc(sizeof(LNode));
	s->data =e;
	s->next = p->next ;
	p->next =s;
}
void DisplayList(LinkList L)			//遍历链表，打印在线用户
{
	L=L->next;
	int i = 1;
	while (L != NULL)
	{
		printf("%d.%s\n",i,L->data.name);
		L = L->next;
		i++;
	}
}
/*发送离线消息，每1秒遍历一遍链表，等待对方上线，当上线时，跳出循环*/
int sendmessage(struct message *m)		
{
	time(&nowtime);
	char name[21];
	int t=0;
	Message  a;
	strcpy(a.flag,m->flag);
	strcpy(a.msg,m->msg);
	strcpy(a.name,m->name);
	memset(a.addressname,0,sizeof(a.addressname));
	strcpy(a.addressname,ctime(&nowtime));
	a.addressname[strlen(a.addressname)]='\0';
	a.size=4;
	LinkList L;
	while(1)
	{
		L=clientlink;
	  	L=L->next;
		while(L!=NULL)
		{
			//printf("%s\n",a.msg);
			if(strcmp(L->data.name,a.flag)==0)
			{
					t=1;
					break;
			}
			L=L->next;
		}
		sleep(1);	//减少服务器资源损耗
		if(t==1)
		{
		  //usleep(20);
		 send(L->data.decr,&a,sizeof(a),0);
		 break;
		}
	}
}	
			
int handleclient (Clientinfo *client)
{
	Clientinfo clientNode = *client;
	int nread;
	Message a;
	int i=0;
	LinkList transNode;
	char buf[MAXLEN],str[MAXLEN];
	while(1)
	{
		nread = recv(clientNode.decr,&a,sizeof(a),0);		//接收客户端的消息
	  if(nread == 0)						//当recv 的返回值为0时，此用户下线了
	  {
		strcpy(a.flag,"sermsg");
		//printf("用户%s下线了!\n",clientNode.name);
		deletelist(clientlink ,clientNode);
		LinkList L;
		L=clientlink;
		L=L->next;
		sprintf(a.msg,"用户%s下线了!\n",clientNode.name);	
		while(L!=NULL)						//当有用户下线时，提醒每一个在线用户
		{
			//printf("heloo\n");
			send(L->data.decr,&a,sizeof(struct message),0);
			L=L->next;
		}
		return;
	  }
	else  if(strcmp(a.flag,"revice")==0)				//处理客户端修改密码
	  {
	  	int i;
	     if(a.size==1)
	     {
	  	i=login_check(&a);		//调用登录检查函数,当函数返回 0，用户名，密码正确，向客户端发送"验证通过"，否则，用户名或密码错误
	  	if(i==0)
	  	{
	  		strcpy(buf,"验证通过!");
	  		send(clientNode.decr,buf,strlen(buf)+1,0);
	  	}
	  	else
	  	{
	  		strcpy(buf,"用户名或密码错误!");
	  		send(clientNode.decr,buf,strlen(buf)+1,0);
	  	}
	    }
	    else   			//用新的用户信息覆盖原来的用户名相对应的用户，
	    {
	    	int retfd;
	    	int fd1;
	    	int read_size;
	    	Message  std;
	    	if((fd1=open("user.txt",O_RDWR,0666))<0)
	        {
			my_err("open",__LINE__);
	        }
	       	lseek(fd1,0,SEEK_SET);	//把文件指针移动到文件的开头
	    	do
	    	{
	    		//printf("tian\n");
	    		if((read_size=read(fd1,&std,sizeof(struct message)))<0)
	    		{
	    			perror("read");
	    			close(fd1);
	    			return -1;
	    		}
	    		if(read_size!=(sizeof(struct message)) && read_size!=0)
			{
			//printf("long\n");
			close(fd1);
			perror("read");
			return -1;
			}
	    		if(strcmp(std.name,a.name)==0)	//当用户名相等时，文件指针已经移动到此结构体的最后，所以用lseek()函数向前移动一个结构体长度，然后覆盖原来的用户信息。
	    		{
	    			lseek(fd1,-sizeof(struct message),SEEK_CUR);
	    		   	write(fd1,&a,sizeof(struct message)); //写入文件后退出
	    		   	break;    
	    		}
	    		
	    	}while(read_size>0);
	    	memset(buf,0,sizeof(buf));
	    	strcpy(a.flag,"revice");
	   	strcpy(buf,"修改成功!");	//向客户端发送"修改成功"；
	  	send(clientNode.decr,buf,strlen(buf)+1,0);
	    	close(fd1);
	    }
	    		
	  continue;
	  	
	  }
	 else if(strcmp(a.flag,"login") == 0)	//用户登录检查，调用login_check(）函数验证成功后向客户端发送"登录成功"，否则发送“登录失败”
	  {
		 int i;
		 i = login_check(&a);	
		if(i == 0)
		{
		 LinkList L;
		L=clientlink;
		L=L->next;
		 strcpy(buf,"登录成功!");
		 strcpy(clientNode.name,a.name);
		 insertend(clientlink,clientNode);
		 send(clientNode.decr,buf,strlen(buf)+1,0);
		 usleep(10);
		 sprintf(a.msg,"用户%s上线了!\n",clientNode.name);
		 strcpy(a.flag,"online");
		 time (&nowtime);
		 memset(sbuf,0,strlen(sbuf));
		 sprintf(sbuf,"%s%s\n",ctime(&nowtime),a.msg);
		 write(System,sbuf,strlen(sbuf));
		 while(L!=NULL)
		 {
			//printf("heloo\n");				//向所有用户发送上线提醒
			if(strcmp(L->data.name,clientNode.name)!=0)
			send(L->data.decr,&a,sizeof(struct message),0);
			L=L->next;
		 }
	      }			
		else
		{
			t++;
			strcpy(buf,"登录失败!");
			send(clientNode.decr,buf,strlen(buf)+1,0);
		}
		if(t==3)
		{
		  return;
		}
	  }
	  else if (strcmp(a.flag,"reg") == 0)		//注册检查函数
	  {
		static int i=2;
		if(strcmp(a.msg,"t")==0)
		{
		 i=reg_check(&a);	//验证用户名是否存在；
		 if(i==0)
	 	 {
			strcpy(buf,"验证通过!");
			strcpy(clientNode.name,a.name);
			send(clientNode.decr,buf,strlen(buf)+1,0);
		 }
		 else if(i==-1)
		 {
		 	strcpy(buf,"此用户名不可用!");
		 	strcpy(clientNode.name,a.name);
		 	send(clientNode.decr,buf,strlen(buf)+1,0);
		  }
		  continue;
		}
	       else if(a.size==1)
	       {
		i=reg_write(&a);
		if(i==1)			//调用reg_write()函数返回1，写入成功，向客户端发送"注册成功"
		{
			memset(buf,0,sizeof(buf));
			strcpy(buf,"注册成功!");
			time (&nowtime);
		 	memset(sbuf,0,strlen(sbuf));
			sprintf(sbuf,"%s用户%s注册成功\n",ctime(&nowtime),a.name);
			write(System,sbuf,strlen(sbuf));
			strcpy(clientNode.name,a.name);
			send(clientNode.decr,buf,strlen(buf)+1,0);
		}
	       }
		continue;
	}
	else if (strcmp(a.flag,"all") == 0)	//处理客户端发来的群聊消息
	{
		if(strcmp(a.msg," ")!=0)
		{
			LinkList L;
			L = clientlink;
			L=L->next;
			time (&nowtime);
		 	memset(sbuf,0,strlen(sbuf));
		 	sprintf(sbuf,"%s%s发来群聊消息:%s\n",ctime(&nowtime),a.name,a.msg);
		 	write(chat,sbuf,strlen(sbuf));
		 	strcpy(a.name,clientNode.name);
			while(L!=NULL)
			{				//遍历链表，除了自己的所有在线用户发送群聊消息
				if(strcmp(L->data.name,clientNode.name)!=0)
				send(L->data.decr,&a,sizeof(struct message),0);
				L=L->next;
			}
		}
		continue;
	}
	else if(strcmp(a.flag,"look")==0)	//处理客户端的查看在线用户
	{
		//printf("look\n");
		LinkList L;
		int i=1;
		L=clientlink;
		L=L->next;
		memset(buf,0,strlen(buf));
		while(L!=NULL)		//遍历在线链表，给客户端发送在线用户的用户名
		{
			memset(str,0,strlen(str));
			strcpy(a.name,L->data.name);
			a.size=i;		
			//strcat(buf,str);
			send(clientNode.decr,&a,sizeof(struct message),0);
			L=L->next;
			i++;
		}
	 }
	 else if((strcmp(a.flag,"trans")==0) &&((strcmp(a.msg,"n")==0)) || (strcmp(a.msg,"y")==0))  //处理接收客户端文件传输
	{ 
			LinkList L;
			L=clientlink;
			L=L->next;
			while(L!=NULL)
			{
			   if(strcmp(L->data.name,a.name)==0)
			   {
			   	time (&nowtime);
		                memset(sbuf,0,strlen(sbuf));
		                if(strcmp(a.msg,"n")==0)	//客户端拒绝接收文件时，给发送方回应加拒绝消息
		                sprintf(sbuf,"%s%s拒绝%s发来的文件\n",ctime(&nowtime),clientNode.name,a.name);
		                else
		                sprintf(sbuf,"%s%s同意接收%s发来的文件\n",ctime(&nowtime),clientNode.name,a.name);
		                write(System,sbuf,strlen(sbuf));
			   	break;
			   }
			    L=L->next;
			 }
			 
			 send(L->data.decr,&a,sizeof(struct message),0);
			// usleep(10);
			 //memset(a.flag,0,sizeof(struct message));
	}					
	else if(strcmp(a.flag,"trans")==0)    //处理发送文件的客户端
	{
		//printf("流\n");
		LinkList L;
		L = clientlink;
		L=L->next;
		while(L!=NULL)
		{
			if (strcmp(L->data.name,a.name) == 0)
			{
			   time (&nowtime);
		           memset(sbuf,0,strlen(sbuf));
		           sprintf(sbuf,"%s%s请求向%s发送文件名为%s的文件\n",ctime(&nowtime),clientNode.name,a.name,a.msg);//写进系统日志
		           write(System,sbuf,strlen(sbuf));
			    break;
			}
			  L=L->next;
		}
		if(L == NULL)		//当发送方所发送的人不存在，或未上线时，提示发送方客户端不存在
		{
			//printf("cndjcndj\n");			
			strcpy(a.msg,"noexist");
			send(clientNode.decr,&a,sizeof(struct message),0);
		}
		else        //当接收方用户在线时，发送请求同意消息
		{
			
			transNode = L;
			strcpy(a.flag,"trans");
			strcpy(a.name,clientNode.name);
			//printf("%s",)
			send(L->data.decr,&a,sizeof(struct message),0);
		}
		continue;
	}
	
	else if(strcmp(a.flag,"transf") == 0)	//转发客户端发来的文件
	{
		//printf("你是");
		send(transNode->data.decr,&a,sizeof(struct message),0);
		//printf("%s\n",transNode->data.name);
	}
	else //处理私聊消息，
	{
			LinkList L;
			L=clientlink;
			L=L->next;
			time (&nowtime);
		        memset(sbuf,0,strlen(sbuf));
		        sprintf(sbuf,"%s%s发来私聊消息%s\n",ctime(&nowtime),a.name,a.msg);
		        write(chat,sbuf,strlen(sbuf));
			strcpy(a.name,clientNode.name);
			while(L != NULL)				//当用户在线时，直接发送个给用户
			{
				if (strcmp(L->data.name,a.flag) == 0)	
				{
					send(L->data.decr,&a,sizeof(struct message),0);
					break;
				}
				L=L->next;
			}
			if(L == NULL)			//当在线链表中没有此用户时，说明用户不在线或不存在
			{
				int ret,b=0;
				int read_size;
				Message db;
				pthread_t pd;
				char pinline[21];
				if((ret=open("user.txt",O_RDONLY))<0)	
				{
					my_err("open",__LINE__);
				}
				do{					//遍历文件，如果有此用户，则创建线程发送离线
				        read_size=read(ret,&db,sizeof(struct message));
					   if(strcmp(db.name,a.flag)==0)
					   {
					   	b=1;
					   	strcpy(a.name,clientNode.name);
					   	pthread_create(&pd,NULL,(void *)&sendmessage,&a);
					   	break;
					   }
				  }while(read_size>0);   
				if(b==0)		//用户不存在，则提示发送方此用户不存在
				{	
				 // printf("long\n");   
				  memset(a.flag,0,sizeof(a.flag));
				  memset(a.msg,0,sizeof(a.msg));
				  strcpy(a.flag,"trans");
				  strcpy(a.msg,"noexist");
				  send(clientNode.decr,&a,sizeof(struct message),0);
				 }
			}	
			continue;
	}
    }
}			
		
		
		
		
void clientaccept(int *servefd)		// 处理客户端的连接
{
	socklen_t client_len;
	int sockfd = *servefd;
	Clientinfo  clientNode;
	struct sockaddr_in client_addr;
	while(1)
	{
		client_len = sizeof(struct sockaddr_in);
		if((clientNode.decr = accept(sockfd,(struct sockaddr*)&client_addr,&client_len))<0)
		{
			my_err("accept",__LINE__);
		}
		else
		{
			time (&nowtime);	
		        memset(sbuf,0,strlen(sbuf));
		        sprintf(sbuf,"%s与%s:%d连接成功\n\n",ctime(&nowtime),inet_ntoa(client_addr.sin_addr),ntohs(client_addr.sin_port));
		        write(System,sbuf,strlen(sbuf));	//把连接消息写进系统日志
			printf("与%s:%d连接建立成功\n",inet_ntoa(client_addr.sin_addr),ntohs(client_addr.sin_port));
			clientNode.addr_in = client_addr;
			pthread_create(&clientNode.thid,NULL,(void *)handleclient,(void *)&clientNode);	//创建线程处理连接的客户端
		}
	}
}
void admin()
{
	char choice;
	char name[10];
	int status;
	if((System=open("system.txt",O_RDWR | O_CREAT | O_APPEND,0666))<0)	//服务器刚运行，打开系统日志
	{
		 	my_err("open",__LINE__);
	}
	if((chat=open("systemchat.txt",O_RDWR | O_CREAT | O_APPEND,0666))<0)
	{
		 	my_err("open",__LINE__);
	}
	while(1)
	{
		//system("clear");
	 printf("1.踢出客户端，2.关闭服务器,3.显示在线客户端\n");		//服务器菜单
	 choice=l_getc();
	 cl_stdin();
	 while(choice < '1' || choice > '3')
	{
		printf("输入有误,请重新输入:\n");
		choice=l_getc();
		cl_stdin();
	}
	//scanf("%s",name);
	if(choice == '1')
	{
		printf("请输入你踢除的用户名:");			//在服务器端可以踢出任何一个在线用户
		scanf("%s",name);
		if(strcmp(name," ") == 0) continue;
		LinkList L,s;
		L = clientlink;
		s=clientlink;
		L=L->next;
		while(L!=NULL)
		{
			if(strcmp(L->data.name,name)==0)
			{
				close(L->data.decr);
				pthread_cancel(L->data.thid);
				s->next = L->next;
				free(L);
				break;
			}
			else
			{	
			   s=L;
			   L = L->next;
			}
			    
		 }
		if(L==NULL)
		{
			printf("该用户不存在或未上线!\n"); // 遍历在线链表
		}
	}
	else if(choice == '2')
	{
		close(System);
		close(chat);
		return;
	}
	else if(choice == '3')
	{
		DisplayList(clientlink);
	}
}

}

int main()
{
	struct sockaddr_in serv_addr;
	struct sockaddr_in client_addr;
	pthread_t pid;
	int sockfd,connectfd,port,nread;
	char buf[MAXLEN];
	char str[MAXLEN];
	int  optival=1;
	int sock_fd;
	//创建 套接字
	if((sock_fd=socket(AF_INET,SOCK_STREAM,0))<0)
	{
		my_err("socket",__LINE__);
	}
	//设置
	clientlink = CreateLinkList();
	clientlink->data.decr = sock_fd;
	if(setsockopt(sock_fd,SOL_SOCKET,SO_REUSEADDR,(void *)&optival,sizeof(int))<0)
	{
		my_err("setsockopt",__LINE__);
	}
	//初始化
	memset(&serv_addr,0,sizeof(struct sockaddr_in));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_port = htons(PORT);
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	//绑定
	if(bind(sock_fd,(struct sockaddr *)&serv_addr,sizeof(struct sockaddr_in))<0)
	{
		my_err("bind",__LINE__);
	}
	//监听
	if(listen(sock_fd,LISTEN)<0)
	{
		my_err("listen",__LINE__);
	}
	printf("服务器运行中....\n");
        pthread_create(&pid,NULL,(void *)clientaccept,(void *)&sock_fd);   //每连接一个客户端，创建一个线程，
	admin();
	return 0;

}	
