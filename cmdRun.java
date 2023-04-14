public class GitCommandRunner {
  public static void main(String[] args) {
    try {
      File directory = new File("/path/to/directory");
      ProcessBuilder pb = new ProcessBuilder("git", "status");
      pb.directory(directory);
      pb.redirectErrorStream(true);
      Process process = pb.start();

      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }

      int exitCode = process.waitFor();
      System.out.println("\nExited with error code : " + exitCode);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
