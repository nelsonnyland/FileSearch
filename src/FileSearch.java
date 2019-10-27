import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Scanner;

// FileSearch 0.1
// This application searches a directory for files and folders
// and returns the location if it matches the search term.
public class FileSearch {
    public static void main(String[] args) {
        // get input
        SearchItem searchItem = input();
        // search for item
        File[] matchingFiles = search(searchItem);
        // format search results
        //format();
        // output results to console
        output(matchingFiles);
    }

    private static SearchItem input() {
        SearchItem searchItem = new SearchItem();
        Scanner scanner = new Scanner(System.in);
        System.out.println("FileSearch 0.1");
        System.out.print("Enter search: ");
        searchItem.item = scanner.nextLine();
        System.out.print("Enter directory: ");
        searchItem.directory = scanner.nextLine();
        return searchItem;
    }

    private static File[] search(SearchItem searchItem) {
        //PathMatcher matcher =
        //    FileSystems.getDefault().getPathMatcher("glob:*");
        File file = new File(searchItem.directory);
        return file.listFiles((dir, name) -> name.contains(searchItem.item));
    }

    public static void format() {
        //
    }

    public static void output(File[] matchingFiles) {
        for (File file : matchingFiles) {
            System.out.println(file);
        }
    }
}
