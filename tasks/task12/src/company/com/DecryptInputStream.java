package company.com;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DecryptInputStream extends FilterInputStream {
    public DecryptInputStream(InputStream inp){
        super(inp);
    }
    byte xorKey = (byte)98;
    @Override
    public int read() throws IOException{
        int r = super.read();
        return r ^ this.xorKey;
    }
    @Override
    public int read(byte[] b) throws IOException{
        int num = super.read(b);
        for(int i = 0; i < b.length; i++){
            b[i] = (byte)((int)b[i] ^ xorKey);
        }
        return num;
    }
}
