import java.util.ArrayList;
import java.util.Scanner;

public class DataFile {
    
    private Scanner scanner;
    private String path;

    public DataFile(String path) {
        this.path = path;
    }

    public String getStringData(String loc) {
        this.scanner = new Scanner(DungeonCrawlerGame.game.getClass().getResourceAsStream(this.path));
        while (this.scanner.hasNextLine()) {
            String line = this.scanner.nextLine();
            if (line.startsWith("#"))
                continue;
            String[] split = line.split(":");
            if (split[0].equals(loc))
                return split[1].trim();
        }
        return null;
    }

    public ArrayList<String> getStringListData(String loc) {
        this.scanner = new Scanner(DungeonCrawlerGame.game.getClass().getResourceAsStream(this.path));
        ArrayList<String> list = new ArrayList<String>();
        while (this.scanner.hasNextLine()) {
            String line = this.scanner.nextLine();
            if (line.startsWith("#"))
                continue;
            String[] split = line.split(":");
            if (split[0].equals(loc)) {
                String next = scanner.nextLine();
                while (next.length() > 2 && next.substring(0, 2).equals(" -")){
                    list.add(next.substring(2).trim());
                    if (!scanner.hasNextLine())
                        break;
                    next = scanner.nextLine();
                }
                return list;
            }
        }
        return list;
    }



}
