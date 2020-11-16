/*
Certa empresa fabrica 2 produtos P1 e P2. O lucro por unidade de P1 é de 100 u.m. e o lucro
unitário de P2 é de 150 u.m. A empresa necessita de 2 horas para fabricar uma unidade de P1
e 3 horas para fabricar uma unidade de P2. O tempo mensal disponível para essas atividades é
de 120 horas. As demandas esperadas para os 2 produtos levaram a empresa a decidir que os
montantes produzidos de P1 e P2 não devem ultrapassar 40 unidades de P1 e 30 unidades de
P2 por mês. Construa o modelo do sistema de produção mensal com o objetivo de maximizar o
lucro da empresa.
 */
package gurobi.pkg02tp;

import gurobi.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author CITec
 */
public class Gurobi02TP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
            // TODO code application logic here
            //variaveis de decisao
            // x - > quantidade de P1 produzida por mes
            // y - > quantidade de P2 produzida por mes
        try {
            //ambiente
            GRBEnv env = new GRBEnv();
            
            //modelo
            GRBModel modelo = new GRBModel(env);
            
            //variaveis de decisão
            GRBVar x = modelo.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "x");
            GRBVar y = modelo.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "y");
            
            //funcao obejiva
            GRBLinExpr funcao = new GRBLinExpr();
            
            //adiciona os termos da funcao
            funcao.addTerm(100.0, x);
            funcao.addTerm(150.0, y);
            
            //objetivo
            modelo.setObjective(funcao, GRB.MAXIMIZE);
            
            //restrições
            funcao = new GRBLinExpr();
            funcao.addTerm(2.0, x);
            funcao.addTerm(3.0, y);
            modelo.addConstr(funcao, GRB.LESS_EQUAL, 120, "restricaoHora");
            
            funcao = new GRBLinExpr();
            funcao.addTerm(1.0, x);
            modelo.addConstr(funcao, GRB.LESS_EQUAL,40.0 , "restricaoProducaoP1");
            
            funcao = new GRBLinExpr();
            funcao.addTerm(1.0, y);
            modelo.addConstr(funcao, GRB.LESS_EQUAL, 30, "restricaoProducaoP2");
            
            //otimizar
            modelo.optimize();
            
            //resultado
            System.out.println("Solucão Otima");
            System.out.println("Quantidade produzida de P1: " + x.get(GRB.StringAttr.VarName) + 
            " = " + x.get(GRB.DoubleAttr.X));
            System.out.println("Quantidade produzida de P2: "+ y.get(GRB.StringAttr.VarName) +
            " = " + y.get(GRB.DoubleAttr.X));
            
            System.out.println("Maximizar o lucro por mes");
            System.out.println("Objetivo: " + modelo.get(GRB.DoubleAttr.ObjVal));
            
            modelo.dispose();
            funcao.clear();
        } catch (GRBException ex) {
            Logger.getLogger(Gurobi02TP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
