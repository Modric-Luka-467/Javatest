import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class FileSplitterMerger {
    public static void splitFile(String sourceFilePath, String outputDirPath, int chunkSize) throws IOException {
        File sourceFile = new File(sourceFilePath);
        File outputDir = new File(outputDirPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile))) {
            byte[] buffer = new byte[chunkSize];
            int len;
            int partNum = 1;
            String baseName = sourceFile.getName();
            while ((len = bis.read(buffer)) != -1) {
                String partFileName = baseName + ".part" + partNum;
                File partFile = new File(outputDir, partFileName);
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(partFile))) {
                    bos.write(buffer, 0, len);
                }
                partNum++;
            }
        }
    }

    public static void mergeFile(String targetDirPath, String originalFileName, String mergedFilePath) throws IOException {
        File dir = new File(targetDirPath);
        File[] partFiles = dir.listFiles((file, name) -> name.startsWith(originalFileName + ".part"));
        if (partFiles == null || partFiles.length == 0) {
            throw new FileNotFoundException("没有找到分片文件");
        }
        Pattern pattern = Pattern.compile("\\.part(\\d+)$");
        Arrays.sort(partFiles, (f1, f2) -> {
            Matcher m1 = pattern.matcher(f1.getName());
            Matcher m2 = pattern.matcher(f2.getName());
            if (m1.find() && m2.find()) {
                int num1 = Integer.parseInt(m1.group(1));
                int num2 = Integer.parseInt(m1.group(2));
                return Integer.compare(num1, num2);
            }
            return 0;
        });
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(mergedFilePath))) {
            byte[] buffer = new byte[1024];
            int len;
            for (File part : partFiles) {
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(part))) {
                    while ((len = bis.read(buffer)) != -1) {
                        bos.write(buffer, 0, len);
                    }
                }
            }
        }
        for (File part : partFiles) {
            part.delete();
        }
    }

    public static void main(String[] args) {
        try {
            splitFile("bigFile.txt", "partFolder", 1024);
            System.out.println("✅分片完成");
            mergeFile("partFolder", "bigFile.txt", "merged_bigFile.txt");
            System.out.println("✅合并完成,分片自动删除");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}