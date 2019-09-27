/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rff.dao;

import br.com.rff.contracts.ICriteria;
import br.com.rff.contracts.IFilter;
import br.com.rff.contracts.ISqlInstruction;
import br.com.rff.contracts.ISqlUpdate;
import br.com.rff.contracts.ISqlInsert;
import br.com.rff.model.Estado;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ngnic
 */
public class EstadoDao extends AbstractDao{

    public EstadoDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    protected String getTableName() {
        return "estado";
    }

    @Override
    public ArrayList<Object> getByCriterios(ICriteria c) {
        ISqlInstruction sql = this.newInstruction(ISqlInstruction.QueryType.SELECT);
        sql.setCriterio(c);
        try{
            ArrayList<HashMap<String, Object>> dados = this.executeSql(sql);
            if (!dados.isEmpty()){
                ArrayList<Estado> ests = new ArrayList<Estado>();
                for (HashMap<String, Object> row: dados){
                    Estado est = new Estado();
                    est.setId((long)row.get("id"));
                    est.setNome((String)row.get("nome"));
                    est.setUf((String)row.get("uf"));
                    ests.add(est);
                }
                return (ArrayList) ests;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void salvar(Object o) {
        Estado estado = (Estado) o;
        ISqlInstruction sql = this.newInstruction(ISqlInstruction.QueryType.INSERT);
        if (estado.getId()>0){
            sql = this.newInstruction(ISqlInstruction.QueryType.UPDATE);
        } 
        
        ((ISqlUpdate) sql).addRowData("nome", estado.getNome());
        ((ISqlUpdate) sql).addRowData("uf", estado.getUf());
        
        if (sql instanceof ISqlUpdate){
            ICriteria criterio = new ICriteria();
            criterio.addExpressions(new IFilter("id", "=", Long.toString(estado.getId())));
            sql.setCriterio(criterio);
        } else if (sql instanceof ISqlInsert){
            ((ISqlInsert) sql).getRowData().put("id", null);
        }
        try {
            Object ret = this.executeSql(sql);
            if (sql instanceof ISqlInsert && ret instanceof Long){
                estado.setId((Long)ret);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void remover(Object o) {
        Estado estado = (Estado) o;
        if (estado.getId()>0){
            ISqlInstruction del = this.newInstruction(ISqlInstruction.QueryType.DELETE);
            ICriteria criterio = new ICriteria();
            criterio.addExpressions(new IFilter("id", "=", String.valueOf(estado.getId())));
            del.setCriterio(criterio);
            try {
                executeSql(del);
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Object getById(long id) {
        ICriteria criterio = new ICriteria();
        criterio.addExpressions(new IFilter("id", "=", String.valueOf(id)));
        return this.getByCriterios(criterio);
    }
    
}
