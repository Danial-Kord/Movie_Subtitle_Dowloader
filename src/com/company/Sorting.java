package com.company;

import javax.swing.*;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Array;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sorting {
    public static ArrayList<String>stringConditions= new ArrayList<String>();
    public static void userInput(String path) {
        buildConditions();
        userInput(path, stringConditions);
    }
    public static void buildConditions(){

        stringConditions.add("x256");
        stringConditions.add("x264");
        stringConditions.add("x255");
        stringConditions.add("x265");
        stringConditions.add("480p");
        stringConditions.add("20");
        stringConditions.add("19");
        stringConditions.add("21");
        stringConditions.add("22");
        stringConditions.add("1080p");
        stringConditions.add("720p");
        stringConditions.add("bluray");
        stringConditions.add("hdrip");
        stringConditions.add("hdcam");
        stringConditions.add("hdtv");
        stringConditions.add("4k");
        stringConditions.add("web");
        stringConditions.add("10bit");
        for (int i=0;i<FileFinder.movieTypes.size();i++)
            stringConditions.add(FileFinder.movieTypes.get(i));
    }
    public static void userInput(String path, ArrayList<String> in) {
        FileFinder.buildNewFolder(path);
        String dirPath = path; // Takes the directory path as the user input
        ArrayList<String> input = in;

        File folder = new File(dirPath);
        if (folder.isDirectory()) {
            File[] fileList = folder.listFiles();

            Arrays.sort(fileList);

            System.out.println("\nTotal number of items present in the directory: " + fileList.length);


            // Lists only files since we have applied file filter
            for (File file : fileList) {
                if(!file.isDirectory())
                if (isMovie(file)) {
                    int year = getYear(file.getName());
                    String temp = findName(file);
                    temp += " " + year;
                    if (temp.endsWith("-1")) {
                        temp = temp.replace("-1","");
                    }
                    while (temp.endsWith(" ")) {
                        temp = temp.substring(0,temp.length()-1);
                    }
                    System.out.println(temp + "..............." + year);
                    File dir = new File(dirPath + "\\" + temp);
                    dir.mkdir();
                    File destination = new File(dir.getAbsolutePath() + "\\" + file.getName());
                    System.out.println(destination.getPath());
                    if (!destination.exists()) {
                        if (file.renameTo(destination)) {
                            file.delete();
                        }
                    } else {
                        System.out.println("ERROR : " + file.getName());
                    }
                }
            }
            /*
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.getName().endsWith(".srt") || file.getName().endsWith(".ass")) {
                    for (File file1 : folder.listFiles()) {
                        if (file1.isDirectory()) {
                            if (checkSameName(file1.getName(), file.getName(),input)) {
                                File dir = new File(file1.getPath());
                                File destination = new File(dir.getAbsolutePath() + "\\" + file.getName());
                                if (file.renameTo(destination)) {
                                    file.delete();
                                }
                            }
                        }
                    }
                }
            }
            */
        }
    }
    private static boolean checkSameName(String name ,String subtitleName,ArrayList<String>input){
        /*
        String year = getYear(subtitleName).replace(" ","");
        subtitleName = subtitleName.substring(0, index(input, subtitleName));
        if(getYear(name).replace(" ","").equals(year) || year.equals("") || getYear(name).equals("")) {
            name = name.substring(0, index(input, name)).replace(" ","");
            subtitleName = subtitleName.replace(".","").replace("-","").replace("_","").replace("(","")
                    .replace(")","").replace(" ","");
            char[] nameArray = name.toLowerCase().toCharArray();
            for (int i=0;i<name.length();i++){
                if(!subtitleName.toLowerCase().contains(""+nameArray[i])) {
                    return false;
                }
            }
            return true;
        }*/
        return false;

    }
    public static boolean isPathExist(String path){
        File file = new File(path);
        return file.exists();
    }
        private static ArrayList<String> input () {
            ArrayList<String> input = new ArrayList<String>();
            boolean flag = true;
            Scanner scanner = new Scanner(System.in);
            System.out.printf("Enter your file filtering : file ends with \n(it build a folder from start name of file and ends with your input):");
            while (flag) {
                input.add(scanner.nextLine());
                System.out.printf("for ending yor input type---> END ");
                if (input.get(input.size() - 1).equals("END"))
                    flag = false;
            }
            return input;
        }
        private static int index (ArrayList < String > in, String file){
            int f = file.length();
            for (int i = 2; i < file.length(); i++) {
                boolean flag = false;
                if (i > 0) {
                    String temp = file.toLowerCase();
                    for (int j = 0; j < in.size(); j++) {
                        if (temp.substring(2, i).endsWith(in.get(j))) {
                            f = i - in.get(j).length();
                            flag = true;
                            break;
                        }
                    }
                }
                if (flag)
                    break;
            }
            if(isSerial(file)){
                for (int i=0;i<f;i++){
                  if(!isSerial(file.substring(i,f))) {
                      return i+1;
                  }
                }
            }
            return f;
        }
        public static int getYear (String in){
            ArrayList<Integer> allNumbers = new ArrayList<Integer>();
            int jump =1;
            boolean check = false;
            for (int i=0;i<in.length();i+=jump){
                jump = 1;
                for (int j=i;j<in.length();j++){
                    if(!isNumber(in.charAt(j))){
                        if(i == j || !check)
                            break;
                        allNumbers.add(Integer.parseInt(in.substring(i,j)));
                        jump = j-i+1;
                        break;
                    }
                    else {
                        check = true;
                    }
                }
            }
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy");
            Date date = new Date(System.currentTimeMillis());

            for (int i=allNumbers.size()-1;i>=0;i--){
                if(allNumbers.get(i) > 1900 && allNumbers.get(i) < Integer.parseInt(formatter.format(date))){
                    return allNumbers.get(i);
                }
            }
            return -1;
        }
        public static boolean isMovie(File file){
                   return FileFinder.movieTypes.contains(file.getName().toLowerCase().substring(file.getName().toLowerCase().lastIndexOf(".")+1));
        }
    public static boolean isPicture(File file){
        if(file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".png")
                || file.getName().toLowerCase().endsWith(".jpeg") ||
                file.getName().toLowerCase().endsWith(".gif") )
            return true;
        return false;
    }

    public static boolean isSerial(String name){

        name = name.toLowerCase().replaceAll("_","");
        return name.matches("(.+)[s](\\d+)[e](\\d+)(.+)");

    }
        public static String findName(File file) {
            if (isMovie(file)) {

                String fn = file.getName().substring(0,file.getName().lastIndexOf("."));
                String temp1 = file.getName().substring(0, index(stringConditions,fn));
                char[] temp2 = temp1.toCharArray();
                String temp = "";
                for (int i = 0; i < temp2.length; i++) {
                    if (i != temp2.length - 1) {//for buildinf=g dir currectly
                        if (temp2[i] == '.' || temp2[i] == '-' || temp2[i] == '_' || temp2[i] == ')' || temp2[i] == '(' || temp2[i] == '*') {
                            temp += ' ';
                        } else {
                            temp += temp2[i];
                        }
                    }
                }
                return temp;
            }
            return null;
        }


        private static boolean isNumber(char in){
            return Character.isDigit(in);
        }
    }
