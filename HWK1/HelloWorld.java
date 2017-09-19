import java.io.*;
import java.util.*;

class Simple{  
    public static void main(String[] args)
    {   
        try 
        {
            String text = "Your sample content to save in a text file. \n";
            BufferedWriter out = new BufferedWriter(new FileWriter("sample.txt"));
            out.write(text);
            text = " EECS592:";
            out.write(text);

            int a = 3;
            text = String.valueOf(a);

            out.write(text + "\n");

            for(int i=1; i<11; i++)
            {
	            text = String.valueOf(i);
	            out.write(text + "\n");
         	}
            out.close();
        }
        catch (IOException e)
        {
            System.out.println("Exception ");       
        }

        return ;
    }
}  