package com.example.crudaluno;

import br.com.puc.dao.AlunoDAO;
import br.com.puc.model.Aluno;
import br.com.puc.model.Cursos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PesquisaController implements Initializable {
    @FXML
    private TextField txtPesquisa;
    @FXML
    private TableView<Aluno> tablePesquisa;
    @FXML
    private TableColumn<Aluno, Integer> colId;
    @FXML
    private TableColumn<Aluno, String> colNome;
    @FXML
    private TableColumn<Aluno, Integer> colIdade;
    @FXML
    private TableColumn<Aluno, Cursos> colCurso;
    @FXML
    private Button btnExcluir;

    private AlunoDAO alunoDAO;
    private ObservableList<Aluno> alunosList;
    private HelloController mainController;
    private PesquisaController pesquisaController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.alunoDAO = new AlunoDAO();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        btnExcluir.setDisable(false);
        carregarTodosAlunos();
        if (btnExcluir != null) {
            btnExcluir.setDisable(false);
        } else {
            System.out.println("AVISO: btnExcluir não foi injetado corretamente.");
        }
        carregarTodosAlunos();
    }

    public void setMainController(HelloController controller) {
        this.mainController = controller;
    }

    private void carregarTodosAlunos() {
        try {
            if (alunoDAO == null) {
                alunoDAO = new AlunoDAO();
                System.out.println("AlunoDAO foi inicializado no método carregarTodosAlunos()");
            }
            List<Aluno> alunos = alunoDAO.findAll();
            System.out.println("Carregando alunos: " + alunos.size() + " encontrados.");
            alunosList = FXCollections.observableArrayList(alunos);
            tablePesquisa.setItems(alunosList);
        } catch (Exception e) {
            System.out.println("Erro ao carregar alunos: " + e.getMessage());
            e.printStackTrace();
            alunosList = FXCollections.observableArrayList();
            tablePesquisa.setItems(alunosList);
        }
    }


    @FXML
    private void handlePesquisar(ActionEvent event) {
        String termo = txtPesquisa.getText().trim();
        try {
            List<Aluno> alunos;
            if (!termo.isEmpty()) {
                alunos = alunoDAO.findByNome(termo);
                System.out.println("Pesquisando por: " + termo + " - Encontrados: " + alunos.size());
            } else {
                alunos = alunoDAO.findAll();
                System.out.println("Carregando todos - Encontrados: " + alunos.size());
            }
            alunosList = FXCollections.observableArrayList(alunos);
            tablePesquisa.setItems(alunosList);
            tablePesquisa.refresh();
        } catch (Exception e) {
            System.out.println("Erro na pesquisa: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setPesquisaController(PesquisaController controller) {
        this.pesquisaController = controller;
    }


    @FXML
    private void handleEditar(ActionEvent event) {
        Aluno alunoSelecionado = tablePesquisa.getSelectionModel().getSelectedItem();
        if (alunoSelecionado != null) {
            if (mainController != null) {
                mainController.carregarAluno(alunoSelecionado);
                fecharJanela();
            } else {
                mostrarAlerta("Erro", "Não foi possível editar o aluno selecionado.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Aviso", "Selecione um aluno para editar.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleExcluir(ActionEvent event) {
        Aluno alunoSelecionado = tablePesquisa.getSelectionModel().getSelectedItem();
        if (alunoSelecionado != null) {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmar Exclusão");
            confirmacao.setHeaderText(null);
            confirmacao.setContentText("Tem certeza que deseja excluir o aluno " + alunoSelecionado.getNome() + "?");
            Optional<ButtonType> resultado = confirmacao.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                boolean sucesso = alunoDAO.delete(alunoSelecionado.getId());
                if (sucesso) {
                    mostrarAlerta("Sucesso", "Aluno excluído com sucesso!", Alert.AlertType.INFORMATION);
                    carregarTodosAlunos();
                } else {
                    mostrarAlerta("Erro", "Não foi possível excluir o aluno.", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarAlerta("Aviso", "Selecione um aluno para excluir.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleFechar(ActionEvent event) {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) txtPesquisa.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void atualizarListaAlunos() {
        carregarTodosAlunos();
    }
}