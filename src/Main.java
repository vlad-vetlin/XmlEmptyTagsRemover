import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private interface EstimateFunction {
        long estimate();
    }

    public static void main(String[] args) {
        double bigFileResult = estimate(Main::estimateBigTest);
        System.out.println("Big File seconds: " + Math.round(bigFileResult / 1000000.0) / 1000.0);

        double smallFilesResult = estimate(Main::estimateSmallTest);
        System.out.println("Small File seconds: " + Math.round(smallFilesResult / 1000000.0) / 1000.0);
    }

    private static double estimate(EstimateFunction function) {
        int count = 10;

        double result = 0;
        for (int i = 0; i < count; ++i) {
            result += function.estimate();
        }
        return result / count;
    }

    private static long estimateSmallTest() {
        String xml = "<form1>\n" +
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
                "<form1>\n" +
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
                "<form1>\n" +
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
                "<form1>\n" +
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
                "<form1>\n" +
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
                "<form1>\n" +
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
                "";

        long startTime = System.nanoTime();

        try {
            for (int i = 0; i < 10000; ++i) {
                XmlWrapper xmlWrapper = new XmlWrapper(xml);
            }
        } catch (NotValidXml ignored) {

        }

        long finishTime = System.nanoTime();

        return finishTime - startTime;
    }

    private static long estimateBigTest() {
        long startTime = System.nanoTime();
        XmlWrapper wrapper = new XmlWrapper();
        try (BufferedReader br = new BufferedReader(new FileReader("/home/green-tea/all_projects/XmlEmptyTagsRemover/part.xml"))) {
            String line;
            while ((line = br.readLine()) != null) {
                wrapper.processLine(line);
            }
        } catch (IOException | NotValidXml ignored) {
        }

        long finishTime = System.nanoTime();

        return finishTime - startTime;
    }
}
