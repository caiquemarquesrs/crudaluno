package com.example.crudaluno;

import br.com.puc.dao.AlunoDAO;
import br.com.puc.dao.CursoDAO;
import br.com.puc.model.Area;
import br.com.puc.model.Curso;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            AlunoDAO alunoDAO = new AlunoDAO();
            CursoDAO cursoDAO = new CursoDAO();
            alunoDAO.createTable();
            if (cursoDAO.findAll().isEmpty()) {
                cursoDAO.create(new Curso("Engenharia de Software", "ENGSW", Area.EXATAS));
                cursoDAO.create(new Curso("Medicina", "MED", Area.BIOLOGICAS));
                cursoDAO.create(new Curso("Direito", "DIR", Area.HUMANAS));
                cursoDAO.create(new Curso("Administração", "ADM", Area.HUMANAS));
                cursoDAO.create(new Curso("Ciência da Computação", "CC", Area.EXATAS));
                cursoDAO.create(new Curso("Arquitetura", "ARQ", Area.ARTES));
                cursoDAO.create(new Curso("Psicologia", "PSI", Area.HUMANAS));
                cursoDAO.create(new Curso("Enfermagem", "ENF", Area.BIOLOGICAS));
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Sistema de Gerenciamento de Alunos");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
