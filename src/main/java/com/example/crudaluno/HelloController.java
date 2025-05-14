package com.example.crudaluno;

import br.com.puc.dao.AlunoDAO;
import br.com.puc.model.Aluno;
import br.com.puc.model.Cursos;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtIdade;
    @FXML
    private ComboBox<Cursos> cbCurso;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnLimpar;
    @FXML
    private Button btnPesquisar;
    private AlunoDAO alunoDAO;
    private int idAtual = 0;
    private boolean editando = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alunoDAO = new AlunoDAO();
        cbCurso.setItems(FXCollections.observableArrayList(Cursos.values()));
        if (btnExcluir != null) {
            btnExcluir.setDisable(false);
        } else {
            System.out.println("AVISO: btnExcluir não foi injetado corretamente.");
        }

    }

    public void carregarAluno(Aluno aluno) {
        if (aluno != null) {
            idAtual = aluno.getId();
            txtNome.setText(aluno.getNome());
            txtIdade.setText(String.valueOf(aluno.getIdade()));
            cbCurso.setValue(aluno.getCurso());
            editando = true;
            btnSalvar.setText("Atualizar");
        }
    }

    @FXML
    private void handleSalvar(ActionEvent event) {
        if (validarCampos()) {
            String nome = txtNome.getText();
            int idade = Integer.parseInt(txtIdade.getText());
            Cursos curso = cbCurso.getValue();
            Aluno aluno = new Aluno(nome, idade, curso);

            if (editando && idAtual > 0) {
                aluno.setId(idAtual);
                alunoDAO.update(aluno);
                mostrarAlerta("Sucesso", "Aluno atualizado com sucesso!", Alert.AlertType.INFORMATION);
            } else {
                alunoDAO.create(aluno);
                List<Aluno> alunos = alunoDAO.findByNome(nome);
                if (!alunos.isEmpty()) {
                    int novoId = alunos.get(0).getId();
                    mostrarAlerta("Sucesso", "Aluno cadastrado com sucesso!\nID gerado: " + novoId, Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Sucesso", "Aluno cadastrado com sucesso!", Alert.AlertType.INFORMATION);
                }
            }
            limparCampos();
        }
    }

    @FXML
    private void handleExcluir(ActionEvent event) {
        if (idAtual > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Excluir Aluno");
            alert.setContentText("Tem certeza que deseja excluir este aluno?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                alunoDAO.delete(idAtual);
                mostrarAlerta("Sucesso", "Aluno excluído com sucesso!", Alert.AlertType.INFORMATION);
                limparCampos();
            }
        }
    }

    @FXML
    private void handleLimpar(ActionEvent event) {
        limparCampos();
    }

    private void limparCampos() {
        idAtual = 0;
        txtNome.clear();
        txtIdade.clear();
        cbCurso.setValue(null);
        editando = false;
        btnSalvar.setText("Salvar");
    }

    @FXML
    private void handleAbrirPesquisa() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pesquisaview.fxml"));
            Parent root = loader.load();
            PesquisaController pesquisaController = loader.getController();
            pesquisaController.setMainController(this);
            Stage stage = new Stage();
            stage.setTitle("Pesquisa de Alunos");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível abrir a tela de pesquisa: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        String nome = txtNome.getText().trim();
        String idade = txtIdade.getText().trim();
        Cursos curso = cbCurso.getValue();
        StringBuilder erros = new StringBuilder();
        if (nome.isEmpty()) {
            erros.append("- Nome é obrigatório\n");
        }
        if (idade.isEmpty()) {
            erros.append("- Idade é obrigatória\n");
        } else {
            try {
                int idadeInt = Integer.parseInt(idade);
                if (idadeInt <= 0) {
                    erros.append("- Idade deve ser maior que zero\n");
                }
            } catch (NumberFormatException e) {
                erros.append("- Idade deve ser um número válido\n");
            }
        }
        if (curso == null) {
            erros.append("- Curso é obrigatório\n");
        }
        if (erros.length() > 0) {
            mostrarAlerta("Erro de Validação", erros.toString(), Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}