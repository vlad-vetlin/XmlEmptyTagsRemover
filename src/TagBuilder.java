import java.util.ArrayList;

public class TagBuilder implements Tag {
    private String tagName = null;

    private final ArrayList<Tag> tagData;

    public TagBuilder() {
        tagData = new ArrayList<>();
    }

    public TagBuilder addName(String name) {
        this.tagName = name;
        return this;
    }

    public TagBuilder addChild(Tag child) {
        tagData.add(child);
        return this;
    }

    public String getTagName() {
        return tagName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("<").append(tagName).append(">");
        for (Tag tag : tagData) {
            builder.append(tag.toString());
        }
        builder.append("</").append(tagName).append(">");

        return builder.toString();
    }

    public boolean isEmpty() {
        return tagData.isEmpty();
    }
}
