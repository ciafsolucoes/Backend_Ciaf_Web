package br.com.ciaf.admin.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping({ "/my-bash" })
public class BashController {
    @GetMapping
    public String getBash() throws IOException {
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", "./myBash.sh"});
//            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", "mkdir /home/gilvan/testando"});
//            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", "curl -s http://localhost:3000/hello.sh | bash"});
            BufferedReader srdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String a = null;
            while ((a = srdInput.readLine()) != null)
            {
                System.out.println(a);
            }
            return a;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
