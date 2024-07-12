package crewmate.lib;

import java.io.FileWriter;
import java.io.IOException;

public class HtmlGenerator {
    public static void main(String[] args) {
        String htmlTitle = "My Generated HTML";
        String htmlContent = "This is the content of the generated HTML file.";

        try (FileWriter writer = new FileWriter("bindings.html")) {
            writer.write("<html>\n");
            writer.write("<head><title>" + htmlTitle + "</title></head>\n");
            writer.write("<body>\n");
            writer.write("<h1>" + htmlTitle + "</h1>\n");
            writer.write("<p>" + htmlContent + "</p>\n");
            writer.write("</body>\n");
            writer.write("</html>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
