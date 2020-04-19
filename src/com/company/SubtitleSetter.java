package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class SubtitleSetter {

    public static HashMap<String,ArrayList<String>> webSites = null;
    public static String choosenSite;
    public SubtitleSetter(){
        if(webSites == null){
            webSites = new HashMap<String,ArrayList<String>>();
            File file = new File("Subtitle sites.txt");
            try {
                FileInputStream inputStream = new FileInputStream(file);
                Scanner sc = new Scanner(inputStream, "UTF-8");
                String line = sc.nextLine();
                String[] temp = line.split(",");
                ArrayList<String>condition = new ArrayList<String>();
                for (int i=1;i<temp.length;i++)
                    condition.add(temp[i]);
                choosenSite = temp[0];
                webSites.put(temp[0],condition);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public static void findAllSubtitlesInFolder(String path){
        ArrayList<File> files = new ArrayList<File>();
        files = FileFinder.getMovies(new File(path),files);
        for (int i=0;i<files.size();i++){
            String url = UrlManager.SearchGoogle(choosenSite,Sorting.findName(files.get(i))+ " "+ Sorting.getYear(files.get(i).getName()));
            System.out.println(url);
            if(url != null){
                String target=null;
                Elements elements = UrlManager.search(url,webSites.get(choosenSite));
                ArrayList<String> rules = webSites.get(choosenSite);
                String rule = rules.get(rules.size()-1);
                String condition = rule;
                if(rules.get(rules.size()-1).startsWith("class")){
                    condition = "class";
                    rule = rule.substring(rule.indexOf("class=")).replaceAll("class=","");
                }
                for (int j=0;j<elements.size();j++){
                    Element element = elements.get(j);
                    if(condition.equals("class")){
                        if(element.className().equals(rule)){
                            target = element.attr("href");
                            break;
                        }
                    }
                }

                if(target != null && !target.equals("")){
                    if(!target.startsWith("https://www."+choosenSite)){
                        target = "https://www."+choosenSite + target;
                    }
                    FileDownloader.downloadFile(target,files.get(i).getParentFile().getAbsolutePath());
                }
            }
        }
    }


}
