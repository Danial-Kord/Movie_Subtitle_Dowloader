package com.company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class UrlManager
{
    public static String getURLSource(String url1) throws IOException, UnknownHostException
    {

        try {
            java.lang.System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
            URL url = new URL(url1);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
//        httpCon.addRequestProperty("Host", "www.cumhuriyet.com.tr");
//        httpCon.addRequestProperty("DBCoonection", "keep-alive");
//        httpCon.addRequestProperty("Cache-Control", "max-age=0");
//        httpCon.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpCon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
//        httpCon.addRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
//        httpCon.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
//        httpCon.setInstanceFollowRedirects(false);
//        httpCon.setDoOutput(true);
//        httpCon.setUseCaches(true);
//        httpCon.setRequestMethod("GET");
            httpCon.setConnectTimeout(500000);
            httpCon.setReadTimeout(500000);
//        httpCon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            String out = toString(httpCon.getInputStream());
            httpCon.disconnect();
            return out;

        }
        catch (NoRouteToHostException | UnknownHostException e){
            System.out.println("offline");
        }
        return null;
//        BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), "UTF-8"));
//        String inputLine;
//        StringBuilder a = new StringBuilder();
//        while ((inputLine = in.readLine()) != null)
//            a.append(inputLine);
//        in.close();
//
//        httpCon.disconnect();
//        return a.toString();


    }

    public static Elements search(String search,ArrayList<String>condition){
        java.lang.System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

        try {
            Element links = Jsoup.connect(search).timeout(50000).get();
            Elements out = links.getAllElements();
            if(condition.size() != 0) {
                out = links.select(condition.get(0));
                for (int i = 1; i < condition.size()-1; i++) {
                    out = out.select(condition.get(i));
                }
            }
            return out;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Element search(String search){
        java.lang.System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

        try {
            Element links = Jsoup.connect(search).timeout(50000).get();
            return links;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String SearchGoogle(String site,String search){
        String google = "https://www.google.com/search?q=";
        //String site = "subtitlepedia.biz";
        search += " Site:"+site;
        search.replaceAll(" ","+");
        String charset = "UTF-8";


        String target = null;

        Elements links = UrlManager.search(google+search).select("div").select("a");
        for (Element link : links) {
            if(link.attr("href").startsWith("https://www." + site)){
                target = link.attr("href");
            }
        }
        return target;
    }


    private static String toString(InputStream inputStream) throws IOException
    {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")))
        {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }

            return stringBuilder.toString();
        }
    }
}