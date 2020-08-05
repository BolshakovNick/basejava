package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println();
        System.out.println();

        FileRecursion(new File("C:\\Users\\Local\\IdeaProjects\\basejava\\src"));

        System.out.println();
        System.out.println();

        FileQueue(new File("C:\\Users\\Local\\IdeaProjects\\basejava\\src"));
    }

    public static void FileRecursion(File dir) {
        if (dir != null) {

            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    if (file.isDirectory()) {
                        FileRecursion(file);
                    } else
                        System.out.println(file.getName());
                }
            } else {
                System.out.println(dir.getName());
            }
        }
    }

    public static void FileQueue(File dir) {
        if (dir != null) {
            Queue<File> files = new PriorityQueue<>();
            if (dir.isDirectory()) {
                files.addAll(Arrays.asList(dir.listFiles()));
            }

            while (!files.isEmpty()) {
                File file = files.poll();
                if (file.isDirectory()) {
                    files.addAll(Arrays.asList(file.listFiles()));
                }
                else {
                    System.out.println(file.getName());
                }
            }
        }
    }
}
