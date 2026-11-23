package TP_05;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * ADS 471 - LPR2
 * FERNANDA CRISTINA OLIVEIRA PINHEIRO / LETÍCIA AMARAL XAVIER
 * 1. Elabore um programa em Java com as seguintes características...
 */
public class JanelaPrincipal extends JFrame {

    private final JTextField campoMaximo;
    private final JComboBox<Integer> prioridade1;
    private final JComboBox<Integer> prioridade2;
    private final JButton botaoIniciar;
    private final JButton botaoLimpar;

    private final PainelContagem painel1;
    private final PainelContagem painel2;

    public JanelaPrincipal() {
        super("Trabalho Prático 5");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        campoMaximo = new JTextField(10);

        Integer[] prioridades = new Integer[10];
        for (int i = 0; i < prioridades.length; i++) {
            prioridades[i] = i + 1;
        }

        prioridade1 = new JComboBox<>(prioridades);
        prioridade2 = new JComboBox<>(prioridades);

        botaoIniciar = new JButton("Iniciar");
        botaoLimpar = new JButton("Limpar");

        painel1 = new PainelContagem("Thread 1");
        painel2 = new PainelContagem("Thread 2");

        montarTela();

        botaoIniciar.addActionListener(e -> iniciarContagens());
        botaoLimpar.addActionListener(e -> limparTela());
    }

    private void montarTela() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(12, 12));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel painelConfiguracao = new JPanel(new GridLayout(3, 2, 10, 8));
        painelConfiguracao.setBorder(
                BorderFactory.createTitledBorder("Configuração das Threads"));

        painelConfiguracao.add(new JLabel("Valor máximo:"));
        painelConfiguracao.add(campoMaximo);
        painelConfiguracao.add(new JLabel("Prioridade 1 (1 a 10):"));
        painelConfiguracao.add(prioridade1);
        painelConfiguracao.add(new JLabel("Prioridade 2 (1 a 10):"));
        painelConfiguracao.add(prioridade2);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.add(botaoIniciar);

        JPanel painelSuperior = new JPanel(new BorderLayout(5, 8));
        painelSuperior.add(painelConfiguracao, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        JPanel painelListas = new JPanel(new GridLayout(1, 2, 20, 0));
        painelListas.add(painel1);
        painelListas.add(painel2);

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelInferior.add(botaoLimpar);

        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
        painelPrincipal.add(painelListas, BorderLayout.CENTER);
        painelPrincipal.add(painelInferior, BorderLayout.SOUTH);

        setContentPane(painelPrincipal);
    }

    private void iniciarContagens() {
        final int maximo;

        try {
            maximo = Integer.parseInt(campoMaximo.getText().trim());
            if (maximo < 1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Informe um valor máximo maior que zero.",
                        "Valor inválido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "O valor máximo deve ser um número inteiro.",
                    "Valor inválido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int p1 = (Integer) prioridade1.getSelectedItem();
        int p2 = (Integer) prioridade2.getSelectedItem();

        botaoIniciar.setEnabled(false);
        campoMaximo.setEnabled(false);
        prioridade1.setEnabled(false);
        prioridade2.setEnabled(false);

        painel1.iniciar(maximo, p1);
        painel2.iniciar(maximo, p2);
    }

    private void limparTela() {
        if (painel1.estaExecutando() || painel2.estaExecutando()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Aguarde as duas Threads terminarem para limpar.",
                    "Contagem em andamento",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        painel1.limpar();
        painel2.limpar();

        campoMaximo.setText("1000");
        prioridade1.setSelectedItem(1);
        prioridade2.setSelectedItem(10);

        campoMaximo.setEnabled(true);
        prioridade1.setEnabled(true);
        prioridade2.setEnabled(true);
        botaoIniciar.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JanelaPrincipal janela = new JanelaPrincipal();
            janela.setVisible(true);
        });
    }
}
