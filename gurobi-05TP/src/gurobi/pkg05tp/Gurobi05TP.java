/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gurobi.pkg05tp;



import gurobi.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author CITec
 */
public class Gurobi05TP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int prod=2;
        int recursos=3;
            
        try {
            //ambiente
           GRBEnv env = new GRBEnv();
         
            //modelo
            GRBModel modelo = new GRBModel(env);
            
            //variaveis
            GRBVar x =  modelo.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "x");
                         
       
            GRBVar y = modelo.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "y");
            
            //funcao objetiva
            GRBLinExpr funcao = new GRBLinExpr();
           
         
            funcao.addTerm(120.0, x);
            funcao.addTerm(150.0, y);
            modelo.setObjective(funcao, GRB.MAXIMIZE);
            
            //restricoes
            funcao = new GRBLinExpr();
            funcao.addTerm(2.0, x);
            funcao.addTerm(4.0, y);
            modelo.addConstr(funcao, GRB.LESS_EQUAL, 100.0, "restriçãoRecursoR1");
            
            funcao = new GRBLinExpr();
            funcao.addTerm(3.0, x);
            funcao.addTerm(2.0, y);
            modelo.addConstr(funcao, GRB.LESS_EQUAL, 90.0, "restricaoRecursoR2");
            
            funcao = new GRBLinExpr();
            funcao.addTerm(5.0, x);
            funcao.addTerm(3.0, y);
            modelo.addConstr(funcao, GRB.LESS_EQUAL,120.0, "restricaoRecursoR3");
           
            
            //otimizar
            modelo.optimize();
            
            System.out.println("Solução Otima");
            System.out.println("Quantidade produto P1: " + x.get(GRB.StringAttr.VarName)+
              " = " + x.get(GRB.DoubleAttr.X));
            System.out.println("Quantidade produto P2: " + y.get(GRB.StringAttr.VarName)+
              " = " + y.get(GRB.DoubleAttr.X));
            
            System.out.println("Maximizar o lucro");
            System.out.println("Objetivo: "+ modelo.get(GRB.DoubleAttr.ObjVal));
        }            
            catch (GRBException ex) {

                Logger.getLogger(Gurobi05TP.class.getName()).log(Level.SEVERE, null, ex);
        }
        } 
    }
    
