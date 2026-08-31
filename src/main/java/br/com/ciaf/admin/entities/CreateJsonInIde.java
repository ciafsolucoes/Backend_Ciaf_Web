package br.com.ciaf.admin.entities;

import com.google.gson.Gson;

import javax.persistence.Column;
import javax.persistence.OneToOne;

public class CreateJsonInIde {
    public static void main(String[] args) {
        Persons entity = new Persons();
        // Set entity fields...
        Gson gson = new Gson();
        String json = gson.toJson(entity);
        System.out.println(json);
    }

   
}
