package lift11;
import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.IOException; 
import java.net.Socket; 
import java.util.Scanner;   
public class Client 
{ 
    public static void main(String[] args) throws IOException 
    { 
        int port = 3125;
        Scanner sc = new Scanner(System.in);  
        Socket s = new Socket("127.0.0.1", port); 
        DataInputStream dis = new DataInputStream(s.getInputStream()); //for getting from server
        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); //to give to server object
        
        
        boolean ch=true;
        int choice=1;
        int itr,dir,position,client_floor ,movement,result ;
        int No_of_lifts=dis.readInt();
        int dest=05
                ;
        while (ch){ 
            System.out.println("\nenter your choice:\n 1.Display lift positions\n 2.exit");
            choice=sc.nextInt();
            dos.writeInt(choice);//send to server
            
            
            switch(choice ){
                case 1:
                    for(itr=0;itr<No_of_lifts;itr++){
                      position=dis.readInt();//read from server
                      dir=dis.readInt();//read from server
                      if(dir==1)
                      System.out.print(position +" ↑ ");
                      else if(dir==2)
                      System.out.print(position +" ↓ "); 
                      else
                      System.out.print(position +" | ");    
                    }
                    System.out.println("\nEnter user floor:");
                     client_floor=sc.nextInt();
                    System.out.println("Enter user destination direction:");
                     movement=sc.nextInt();
                     
                     dos.writeInt(client_floor);
                     dos.writeInt(movement);
                     result=dis.readInt();
                     if(result==-10){
                       System.out.println("Invalid selection");    
                     }
                     else if(result==-5){
                       System.out.println("cannot go further up");    
                     }
                     else if(result ==-7){
                       System.out.println("cannot go further down");       
                     }
                     else{
                       System.out.println("The nearest lift for user is :"+result);
                       System.out.println("destination:");
                       dest=sc.nextInt();
                       dos.writeInt(dest);
                     }
                     if((dest<=client_floor && movement==1)||(dest>=client_floor && movement==2))
                     System.out.println("Invalid selection and lift doesnot move to the destination");
                   break;
                case 2:
                     ch=false;
                     dos.writeInt(1);
                     break;
                default:
                     System.out.println("System fails enter valid input");
                     break;
            }
            
        }
       }
    } 