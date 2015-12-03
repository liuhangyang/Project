#include "Ticket.h"
#include "EntityKey.h"

#include "../Common/List.h"
#include "../Service/Schedule.h"
#include "../Service/Play.h"
#include "../Persistence/Ticket_Persist.h"
#include "../Persistence/Play_Persist.h"
#include "../Persistence/Schedule_Persist.h"
#include "../Service/Seat.h"

#include <stdio.h>
#include <stdlib.h>

int Ticket_Srv_AddBatch(int schedule_id, int studio_id){
	int rtn,i,key;
	ticket_node_t *p;
	seat_node_t  *q;
	ticket_list_t ticket_list;
	seat_list_t   seat_list;
	
	schedule_t scd;
	play_t pld;
	
	Schedule_Srv_FetchByID(schedule_id, &scd);
	Play_Srv_FetchByID( scd.play_id,  &pld);
	
	
	//Seat_Number_Count(studio_id);
	 List_Init(ticket_list, ticket_node_t);
	 
	 List_Init(seat_list,seat_node_t);
	 
	 rtn=Seat_Srv_FetchValidByRoomID(seat_list, studio_id);
	 key=EntKey_Srv_CompNewKeys("ticket",rtn);
	 //Seat_Srv_FetchByRoomID(seat_list,studio_id);
	  
	
	 q=seat_list->next;
	for(i=1;i<=rtn;i++)
	{
	   
	   p=(ticket_node_t *)malloc(sizeof(ticket_node_t));
	   p->data.id=key;
	   p->data.schedule_id=schedule_id;
	  	p->data.seat_id=q->data.id;
	   	p->data.price=pld.price;
	   	p->data.status=TICKET_AVL;
	   	List_AddTail(ticket_list,p);
	   	key++;
	   	q=q->next;
	   
	 }
	 
	
	   Ticket_Perst_Insert(ticket_list);
       	   return 1;
}

int Ticket_Srv_DeleteBatch(int schedule_id) {
	
       return Ticket_Perst_Delete(schedule_id);
}

int Ticket_Srv_Modify(const ticket_t * data){
	// �벹������
       return Ticket_Perst_Update(data);
}

int Ticket_Srv_FetchByID(int ID, ticket_t *buf) {
	// �벹������
       return Ticket_Perst_SelectByID(ID,buf);
}


//������λID��list���Ҷ�ӦƱ
inline ticket_node_t * Ticket_Srv_FindBySeatID(ticket_list_t list, int seat_id){
	// �벹������
	ticket_node_t *p;
	
	 List_ForEach(list,p)
	 {
	 	if(p->data.seat_id == seat_id)
	 	{
	 	
	 		return p;
	 	 }
	 }
	 
       return NULL;
}


//���ݼƻ�ID��ȡ�����ݳ�Ʊ
int Ticket_Srv_FetchBySchID(ticket_list_t list, int schedule_id) {

       // �벹������
       return Ticket_Perst_SelectBySchID(list,schedule_id);

}

//�����ݳ��ƻ�ID��ͳ�������ʼ�Ʊ��������Ʊ������
int Ticket_Srv_StatRevBySchID(int schedule_id, int *soldCount, int *totalCount){
	// �벹������
	
       return 1;
}


