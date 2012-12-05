/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package templatedetection;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.jsoup.nodes.Element;

/**
 *
 * @author XiaDsh
 */
public class Pagelets {
    private Element pagelet;
    private int shingle;
    public Pagelets(Element p) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        pagelet=p;
        shingle=this.Shingl();
    }
    private int Shingl() throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        String str=pagelet.data();
        //MessageDigest mss=MessageDigest.getInstance("MD5");
        //byte mds[] = str.getBytes("UTF-8");
        //byte dms[]=mss.digest(mds);
        //return dms.toString();
        int hs=str.hashCode();
        return hs;
    }
    public String get()
    {
        return pagelet.html();
    }
    public int getshingle()
    {
        return shingle;
    }
    public boolean comshingle(int sh1)
    {
        if(shingle==sh1)
        {
            return true;
        }
        return false;
    }
}
