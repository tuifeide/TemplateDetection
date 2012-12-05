/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package templatedetection;

/**
 *
 * @author XiaDsh
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TemplateDetection {

    static Queue<Element> que=new LinkedList<>();
    static List<Pagelets> pagelet=new ArrayList<>();
    static int keyhy=3;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        // 控制循环读入文本，得出pagelet，进行比较并输出
        String fies[]={"C:\\news\\12.html","C:\\news\\6652.html","C:\\news\\7395.html",
            "C:\\news\\043924983433.shtml","C:\\news\\050524824300.shtml","C:\\news\\101624367405.shtml",
            "C:\\news\\104120675894.shtml","C:\\news\\110923414553.shtml","C:\\news\\133722569080.shtml",
            "C:\\news\\plgz_3.html","C:\\news\\140517393117s.shtml","C:\\news\\143561203357.html","C:\\news\\145824915810.shtml",
            "C:\\news\\160422767665.shtml","C:\\news\\165223866575.shtml","C:\\news\\174224075983.shtml",
            "C:\\news\\gjqt\\index.html","C:\\news\\183324959414.shtml","C:\\news\\205814157282.shtml","C:\\news\\ah.shtml",
            "C:\\news\\bj.shtml","C:\\news\\cq.shtml","C:\\news\\fanmeizhong.html","C:\\news\\fj.shtml",
            "C:\\news\\index_weekly.shtml","C:\\news\\loushi.html","C:\\news\\simen.html","C:\\news\\yuanlongping.html",
            "C:\\news\\zhangmengsu.html","C:\\news\\2008mgdx\\index.html","C:\\news\\2011wpp\\index.html","C:\\news\\2012wpp\\index.html",
            "C:\\news\\china\\index.html","C:\\news\\env\\index.shtml",
            "C:\\news\\lwdfzk\\index.html","C:\\news\\slshzk\\index.html"};//暂时以代码写入的方式
        int fi=fies.length;
        boolean flag;
        String fie;
        for(int i=0;i<fi;i++)
        {
            fie=fies[i];
            File input=new File(fie);
            Document doc=Jsoup.parse(input,"UTF-8",fie);
            Elements contents=doc.getElementsByTag("Html");
            Element root=contents.first();
            if(root!=null){
            que.add(root);
            }
            while(!que.isEmpty())//得出全部pagelet
            {
                flag=true;
                Element v=que.poll();
                Elements childNodes=v.children();
                Element childNode;
                for(int j=0;j<childNodes.size();j++)
                {
                    childNode=childNodes.get(j);
                    Elements links=childNode.select("[href]");
                    if(links.size()>keyhy)
                    {
                        que.addAll(childNodes);
                        flag=false;
                        break;
                    }
                }
                if(flag==true)
                {
                    Pagelets p=new Pagelets(v);
                    pagelet.add(p);
                }
            }
        }
        int j=0;
        int k;
        int wholed;//标志是否有重合
        int pagestr1;
        int pagestr2;
        while(j<pagelet.size())
        {
            wholed=0;
            Pagelets q=pagelet.get(j);
            pagestr1=q.getshingle();
            k=j+1;
            while(k<pagelet.size())
            {
                pagestr2=pagelet.get(k).getshingle();
                if(pagestr1==pagestr2)
                {
                    pagelet.remove(k);
                    wholed=1;
                }
                else
                {
                    k++;
                }
            }
            if(wholed==0)
            {
                pagelet.remove(j);
            }
            else 
            {
                j++;
            }
        }
        if(pagelet.isEmpty())
        {
            System.out.println("Fail!!!");
        }
        else
        {
            Pagelets page;
            int m=0;
            byte buff[];
            int n=0;
            byte[] c={0x0d,0x0a};
            String t=new String(c);
            FileOutputStream output=new FileOutputStream("E:\\TemplateDetectionResults.txt");
            while(m<pagelet.size())
            {
                page=pagelet.get(m);
                String hhh=t+page.get()+t;
                buff=hhh.getBytes();
                output.write(buff,0, buff.length);
                m++;
                n+=buff.length;
            }
        }
        System.out.println(fies.length);
        System.out.println(pagelet.size());
    }
}
