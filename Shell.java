import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Shell {
  private static Trie trie;

  private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  private static String readLine() throws IOException {
    String name = reader.readLine();
    return name;
  }

  private static boolean isCommandValid(int numExpectedArgs, String[] commands) {
    if(commands.length != numExpectedArgs) {
      return false; // it should be something like "ADD name 10"
    }

    if(trie == null) {
      System.out.println("Trie is not created. use \"new\" to create a trie");
      return false;
    }

    return true;
  }

  private static void add(String[] commands) {
    if(!isCommandValid(3, commands)) {
      return;
    }

    try {
      Integer points = Integer.parseInt(commands[2]);
      boolean isAdded = trie.add(commands[1], points);
      if(!isAdded) {
        System.out.println("Record exists");
      }
    } catch(NumberFormatException ne) {
      System.out.println("Given number isn't valid");
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private static void printTrie(String [] commands) {
    if(!isCommandValid(1, commands)) {
      return;
    }
    System.out.println(trie.toString());
  }

  private static void change(String[] commands) {
    if(!isCommandValid(3, commands)) {
      return;
    }

    try {
      Integer points = Integer.parseInt(commands[2]);
      boolean isChanged = trie.change(commands[1], points);
      if(!isChanged) {
        System.out.println("No such record found");
      }
    } catch(Exception e) {
      e.printStackTrace();
      return;
    }

  }

  private static void createTrie() {
    trie = new Trie();
  }
  private static void showHelp() {
    // TODO: display the helpful message here.
    System.out.println("My helpful message");
  }

  private static void delete(String[] commands) {
    if(!isCommandValid(2, commands)) {
      System.out.println("Invalid command");
      return;
    }
    boolean deleted = trie.delete(commands[1]);
    if(!deleted) {
      System.out.println("Record not found.");
    } else {
      System.out.println("Record deleted");
    }
  }
  private static void queryPoints(String[] commands) {
    if(!isCommandValid(2, commands)) {
      System.out.println("Invalid command");
      return;
    }
    Integer points = trie.points(commands[1]);
    if(points == null) {
      System.out.println("No records found.");
    } else {
      System.out.println(commands[1] + " has " + points + " points.");
    }
  }

  /**
    Given a command (either a completely recognizable command or it's prefix),
    return the complete command.
    e.g. P ->"points", "t" -> "trie", "qu" -> "quit"
  */
  private static String getMainCommand(String command) {
    String[] recognizableCommands = {
      "NEW", "POINTS", "QUIT", "DELETE", "CHANGE", "ADD", "HELP", "TRIE", "QUIT"
    };

    for (String candidateCommand: recognizableCommands) {
      if(candidateCommand.startsWith(command.toUpperCase())) {
        return candidateCommand;
      }
    }

    // if the for loop has passed, that means the given command is not recognizable
    return null;
  }
  /*
    Parse and execute command given one.
    return false if the program should terminate, true otherwise.
  */
  private static boolean parseCommand(String command) {
    String[] subCommand = command.split(" ");

    if(subCommand.length > 3) {
      return true; // terminate here as the input is invalid
    }
    // get the final command that needs to be executed.
    String mainCommand = getMainCommand(subCommand[0]);

    switch(mainCommand) {
      case "NEW":
        createTrie();
        break;
      case "POINTS":
        queryPoints(subCommand);
        break;
      case "DELETE":
        delete(subCommand);
        break;
      case "CHANGE":
        change(subCommand);
        break;
      case "ADD":
        add(subCommand);
        break;
      case "HELP":
        showHelp();
        break;
      case "TRIE":
        printTrie(subCommand);
        break;
      case "QUIT":
        System.out.println("bye.");
        return false;
    }
    return true;
  }

  public static void main (String[] args)  {
    System.out.println("Welcome to my Trie maker");
    // the main loop
    while(true) {
      try {
        System.out.println("Give me a command!");
        String command = readLine();
        boolean shouldContinue = parseCommand(command);
        if(!shouldContinue)  {
          break;
        }
      } catch (Exception e) { }
    }

  }
}
