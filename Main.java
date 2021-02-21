import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
  private static final char[] english = "abcdefghijklmnopqrstuvwxyz".toCharArray();

  public static void main(String... args) throws Exception {
    if (args.length == 0)
      test();
    else
      count(args[0]);
  }

  private static void count(String file) throws Exception {
    Occurence stats = new Occurence();
    char chunk[] = new char[1024];

    try (Reader in = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
      for (;;) {
        int n = in.read(chunk);
        if (n == -1)
          break;
        for (int i = 0; i < n; i++)
          stats.register(chunk[i]);
      }
    }
    for (char c : english)
      System.out.println(c + " " + stats.countOf(c));
  }

  private static void check(boolean expr) {
    if (!expr)
      throw new RuntimeException("test failed");
  }

  private static void test() {
    String text1 = "AAA aaa bcd";
    String text2 = "osa muutuma üles koos võimalus vastama ootama jalg parem aeg ometi liige hiljem pikk otsima"
        + " eriti ajama tähendama mis viimane rääkima tänav eesti töö all hääl küsima pidama kuigi aitama";

    Occurence stats = new Occurence();
    check(stats.countOf('A') == 0);
    stats.register('A');
    check(stats.countOf('A') == 1);
    check(stats.countOf('a') == 1);
    stats.register('A');
    check(stats.countOf('a') == 2);
    stats.register('a');
    check(stats.countOf('a') == 3);
    stats.register('b');
    check(stats.countOf('a') == 3);
    text1.chars().forEach(c -> stats.register((char) c));
    check(stats.countOf('a') == 9);
    check(stats.countOf('c') == 1);

    Occurence stats2 = new Occurence();
    for (char c : text2.toCharArray())
      stats2.register(c);
    Map<Integer, Long> epicLambda13xUnderperformer = text2.toLowerCase().chars().mapToObj(c -> c)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    for (char c : english)
      check(stats2.countOf(c) == epicLambda13xUnderperformer.getOrDefault((int) c, (long) 0));
    System.out.println("tests passed");
  }
}
