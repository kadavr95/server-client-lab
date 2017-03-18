import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by admin on 17.03.2017.
 */
public class MainApacheVersion {
    public static void main(String[] args) throws IOException, InterruptedException {
        CloseableHttpClient clientGlobal = HttpClients.createDefault();
        HttpGet getGlobal = new HttpGet("http://www.mephist.ru/mephist/prepods.nsf/id/7FCF13FA116556AFC3257157001B1EDF");
        try (CloseableHttpResponse respGlobal = clientGlobal.execute(getGlobal)) {
            String htmlGlobal = EntityUtils.toString(respGlobal.getEntity());
            //System.out.print(htmlGlobal);
            //Pattern p = Pattern.compile("http://\\S+");

            Pattern pGlobal = Pattern.compile("a href=/mephist\\\\prepods.nsf/id/(.*?) title=\"");
            Matcher mGlobal = pGlobal.matcher(htmlGlobal);
            while (mGlobal.find()) {
                CloseableHttpClient client = HttpClients.createDefault();
                HttpGet get = new HttpGet("http://www.mephist.ru/mephist/prepods.nsf/id/"+ mGlobal.group(1));
                try (CloseableHttpResponse resp = client.execute(get)) {
                    String html = EntityUtils.toString(resp.getEntity());
                    //System.out.print(html);
                    //Pattern p = Pattern.compile("http://\\S+");

                    Pattern p = Pattern.compile("<title>Преподаватель МИФИ (.*) — MEPHIST.ru — Портал студентов и выпускников МИФИ</title>");
                    Matcher m = p.matcher(html);
                    while (m.find()) {
                        System.out.print(m.group(1) + ", ");
                    }
                    p = Pattern.compile("Характер.*span.*id.*>\\s*(-?\\d\\.?\\d?\\d?)\\s*<.*голос");
                    m = p.matcher(html);
                    while (m.find()) {
                        System.out.print(m.group(1) + ", ");
                    }
                    p = Pattern.compile("Качество.*span.*id.*>\\s*(-?\\d\\.?\\d?\\d?)\\s*<.*голос");
                    m = p.matcher(html);
                    while (m.find()) {
                        System.out.print(m.group(1) + ", ");
                    }
                    p = Pattern.compile("Приём.*span.*id.*>\\s*(-?\\d\\.?\\d?\\d?)\\s*<.*голос");
                    m = p.matcher(html);
                    while (m.find()) {
                        System.out.print(m.group(1) + ", ");
                    }
                    p = Pattern.compile("Характер.*голос.*?(\\d+)");
                    m = p.matcher(html);
                    while (m.find()) {
                        System.out.print(m.group(1) + ", ");
                    }
                    p = Pattern.compile("Качество.*голос.*?(\\d+)");
                    m = p.matcher(html);
                    while (m.find()) {
                        System.out.print(m.group(1) + ", ");
                    }
                    p = Pattern.compile("Приём.*голос.*?(\\d+)");
                    m = p.matcher(html);
                    while (m.find()) {
                        System.out.println(m.group(1));
                    }
                }
                client.close();
               // TimeUnit.SECONDS.sleep(1);
            }
        }

        clientGlobal.close();
}

}
