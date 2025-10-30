package build_and_run;

public class Main {
  static void main(String[] args) {
    System.out.println("Building and running a Luego app");

    String name = "Joe";
    String message = """
            Hello %s
            This is a multiline string.
            """.formatted(name);
    System.out.println(message);
  }
}
