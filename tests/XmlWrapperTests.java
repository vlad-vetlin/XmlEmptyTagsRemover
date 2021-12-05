import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class XmlWrapperTests {
    @Test
    public void testSuccessParseXml() throws NotValidXml {
        XmlWrapper xmlWrapper = new XmlWrapper("<form1>\n" +
                "        <GenInfo>\n" +
                "            <Section1>\n" +
                "                <EmployeeDet>\n" +
                "                    <Title>999990000</Title>\n" +
                "                    <Firstname>MIKE</Firstname>\n" +
                "                    <Surname>SPENCER</Surname>\n" +
                "                    <CoName/>\n" +
                "                    <EmpAdd>\n" +
                "                        <Address><Add1/><Add2/><Town/><County/><Pcode/></Address>\n" +
                "                    </EmpAdd>\n" +
                "                    <PosHeld>DEVELOPER</PosHeld>\n" +
                "                    <Email/>\n" +
                "                    <ConNo/>\n" +
                "                    <Nationality/>\n" +
                "                    <PPSNo/>\n" +
                "                    <EmpNo/>\n" +
                "                </EmployeeDet>\n" +
                "            </Section1>\n" +
                "        </GenInfo>\n" +
                "    </form1>" +
                "    <form2/>" +
                "    <form3>test</form3>" +
                "");

        Assertions.assertEquals("<form1><GenInfo><Section1><EmployeeDet><Title>999990000</Title><Firstname>MIKE</Firstname><Surname>SPENCER</Surname><PosHeld>DEVELOPER</PosHeld></EmployeeDet></Section1></GenInfo></form1><form3>test</form3>", xmlWrapper.toString());
    }

    @Test
    public void testSuccessParseEmpty() throws NotValidXml {
        XmlWrapper xmlWrapper = new XmlWrapper("");

        Assertions.assertEquals("", xmlWrapper.toString());
    }

    @Test
    public void testSuccessParseOneEmptyTag() throws NotValidXml
    {
        XmlWrapper xmlWrapper = new XmlWrapper("<test></test>");

        Assertions.assertEquals("", xmlWrapper.toString());
    }

    @Test
    public void testSuccessParseNestedEmptyTag() throws NotValidXml
    {
        XmlWrapper xmlWrapper = new XmlWrapper("<test><subTest></subTest></test>");

        Assertions.assertEquals("", xmlWrapper.toString());
    }

    @Test
    public void testSuccessParseAnyRootTags() throws NotValidXml
    {
        XmlWrapper xmlWrapper = new XmlWrapper("<test></test><test1>test1</test1><test2/><test3>test3</test3>");

        Assertions.assertEquals("<test1>test1</test1><test3>test3</test3>", xmlWrapper.toString());
    }

    @Test
    public void testTextWithoutTags() {
        Assertions.assertThrows(NotValidXml.class, () -> new XmlWrapper("test"));
    }

    @Test
    public void testCloseNotValidTag() {
        Assertions.assertThrows(NotValidXml.class, () -> new XmlWrapper("<test1></test2>"));
    }

    @Test
    public void OnlyOneClosedTag() throws NotValidXml {
        XmlWrapper xmlWrapper = new XmlWrapper("<test/>");

        Assertions.assertEquals("", xmlWrapper.toString());
    }
}
