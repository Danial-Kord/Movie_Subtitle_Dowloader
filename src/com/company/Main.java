package com.company;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	new FileFinder();
        Scanner scanner = new Scanner(System.in);
       // System.out.println(Sorting.findName(new File(scanner.nextLine())));

	//Sorting.userInput(scanner.nextLine());

           // FileDownloader.downloadFile("https://www.subtitlepedia.biz/download/finish/15/19972","dd.zip");
        Sorting.buildConditions();
        SubtitleSetter s = new SubtitleSetter();

        SubtitleSetter.findAllSubtitlesInFolder(scanner.nextLine());


    }
}
