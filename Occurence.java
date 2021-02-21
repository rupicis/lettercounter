public class Occurence {
  int count[] = new int['Z' - 'A' + 2]; // extra space for unknown chars 

  private int toIndex(char c) {

    if (c < 'A')
      return 0;
    if (c <= 'Z')
      return c - 'A' + 1;
    if (c < 'a')
      return 0;
    if (c <= 'z')
      return c - 'a' + 1;
    return 0;
  }

  public void register(char c) {
    count[toIndex(c)]++;
  }

  public int countOf(char c) {
    return count[toIndex(c)];
  }
}
