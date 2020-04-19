package com.company;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FileDownloader {

    public static void downloadFile(String url1,String outputPath){
        System.out.println(outputPath);
        File file=null;
        try {
            URL url = new URL(url1);
            HttpURLConnection urlConnection;
            if (url.getProtocol().equals("http")) {
                urlConnection = (HttpURLConnection) url.openConnection();
            } else if (url.getProtocol().equals("https")) {
                urlConnection = (HttpsURLConnection) url.openConnection();
            } else return;
//        urlConnection.setConnectTimeout(14000);

            urlConnection.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            long size = (urlConnection.getContentLengthLong());
            System.out.println(size);


            System.out.println(urlConnection.getResponseCode());
            String fileName = "";

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String disposition = urlConnection.getHeaderField("Content-Disposition");
                System.out.println(disposition);

                if (disposition != null) {
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index).replace("filename=","").replaceAll("\"","");
                        System.out.println(fileName);
                    }
                }
            }

//        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(downloadFile))) {
            file = new File(outputPath + "\\" +fileName);
            if(!file.exists())
                try (FileOutputStream out = new FileOutputStream(file, true)) {



                    System.out.println("1");
                    while (urlConnection.getHeaderField("Location") != null) {
                        System.out.println("2");
                        url = new URL(url1);
                        switch (url.getProtocol()) {
                            case "http":
                                urlConnection = (HttpURLConnection) url.openConnection();
                                break;
                            case "https":
                                urlConnection = (HttpsURLConnection) url.openConnection();
                                break;
                            default:
                                return ;
                        }

                        urlConnection.connect();
                    }


                    BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                BufferedInputStream in = new BufferedInputStream(url.openStream());
                    byte[] data = new byte[1024];
                    int count = 0;
                    while ((count = in.read(data, 0, 1024)) != -1) {

                        out.write(data, 0, count);

                    }
                }
        }  catch (FileNotFoundException e1) {
            e1.printStackTrace();
            if(file!=null)
                file.delete();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            if(file!=null)
                file.delete();
        } catch (IOException e1) {
            e1.printStackTrace();
            if(file!=null)
                file.delete();
        }
    }
}
