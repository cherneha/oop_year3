package company.com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestCryptoSteams {
    public static void main(String args[]) throws FileNotFoundException, IOException{
        FileOutputStream fileOut = new FileOutputStream("/home/stasey/cryptoTest.txt");
        EncryptOutputStream out = new EncryptOutputStream(fileOut);
        byte[] test1Write = {11, 22, 33, 44, 98, 97, 96, 95, 94};
        out.write(test1Write);

        FileInputStream fileInput = new FileInputStream("/home/stasey/cryptoTest.txt");
        DecryptInputStream inp = new DecryptInputStream(fileInput);
        byte[] test1Read = new byte[9];
        while(inp.read(test1Read) != -1){}
        for(int i = 0; i < test1Write.length; i++){
            System.out.println(test1Read[i]);
        }

    }

}
