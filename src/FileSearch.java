//import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

// FileSearch 0.1
// This application searches a directory for files and folders
// and returns the location if it matches the search term.
public class FileSearch {

    private static SearchItem SearchItem;
    private static int ERROR_COUNT = 0;

    public static void main(String[] args) throws IOException {
        getConsoleInput();
        setSearchMode();
        checkSearchMode();
        search();
    }

    private static void getConsoleInput() {
        SearchItem = new SearchItem();
        Scanner scanner = new Scanner(System.in);
        System.out.println("FileSearch 0.1");
        System.out.print("Safe search on (Enter Y or N)? ");
        SearchItem.SearchMode = scanner.nextLine();
        System.out.print("Enter search phrase: ");
        SearchItem.Query = scanner.nextLine();
        System.out.print("Enter directory address: ");
        SearchItem.Directory = scanner.nextLine();
    }

    private static void setSearchMode() {
        if (SearchItem.SearchMode.toLowerCase().charAt(0) == 'y') {
            SearchItem.Mode = 0; // safe search
        } else {
            SearchItem.Mode = 1; // un-timed search
        }
    }

    private static void checkSearchMode() {
        if (SearchItem.Mode == 0) {
            long startTimer = System.currentTimeMillis();
            SearchItem.StopTimer = startTimer + 60 * 1000; // 60 seconds * 1000 ms/sec
        }
    }

    // recursively searches subdirectories
    private static void search() throws IOException {
        try {
            File dir = new File(SearchItem.Directory);
            Files.walkFileTree(
                    Paths.get(dir.getPath()),
                    new HashSet<FileVisitOption>(Arrays.asList(FileVisitOption.FOLLOW_LINKS)),
                    Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                                throws IOException {
                            if (System.currentTimeMillis() > SearchItem.StopTimer) {
                                System.out.println("Safe search exiting process...");
                                System.exit(0);
                            }
                            if (file.getFileName().toString().toLowerCase().contains(SearchItem.Query.toLowerCase())) {
                                //SearchItem.FoundPaths.add(file.toFile());
                                System.out.println(file.toString());
                            }
                            return FileVisitResult.CONTINUE;
                        }
                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException e)
                                throws IOException {
                            //System.err.printf("Visiting failed for %s\n", file);
                            return FileVisitResult.SKIP_SUBTREE;
                        }
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                                throws IOException {
                            //System.out.printf("About to visit directory %s\n", dir);
                            return FileVisitResult.CONTINUE;
                        }
                    });
        } catch(NullPointerException | IOException | UncheckedIOException ex) {
            if (ERROR_COUNT == 0) {
                System.out.println(ex.toString());
                ERROR_COUNT++;
            } else {
                System.exit(-1);
            }
        }
    }
}

class SearchItem {
    String Query;
    String Directory;
    String SearchMode;
    int Mode;
    long StopTimer;
    Stack<File[]> Directories;
    List<File> FoundPaths;

    SearchItem() {
        this.Query = "";
        this.Directory = "";
        this.SearchMode = "";
        this.Mode = 0;
        this.StopTimer = Integer.MAX_VALUE;
        this.Directories = new Stack<File[]>();
        this.FoundPaths = new ArrayList<File>();
    }
}
