package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SubtitleSetter {

    public static HashSet<String> webSites = null;
    public SubtitleSetter(){
        if(webSites == null){
            File file = new File("Subtitle sites.txt");
            try {
                FileInputStream inputStream = new FileInputStream(file);
                Scanner sc = new Scanner(inputStream, "UTF-8");
                String line = sc.nextLine();
                String[] temp = line.split(",");
                for (int i=0;i<temp.length;i++){
                    webSites.add(temp[i]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public static void setAllFilesInFolder(String path){
        ArrayList<File> files = new ArrayList<File>();
        files = FileFinder.getMovies(new File(path),files);
        for (int i=0;i<files.size();i++){

        }
    }

    private static void findUrl(String site,String search){
        String google = "https://www.google.com/search?q=";
        //String site = "subtitlepedia.biz";
        search += " Site:"+site;
        search.replaceAll(" ","+");
        String charset = "UTF-8";


        String target = null;

        Elements links = UrlManager.search(google+search).select("div").select("a");
        for (Element link : links) {
            if(link.attr("hrf").startsWith("https://www." + site)){
                target = link.attr("hrf");
            }
        }
        if(target != null){

        }
    }
}
