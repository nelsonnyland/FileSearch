//import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.*;
import java.util.*;

// FileSearch 0.1
// This application searches a directory for files and folders
// and returns the location if it matches the search term.
public class FileSearch {

    private static SearchItem SearchItem;

    public static void main(String[] args) {
        // get input
        input();
        // search for item
        search();
        // format search results
        format();
        // output results to console
        output();
    }

    private static void input() {
        SearchItem = new SearchItem();
        Scanner scanner = new Scanner(System.in);
        System.out.println("FileSearch 0.1");
        System.out.print("Enter search: ");
        SearchItem.Query = scanner.nextLine();
        System.out.print("Enter directory: ");
        SearchItem.Directory = scanner.nextLine();
    }

    private static void search() {
        File file = new File(SearchItem.Directory);
        //PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*");
        //searchItem.result = file.listFiles((dir, name) -> name.contains(searchItem.item));
        FilenameFilter filter = (dir, name) -> name.contains(SearchItem.Query);
        SearchItem.FileName = file.list(filter);
    }

    private static void format() {
        SearchItem.Path = new String[SearchItem.FileName.length];
        for (int i = 0; i < SearchItem.FileName.length; i++) {
             File file = new File(SearchItem.FileName[i]);
             SearchItem.Path[i] = file.getAbsolutePath();
        }
    }

    private static void output() {
        if (SearchItem.FileName.length == 0) {
            System.out.println("NO FILES FOUND");
        } else {
            for (String path : SearchItem.Path) {
                System.out.println(path);
            }
        }
    }
}

class SearchItem {
    String Query;
    String Directory;
    String[] FileName;
    String[] Path;
}
