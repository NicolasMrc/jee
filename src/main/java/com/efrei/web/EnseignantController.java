package com.efrei.web;

import com.efrei.model.Enseignant;
import com.efrei.model.Entreprise;
import com.efrei.model.Etudiant;
import com.efrei.model.Promo;
import com.efrei.service.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nico on 03/12/2017.
 */

@PreAuthorize("isAuthenticated()")
@Controller
public class EnseignantController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private EnseignantService enseignantService;

    @Autowired
    private UserController userController;


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String submit(@RequestParam(value = "_csrf", required = false) String csrf, @RequestParam("file") MultipartFile file, Model model, HttpServletRequest request) {
        Enseignant enseignant = this.securityService.getCurrentUser().getEnseignant();
        String dirPath = request.getServletContext().getRealPath("/") + "resources/signatures";
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = "signature_" + enseignant.getNom()+ "_" + enseignant.getPrenom()+ "." + extension;

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                File dir = new File(dirPath);

                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                this.enseignantService.updateSignature(this.securityService.getCurrentUser().getEnseignant(), name);

                return this.userController.profil(model, request);
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return this.userController.profil(model, request);
        }
    }

}
