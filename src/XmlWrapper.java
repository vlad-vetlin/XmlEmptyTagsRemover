import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Stack;

public class XmlWrapper {
    private XmlParserState state = XmlParserState.ReadNothing;

    private StringBuilder currentTagName = new StringBuilder();

    private final ArrayList<Tag> tags = new ArrayList<>();

    private final Stack<Tag> currentTagStack = new Stack<>();

    private boolean onlyClosed = false;

    public XmlWrapper(@NotNull String xml) throws NotValidXml {
        for (int i = 0; i < xml.length(); ++i) {
            processSymbol(xml.charAt(i));
        }
    }

    /**
     * For iterating process line by line
     * @param line current line from xml text
     * @return current instance
     * @throws NotValidXml if xml was invalid
     */
    public XmlWrapper processLine(@NotNull String line) throws NotValidXml {
        for (int i = 0; i < line.length(); ++i) {
            processSymbol(line.charAt(i));
        }

        return this;
    }

    /**
     * Check that symbol is tabulation for ignore this symbol. You can add some new check or add regular expression
     * @param c current symbol
     * @return true if current symbol was tabulation
     */
    private boolean isTabulate(@NotNull char c) {
        return c == ' ' || c == '\n';
    }

    /**
     * Process one symbol from the text. This function work O(1). You can add to this function some new checks. For
     * example for character skipping symbols
     * @param c current symbol
     * @return current instance for chaining execute
     * @throws NotValidXml if xml is not valid
     */
    public XmlWrapper processSymbol(@NotNull char c) throws NotValidXml {
        if (isTabulate(c)) {
            return this;
        }

        switch (c) {
            case '<':
                tryStartReadTagName();
                break;
            case '>':
                tryFinishReadTagName();
                break;
            case '/':
                tryReadCloseTag();
                break;
            default:
                tryReadSymbol(c);
        }

        return this;
    }

    /**
     * Read symbol /. That mean two variants.
     * 1) Current tag is finishing and now we can read finish tag name. In this case we need to change current state to
     * ReadTagFinish.
     * 2) Current tag was empty and finished (i.e. <test/>). In this case we need save information about this tag
     * hadn't his name, and we need to drop it
     * @throws NotValidXml if we found this tag not in reading tag name, that this is a not valid XML. (We don't support
     * skipping symbols yet)
     */
    private void tryReadCloseTag() throws NotValidXml {
        if (state == XmlParserState.ReadTagName) {
            state = XmlParserState.ReadTagFinish;
            if (currentTagName.length() > 0) {
                onlyClosed = true;
            }
            return;
        }

        throw new NotValidXml();
    }

    /**
     * Try to append symbol to current last tag. We can append symbol only for anonymous tag. Because of it, we need
     * to push empty tag on stack (if stack peek is not EmptyTag)
     * @param symbol current symbol to push
     */
    private void appendSymbolToLastTag(@NotNull char symbol) {
        Tag topTag = currentTagStack.peek();

        if (topTag instanceof TagBuilder) {
            currentTagStack.push(new EmptyTag());
        }

        EmptyTag tag = (EmptyTag) currentTagStack.peek();
        tag.addSymbol(symbol);
    }

    /**
     * Try to read current symbol. We have two cases
     * 1) current state is ReadTagName or FinishTagName. Then we need to add current symbol to current tag name
     * 2) current state is ReadTagData. Then we need to add symbol to a last anonymous tag
     * @param symbol current symbol
     * @throws NotValidXml current state was not ReadTagName or ReadTagFinish or ReadTagData
     */
    private void tryReadSymbol(@NotNull char symbol) throws NotValidXml {
        if (state == XmlParserState.ReadTagName || state == XmlParserState.ReadTagFinish) {
            currentTagName.append(symbol);
            return;
        }

        if (state == XmlParserState.ReadTagData) {
            appendSymbolToLastTag(symbol);

            return;
        }

        throw new NotValidXml();
    }

    /**
     * Try to Finish empty tag. If last tag is not empty, then skip it. On the other way we need check parent tag.
     * If it is also empty tag then something was wrong. If last task is not EmptyTag then push current empty tag to it
     * @throws NotValidXml when something was wrong
     */
    private void finishEmptyTag() throws NotValidXml {
        Tag tag = currentTagStack.peek();
        if (tag instanceof EmptyTag) {
            EmptyTag emptyTag = (EmptyTag) currentTagStack.pop();

            Tag secondTag = currentTagStack.peek();
            if (secondTag instanceof EmptyTag) {
                throw new NotValidXml();
            }

            ((TagBuilder)secondTag).addChild(emptyTag);
        }
    }

    /**
     * this function has two different cases
     * 1) If current state was ReadTagData. Then we need to try finish current EmptyTag and change state to ReadTagName
     * 2) if current state was ReadNothing (we finished last tag), then we need to start new tag and change state to
     * ReadTagName
     * @throws NotValidXml when something was wrong
     */
    private void tryStartReadTagName() throws NotValidXml {
        if (state == XmlParserState.ReadTagData) {
            finishEmptyTag();

            state = XmlParserState.ReadTagName;

            return;
        }

        if (state == XmlParserState.ReadNothing) {
            state = XmlParserState.ReadTagName;

            return;
        }

        throw new NotValidXml();
    }

    /**
     * In this function we need to push current names tag to stack and clear tag name
     */
    private void finishTagNameReading() {
        TagBuilder tag = new TagBuilder();
        tag.addName(currentTagName.toString());
        currentTagStack.push(tag);
        currentTagName = new StringBuilder();
    }

    /**
     * This function has two cases
     * 1) if current state was ReadTagName, then we need to finish current tag name and push it on the stack
     * 2) if current state was ReadTagFinish, then we need to finish current tag and check his data (is empty?)
     * @throws NotValidXml current state is not valid
     */
    private void tryFinishReadTagName() throws NotValidXml {
        if (state == XmlParserState.ReadTagName) {
            finishTagNameReading();
            state = XmlParserState.ReadTagData;

            return;
        }

        if (state == XmlParserState.ReadTagFinish) {
            finishTag();
            state = XmlParserState.ReadNothing;

            return;
        }

        throw new NotValidXml();
    }

    /**
     * If current tag is only empty tag, then we need to skip it
     * If current stack top tag is not NamedTag, then something was wrong
     * If current tag closing nam is not equal to it starting, then this xml is not valid
     * If all is valid, then we need to remove current stack top.
     * If stack is epty, then current tag is root and we need to push it to tags. On the other way, we need to push this
     * tag as child to a new stack top
     * @throws NotValidXml something was wrong
     */
    private void finishTag() throws NotValidXml {
        if (onlyClosed) {
            onlyClosed = false;
            currentTagName = new StringBuilder();

            return;
        }

        if (!(currentTagStack.peek() instanceof TagBuilder)) {
            throw new NotValidXml();
        }

        TagBuilder currentPoppedTag = (TagBuilder)currentTagStack.peek();
        String curName = currentPoppedTag.getTagName();
        if (!curName.equals(currentTagName.toString())) {
            currentTagName = new StringBuilder();
            throw new NotValidXml();
        }

        currentTagStack.pop();

        currentTagName = new StringBuilder();

        if (currentPoppedTag.isEmpty()) {
            return;
        }

        if (currentTagStack.isEmpty()) {
            tags.add(currentPoppedTag);
            return;
        }

        Tag topTag = currentTagStack.peek();
        if (topTag instanceof TagBuilder) {
            ((TagBuilder) topTag).addChild(currentPoppedTag);
            return;
        }

        throw new NotValidXml();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Tag tag : tags) {
            builder.append(tag.toString());
        }

        return builder.toString();
    }
}
