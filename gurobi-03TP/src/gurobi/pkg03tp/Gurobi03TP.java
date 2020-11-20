/*
Uma rede de televisão local tem o seguinte problema: foi descoberto que o programa “A"
com 20 minutos de música e 1 minuto de propaganda chama a atenção de 30.000
telespectadores, enquanto o programa "B", com 10 minutos de música e 1 minuto de
propaganda chama a atenção de 10.000 telespectadores. No decorrer de uma semana, o
patrocinador insiste no uso de no mínimo, 5 minutos para sua propaganda e que não há verba
para mais de 80 minutos de música. Quantas vezes por semana cada programa deve ser levado
ao ar para obter o número máximo de telespectadores? Construa o modelo do sistema.
 */
package gurobi.pkg03tp;

import gurobi.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author CITec
 */
public class Gurobi03TP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //variaveis de decisao
        // x->quantidade do programa A
        // y->quantidade do programa B
        
        try { 
            //ambiente
            GRBEnv env = new GRBEnv();
            
            //modelo
            GRBModel modelo = new GRBModel(env);
            
            //variaveis de decisao
            GRBVar x = modelo.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "x");
            GRBVar y = modelo.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "y");
            
            //funcao objetivo
            GRBLinExpr funcao = new GRBLinExpr();
            funcao.addTerm(30000, x);
            funcao.addTerm(10000, y);
            modelo.setObjective(funcao, GRB.MAXIMIZE);
            
            funcao = new GRBLinExpr();
            funcao.addTerm(20, x);
            funcao.addTerm(10, y);
            modelo.addConstr(funcao, GRB.LESS_EQUAL, 80, "restricaoMusica");
            //funcao restricao
            funcao = new GRBLinExpr();
            funcao.addTerm(1, x);
            funcao.addTerm(1, y);
            modelo.addConstr(funcao, GRB.GREATER_EQUAL, 5, "restricaoPropaganda");
            
            
            
            //otimizar
            modelo.optimize();
            
            System.out.println("Solução Otima");
            System.out.println("Quantidade programa A: " + x.get(GRB.StringAttr.VarName)+
              " = " + x.get(GRB.DoubleAttr.X));
            System.out.println("Quantidade programa B: " + y.get(GRB.StringAttr.VarName)+
              " = " + y.get(GRB.DoubleAttr.X));
            
            System.out.println("Maximizar o numero de telespectadores");
            System.out.println("Objetivo: "+ modelo.get(GRB.DoubleAttr.ObjVal));
        } catch (GRBException ex) {
            Logger.getLogger(Gurobi03TP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
