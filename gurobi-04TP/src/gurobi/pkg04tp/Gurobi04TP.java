/*
Um empresa fabrica 2 modelos de cintos de couro. O modelo M1, de melhor qualidade,
requer o dobro do tempo de fabricação em relação ao modelo M2. Se todos os cintos fossem
do modelo M2, a empresa poderia produzir 1.000 unidades por dia. A disponibilidade de couro
permite fabricar 800 cintos de ambos os modelos por dia. Os cintos empregam fivelas
diferentes, cuja disponibilidade diária é de 400 para M1 e 700 para M2. Os lucros unitários são
de $ 4,00 para M1 e $ 3,00 para M2. Qual o programa ótimo de produção que maximiza o
lucro total diário da empresa? Construa o modelo do sistema descrito.
 */
package gurobi.pkg04tp;

import gurobi.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author CITec
 */
public class Gurobi04TP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
         //x->quantidade de M1
         //y->quantidade de M2
         
        try{
            //ambiente
            GRBEnv env = new GRBEnv();
            //modelo
            GRBModel modelo = new GRBModel(env);
            
            //variaveis
            GRBVar x = modelo.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "x");
            GRBVar y = modelo.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "y");
            
            //funcao objetiva
            GRBLinExpr funcao = new GRBLinExpr();
            funcao.addTerm(4, x);
            funcao.addTerm(3, y);
            modelo.setObjective(funcao, GRB.MAXIMIZE);
            
            //restricoes
            funcao = new GRBLinExpr();
            funcao.addTerm(1, x);
            funcao.addTerm(1, y);
            modelo.addConstr(funcao, GRB.LESS_EQUAL, 800, "restriçãoCouro");
            
            funcao = new GRBLinExpr();
            funcao.addTerm(1, x);
            modelo.addConstr(funcao, GRB.LESS_EQUAL, 400, "restricaoFivelaM1");
            
            funcao = new GRBLinExpr();
            funcao.addTerm(1, y);
            modelo.addConstr(funcao, GRB.LESS_EQUAL,700, "restricaoFivelaM2");
            
            funcao = new GRBLinExpr();
            funcao.addTerm(1, y);
            modelo.addConstr(funcao, GRB.LESS_EQUAL,1000, "restricaoTempoM2");
            
            funcao = new GRBLinExpr();
            funcao.addTerm(1, x);
            modelo.addConstr(funcao, GRB.LESS_EQUAL,500, "restricaoTempoM1");
            
            //otimizar
            modelo.optimize();
            
            System.out.println("Solução Otima");
            System.out.println("Quantidade modelo M1: " + x.get(GRB.StringAttr.VarName)+
              " = " + x.get(GRB.DoubleAttr.X));
            System.out.println("Quantidade modelo M2: " + y.get(GRB.StringAttr.VarName)+
              " = " + y.get(GRB.DoubleAttr.X));
            
            System.out.println("Maximizar o lucro");
            System.out.println("Objetivo: "+ modelo.get(GRB.DoubleAttr.ObjVal));
                    
            
        } catch (GRBException ex) {
            Logger.getLogger(Gurobi04TP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
