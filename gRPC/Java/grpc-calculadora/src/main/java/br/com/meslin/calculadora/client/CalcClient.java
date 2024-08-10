package br.com.meslin.calculadora.client;

import br.com.meslin.CalcServiceGrpc;
import br.com.meslin.Conta;
import br.com.meslin.ContaMultipla;
import br.com.meslin.Resultado;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 5003)
                .usePlaintext()
                .build();

        CalcServiceGrpc.CalcServiceBlockingStub stub = CalcServiceGrpc.newBlockingStub(channel);

        int operando1 = 87;
        int operando2 = 52;

        System.out.println("Resultado único");
        Conta conta = Conta.newBuilder()
                .setOperacao("+")
                .setOperando1(operando1)
                .setOperando2(operando2)
                .build();
        Resultado resultado = stub.opera(conta);
        System.out.println(operando1 + " + " + operando2 + " = " + resultado.getResultado());

        System.out.println("Resultado múltiplo");
        ContaMultipla contaMultipla = ContaMultipla.newBuilder()
                .setOperando1(operando1)
                .setOperando2(operando2)
                .build();
        stub.operaVariasVezes(contaMultipla).forEachRemaining(resultadoMultiplo -> {
            System.out.println(operando1 + " " + resultadoMultiplo.getOperacao() + " " + operando2 + " = " + resultadoMultiplo.getResultado());
        });
    }
}
