import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class RemoveAllureResultByKeyword {

    public static void main(String[] args) {
        // Đường dẫn đến thư mục allure-results
        String allureResultsPath = "C:\\Users\\trangnhung\\NguyenTrangNhung\\DATN_TestHauiFood\\target\\allure-results";
        String keyword = "testHomePageTitle";

        try (Stream<Path> paths = Files.walk(Paths.get(allureResultsPath))) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> {
                        try {
                            String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                            if (content.contains(keyword)) {
                                Files.delete(path);
                                System.out.println("Đã xóa: " + path.getFileName());
                            }
                        } catch (IOException e) {
                            System.err.println("Lỗi khi xử lý file: " + path.getFileName());
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
