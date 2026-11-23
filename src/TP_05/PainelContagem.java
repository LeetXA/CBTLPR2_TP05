package TP_05;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * ADS 471 - LPR2
 * FERNANDA CRISTINA OLIVEIRA PINHEIRO / LETÍCIA AMARAL XAVIER
 * 1. Elabore um programa em Java com as seguintes características...
 */

/**
 * Painel responsável por executar uma contagem em uma Thread.
 */
public class PainelContagem extends JPanel implements Runnable {

    private final DefaultListModel<Integer> modelo;
    private final JList<Integer> lista;
    private final JLabel labelFinal;

    private int valorMaximo;
    private int prioridade;
    private volatile boolean executando;
    private Thread thread;

    public PainelContagem(String titulo) {
        super(new BorderLayout(5, 8));

        modelo = new DefaultListModel<>();
        lista = new JList<>(modelo);
        labelFinal = new JLabel("Final", JLabel.CENTER);
        labelFinal.setVisible(false);

        setBorder(BorderFactory.createTitledBorder(titulo));

        add(new JScrollPane(lista), BorderLayout.CENTER);
        add(labelFinal, BorderLayout.SOUTH);
    }

    public void iniciar(int maximo, int prioridadeThread) {
        if (executando) {
            return;
        }

        valorMaximo = maximo;
        prioridade = prioridadeThread;
        executando = true;

        modelo.clear();
        labelFinal.setVisible(false);

        thread = new Thread(this, "Contagem-" + prioridadeThread);
        thread.setPriority(prioridade);
        thread.start();
    }

    @Override
    public void run() {
        for (int numero = 1; numero <= valorMaximo; numero++) {
            final int valorAtual = numero;

            // A atualização dos componentes Swing deve ocorrer na EDT.
            SwingUtilities.invokeLater(() -> {
                modelo.addElement(valorAtual);
                lista.ensureIndexIsVisible(modelo.size() - 1);
            });

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        SwingUtilities.invokeLater(() -> {
            labelFinal.setVisible(true);
            revalidate();
            repaint();
            executando = false;
        });
    }

    public boolean estaExecutando() {
        return executando;
    }

    public void limpar() {
        if (executando) {
            return;
        }

        modelo.clear();
        labelFinal.setVisible(false);
    }
}
