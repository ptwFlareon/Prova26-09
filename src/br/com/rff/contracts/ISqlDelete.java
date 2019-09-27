package br.com.rff.contracts;

import java.util.HashMap;

public class ISqlDelete extends ISqlInstruction{
    
    private HashMap<String,String> rowData;

    public ISqlDelete(String nomeDaTabela) {
        super(nomeDaTabela);
        this.rowData = new HashMap<>();
    }

    @Override
    public String getSql() {
        this.sql.append("delete from ");
        this.sql.append(this.tabName);
        this.sql.append(" where ");
        this.sql.append(this.criterio.dump());
        return this.sql.toString().replace("'NULL'", "'NULL'");
    }

    public HashMap<String, String> getRowData() {
        return rowData;
    }

    public void setRowData(HashMap<String, String> rowData) {
        this.rowData = rowData;
    }
    
    
    
}
