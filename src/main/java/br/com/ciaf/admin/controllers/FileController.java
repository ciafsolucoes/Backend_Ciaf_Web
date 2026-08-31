package br.com.ciaf.admin.controllers;


import javax.servlet.http.HttpServletRequest;

import br.com.ciaf.admin.config.dao.UserDao;
import br.com.ciaf.admin.config.model.User;
import br.com.ciaf.admin.config.multitenant.TenantContext;
import br.com.ciaf.admin.repositories.EmitenteRepository;
import br.com.ciaf.admin.services.FileStorageService;
import br.com.ciaf.admin.vo.UploadFileResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@RestController
@RequestMapping("/files")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private User user;
    private final UserDao repository;
    private final JdbcTemplate jdbcTemplate;
    private static final String FILE_DIRECTORY = "/home/gilvan/ciaf/csv";

    @Autowired
    private FileStorageService fileStorageService;

    public FileController(UserDao repository,  JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @CrossOrigin
    @PostMapping("/{name}")
    public UploadFileResponseVO uploadFile(
            @PathVariable("name") String name,
            @RequestParam("file") MultipartFile file)
    {
        user = repository.findByUsername(name);
        Long emitente_id = user.getEmitente_id();
        String fileName = fileStorageService.storeFile(file, emitente_id);
        user.setLogo_name(fileName);
        repository.save(user);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/var/www/ciafonline.com.br/images/")
                .path(fileName)
                .toUriString();
        return new UploadFileResponseVO(fileName, name, fileDownloadUri, file.getContentType(), file.getSize());
    }

//    @PostMapping("/uploadMultipleFiles")
//    public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        List<UploadFileResponseVO> collect = Arrays.asList(files)
//                .stream()
//                .map(file -> uploadFile(file))
//                .collect(Collectors.toList());
//        return collect;
//    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            logger.info("Could not determine file type!");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            // Locate the file in the file directory
            Path filePath = Paths.get(FILE_DIRECTORY).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            System.out.println(resource.exists() + " download " + resource.getFilename());
            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Prepare response with appropriate headers for file download
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private String buildInsertSqlForTable(String tableName, String[] headers) {
        if(tableName.equals("clientes")) tableName = "persons";
        if(tableName.equals("produtos")) tableName = "products";
        // Build the column names dynamically
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableName).append(" (emitente_id, ");
        sql.append(String.join(", ", headers));
        sql.append(") VALUES (?");
        for (int i = 0; i < headers.length; i++) {
            sql.append(", ?");
        }
        sql.append(")");
        return sql.toString();
    }

    @PostMapping("/csv/{tableName}/{emitente_id}")
    public ResponseEntity<String> uploadCsvFile(
            @PathVariable("tableName") String tableName,
            @PathVariable("emitente_id") Long emitente_id,
            @RequestParam("file") MultipartFile file
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Por favor upload um arquivo CSV valido.");
        }
        if(tableName.equals("clientes")) tableName = "persons";
        try {
            // Save the file temporarily
            File tempFile = new File(System.getProperty("java.io.tmpdir"), file.getOriginalFilename());
            file.transferTo(tempFile);
            // Read and process the CSV file
            try (BufferedReader br = new BufferedReader(new FileReader(tempFile))) {
                String line;

                boolean isFirstRow = true;
                String[] headers = null; // To store headers
                int lineNumber = 0;

                while ((line = br.readLine()) != null) {
                    lineNumber++;
                    if (lineNumber <= 1) {
                        continue;
                    }
                    if (isFirstRow) {
                        // Extract headers from the first row to determine column names for the table
                        headers = line.split(",");
                        isFirstRow = false;
                        continue;
                    }
                    String[] columns = line.split(",");
                    for (int i = 0; i < columns.length; i++) {
                        if (columns[i].trim().isEmpty()) {
                            columns[i] = null; // Replace blank fields with null
                        }
                    }
                    Object[] values = new Object[headers.length + 1]; // Number of columns (+1 for emitente_id)
                    values[0] = emitente_id; // Add emitente_id in the first position
                    for (int i = 0; i < headers.length; i++) {
                        if (i < columns.length && columns[i] != null) {
                            String sanitizedValue = columns[i].trim().replaceAll("\\p{C}", "");
                            values[i + 1] = sanitizedValue;
                        } else {
                            values[i + 1] = null; // Map missing columns to `null`
                        }
                    }
                    // Dynamically build SQL for the given table and its columns
                    String sql = buildInsertSqlForTable(tableName, headers);
                    // Execute dynamic SQL
                    jdbcTemplate.update(sql, values);
                }
            }
            tempFile.delete(); // Delete the temporary file
            return ResponseEntity.ok("A tabela foi importada com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro na importação da tabela");
        }
    }
    @PostMapping("/xlsx/{tableName}/{emitente_id}")
    public ResponseEntity<String> uploadXlsxFile(
            @PathVariable("tableName") String tableName,
            @PathVariable("emitente_id") Long emitente_id,
            @RequestParam("file") MultipartFile file,
            @RequestHeader(value = "schema", required = false) String schema
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Por favor upload um arquivo Excel valido.");
        }

        // Set tenant context from header if provided
        if (schema != null && !schema.trim().isEmpty()) {
            TenantContext.setCurrentTenant(schema.toLowerCase());
        }

        if(tableName.equals("clientes")) tableName = "persons";
        if(tableName.equals("produtos")) tableName = "products";
        try {
            // Save the xlsx file temporarily
            File xlsxFile = new File(System.getProperty("java.io.tmpdir"), file.getOriginalFilename());
            file.transferTo(xlsxFile);
//            System.out.println("Processing table: " + tableName + " for schema: " + TenantContext.getCurrentTenant());

            // Convert Excel to CSV - skip this, read directly from Excel
            // Read and process the Excel file directly
            try (Workbook workbook = new XSSFWorkbook(xlsxFile)) {
                Sheet sheet = workbook.getSheetAt(0);

                // Switch to tenant database once before processing all rows
                String currentTenant = TenantContext.getCurrentTenant();
                jdbcTemplate.execute("USE " + currentTenant);
                System.out.println("Switched to database: " + currentTenant);

                String[] headers = null;
                int rowIndex = 0;

                for (Row row : sheet) {
                    rowIndex++;

                    // Skip first row (usually title/description)
                    if (rowIndex <= 1) {
                        continue;
                    }

                    // Second row contains headers
                    if (rowIndex == 2) {
                        headers = new String[row.getLastCellNum()];
                        for (int i = 0; i < row.getLastCellNum(); i++) {
                            Cell cell = row.getCell(i);
                            headers[i] = cell != null ? getCellValueAsString(cell).trim() : "";
                        }
//                        System.out.println("Headers found: " + Arrays.toString(headers));
//                        System.out.println("Number of headers: " + headers.length);
                        continue;
                    }

                    // Process data rows
                    if (headers == null) continue;

                    Object[] values = new Object[headers.length + 1];
                    values[0] = emitente_id;

                    System.out.println("\n=== Processing Row " + rowIndex + " ===");
                    System.out.println("Row has " + row.getLastCellNum() + " cells");

                    for (int i = 0; i < headers.length; i++) {
                        Cell cell = row.getCell(i);
                        String cellValue = cell != null ? getCellValueAsString(cell) : "";

                        if (cellValue == null || cellValue.trim().isEmpty() || "NULL".equalsIgnoreCase(cellValue)) {
                            values[i + 1] = null;
                        } else {
                            values[i + 1] = cellValue.trim();
                        }

                        if (i < 10) { // Log first 10 columns for debugging
                            System.out.println("  Column " + i + " [" + headers[i] + "]: " + values[i + 1]);
                        }
                    }

                    // Build and execute SQL
                    String sql = buildInsertSqlForTable(tableName, headers);
//                    System.out.println("SQL: " + sql);
//                    System.out.println("Values: " + Arrays.toString(values));
                    jdbcTemplate.update(sql, values);
                }
            }

            xlsxFile.delete();
            return ResponseEntity.ok("A tabela foi importada com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro na importação da tabela: " + e.getMessage());
        } finally {
            TenantContext.clear();
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == Math.floor(numericValue)) {
                        return String.valueOf((long) numericValue);
                    } else {
                        return String.format("%.2f", numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    return cell.getStringCellValue();
                }
            case BLANK:
                return "";
            default:
                return "";
        }
    }

}
