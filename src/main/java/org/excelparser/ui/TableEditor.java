package org.excelparser.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import org.excelparser.parser.expression.Expression;
import org.excelparser.parser.formula.FormulaLexer;
import org.excelparser.parser.formula.FormulaParser;
import org.excelparser.parser.token.Token;

public class TableEditor extends JFrame {
    private JTable table;

    public TableEditor() {
        setTitle("Formula Table Editor");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initTable();
    }

    private void initTable() {
        String[] columnHeaders = new String[27];
        columnHeaders[0] = "";
        for (int i = 1; i < 27; i++) {
            columnHeaders[i] = String.valueOf((char) ('A' + i - 1));
        }

        Object[][] rowData = new Object[100][27];
        for (int i = 0; i < 100; i++) {
            rowData[i][0] = i + 1;
            for (int j = 1; j < 27; j++) {
                rowData[i][j] = "";
            }
        }

        DefaultTableModel model = new DefaultTableModel(rowData, columnHeaders) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };


        table = new JTable(model);
        table.putClientProperty("JTable.autoStartsEdit", Boolean.FALSE);
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (table.isEditing() && table.getEditorComponent() != null) {
                    Component editor = table.getEditorComponent();
                    if (editor instanceof JTextComponent) {
                        JTextComponent textComponent = (JTextComponent) editor;
                        textComponent.setCaretPosition(textComponent.getDocument().getLength());
                    }
                }
            }
        });

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int row = table.getSelectedRow();
                    int column = table.getSelectedColumn();
                    String input = table.getValueAt(row, column).toString();
                    if (!input.isEmpty()) {
                        processInput(row, column, input);
                    }
                }
            }
        });

        table.setRowHeight(20);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setMinWidth(100);
        }

        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(80);
        }


        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void processInput(int row, int column, String input) {
        try {
            FormulaLexer lexer = new FormulaLexer(input);
            List<Token> tokens = lexer.tokenize();
            FormulaParser parser = new FormulaParser(tokens, table);
            Expression result = parser.parse();
            table.setValueAt(result.evaluate(), row, column);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error processing formula: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
