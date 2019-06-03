package lift11;
import java.io.*;  
import java.net.*;  
import java.util.Scanner;
class Thread1 extends Thread{
    int limit;
    boolean bool1=true;
    int lift_position;
    int lift_dest;
    int lift_direction;
    int max=-1;
    public Thread1(int size_val){
      limit=size_val;
      lift_dest=0;
      lift_direction=0;
      lift_position=0;
    }
    
    
    public void run(){ 
     
        while(bool1){   
        if(lift_position==limit){
            lift_direction=2;
        }     
        if(lift_position==0){
            lift_direction=1;
        }
        if(max==-1){
          max=lift_dest;  
        }
        if(lift_dest < max && lift_direction==1){
            lift_dest=max;
        }
        else if(lift_dest > max && lift_direction==2){
            lift_dest=max;
        }    
        if(lift_dest>lift_position){
               
                if(lift_direction==1){
                    lift_position++;
                }    
                else if(lift_direction==2){
                     lift_position--;
                }     
            }  
            
            
            else if (lift_dest<lift_position){
                if(lift_direction==2){
                   lift_position--;
                }   
                else if (lift_direction==1){
                    lift_position++;
                }
            }
          
        try {
            if(lift_position!=0){
              System.out.println("\n lift "+this.getName()+ "is in  "+lift_position+" moving to "+lift_dest);
            }
            Thread.sleep(1000);
            
        } 
        catch (InterruptedException ex) {
                //Logger.getLogger(Thread1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}



public class Lift11 {  
    public static void main(String args[]) throws IOException, InterruptedException{
        
        ServerSocket ss = new ServerSocket(3125); 
        Socket s = ss.accept();  
        DataInputStream dis = new DataInputStream(s.getInputStream()); 
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        int itr,temp1 =-1,temp2=-1,temp3,zero=-1;
        int lift_count;
        int floor_count;
        int user_dest;
        int result = 0;
        
        
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the no. of lifts:\n");
        lift_count = sc.nextInt();
        dos.writeInt(lift_count);//send
        System.out.println("enter the value of the top floors:\n");
        floor_count = sc.nextInt();
         
        
        //no.of threads = no.of lifts 
        Thread1 lift_thread[]=new Thread1[lift_count];
        for(itr = 0; itr < lift_thread.length; itr++){
           lift_thread[itr] = new Thread1(floor_count);       
           lift_thread[itr].start();
           
           
        } 
        
        int choice,user_floor,user_direction,min,max,temp4;
        int ch1=2;
        while(ch1==2)
        {
            choice=dis.readInt();
            
            switch(choice)
            {
                 case 1:
                    for(itr=0;itr<lift_thread.length;itr++){
                      dos.writeInt(lift_thread[itr].lift_position);//send
                      dos.writeInt(lift_thread[itr].lift_direction);//send
                      
                     } 
                    user_floor=dis.readInt();
                    user_direction=dis.readInt();
                    //Thread1.sleep(1000);
                    if((user_floor >= floor_count && user_direction==1 )||(user_floor>floor_count)){
                        result=-5;
                    }    
                    else if(user_floor<=0 && user_direction ==2){
                         result=-7;
                    }
                    else if(user_direction > 3 || user_direction<=0 ){
                        result=-10;
                    }
                    else{  
                    
                        
                      if(user_direction==1){
                        max=0;min=floor_count;temp1=-1;temp2=-1;temp3=-1;zero=-1;temp4 =-1;
                        for(itr=0;itr<lift_count;itr++){
                               if(lift_thread[itr].lift_direction==0){
                             zero=itr+1;
                           }
                            if((lift_thread[itr].lift_direction==1 ) && lift_thread[itr].lift_position <= user_floor){
                                if(max <= lift_thread[itr].lift_position){
                                max=lift_thread[itr].lift_position;
                                temp2=itr+1;
                                }  
                            } 
                          }
                         
                       if(temp2==-1){ 
                          for(itr=0;itr<lift_count;itr++){
                           if(lift_thread[itr].lift_direction==2){
                               if(min >= lift_thread[itr].lift_position){
                                   min = lift_thread[itr].lift_position;
                                   temp3=itr+1;
                               }
                           }
                          }
                       }
                       if(((min+user_floor) > (user_floor)) &&(zero>=0)){
                          temp3=zero;
                       }   
                        max=0;
                       if(temp1==-1 && temp2 == -1 && temp3 == -1){
                         for(itr=0;itr<lift_thread.length;itr++){
                           if(lift_thread[itr].lift_direction==1 && max < lift_thread[itr].lift_position && lift_thread[itr].lift_position > user_floor){
                                  max=lift_thread[itr].lift_position;
                                  temp4=itr+1;
                            }
                         } 
                       }
                    
                    if(temp4!=-1)
                      result=temp4;
                    else if(temp3!=-1)
                      result=temp3;
                    else if(temp2 !=-1)
                       result = temp2;
                    
                    
                    }  
                    else if(user_direction==2)
                    {
                     max=0;min=floor_count;temp1=-1;temp2=-1;temp3=-1;zero=-1;temp4 =-1;
                        for(itr=0;itr<lift_thread.length;itr++){ 
                          if(lift_thread[itr].lift_direction==0){
                             zero=itr+1;
                           }  
                          if((lift_thread[itr].lift_direction==2 ) && lift_thread[itr].lift_position >= user_floor){
                              if(min > lift_thread[itr].lift_position){
                                max=lift_thread[itr].lift_position;
                                temp2=itr+1;
                              }  
                           } 
                         }
                         if(temp2 ==-1){ 
                         for(itr=0;itr<lift_thread.length ;itr++){
                           if(lift_thread[itr].lift_direction==1){
                               if(max <= lift_thread[itr].lift_position){
                                   max = lift_thread[itr].lift_position;
                                   temp3=itr+1;
                               }
                           }
                         }
                       }
                      if(((max - user_floor) < (user_floor)) &&(zero>=0)){
                          temp3=zero;
                          lift_thread[temp3-1].lift_position=user_floor;
                          lift_thread[temp3-1].lift_direction=1;
                       } 
                         
                       min=floor_count;
                       if(temp2 == -1 && temp3 == -1){
                          for(itr=0;itr<lift_thread.length;itr++){
                            if(lift_thread[itr].lift_direction==2 && min > lift_thread[itr].lift_position && lift_thread[itr].lift_position < user_floor){
                                  max=lift_thread[itr].lift_position;
                                  temp4=itr+1;
                            }
                          } 
                       }
                    
                    if(temp4!=-1){
                     result=temp4;
                    }
                    else if(temp3!=-1){
                    result=temp3;
                    }
                    else if(temp2 !=-1){ 
                     result = temp2;
                    }
                        
                    }   
                    else
                    {
                        result=-10;
                    }
                    }
                    dos.writeInt(result);//send
                    if(result!=-10 && result !=-5 && result!=-7)
                    { 
                      user_dest=dis.readInt();
                      
                      if((user_dest<=user_floor && user_direction==1)||(user_dest>=user_floor && user_direction==2))
                       System.out.println("Invalid selection from user");
                      else
                       lift_thread[result-1].lift_dest=user_dest;   
                    }
                    
                    break;
                case 2:
                    ch1 =dis.readInt();
                    for(itr=0;itr<lift_count;itr++){   
                    lift_thread[itr].bool1=false;
                    }
                    break;
                default:
                     System.out.println("invalid operation");
                      break;
            }
        }
        } 
    }  