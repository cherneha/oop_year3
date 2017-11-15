package company.com;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EncryptOutputStream extends FilterOutputStream {
    byte xorKey = (byte)98;

    public EncryptOutputStream(OutputStream out){
        super(out);
    }
    @Override
    public void write(int b) throws IOException{
        super.write(b ^ this.xorKey);
    }

    @Override
    public void write(byte b[]) throws IOException{
        for(int i = 0; i < b.length; i++){
            b[i] = (byte)((int)b[i] ^ this.xorKey);
        }
        super.write(b);
    }
}
