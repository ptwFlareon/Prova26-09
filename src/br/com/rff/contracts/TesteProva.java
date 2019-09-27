/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rff.contracts;

/**
 *
 * @author ngnic
 */
public class TesteProva {

    public static void main(String[] args){
      //EXERCICIO 4
       ISqlSelect selectTeste = new ISqlSelect("produto");
      selectTeste.getCols().add("nome");
      selectTeste.getCols().add("valor");
      
      ICriteria crit = new ICriteria();
      crit.addExpressions(new IFilter("situacao","=","ATIVA"));
      
      crit.getProperties().put("top", 3);
      crit.getProperties().put("order", "valor desc");
      
      selectTeste.setCriterio(crit);
      
     System.out.println(selectTeste.getSql());
     
     
     
     
     
     
     //EXERCICIO 5
       ISqlUpdate selectTeste1 = new ISqlUpdate("usuario");
      selectTeste1.getRowData().get("nome");
      
      ICriteria crit1 = new ICriteria();
      crit1.addExpressions(new IFilter("nome","like","JOAO"));
      
      selectTeste1.addRowData("nome", "Pedro");
  
      selectTeste1.setCriterio(crit);
      
     System.out.println(selectTeste1.getSql());
     
     
    }
        
}
