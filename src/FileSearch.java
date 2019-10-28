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
        // get input
        input();
        // check search mode
        mode();
        // search for item
        //File rootDir = new File(SearchItem.Directory);
        search(SearchItem.Mode);
        // format search results
        //format();
        // output results to console
        //output();
    }

    private static void input() {
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

    private static void mode() {
        if (SearchItem.SearchMode.toLowerCase().charAt(0) == 'y') {
            SearchItem.Mode = 0; // safe search
        } else {
            SearchItem.Mode = 1; // un-timed search
        }
    }

    private static void search(int mode) throws IOException {
        if (SearchItem.Mode == 0) {
            long startTimer = System.currentTimeMillis();
            SearchItem.StopTimer = startTimer + 60 * 1000; // 60 seconds * 1000 ms/sec
            search();
        } else {
            search();
        }
    }

    // recursively searches subdirectories
    private static void search() throws IOException {
        //PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*");
        //searchItem.result = rootDir.listFiles((dir, name) -> name.contains(searchItem.item));
        //FilenameFilter filter = (dir, name) -> name.contains(SearchItem.Query);
        //SearchItem.FileName = rootDir.list(filter);
        try {
            //if (rootDir.listFiles() != null) {
            // push new dir to stack
            //SearchItem.Directories.push(rootDir.listFiles());
            // go through the files in the dir
            //for (int i = 0; i < SearchItem.Directories.peek().length; i++) {
            //if (SearchItem.Directories.peek()[i] != null) {
            //if (SearchItem.Directories.peek()[i].isFile()) {
            // if filename contains search phrase, add to FoundPaths
            //if (SearchItem.Directories.peek()[i].getName().toLowerCase().contains(SearchItem.Query.toLowerCase())) {
            //SearchItem.FoundPaths.add(new File (SearchItem.Directories.peek()[i].getAbsolutePath()));
            //} // else recursively crawl through subdirectories
            //} else if (SearchItem.Directories.peek()[i].isDirectory()) {
            //search(SearchItem.Directories.peek()[i]);
            //}
            //}
            //}
            //}
            //Files.walk(Paths.get(SearchItem.Directory)).filter(Files::isRegularFile).forEach((f) -> {
            //String file = f.toString().toLowerCase();
            //if (file.contains(SearchItem.Query.toLowerCase())) {
            //SearchItem.FoundPaths.add(f.toFile());
            //}
            //});
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

    //private static void format() {
        //SearchItem.Path = new String[SearchItem.FileName.length];
        //for (int i = 0; i < SearchItem.FileName.length; i++) {
        //     File file = new File(SearchItem.FileName[i]);
        //     SearchItem.Path[i] = file.getAbsolutePath();
        //}
    //}

    //private static void output() {
        //if (SearchItem.FoundPaths.size() == 0) {
            //System.out.println("NO FILES OR FOLDERS FOUND");
        //} else {
            //for (File path : SearchItem.FoundPaths) {
                //System.out.println(path);
            //}
        //}
    //}
}

class SearchItem {
    String Query;
    String Directory;
    String SearchMode;
    int Mode;
    long StopTimer;
    String[] FileName;
    String[] Path;
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
