package br.com.meslin.calculator.client;

import java.rmi.Naming;

import br.com.meslin.calculator.shared.Calculator;

public class Client {
    public static void main(String[] args) {
        try {
            Calculator calculadora = (Calculator) Naming.lookup("rmi://172.17.0.2/Calculator");

            double a = 12.0;
            double b = 8.0;

            System.out.println(a + " + " + b + " = " + calculadora.adicionar(a, b));
            System.out.println(a + " - " + b + " = " + calculadora.subtrair(a, b));
            System.out.println(a + " x " + b + " = " + calculadora.multiplicar(a, b));
            System.out.println(a + " / " + b + " = " + calculadora.dividir(a, b));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
