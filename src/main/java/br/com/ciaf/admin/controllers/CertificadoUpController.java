package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.config.dao.UserDao;
import br.com.ciaf.admin.config.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(value = {"/photos"})
public class CertificadoUpController {
    @Autowired
    UserDao repository;
    @Value("${application.disco.raiz}")
    private String raiz;
    @Value("${application.disco.diretorio}")
    private String diretorio;
    @CrossOrigin
    @PostMapping("/{name}")
    public String upload(
            @PathVariable("name") String name,
            @RequestParam("file") MultipartFile file) {
        User user = repository.findByUsername(name);
        String certName = file.getOriginalFilename();
        certName = "certificado_"+ user.getEmitente_id()+ "." + certName.substring(certName.length() -3);
        Path diretorioPath = Paths.get(this.raiz, diretorio);
        Path arquivoPath = diretorioPath.resolve(certName);
        try {
            Files.createDirectories(diretorioPath);
            file.transferTo(arquivoPath.toFile());
            this.saveCertPath(name, certName);
        }catch (Exception e) {
            throw new RuntimeException("Falhou Upload " + e);
        }
        return "Seu certificado foi salvo com sucesso!";
    }

    public void saveCertPath(String name, String certName){
        User user = repository.findByUsername(name);
        user.setCert_name(certName);
        repository.save(user);
    }

    public static String UPLOAD_DIRECTORY = "";
    @GetMapping("/uploadimage") public String displayUploadForm() {
        return "imageupload/index";
    }

//    @PostMapping("/upload") public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException, IOException {
//        StringBuilder fileNames = new StringBuilder();
//        Path fileNameAndPath = Paths.get(raiz, file.getOriginalFilename());
//        fileNames.append(file.getOriginalFilename());
//        Files.write(fileNameAndPath, file.getBytes());
//        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
//        return "Sua imagem foi salva com sucesso!";
//    }
}
