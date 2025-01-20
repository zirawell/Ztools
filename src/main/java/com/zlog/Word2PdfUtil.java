package com.zlog;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.google.common.base.Preconditions;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.itext.extension.font.ITextFontRegistry;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Locale;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 将Word文档转为Pdf格式
 * @Author: Dark Wang
 * @Create: 2025/3/18 10:34
 **/

public class Word2PdfUtil {
    static boolean licenseFlag = false;
    static String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);

    static {
        try {
            // license.xml放在src/main/resources文件夹下
            InputStream is = Word2PdfUtil.class.getClassLoader().getResourceAsStream("license.xml");
            License license = new License();
            license.setLicense(is);
            licenseFlag = true;
            System.out.println("License验证成功...");
        } catch (Exception e) {
            System.out.println("License验证失败...");
            System.out.println(e.getMessage());
        }
    }

    private static final Logger logger = LoggerFactory.getLogger("Word2PdfUtil.class");

    public static void main(String[] args) throws Exception {
        String wordPath = "/Users/zirawell/Downloads/移动平台-小程序需求.docx";
        String pdfPath = "/Users/zirawell/Downloads/移动平台-小程序需求-#.pdf";
        String methodName = osName.contains("win") ? "documents4j" : "libreoffice";
        System.out.println("转换开始...");
        long old = System.currentTimeMillis();
        poiWordToPdf(wordPath, pdfPath.replace("#", "poi"));
        long poi = System.currentTimeMillis();
        officeWordToPdf(wordPath, pdfPath.replace("#", methodName));
        long office = System.currentTimeMillis();
        asposeWordToPdf(wordPath, pdfPath.replace("#", "aspose"));
        long aspose = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        // 耗时统计
        System.out.println("转换结束... 共耗时：" + ((now - old) / 1000.0) + "秒");
        System.out.println("poi耗时: " + ((poi - old) / 1000.0) + "秒");
        System.out.println("office耗时: " + ((office - poi) / 1000.0) + "秒");
        System.out.println("aspose耗时: " + ((aspose - office) / 1000.0) + "秒");
    }

    /**
     * 使用POI将Word文档转为Pdf格式
     * maven依赖包
     * <dependency>
     * <groupId>org.apache.poi</groupId>
     * <artifactId>poi-ooxml</artifactId>
     * <version>3.17</version>
     * </dependency>
     * <dependency>
     * <groupId>fr.opensagres.xdocreport</groupId>
     * <artifactId>fr.opensagres.poi.xwpf.converter.pdf-gae</artifactId>
     * <version>2.0.1</version>
     * </dependency>
     *
     * @param sourcePath 源文件地址 如 /root/example.doc
     * @param targetPath 目标文件地址 如 /root/example.pdf
     */
    public static void poiWordToPdf(String sourcePath, String targetPath) {
        try (FileInputStream fileInputStream = new FileInputStream(sourcePath);
             FileOutputStream fileOutputStream = new FileOutputStream(targetPath)) {
            XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
            PdfOptions pdfOptions = getPdfOptions();
            PdfConverter.getInstance().convert(xwpfDocument, fileOutputStream, pdfOptions);
        } catch (Exception e) {
            logger.error("poiWordToPdf error: {}", e.getMessage());
        }

    }

    private static PdfOptions getPdfOptions() {
        PdfOptions pdfOptions = PdfOptions.create();
        String pdfChineseTTF = "/System/Library/Fonts/Supplemental/Songti.ttc";
        // 解决中文不显示问题
        pdfOptions.fontProvider((familyName, encoding, size, style, color) -> {
            try {
                File file = new File(pdfChineseTTF);
                Preconditions.checkState(file.exists(), "中文字体文件不存在:" + pdfChineseTTF);
                BaseFont bfChinese = BaseFont.createFont(pdfChineseTTF + ",1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font fontChinese = new Font(bfChinese, size, style, color);
                if (familyName != null) {
                    fontChinese.setFamily(familyName);
                }
                return fontChinese;
            } catch (Throwable e) {
                System.out.println(e.getMessage());
                // An error occurs, use the default font provider.
                return ITextFontRegistry.getRegistry().getFont(familyName, encoding, size, style, color);
            }
        });
        return pdfOptions;
    }


    /**
     * 通过documents4j 实现word转pdf
     * maven依赖包
     * <dependency>
     * <groupId>com.documents4j</groupId>
     * <artifactId>documents4j-local</artifactId>
     * <version>1.0.3</version>
     * </dependency>
     * <dependency>
     * <groupId>com.documents4j</groupId>
     * <artifactId>documents4j-transformer-msoffice-word</artifactId>
     * <version>1.0.3</version>
     * </dependency>
     * Java的所有word转pdf库中，document4j是转化效果最好的。
     * 因为document4j是通过vbs脚本调用word程序，将word文件转化为pdf。
     * 所有能保证不失真。也因此document4j只能在安装office的window上运行。
     * 必须安装wps企业版、微软office。wps个人版不行
     *
     * @param sourcePath 源文件地址 如 /root/example.doc
     * @param targetPath 目标文件地址 如 /root/example.pdf
     */
    public static void documents4jWordToPdf(String sourcePath, String targetPath) {
        File sourceFile = new File(sourcePath);
        File outputFile = new File(targetPath);
        IConverter converter = null;
        try (InputStream inputStream = new FileInputStream(sourceFile);
             OutputStream outputStream = new FileOutputStream(outputFile)) {
            // 创建LocalConverter实例
            converter = LocalConverter.builder().build();

            // 动态获取源文件的DocumentType
            DocumentType sourceType = getDocumentTypeFromExtension(sourcePath);

            Future<Boolean> conversion = converter.convert(inputStream)
                    .as(sourceType)
                    .to(outputStream)
                    .as(DocumentType.PDF).schedule();
            // 等待转换完成或超时10秒
            conversion.get(10, TimeUnit.SECONDS);
            logger.info("转换完毕 targetPath = {}", outputFile.getAbsolutePath());
        } catch (Exception e) {
            logger.error("[documents4J] word转pdf失败:{}", e.toString());
        } finally {
            // 确保正确关闭转换器
            if (converter != null) {
                converter.shutDown();
            }
        }
    }

    /**
     * 通过aspose 实现word转pdf
     * maven依赖包
     * mvn install:install-file -Dfile=/Users/zirawell/Downloads/aspose/aspose-words-15.8.0-jdk16.jar -DgroupId=com.aspose -DartifactId=aspose-words -Dversion=15.8.0 -Dpackaging=jar
     * <dependency>
     * <groupId>com.aspose</groupId>
     * <artifactId>aspose-words</artifactId>
     * <version>15.8.0</version>
     * </dependency>
     * 若出现中文乱码可能是因为在linux系统下没有中文字体，需要我们手动把window系统的字体文件拷贝到linux系统下的字体目录下。
     * 找到window系统的字体目录位置，在C:\Windows\Fonts目录下，将Fonts文件夹打成压缩包Fonts.zip，扔到linux服务器的/usr/share/fonts/目录上。
     * 在linux下安装解压zip的工具 yum install -y unzip zip
     * 然后将该压缩包解压到字体目录中 cd /usr/share/fonts/
     * unzip Fonts.zip
     * 安装字体命令
     * fc-cache -fv
     * 重启一下服务器
     *
     * @param sourcePath 源文件地址 如 /root/example.doc
     * @param targetPath 目标文件地址 如 /root/example.pdf
     */
    public static void asposeWordToPdf(String sourcePath, String targetPath) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!licenseFlag) {
            logger.error("License验证不通过...");
            return;
        }
        try {
            //新建一个pdf文档
            File file = new File(targetPath);
            FileOutputStream os = new FileOutputStream(file);
            //Address是将要被转化的word文档
            Document doc = new Document(sourcePath);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.PDF);
            os.close();
        } catch (Exception e) {
            logger.error("Word 转 Pdf 失败...");
            logger.error(e.getMessage());
        }
    }

    /**
     * 通过libreoffice 实现word转pdf
     * maven依赖包
     * <dependency>
     * <groupId>com.documents4j</groupId>
     * <artifactId>documents4j-local</artifactId>
     * <version>1.0.3</version>
     * </dependency>
     * <dependency>
     * <groupId>com.documents4j</groupId>
     * <artifactId>documents4j-transformer-msoffice-word</artifactId>
     * <version>1.0.3</version>
     * </dependency>
     *
     * @param sourcePath 源文件地址 如 /root/example.doc
     * @param targetPath 目标文件地址 如 /root/example.pdf
     */
    public static void libreOfficeWordToPdf(String sourcePath, String targetPath) {
        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);
        String outputPath = targetFile.getParent();
        String tmpPDFFileName = sourceFile.getName().replace(sourceFile.getName().substring(sourceFile.getName().lastIndexOf(".")), ".pdf");
        String targetFileName = targetFile.getName();
        String[] convertCommand = {
                "libreoffice", // 使用配置后的命令
                "--headless",
                "--invisible",
                "--convert-to", "pdf",
                sourcePath,
                "--outdir", outputPath
        };
        String[] renameCommand = {
                "mv",
                outputPath + "/" + tmpPDFFileName,
                outputPath + "/" + targetFileName
        };
        convertCommand[0] = osName.contains("mac") ? "/opt/homebrew/bin/soffice" : "/usr/bin/libreoffice";
        // 执行转换命令
        executeCmd(convertCommand);
        // 执行重命名命令
        executeCmd(renameCommand);

    }

    /**
     * 执行命令行指令
     * 该方法用于执行外部命令，例如调用其他程序或系统命令
     *
     * @param command 命令行指令数组，包含需要执行的命令及其参数
     */
    private static void executeCmd(String[] command) {
        try {
            // 创建并启动一个新的进程来执行给定的命令
            Process process = Runtime.getRuntime().exec(command);
            // 等待进程执行完成
            process.waitFor();
        } catch (Exception e) {
            // 如果在执行命令过程中发生异常，记录错误信息
            logger.error("libreOfficeWordToPdf 执行转换命令时出现异常：", e);
            // 重新设置当前线程的中断状态，以便其他线程可以检查中断状态
            Thread.currentThread().interrupt();
        }
    }


    /**
     * 将Word文档转换为PDF格式
     * 此方法根据操作系统的不同选择合适的转换工具
     *
     * @param sourcePath Word文档的路径
     * @param targetPath 转换后PDF文档的路径
     */
    public static void officeWordToPdf(String sourcePath, String targetPath) {
        // 根据操作系统选择转换方法
        if (osName.contains("win")) {
            // 如果是Windows系统，使用documents4j进行转换
            documents4jWordToPdf(sourcePath, targetPath);
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
            // Unix/Linux/Mac操作系统，使用LibreOffice进行转换
            libreOfficeWordToPdf(sourcePath, targetPath);
        } else {
            // 如果操作系统不受支持，抛出异常
            throw new UnsupportedOperationException("Unsupported operating system: " + osName);
        }
    }

    /**
     * 根据文件路径的扩展名获取文档类型
     * 此方法用于将文件路径映射到其对应的文档类型枚举
     * 它首先提取文件的扩展名，然后根据扩展名的类型返回相应的DocumentType枚举值
     * 如果扩展名不在预定义的类型列表中，则抛出IllegalArgumentException异常
     *
     * @param filePath 文件路径，用于获取文件的扩展名
     * @return 根据文件扩展名对应的文档类型
     * @throws IllegalArgumentException 如果文件扩展名不受支持
     */
    private static DocumentType getDocumentTypeFromExtension(String filePath) {
        // 获取文件的扩展名并转换为小写
        String extension = FilenameUtils.getExtension(filePath).toLowerCase(Locale.ROOT);
        // 根据文件扩展名选择并返回相应的文档类型
        switch (extension) {
            case "doc":
                return DocumentType.DOC;
            case "docx":
                return DocumentType.DOCX;
            case "rtf":
                return DocumentType.RTF;
            case "xls":
                return DocumentType.XLS;
            case "xlsx":
                return DocumentType.XLSX;
            case "ods":
                return DocumentType.ODS;
            case "csv":
                return DocumentType.CSV;
            case "xml":
                return DocumentType.XML;
            case "mhtml":
                return DocumentType.MHTML;
            case "txt":
                return DocumentType.TEXT;
            case "pdf":
                return DocumentType.PDF;
            case "pdfa":
                return DocumentType.PDFA;
            default:
                // 如果文件扩展名不受支持，则抛出异常
                throw new IllegalArgumentException("Unsupported file type: " + extension);
        }
    }


}
