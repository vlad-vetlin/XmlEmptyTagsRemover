public class EmptyTag implements Tag {
    private final StringBuilder tagData = new StringBuilder();

    public void addSymbol(char c) {
        tagData.append(c);
    }

    @Override
    public String toString() {
        return tagData.toString();
    }
}
