package org.excelparser;

import javax.swing.SwingUtilities;

import org.excelparser.ui.TableEditor;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TableEditor().setVisible(true));
    }
}
