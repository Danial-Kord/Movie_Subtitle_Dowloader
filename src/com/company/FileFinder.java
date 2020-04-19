package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class FileFinder {
    //    public static String buildNewFolder(String path){
//        Random random = new Random();
//        int a = random.nextInt(2000);
//        return buildNewFolder(path,"Dan20");
//    }
    public static ArrayList<String> movieTypes ;
    public static ArrayList<String> subTypes;
    public FileFinder(){
        movieTypes = new ArrayList<String>();
        subTypes = new ArrayList<String>();
        File file = new File("movie format types.txt");
        System.out.println(file.getAbsolutePath());
        try {
            FileInputStream inputStream = new FileInputStream(file);
            Scanner sc = new Scanner(inputStream, "UTF-8");
                String line = sc.nextLine();
                String[] temp = line.split(",");
                for (int i=0;i<temp.length;i++){
                    movieTypes.add(temp[i]);
                }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File path = new File("subtitle format types.txt");

        try {
            FileInputStream inputStream = new FileInputStream(path);
            Scanner sc = new Scanner(inputStream, "UTF-8");
            String line = sc.nextLine();
            String[] temp = line.split(",");
            for (int i=0;i<temp.length;i++){
                subTypes.add(temp[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static String buildNewFolder(String path){
        File folder = new File(path);
        if(folder.isDirectory())
        {
            ArrayList<File>fileList = new ArrayList<File>();
            fileList = getMovies(folder,fileList);

            System.out.println("\nTotal number of items present in the directory: " + fileList.size() );

            File dir = new File(path);
//            dir.mkdir();
            // Lists only files since we have applied file filter
            for(File file:fileList)
            {
                String fileName = file.getName().toLowerCase();
                if(subTypes.contains(fileName.substring(fileName.lastIndexOf(".")+1)) || movieTypes.contains(fileName.substring(fileName.lastIndexOf(".")+1))) {
                    if(!(dir.getAbsolutePath() + "\\" + file.getName()).equals(file.getAbsolutePath())) {
                        File destination = new File(dir.getAbsolutePath() + "\\" + file.getName());
                        if (file.renameTo(destination)) {
                            file.delete();
                        }
                    }
                }
            }
        }
        //sorting subtitles
        deletDirs(folder);
        return path;
    }
    public static void cutAndPaste(File destination ,File start){
        if (start.renameTo(destination)) {
            start.delete();
            System.out.println("why");
        }
    }
    public static ArrayList<File> getMovies(File dad,ArrayList<File>files){
        File[] fileList = dad.listFiles();
        if(fileList == null)
            return files;
        for (File temp : fileList){
            if(temp.isDirectory()){
                files = getMovies(temp,files);
            }
            else {
                String fileName = temp.getName();
                if(movieTypes.contains(fileName.substring(fileName.lastIndexOf(".")+1)))
                    files.add(temp);
            }
        }
        return files;
    }
    private static void deletDirs(File folder){
        if(folder.isDirectory()) {
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    if (file.delete()){}
                    else{
                        deletDirs(file);
                    }
                    file.delete();
                }
            }
        }
    }
}

