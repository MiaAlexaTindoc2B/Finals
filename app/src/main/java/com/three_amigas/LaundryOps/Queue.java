package com.three_amigas.LaundryOps;

import com.three_amigas.LaundryOps.Models.Customer;
import com.three_amigas.LaundryOps.Models.SQLquery;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Queue {
    private final DefaultTableModel model;
    private final DefaultTableModel model1;
    private final CRUD crud;
    private final ExecutorService emailExecutor;

    public Queue(DefaultTableModel model, DefaultTableModel model1) {
        this.model = model;
        this.model1 = model1;
        this.crud = new CRUD();
        this.emailExecutor = Executors.newFixedThreadPool(5);
        loadExistingData();
    }

    private void loadExistingData() {
        List<SQLquery> data = this.crud.read();
        for (SQLquery sql : data) {
            if (!sql.done) {
                this.model.insertRow(0, new Object[]{sql.id, sql.name, sql.number, sql.email, sql.date});
            } else {
                this.model1.insertRow(0, new Object[]{sql.id, sql.name, sql.number, sql.email, sql.date});
            }
        }
    }

    public void addRowToQueue(Customer customer) {
        int newId = this.crud.getLastInsertedId() + 1;

        SQLquery sql = new SQLquery(newId, customer.name, customer.number, customer.email, customer.date, false, false);
        if (this.crud.create(sql)) {
            this.model.addRow(new Object[]{newId, customer.name, customer.number, customer.email, customer.date});
        } else {
            System.err.println("Failed to add row to the database.");
        }
    }

    public void addRowToPriorityQueue(Customer customer) {
        int isLast = this.crud.getLastInsertedId() + 1;
        Object[] newRow = {isLast, customer.name, customer.number, customer.email, customer.date};

        SQLquery newSql = new SQLquery(isLast, customer.name, customer.number, customer.email, customer.date, false, false);
        if (this.crud.create(newSql)) {
            this.model.insertRow(0, newRow);
        } else {
            System.err.println("Failed to add row to the database.");
        }
    }

    public void removeFirstRowFromQueue() {
        if (this.model.getRowCount() > 0) {
            int rowIndex = 0;
            
            SQLquery sql = new SQLquery((int) model.getValueAt(rowIndex, 0), 
                                         (String) this.model.getValueAt(rowIndex, 1), 
                                         (String) this.model.getValueAt(rowIndex, 2), 
                                         (String) this.model.getValueAt(rowIndex, 3), 
                                         (String) this.model.getValueAt(rowIndex, 4), 
                                         true, true);
            
            this.model1.insertRow(0, new Object[] { 
                model.getValueAt(rowIndex, 0), 
                model.getValueAt(rowIndex, 1), 
                model.getValueAt(rowIndex, 2), 
                model.getValueAt(rowIndex, 3), 
                model.getValueAt(rowIndex, 4) 
            });
            
            
            emailExecutor.submit(() -> {
                SMTPClient smtp = new SMTPClient();
                smtp.sendMail((String) model.getValueAt(rowIndex, 3), (String) model.getValueAt(rowIndex, 1));
            });
            
            if(this.crud.update(sql)) {
                this.model.removeRow(0);
            } else {
                System.err.println("Failed to remove row from the database.");
            }
        }
    }
    
    public void shutdownExecutor() {
        emailExecutor.shutdown();
    }
}